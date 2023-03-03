package com.dart.dongyeol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CorpClassification {

	public static void main(String[] args) throws Exception {
		//182023b0d0bb93439f2cdd9f8f8ed93215fb72dd
		String includedMarket = findMarket("182023b0d0bb93439f2cdd9f8f8ed93215fb72dd", "카카오");
		
		
		if(includedMarket.equals("Y")) {
			System.out.println("코스피 소속입니다.");
		} else if(includedMarket.equals("K")) {
			System.out.println("코스닥 소속입니다.");
		} else {
			System.out.println("비상장 주식입니다.");
		}
		
		
			
	}
	
	public static String findMarket(String crtfc_key,String corpName) {
		String market ="";
		String url = "https://opendart.fss.or.kr/api/company.json?crtfc_key=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx&corp_code=name";
		url = url.replace("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",crtfc_key);
		url = url.replace("name",CompanyUrl.findCorpcode(corpName));
		
		try {
			URL requesturl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) requesturl.openConnection();
			BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			StringBuffer sb = new  StringBuffer();
			String inputline = "";
			
			while((inputline=bf.readLine()) != null) sb.append(inputline);
			bf.close();
			String response = sb.toString();
			
			
			JSONParser parser = new JSONParser();
			JSONObject f = (JSONObject) parser.parse(response);
			
			market = f.get("corp_cls").toString();
			
		} catch(Exception e) {
			System.out.println("소속증권시장을 찾을수없습니다.");
			e.printStackTrace();
		} 			
		
		return market;
		
	}
		
	
	
}
