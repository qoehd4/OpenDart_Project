package com.dart.dongyeol;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class FindAccmtCorpcls {
	


	public  HashMap<String, String> findCls_Accmt() throws Exception {
		
		HashMap<String, String> corpName_corpCode = findCodeName();
		HashMap<String, String> corpCls_accMt = new HashMap<>();
		
		for(String key:corpName_corpCode.keySet()) {
				
			String url = "https://opendart.fss.or.kr/api/company.json?crtfc_key=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx&corp_code=name";
			url = url.replace("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx","182023b0d0bb93439f2cdd9f8f8ed93215fb72dd");
			url = url.replace("name",corpName_corpCode.get(key));
			
			URL requesturl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) requesturl.openConnection();
			BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			StringBuffer sb = new  StringBuffer();
			String inputline = "";
			
			while((inputline=bf.readLine()) != null) sb.append(inputline);
			bf.close();
			String response = sb.toString();
			
			JSONParser parser = new JSONParser();
			JSONObject corpIntroduction = (JSONObject) parser.parse(response);
			
			String corp_cls = corpIntroduction.get("corp_cls").toString();
			String acc_mt = corpIntroduction.get("acc_mt").toString();
			
			corpCls_accMt.put(corp_cls, acc_mt);
	
			
		}
			
		return corpCls_accMt;

		
	}


	public  HashMap<String, String> findCodeName() {
		HashMap<String, String> corpName_corpCode = new HashMap<>();
		
		try {	
			FileInputStream file  = new FileInputStream("./src/com/dart/dongyeol/CropCode.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			int rows = sheet.getPhysicalNumberOfRows();
			
			for(int i=1;i<rows;i++) {
				XSSFRow row = sheet.getRow(i);
				if(row == null) continue;
				String code = row.getCell(0).toString();
				String name = row.getCell(1).toString();
				
				corpName_corpCode.put(name, code);
				
			}
			
			file.close();
			workbook.close();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return corpName_corpCode;
		
	}

}
