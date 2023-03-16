package com.dart.dongyeol.toDB;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class KrxDbTwo {

	public static void main(String[] args) throws Exception {
		ConnectionMySQL conDb = new ConnectionMySQL();
		List<String> price = new ArrayList<>();
		TreeMap<String, Integer> kospi = conDb.getColumn("market_kospi");
		
//		for (String k : kospi.keySet()) {
//			System.out.print(k+":");
//			System.out.print(kospi.get(k));
//			System.out.println();
//		}
		
			
		
		
		String baseUrl = "http://data-dbg.krx.co.kr/svc/apis/sto/stk_bydd_trd.json?basDd=20230306";
		//String requstUrl=baseUrl.replace("day", day);
		
		
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
		
		int count=0;
		for (int i = 0; i < marketData.size(); i++) {
			JSONObject corp = (JSONObject) marketData.get(i);
			String stockCode = (String) corp.get("ISU_CD");
			int stockPrice = Integer.parseInt((String) corp.get("TDD_CLSPRC"));
			
			if(kospi.containsKey(stockCode)) {
				kospi.put(stockCode, stockPrice);
				count++;
			}
				
		
		}
		
		for (String k : kospi.keySet()) {
			System.out.print(k+":");
			System.out.print(kospi.get(k));
			System.out.println();
		}
		
		
		
		System.out.println(count);
		
		
		

		
		
		
	}

}