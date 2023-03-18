package com.dart.dongyeol.toDB;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class KrxDbTwo {

	public static void main(String[] args) throws Exception {
		ConnectionMySQL conDb = new ConnectionMySQL();
		
		TreeMap<String, Integer> kosdaq = conDb.getColumn("market_kosdaq");
		List<String> dayList = makeDaylist();
		
		dayList.stream().forEach(day -> {
			StringBuffer sbQuery = new StringBuffer(day+",");
		
			String baseUrl = "http://data-dbg.krx.co.kr/svc/apis/sto/ksq_bydd_trd.json?basDd="+day ;
			
			
			try {
				URL url = new URL(baseUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("AUTH_KEY", "DD0DFC1FA0E24D1F96A8B50BB69A245520AA9CD0");
				
				BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
				StringBuffer sb = new StringBuffer();
				String inputline;
				
				while((inputline=bf.readLine())!=null) sb.append(inputline);
				
				String response = sb.toString();
				
				bf.close();
				
				JSONParser parser = new JSONParser();
				JSONObject  obj =(JSONObject) parser.parse(response);
				JSONArray marketData = (JSONArray) obj.get("OutBlock_1");
				
				for (int i = 0; i < marketData.size(); i++) {
					JSONObject corp = (JSONObject) marketData.get(i);
					String stockCode = (String) corp.get("ISU_CD");
					int stockPrice = Integer.parseInt((String) corp.get("TDD_CLSPRC"));
					
					if(kosdaq.containsKey(stockCode)) kosdaq.put(stockCode, stockPrice);
					
				}
				
				for (String k : kosdaq.keySet()) sbQuery.append(kosdaq.get(k)).append(",");

				sbQuery.deleteCharAt(sbQuery.length()-1);
				
				String queryInput = sbQuery.toString();
				String sql = "insert into market_kospi values(?)";
				sql= sql.replace("?", queryInput);
				conDb.insertPrice(queryInput);
				Thread.sleep(100);
				

				
			} catch (Exception e) {
				
			}
			

			
			
		});
		
		
		
		
		
		
		
		
		
	}

	public static List<String> makeDaylist(){
		List<String> Daylist = new ArrayList<>();
		
		try(XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("./src/com/dart/dongyeol/toDB/datelist.xlsx"));){
			
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			int rows = sheet.getPhysicalNumberOfRows();
			
			for(int i=1;i<rows;i++) {
				XSSFRow row = sheet.getRow(i);
				if(row == null) continue;
				String day = row.getCell(0).toString();
				day=day.replaceAll("/", "");
				Daylist.add(day);

				
			}
			
		} catch (Exception e) {
			System.out.println("Excel DB를 읽는데 문제가 일어났습니다.");
			e.printStackTrace();
		}
		
		
		
		return Daylist;
	}
}
