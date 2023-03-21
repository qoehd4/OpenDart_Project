package com.dart.dongyeol_v2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ExpensiveOrCheap {

	
	public static void main(String[] args) throws ParseException {
		AwsMysqlDAO dao = new AwsMysqlDAO();
		String corpCode = dao.getcorpCode("SK하이닉스");
		String corp_cls = classifyMarket(corpCode);
		System.out.println(corp_cls);
		System.out.println(corpCode);
		System.out.println("kospi"+corpCode);
		
		TreeMap<Date, Integer> price10yr  = dao.get10yrPrice("kosdaq000250", "market_kosdaq");
		
		
		for (Date date : price10yr.keySet()) {
			System.out.println(date + ":" + price10yr.get(date));
		}
		
		
		
		

		
		List<String> jsonsOFS = dao.get6yrJson(corpCode, "OFS");
		List<String> jsonsCFS = dao.get6yrJson(corpCode, "CFS");
		List<JSONObject> lastJsons = new ArrayList<>();
		
		for (int i = 0; i < jsonsCFS.size(); i++) {
			JSONParser parser = new JSONParser();
			
			JSONObject cfsObj = (JSONObject) parser.parse(jsonsCFS.get(i));
			JSONObject ofsObj = (JSONObject) parser.parse(jsonsOFS.get(i));
			
			if(cfsObj.get("status").toString().equals("013")) {
				lastJsons.add(ofsObj);
			}else {
				lastJsons.add(cfsObj);
			}
				
			
		}
		
		List<JSONArray> statements = lastJsons.stream().map(t -> (JSONArray)t.get("list") ).toList();
		
		List<String> date = new ArrayList<>();
		List<String> eps6 = new ArrayList<>();
		List<String> netIncome6 = new ArrayList<>();
		List<String> totalEquity6 = new ArrayList<>();
		
		statements.stream().forEach(stmts ->{
			JSONObject fordate = (JSONObject) stmts.get(0);
			date.add(fordate.get("rcept_no").toString().substring(0,8));           
			
			
			for(int i=0;i<stmts.size();i++) {
				JSONObject account = (JSONObject) stmts.get(i);
				if(account.get("account_nm").toString().contains("기본주당이익")) {
					eps6.add(account.get("thstrm_amount").toString());
					eps6.add(account.get("frmtrm_amount").toString());
					eps6.add(account.get("bfefrmtrm_amount").toString());
					
				}
				
				if(account.get("account_nm").toString().equals("자본총계")&&account.get("sj_div").toString().equals("BS")) {
					totalEquity6.add(account.get("thstrm_amount").toString());
					totalEquity6.add(account.get("frmtrm_amount").toString());
					totalEquity6.add(account.get("bfefrmtrm_amount").toString());
					
				}
				
				if(account.get("account_nm").toString().contains("당기순이익") && account.get("sj_div").toString().equals("CIS")) {
					netIncome6.add(account.get("thstrm_amount").toString());
					netIncome6.add(account.get("frmtrm_amount").toString());
					netIncome6.add(account.get("bfefrmtrm_amount").toString());
					
				}
				
			}
			
		});
		
		
		System.out.println(date.size());
		System.out.println(eps6.size());
		System.out.println(netIncome6.size());
		System.out.println(totalEquity6.size());
		
		
		
		

		

		
		
	
		
		
		

		
		

	}
	
	public static String classifyMarket(String corpCode) {
		AwsMysqlDAO dao = new AwsMysqlDAO();
		String corp_cls = "none";
		
		HashMap<String, String> allCorps = dao.getinfoOfAlllistedCompany();
		
		for (String code : allCorps.keySet()) {
			if(code.equals(corpCode)) {
				corp_cls= allCorps.get(code);
			}
		}
		
		return corp_cls;
		
	}


	
	

}
