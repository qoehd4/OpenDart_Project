package com.dart.dongyeol_v2;

import java.util.List;



public class KosdaqEvaluator {
	
	private List<StatementsVo>  cfsStatements3yr;
	private List<StatementsVo>  ofsStatements3yr;
	private AwsMysqlDAO dao;
	private String corpCode;
	
	public KosdaqEvaluator(String corpCode) {
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
		
		if(checkHoldings()) lastestStatements = cfsStatements3yr.get(0);
		else lastestStatements = ofsStatements3yr.get(0);
		
		IsVo incomeStatement  = lastestStatements.getIncomeStatements();
		CisVo compreIncomeStatement = lastestStatements.getComprehensiveIncomeStatements();
		
		if(compreIncomeStatement.getAccountNumber()>incomeStatement.getAccountNumber()) {
			 long revenue = compreIncomeStatement.getAmount(0);
			 
			 if(revenue>3000000000L) return true;
			 else return false;

		} else {
			long revenue = incomeStatement.getAmount(0);
		
			 if(revenue>3000000000L) return true;
			 else return false;
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
	
	public boolean checkEquityAmount() {
		StatementsVo latestCFS = cfsStatements3yr.get(0);
		StatementsVo latestOFS = ofsStatements3yr.get(0);
		BsVo balanceSheet;

		if (latestCFS.isExist()) {
			balanceSheet = latestCFS.getBalanceSheets();
		} else {
			balanceSheet = latestOFS.getBalanceSheets();
		}
		
		long equity  = balanceSheet.getAmount("자본총계");
		
		if(equity>1000000000L) {
			return true;
		} else {
			return false;
		}
		
		
		
	}
		
	public boolean checkCIBT() {
		int count = 0;
		for (int i = 0; i < ofsStatements3yr.size(); i++) {
			 StatementsVo cfs = cfsStatements3yr.get(i);
			 StatementsVo ofs = ofsStatements3yr.get(i);
			 IsVo incomeStatement = null;
			 CisVo compreIncomeStatement = null;
			 BsVo balanceSheet = null;
			 long cibt=0;
			 long totalEquity=0;
			 
			 if(cfs.isExist()) {
				 incomeStatement = cfs.getIncomeStatements();
				 compreIncomeStatement = cfs.getComprehensiveIncomeStatements();
				 balanceSheet = cfs.getBalanceSheets();
			 } else {
				 incomeStatement = ofs.getIncomeStatements();
				 compreIncomeStatement = ofs.getComprehensiveIncomeStatements();
				 balanceSheet = ofs.getBalanceSheets();
			 }
			 
			 if(compreIncomeStatement.getAccountNumber()>incomeStatement.getAccountNumber()) {
				 cibt = compreIncomeStatement.getAmountBysimilar("법인세비용차감전순이익");
				 totalEquity = balanceSheet.getAmount("자본총계");
				 long limit = (long) (totalEquity * (-0.45));
				 
				 	if(cibt<0) {
				 		if(cibt<limit && Math.abs(cibt)>1000000000) count ++;
				 	}
				 	
				 	
			 } else {
			 	 cibt = incomeStatement.getAmountBysimilar("법인세비용차감전순이익");
			 	 totalEquity = balanceSheet.getAmount("자본총계");
			 	 long limit = (long) (totalEquity * (-0.45));
			 	 
			 		if(cibt<0) {
			 			if(cibt<limit && Math.abs(cibt)>1000000000) count ++;	
			 		}
			 
			 }
			
		 }
		
		
		 if(count>0) {
			 return false;
		 } else {
			 return true;
		 }
	
	
	}

	
	
}
