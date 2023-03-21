package com.dart.dongyeol_v2;

public class Viewer {
	
	public void viewDelistingCond(Object eval) {
		if(eval instanceof KospiEvaluator kospiEval) {
			viewCapitlImpairementCond(kospiEval.checkCapitalImpairment());
			viewRevecueCond(kospiEval.checkRevenue());
			
			if(kospiEval.checkCapitalImpairment()==false || kospiEval.checkRevenue()==false) {
				System.out.println("------------------------");
				System.out.println("상장폐지 위험이 있습니다.");
			} else System.out.println("안전합니다.");
			
			
		} else if(eval instanceof KosdaqEvaluator kosdaqEval) {
			viewCapitlImpairementCond(kosdaqEval.checkCapitalImpairment());
			viewRevecueCond(kosdaqEval.checkRevenue());
			viewEquityCond(kosdaqEval.checkEquityAmount());
			viewCibtCond(kosdaqEval.checkCIBT());
			
			if(kosdaqEval.checkCapitalImpairment()==false||kosdaqEval.checkRevenue()==false
					||kosdaqEval.checkEquityAmount()==false||kosdaqEval.checkCIBT()==false) {
				System.out.println("------------------------");
				System.out.println("상장폐지 위험이 있습니다.");
			} else System.out.println("안전합니다.");
			
		}
		
	}
	
	public void viewRevecueCond(boolean input) {
		if(input) {
			System.out.println("매출액 기준 통과.");
		} else {
			System.out.println("매출액 기준 탈락!!.");
		}
	}

	public void viewCapitlImpairementCond(boolean input) {
		if(input) {
			System.out.println("자본잠식 상태가 아닙니다.");
		} else {
			System.out.println("자본 잠식중!!.");
		}
	}
	
	public void viewEquityCond(boolean input) {
		if(input) {
			System.out.println("자기자본 기준 통과.");
		} else {
			System.out.println("자기자본 기준 탈락!!.");
		}
	}
	
	public void viewCibtCond(boolean input) {
		if(input) {
			System.out.println("법인세비용차감전계속사업이익(손실) 기준 통과.");
		} else {
			System.out.println("법인세비용차감전계속사업이익(손실) 기준 탈락!!.");
		}
	}
	

}
