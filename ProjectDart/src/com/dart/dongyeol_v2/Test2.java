package com.dart.dongyeol_v2;

import java.util.HashMap;

public class Test2 {

	public static void main(String[] args) {
		AwsMysqlDAO dao = new AwsMysqlDAO();
		String corpCode = dao.getcorpCode("엔지스테크널러지");
		String corp_cls = classifyMarket(corpCode);
		
		if (corp_cls.equals("Y")) {
			KospiEvaluator kospiEv = new KospiEvaluator(corpCode);
			System.out.println(kospiEv.checkRevenue());
			System.out.println(kospiEv.checkCapitalImpairment());
			
		} else if(corp_cls.equals("K")) {
			KosdaqEvaluator kosdaqEv = new KosdaqEvaluator(corpCode);
			System.out.println(kosdaqEv.checkRevenue());
			System.out.println(kosdaqEv.checkCapitalImpairment());
			System.out.println(kosdaqEv.checkEquityAmount());
			System.out.println(kosdaqEv.checkCIBT());
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
