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
	private CisVo comprehensiveIncomeStatements;
	private int bns_year;
	private boolean exist;
	private String type;
	
	
	
	
	public StatementsVo(String dbResponse,String year,String type) throws ParseException {
		this.type = type;
		bns_year = Integer.parseInt(year);
		List<JSONObject> is = new ArrayList<>();
		List<JSONObject> bs = new ArrayList<>();
		List<JSONObject> cfs = new ArrayList<>();
		List<JSONObject> cis = new ArrayList<>();
		
		int isNumber =0; int bsNumber =0; int cfsNumber =0; int cisNumber=0;
		
		
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
					bs.add(account); bsNumber++;
				} else if(sj_div.equals("IS")) {
					is.add(account); isNumber++;
				} else if(sj_div.equals("CF")) {
					cfs.add(account); cfsNumber++;
				} else if(sj_div.equals("CIS"))	{
					cis.add(account); cisNumber++;
				}
			}
			
			balanceSheets = new BsVo(bs,bsNumber);
			incomeStatements = new IsVo(is,isNumber);
			cashFlowStatements = new CfsVo(cfs,cfsNumber);
			comprehensiveIncomeStatements = new CisVo(cis,cisNumber);
			
		} else {
			exist = false;
			balanceSheets = new BsVo(exist);
			incomeStatements = new IsVo(exist);
			cashFlowStatements = new CfsVo(exist);
			comprehensiveIncomeStatements = new CisVo(exist);
			
		}
			
		
	}

	@Override
	public String toString() {
		String result;
		if(exist) result = bns_year +"년 " + type +"재무제표";
		else result = bns_year +"년 " + type +"재무제표가 없습니다.";
		return result;
	}
	
	
	

}
