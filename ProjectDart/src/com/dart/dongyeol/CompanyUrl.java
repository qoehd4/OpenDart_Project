package com.dart.dongyeol;

import java.util.Scanner;
import java.io.FileInputStream;
import java.net.URL;
import java.net.HttpURLConnection;


import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;


public class CompanyUrl {
	
	private String url = null;
	private HttpURLConnection connection = null;

	
	public HttpURLConnection getConnection() {
		return connection;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	

	public CompanyUrl()  {
		super();
		try {
			
			System.out.println("url 잘못입력시 setUrl 사용");
			buildUrl();
			URL url = new URL(this.url);
			this.connection = (HttpURLConnection) url.openConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Overload
	public CompanyUrl(String crtfc_key, String corpName , String bsns_year, String fs_div)  {
		super();
		try {
			
			System.out.println("url 잘못입력시 setUrl 사용");
			buildUrl(crtfc_key, corpName, bsns_year, fs_div);
			URL url = new URL(this.url);
			this.connection = (HttpURLConnection) url.openConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//*****




	public void buildUrl() {
		
		String url = null;
		String baseUrl = 
		"https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json?crtfc_key=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx&corp_code=name&bsns_year=date&reprt_code=11011&fs_div=type";
		
		
		String[] parameters = getParameters();
		
		url=baseUrl.replace("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", parameters[0]);
		url=url.replace("name", parameters[1]);
		url=url.replace("date", parameters[2]);
		url=url.replace("type", parameters[3]);
		
		this.url = url;
		
	}
	
	// OVerload
	public void buildUrl(String crtfc_key, String corpName , String bsns_year, String fs_div) {
		
		String url = null;
		String baseUrl = 
		"https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json?crtfc_key=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx&corp_code=name&bsns_year=date&reprt_code=11011&fs_div=type";
		
		
		url=baseUrl.replace("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", crtfc_key);
		url=url.replace("name", findCorpcode(corpName));
		url=url.replace("date", bsns_year);
		url=url.replace("type", fs_div);
		
		this.url = url;
		
	}
	//*******
		
		

	public  String[] getParameters() {
		
		String[] parameters = new String[4];
		
		String crtfc_key = null;
		String bsns_year = null;
		String fs_div = null;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("API KEY:");
		crtfc_key = sc.nextLine();
		parameters[0]=crtfc_key;
		
		System.out.print("Corp Name:");
		parameters[1]= findCorpcode(sc.nextLine());
		
		System.out.print("Bsns_year:");
		bsns_year = sc.nextLine();
		parameters[2]=bsns_year;
		
		System.out.print("Fs_div:");
		fs_div = sc.nextLine();
		parameters[3]=fs_div;
		
		sc.close();
		
		return parameters;
		
		}

	public static String findCorpcode (String corpName) {
		String corp_code = null;
		
		try {
			FileInputStream file = new FileInputStream("./src/com/dart/dongyeol/Cropcode.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			int totalRows = sheet.getPhysicalNumberOfRows();
			
			for(int rowNo=1;rowNo<totalRows;rowNo++) {
				XSSFRow row = sheet.getRow(rowNo);
				
				
				if(row==null) continue;
				else {
					XSSFCell cell = row.getCell(1);
					String value=cell.toString();
					
					if(!value.equals(corpName)) continue;
					else {
						XSSFCell wantedCell = row.getCell(0);
						corp_code = wantedCell.toString();
						} 
				
				}
			}
			
			workbook.close();

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return corp_code;				
		
	}

	
}
	