package com.dart.dongyeol;

import java.util.List;

public class Test1 {

	public static void main(String[] args) {
		
		FindAccmtCorpcls rawData = new FindAccmtCorpcls();
		List<String> corps_cls = rawData.getCorpCls();
		List<String> acc_mt = rawData.getAccMt();
		
	
	
		int countY=0;
		int countK=0;
		int countN=0;
		int countE=0;
		for(String cls:corps_cls) {
			
			if(cls.equals("Y")) {
				countY++;
			}else if(cls.equals("K")) {
				countK++;
			}else if(cls.equals("N")) {
				countN++;
			} else countE++;
		}
		System.out.println("총 기업수:" + corps_cls.size());
		System.out.println("코스피:"+countY + "코스닥:"+ countK + "코넥스:" + countN + "기타:" + countE);
		
		int tweleve = 0;
		int others = 0;
		
		for(String acc:acc_mt) {
			if(acc.equals("12")) {
				tweleve++;
			} else others++;
		}
		System.out.println("--------------------------------------------");
		System.out.println("총 기업수:" + acc_mt.size());
		System.out.println("12월:"+tweleve+"기타:"+others);
		
		}

	}


