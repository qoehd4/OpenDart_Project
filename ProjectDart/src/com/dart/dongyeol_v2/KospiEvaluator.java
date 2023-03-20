package com.dart.dongyeol_v2;

import java.util.List;

public class KospiEvaluator {

	private List<StatementsVo> cfsStatements3yr;
	private List<StatementsVo> ofsStatements3yr;
	private AwsMysqlDAO dao;
	private String corpCode;

	public KospiEvaluator(String corpCode) {
		this.corpCode = corpCode;
		dao = new AwsMysqlDAO();
		cfsStatements3yr = dao.get3yrStatements(corpCode, "CFS");
		ofsStatements3yr = dao.get3yrStatements(corpCode, "OFS");

	}

	public boolean checkHoldings() {
		return dao.getCorpcodeOfAllHoldings().contains(corpCode);

	}

	public boolean checkRevenue() {
		StatementsVo lastestStatements;

		if (checkHoldings())
			lastestStatements = cfsStatements3yr.get(0);
		else
			lastestStatements = ofsStatements3yr.get(0);

		IsVo incomeStatement = lastestStatements.getIncomeStatements();
		CisVo compreIncomeStatement = lastestStatements.getComprehensiveIncomeStatements();

		if (compreIncomeStatement.getAccountNumber() > incomeStatement.getAccountNumber()) {
			long revenue = compreIncomeStatement.getAmount(0);

			if (revenue > 5000000000L)
				return true;
			else
				return false;

		} else {
			long revenue = incomeStatement.getAmount(0);

			if (revenue > 5000000000L)
				return true;
			else
				return false;
		}

	}

	
	public boolean checkCapitalImpairment() {
		StatementsVo latestCFS = cfsStatements3yr.get(0);
		StatementsVo latestOFS = ofsStatements3yr.get(0);
		BsVo balanceSheet;

		if (latestCFS.isExist()) {
			balanceSheet = latestCFS.getBalanceSheets();
		} else {
			balanceSheet = latestOFS.getBalanceSheets();
		}
			
		double ordinaryCapital = (double) balanceSheet.getAmount("자본금");
		double totalCapital = (double) balanceSheet.getAmount("자본총계");

		double ratio = (ordinaryCapital - totalCapital) / ordinaryCapital;
		
		if(ratio<0) {
			return true;
		} else {
			return false;
		}
			


		
	}

}
