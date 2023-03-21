package com.dart.dongyeol_v2DB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DartDbMaker {
	
	public static void main(String[] args) throws Exception{
		
		List<CorpVO> corpVoList = generateCorpVoList();
		
		List<CorpVO> corpVoList_Kospi_kosdaq =corpVoList.stream().filter(t ->{
			return (t.getCorp_cls().equals("Y") || t.getCorp_cls().equals("K")) && t.getAcc_mt().equals("12");
		}).toList();
		
		System.out.println(corpVoList_Kospi_kosdaq.stream().count());
		ConnectionMySQL connTodb = new ConnectionMySQL();
		
		
		corpVoList_Kospi_kosdaq.stream().forEach(t -> {
			String[] years = new String [] {"2015","2016","2017","2018","2019","2020","2021"};
			for(String year:years) {
				String response = null;
				String baseurl = 
				"https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json?crtfc_key=79d11269739011dcb7291e0238c06aaf57ac855a&corp_code=" + t.getCorp_code()
				+"&bsns_year="+ year + "&reprt_code=11011&fs_div=OFS";
				
				try {
					URL url = new URL(baseurl);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
					StringBuffer sb = new StringBuffer();
					String inputline;
					while((inputline=bf.readLine())!=null) sb.append(inputline);
					bf.close();
					
					response = sb.toString();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				connTodb.insert(t.getCorp_code(), year, "OFS", response);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		});
		
	
		
	}



	public static List<CorpVO> generateCorpVoList() {
		
		List<String> corpJsons = importCorpJson();
		
		List<CorpVO> corpVO=corpJsons.stream()
		.map(s->{
			JSONParser parser = new JSONParser();
			JSONObject corpIntroduction = null;
			try {
				corpIntroduction = (JSONObject) parser.parse(s);
			} catch (ParseException e) {
				System.out.println("pasrsing을 하는데 문제가 발생했습니다.");
				e.printStackTrace();
			}
			
			String corp_code = corpIntroduction.get("corp_code").toString();
			String stock_name = corpIntroduction.get("stock_name").toString();
			String stock_code = corpIntroduction.get("stock_code").toString();
			String corp_cls = corpIntroduction.get("corp_cls").toString();
			String induty_code = corpIntroduction.get("induty_code").toString();
			String acc_mt = corpIntroduction.get("acc_mt").toString();
			
			return new CorpVO(corp_code,stock_name,stock_code,corp_cls,induty_code,acc_mt);
		}).toList();
		
		
		System.out.println(corpVO.size()+"개의 기업객체 생성완료되었습니다.");
		
		
		return corpVO;
	}



	public static List<String> importCorpJson() {
		List<String> corpJsons = new  ArrayList<>();
		try{
			XSSFWorkbook workbook = new XSSFWorkbook("./src/com/dart/dongyeol/toDB/corpJson.xlsx");
			XSSFSheet sheet = workbook.getSheet("기업개황 json");
			
			int rows = sheet.getPhysicalNumberOfRows();
			
			for(int row=0;row<rows;row++) {
				XSSFRow realRow = sheet.getRow(row);
				String json = realRow.getCell(0).toString();
				corpJsons.add(json);
				
			}
			workbook.close();
			
		} catch (Exception e) {
			System.out.println("excel을 읽는데 문제가 발생했습니다.");
			e.printStackTrace();
		}
		
		
		return corpJsons;
		
	}

	
	
	
	
	

}
