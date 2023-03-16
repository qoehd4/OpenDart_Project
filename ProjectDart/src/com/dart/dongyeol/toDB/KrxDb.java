package com.dart.dongyeol.toDB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


//DD0DFC1FA0E24D1F96A8B50BB69A245520AA9CD0
public class KrxDb {

	public static void main(String[] args) throws Exception {
		
//		Set<String> survivedKosdaq = new HashSet<>();
//		survivedKosdaq = makeKosdaqList("20230315");
//		System.out.println(survivedKosdaq.size());
//		
//		Set<String> kosdaq2012 = makeKosdaqList("20120315");
//		
//
//		survivedKosdaq.retainAll(kosdaq2012);
//	
//		System.out.println(survivedKosdaq.size());
//		
//		ConnectionMySQL conDb = new ConnectionMySQL();
//		
//		survivedKosdaq.stream().forEach(name -> conDb.addColumn(name));
		
		HashMap<String, Integer> compare = new HashMap<>();
		
		

		
		
		
		
		
		
		
				
		
		

	}

	public static Set<String> makeKospiList(String day) throws Exception {
		Set<String> kospi = new HashSet<>();
		
		String baseUrl = "http://data-dbg.krx.co.kr/svc/apis/sto/stk_bydd_trd.json?basDd=day";
		String requstUrl=baseUrl.replace("day", day);
		
		
		URL url = new URL(requstUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("AUTH_KEY", "DD0DFC1FA0E24D1F96A8B50BB69A245520AA9CD0");

		
		BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		StringBuffer sb = new StringBuffer();
		String inputline;
		
		while((inputline=bf.readLine())!=null) sb.append(inputline);
		
		String response = sb.toString();
		
		
		JSONParser parser = new JSONParser();
		JSONObject  obj =(JSONObject) parser.parse(response);
		JSONArray marketData = (JSONArray) obj.get("OutBlock_1");
		
		for (int i = 0; i < marketData.size(); i++) {
			JSONObject corp = (JSONObject) marketData.get(i);
			kospi.add("kospi"+corp.get("ISU_CD").toString());
		
		
		}
		
		bf.close();
		return kospi;
		
		
		
		
		
	}

	public static Set<String> makeKosdaqList(String day) throws Exception {
		Set<String> kosdaq = new HashSet<>();
		
		String baseUrl ="http://data-dbg.krx.co.kr/svc/apis/sto/ksq_bydd_trd.json?basDd=day" ;
		String requstUrl=baseUrl.replace("day", day);
		
		
		URL url = new URL(requstUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("AUTH_KEY", "DD0DFC1FA0E24D1F96A8B50BB69A245520AA9CD0");

		
		BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		StringBuffer sb = new StringBuffer();
		String inputline;
		
		while((inputline=bf.readLine())!=null) sb.append(inputline);
		
		String response = sb.toString();
		
		
		JSONParser parser = new JSONParser();
		JSONObject  obj =(JSONObject) parser.parse(response);
		JSONArray marketData = (JSONArray) obj.get("OutBlock_1");
		
		for (int i = 0; i < marketData.size(); i++) {
			JSONObject corp = (JSONObject) marketData.get(i);
			kosdaq.add("kosdaq"+corp.get("ISU_CD").toString());
		
		
		}
		
		bf.close();
		return kosdaq;
		
		
		
		
		
	}
}
