package com.dart.dongyeol.toDB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


//DD0DFC1FA0E24D1F96A8B50BB69A245520AA9CD0
public class KrxDb {

	public static void main(String[] args) throws Exception {
		URL url = new URL("http://data-dbg.krx.co.kr/svc/apis/sto/stk_bydd_trd.json?basDd=20220316");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("AUTH_KEY", "DD0DFC1FA0E24D1F96A8B50BB69A245520AA9CD0");

		
		BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		StringBuffer sb = new StringBuffer();
		String inputline;
		
		while((inputline=bf.readLine())!=null) sb.append(inputline);
		
		String response = sb.toString();
		
		System.out.println(response);
		
		JSONParser parser = new JSONParser();
		JSONObject  obj =(JSONObject) parser.parse(response);
		JSONArray marketData = (JSONArray) obj.get("OutBlock_1");
		System.out.println(marketData.size());
		
		int count;
		for (int i = 0; i < marketData.size(); i++) {
			JSONObject corp = (JSONObject) marketData.get(i);
			System.out.println(corp.get("ISU_NM"));
			
		}
			
		

	}

}
