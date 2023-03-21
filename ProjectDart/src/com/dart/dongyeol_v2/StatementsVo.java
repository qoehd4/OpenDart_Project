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
	
	//StatementVo가 담고 있는 각각의 재무제표 객체들
	private BsVo balanceSheets;
	private IsVo incomeStatements;
	private CfsVo cashFlowStatements;
	private CisVo comprehensiveIncomeStatements;
	
	private int bns_year;
	private boolean exist;
	private String type;
	
	
	
	//생성자 JSON을 DB에서 스트링 형태로 받아낸다.
	public StatementsVo(String dbResponse,String year,String type) throws ParseException {
		this.type = type;
		bns_year = Integer.parseInt(year);
		//회계계정을 담기위한 리스트
		List<JSONObject> is = new ArrayList<>();
		List<JSONObject> bs = new ArrayList<>();
		List<JSONObject> cfs = new ArrayList<>();
		List<JSONObject> cis = new ArrayList<>();
		
		int isNumber =0; int bsNumber =0; int cfsNumber =0; int cisNumber=0;
		
		
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject) parser.parse(dbResponse);
		String isIfrs = jobj.get("status").toString();
		
		//재무제표가 존재할때 Status 키값은 000
		if(isIfrs.equals("000")) {
			exist = true;
			JSONArray statments = (JSONArray) jobj.get("list");
			
			//이 for문을 통하여 각각의 JSONObjedct로 구성된 회계계정들을 분류해서 각각의 리스트에 담아줌
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
			
			// 이후 각각의 재무제표 객체들을 리스트 인자로 받아서 객체를 생성
			balanceSheets = new BsVo(bs,bsNumber);
			incomeStatements = new IsVo(is,isNumber);
			cashFlowStatements = new CfsVo(cfs,cfsNumber);
			comprehensiveIncomeStatements = new CisVo(cis,cisNumber);
			
		} else {
			//제무제표가 없으면 불리언값을 인자로 받아 객체를 생성
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
