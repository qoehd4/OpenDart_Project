package com.dart.dongyeol;

import java.util.ArrayList;
import java.util.HashMap;

//182023b0d0bb93439f2cdd9f8f8ed93215fb72dd

public class Test {

	public static void main(String[] args) {
		CorpFinanceData corp = new CorpFinanceData("182023b0d0bb93439f2cdd9f8f8ed93215fb72dd", "카카오", "2021", "CFS");
		HashMap<String, Long> corpData = corp.getFinancialData();
		ArrayList<String> accountNames = new ArrayList<>();
		
		for(String key:corpData.keySet()) {
			accountNames.add(key);
			//System.out.println("{"+key+":"+corpData.get(key)+"}");
		}
		System.out.println(accountNames);
		
		long netIncome = corpData.get("당기순이익(손실)");
		long paidDivdend = corpData.get("배당금");
		System.out.println((double) paidDivdend/netIncome);
	
		
		
		
	}
}
