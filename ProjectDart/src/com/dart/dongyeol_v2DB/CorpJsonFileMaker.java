package com.dart.dongyeol_v2DB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CorpJsonFileMaker {
	
	public static void main(String[] args) throws Exception {
		List<String> corpName_corpCode = findCodeName(); //총개수 3542
		System.out.println(corpName_corpCode.get(3540)); //엠에프엠코리아-01384477
		String base_url = "https://opendart.fss.or.kr/api/company.json?crtfc_key=182023b0d0bb93439f2cdd9f8f8ed93215fb72dd&corp_code=name";
		
		List<String> codes = corpName_corpCode.stream().map(s -> s.substring(s.indexOf("?")+1)).toList();
		List<String> requestUrls = codes.stream().map(s->base_url.replace("name", s)).toList();
		
		System.out.println(requestUrls);
		
		List<String> responses = new ArrayList<>();
		
		for(String url:requestUrls) {
			HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
			BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			StringBuffer sb = new  StringBuffer();
			String inputline = "";
			
			while((inputline=bf.readLine()) != null) sb.append(inputline);
			bf.close();
			String response = sb.toString();
			
			responses.add(response);
			Thread.sleep(200);
		}
		
		
		writeExcel((ArrayList<String>) responses);
	
		
		

	}
	
	
	
	public static void writeExcel(ArrayList<String> list) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("기업개황 json");
		
		int rowNo = 0;
		
		for(String corpJson:list) {
			XSSFRow row = sheet.createRow(rowNo);
			row.createCell(0).setCellValue(corpJson);
			rowNo++;
		}
		
		try(FileOutputStream fileOut = new FileOutputStream(new File("./src/com/dart/dongyeol_v2DB/corpJson.xlsx"));) {
			workbook.write(fileOut);
			workbook.close();
		} catch (Exception e) {
			System.out.println("exel 데이터를 저장하는데 문제가 발생했습니다.");
			e.printStackTrace();
		}
		
		
		
		
	}



	public static List<String> findCodeName() {
		List<String> corpName_corpCode = new ArrayList<>();
		
		try(XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("./src/com/dart/dongyeol/CropCode.xlsx"));){
	
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			int rows = sheet.getPhysicalNumberOfRows();
			
			for(int i=1;i<rows;i++) {
				XSSFRow row = sheet.getRow(i);
				if(row == null) continue;
				String code = row.getCell(0).toString();
				String name = row.getCell(1).toString();
				
				corpName_corpCode.add(name+"?"+code);

				
			}
			
		} catch (Exception e) {
			System.out.println("Excel DB를 읽는데 문제가 일어났습니다.");
			e.printStackTrace();
		}

		return corpName_corpCode;
		
		
		
		
	}


}
