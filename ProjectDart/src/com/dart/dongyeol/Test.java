package com.dart.dongyeol;

import java.util.HashMap;

//182023b0d0bb93439f2cdd9f8f8ed93215fb72dd

public class Test {

	public static void main(String[] args) {
		CorpFinanceData corp = new CorpFinanceData();
		HashMap<Object, Object> corpData = corp.getFinancialData();
		System.out.println(corpData.get("매출액"));
		
	}
}
