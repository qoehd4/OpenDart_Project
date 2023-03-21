package com.dart.dongyeol_v2;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TEST {

	public static void main(String[] args) {
		AwsMysqlDAO dao = new AwsMysqlDAO();
		
		String corpCode = dao.getcorpCode("엘아이에스");
		List<StatementsVo> statements3yr  = dao.get3yrStatements(corpCode, "CFS");

		

		
		
		
	
		
		for (StatementsVo statement : statements3yr) {
			IsVo incomestatements = statement.getIncomeStatements();
			CisVo compreIncomestatements =statement.getComprehensiveIncomeStatements();
			
			System.out.println(statement.getBns_year());
			if(compreIncomestatements.getAccountNumber()>incomestatements.getAccountNumber()) {
				compreIncomestatements.showEveryAccounts();
				System.out.println("-------------------------------");
			}else {
				incomestatements.showEveryAccounts();
			}	System.out.println("-------------------------------");
			

			
			
		}
		

		
		
		/*
		for (StatementsVo statement : statements3yr) {
			System.out.println(statement.getBns_year());
			BsVo balanceSheets = statement.getBalanceSheets();
			balanceSheets.showEveryAccounts();
			System.out.println("-------------------------------");
			
		}
		
		
		
		/*
		for (StatementsVo statement : statements3yr) {
			System.out.println(statement.getBns_year());
			CfsVo csahflowStatement = statement.getCashFlowStatements();
			csahflowStatement.showEveryAccounts();
			System.out.println("-------------------------------");
			
			
		}
		*/
		
		


		
		
		

	}

}
