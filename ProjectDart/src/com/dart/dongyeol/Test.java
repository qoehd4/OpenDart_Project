package com.dart.dongyeol;

import java.util.HashMap;

//182023b0d0bb93439f2cdd9f8f8ed93215fb72dd

public class Test {

	public static void main(String[] args) {
		CorpFinanceData corp = new CorpFinanceData("182023b0d0bb93439f2cdd9f8f8ed93215fb72dd", "NAVER", "2020", "CFS");
		HashMap<String, Long> corpData = corp.getFinancialData();
		
		for(String key:corpData.keySet()) {
			System.out.println("{"+key+","+corpData.get(key)+"}");
		}
		
	}
}
