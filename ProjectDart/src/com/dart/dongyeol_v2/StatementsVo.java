package com.dart.dongyeol_v2;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lombok.Getter;

@Getter
public class StatementsVo {
	
	private BsVo balanceSheets;
	private IsVo incomeStatements;
	private CfsVo cashFlowStatements;
	private int bns_year;
	private boolean exist;
	private String type;
	
	
	
	
	public StatementsVo(String dbResponse,String year,String type) throws ParseException {
		this.type = type;
		bns_year = Integer.parseInt(year);
		List<JSONObject> is = new ArrayList<>();
		List<JSONObject> bs = new ArrayList<>();
		List<JSONObject> cfs = new ArrayList<>();
		
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject) parser.parse(dbResponse);
		String isIfrs = jobj.get("status").toString();
		
		if(isIfrs.equals("000")) {
			exist = true;
			JSONArray statments = (JSONArray) jobj.get("list");
			
			for (int i = 0; i < statments.size(); i++) {
				JSONObject account = (JSONObject) statments.get(i);
				String  sj_div = account.get("sj_div").toString();
				if(sj_div.equals("BS")) {
					bs.add(account);
				} else if(sj_div.equals("CIS")||sj_div.equals("IS")) {
					is.add(account);
				} else if(sj_div.equals("CF")) {
					cfs.add(account);
				}		
			}
			
			balanceSheets = new BsVo(bs);
			incomeStatements = new IsVo(is);
			cashFlowStatements = new CfsVo(cfs);
			
		} else exist = false;
		

		
	}

	@Override
	public String toString() {
		String result;
		if(exist) result = bns_year +"년 " + type +"재무제표";
		else result = bns_year +"년 " + type +"재무제표가 없습니다.";
		return result;
	}
	
	
	

}
