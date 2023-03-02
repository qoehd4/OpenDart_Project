package com.dart.dongyeol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class CorpFinanceData {
	

	private HashMap<String, Long> financialData;
	
	public HashMap<String, Long> getFinancialData() {
		return financialData;
	}

		
	
	public CorpFinanceData() {
		super();
		
		try {
			
			CompanyUrl url = new CompanyUrl();
			HttpURLConnection conn = url.getConnection();
			
			JSONArray jarray = null;
			HashMap<String, Long> financialData = new HashMap<String,Long>();
			
			int responseCode = conn.getResponseCode();
			
			if(responseCode==200) {
				BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
				StringBuffer sb = new  StringBuffer();
				String inputline;
				
				while((inputline=bf.readLine())!=null) sb.append(inputline);
				bf.close();
				String response = sb.toString();
				
				JSONParser jparser = new JSONParser();
				JSONObject jobject = (JSONObject) jparser.parse(response);
				jarray = (JSONArray) jobject.get("list");
									
			} else System.out.println("응답에 실패했습니다.");
			
						
			
			for(int i=0;i<jarray.size();i++) {
				JSONObject f = (JSONObject) jarray.get(i);
				String account_nm= f.get("account_nm").toString();
				String thstrm_amount = f.get("thstrm_amount").toString();
				long amount;
				
				if(thstrm_amount.equals("")) amount=0;
				else amount=Long.parseLong(thstrm_amount);
			
				
				if(!f.get("sj_nm").equals("자본변동표")) financialData.put(account_nm,amount);
				else continue;
				
				//System.out.println(account_nm + " : " + amount);
							
			}
			
			this.financialData= financialData;
			
		} catch (Exception e) {
			System.out.println("\n재무정보를 불러오는데 실패했습니다.");
			e.printStackTrace();
		}
			
						
	}
	
	
	public CorpFinanceData(String crtfc_key, String corpName , String bsns_year, String fs_div) {
		super();
		
		try {
			
			CompanyUrl url = new CompanyUrl(crtfc_key, corpName, bsns_year, fs_div);
			HttpURLConnection conn = url.getConnection();
			
			JSONArray jarray = null;
			HashMap<String, Long> financialData = new HashMap<String,Long>();
			
			int responseCode = conn.getResponseCode();
			
			if(responseCode==200) {
				BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
				StringBuffer sb = new  StringBuffer();
				String inputline;
				
				while((inputline=bf.readLine())!=null) sb.append(inputline);
				bf.close();
				String response = sb.toString();
				
				JSONParser jparser = new JSONParser();
				JSONObject jobject = (JSONObject) jparser.parse(response);
				jarray = (JSONArray) jobject.get("list");
									
			} else System.out.println("응답에 실패했습니다.");
			
						
			
			for(int i=0;i<jarray.size();i++) {
				JSONObject f = (JSONObject) jarray.get(i);
				String account_nm= f.get("account_nm").toString();
				String thstrm_amount = f.get("thstrm_amount").toString();
				long amount;
				
				if(thstrm_amount.equals("")) amount=0;
				else amount=Long.parseLong(thstrm_amount);
			
				
				if(!f.get("sj_nm").equals("자본변동표")) financialData.put(account_nm,amount);
				else continue;
				
				//System.out.println(account_nm + " : " + amount);
							
			}
			
			this.financialData= financialData;
			
		} catch (Exception e) {
			System.out.println("\n재무정보를 불러오는데 실패했습니다.");
			e.printStackTrace();
		}
			
						
	}
	


}
