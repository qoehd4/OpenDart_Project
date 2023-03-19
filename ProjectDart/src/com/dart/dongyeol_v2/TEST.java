package com.dart.dongyeol_v2;

import java.util.List;

public class TEST {

	public static void main(String[] args) {
		AwsMysqlDAO dao = new AwsMysqlDAO();
		
		String corpCode = dao.getcorpCode("세아제강");
		List<StatementsVo> statements3yr  = dao.get3yrStatements(corpCode, "OFS");
		
		
		for (StatementsVo statement : statements3yr) {
			IsVo incomestatements = statement.getIncomeStatements();
			CisVo compreIncomestatements =statement.getComprehensiveIncomeStatements();

			if(compreIncomestatements.getAccountNumber()>incomestatements.getAccountNumber()) {
				compreIncomestatements.showEveryAccounts();
			}else {
				incomestatements.showEveryAccounts();
			}

			
			
		}
		
		


		
		
		

	}

}
