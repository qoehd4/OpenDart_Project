package com.dart.dongyeol_v2;

import java.util.List;

public class TEST {

	public static void main(String[] args) {
		AwsMysqlDAO dao = new AwsMysqlDAO();
		
		String corpCode = dao.getcorpCode("강원에너지");
		List<StatementsVo> statements3yr  = dao.get3yrStatements(corpCode, "OFS");
		
		
		for (StatementsVo statement : statements3yr) {
			BsVo balanceSheets = statement.getBalanceSheets();
			balanceSheets.showEveryAccounts();
			System.out.println("------------------------------------");
			
		}
		
		


		
		
		

	}

}
