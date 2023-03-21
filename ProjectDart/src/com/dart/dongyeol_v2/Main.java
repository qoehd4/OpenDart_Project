package com.dart.dongyeol_v2;

import java.util.HashMap;

public class Main {

	public static void main(String[] args) {		
		AwsMysqlDAO dao = new AwsMysqlDAO();
		String corpCode = dao.getcorpCode("MIT");
		String corp_cls = classifyMarket(corpCode);
		Viewer viewer = new Viewer();
		
		if (corp_cls.equals("Y")) {
			KospiEvaluator kospiEv = new KospiEvaluator(corpCode);
			viewer.viewDelistingCond(kospiEv);
			
		} else if(corp_cls.equals("K")) {
			KosdaqEvaluator kosdaqEv = new KosdaqEvaluator(corpCode);
			viewer.viewDelistingCond(kosdaqEv);
		} else {
			System.out.println("코스피 코스닥 상장기업이 아닙니다.");
		}
		

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
