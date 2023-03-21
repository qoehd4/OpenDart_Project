package com.dart.dongyeol_v2;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		AwsMysqlDAO dao = new AwsMysqlDAO();
		Scanner scan = new Scanner(System.in);
			
		while(true) {
			System.out.print("회사이름을 입력하세요(#금융업종은 평가가 불가합니다.):");
			String corpName = scan.nextLine();
			System.out.println("-------------------------");

			
			String corpCode = dao.getcorpCode(corpName);
			String corp_cls = classifyMarket(corpCode);
			Viewer viewer = new Viewer();
			
			if (corp_cls.equals("Y")) {
				KospiEvaluator kospiEv = new KospiEvaluator(corpCode);
				viewer.viewDelistingCond(kospiEv);
				
			} else if(corp_cls.equals("K")) {
				KosdaqEvaluator kosdaqEv = new KosdaqEvaluator(corpCode);
				viewer.viewDelistingCond(kosdaqEv);
			} else {
				System.out.print("코스피 코스닥 상장기업이 아닙니다./");
				System.out.println("회사명이 틀렸을수도 있습니다.");
				
			}
			
			System.out.println("프로그램 종료를 원하시면 '종료'를 입력하세요");
			String exit = scan.nextLine();
	
			if(exit.equals("종료")) {
				scan.close();
				break;
			}
			
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
