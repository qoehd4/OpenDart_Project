package com.dart.dongyeol;

import java.util.Scanner;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;


//182023b0d0bb93439f2cdd9f8f8ed93215fb72dd

public class Practice {

	public static void main(String[] args) {
		/*
		String url = null;
		String baseUrl = 
		"https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json?crtfc_key=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx&corp_code=name&bsns_year=date&reprt_code=11011&fs_div=type";
		
		
		String[] parameters = getParameters();
		
		url=baseUrl.replace("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", parameters[0]);
		url=url.replace("name", parameters[1]);
		url=url.replace("date", parameters[2]);
		url=url.replace("type", parameters[3]);
		
		System.out.println(url);
		*/
		
		findCropcode(null);
		
		
						
	}

	private static String[] getParameters() {
		
		String[] parameters = new String[4];
		
		String crtfc_key = null;
		String bsns_year = null;
		String fs_div = null;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("API KEY:");
		crtfc_key = sc.nextLine();
		parameters[0]=crtfc_key;
		
		System.out.print("Corp Code:");
		//parameters[1]= findCropcode(sc.nextLine());
		
		System.out.print("Bsns_year:");
		bsns_year = sc.nextLine();
		parameters[2]=bsns_year;
		
		System.out.print("Fs_div:");
		fs_div = sc.nextLine();
		parameters[3]=fs_div;
		
		sc.close();
		
		return parameters;
		
		}

	private static void findCropcode (String cropName) {
		
		try {
			String corp_code = null;
			FileInputStream file = new FileInputStream("C:\\Users\\fzaca\\eclipse-workspace\\DartProject\\src\\com\\dart\\dongyeol\\Corpcode.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			int rowNo = 1;
			int cellNo =0;
			int totalRows = sheet.getPhysicalNumberOfRows();
			
			System.out.println(totalRows);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}

	
}
	
	
	
	


