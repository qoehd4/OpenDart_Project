package com.dart.dongyeol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;





public class CorpFinanceData {
	

	private HashMap<Object, Object> financialData;
	
	public HashMap<Object, Object> getFinancialData() {
		return financialData;
	}

		
	
	public CorpFinanceData() {
		super();
		
		try {
			
			CompanyUrl url = new CompanyUrl();
			HttpURLConnection conn = url.getConnection();
			
			JSONArray jarray = null;
			HashMap<Object, Object> financialData = new HashMap<Object,Object>();
			
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
									
			} else System.out.println("error");
			
			
			for(int i=0;i<jarray.size();i++) {
				JSONObject f = (JSONObject) jarray.get(i);
				
				if(!f.get("sj_nm").equals("자본변동표")) financialData.put(f.get("account_nm"), f.get("thstrm_amount"));
				else continue;
				
				//System.out.println(f.get("account_nm") + " : " + f.get("thstrm_amount"));
				
				
				
			}
			
			this.financialData= financialData;
			
		} catch (Exception e) {
			System.out.println("\n재무정보를 불러오는데 실패했습니다.");
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
	}
	


}
