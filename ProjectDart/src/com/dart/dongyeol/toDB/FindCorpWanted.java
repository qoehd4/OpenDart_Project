package com.dart.dongyeol.toDB;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//API KEY:182023b0d0bb93439f2cdd9f8f8ed93215fb72dd
public class FindCorpWanted {
	
	public static void main(String[] args) {
		List<String> corpName_corpCode = findCodeName(); //총개수 3542
		System.out.println(corpName_corpCode.get(3540)); //엠에프엠코리아-01384477
		
		System.out.println(corpName_corpCode.stream());
		
//		String base_url = "https://opendart.fss.or.kr/api/company.json?crtfc_key=182023b0d0bb93439f2cdd9f8f8ed93215fb72dd&corp_code=name";
//		int count=0;
//		
//		for(String name_code:corpName_corpCode) {
//			//회사 고유번호를 찾기 위한 코드
//			int beginindex = name_code.indexOf("?") + 1;
//			String corpCode = name_code.substring(beginindex);
//			String requestUrl=base_url.replace("name", corpCode);
//			
//			
//			
//			
//			count++;
//			
//		}
//		
//		System.out.println("------------------");
//		System.out.println(count);
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
