package com.dart.dongyeol_v2;

import java.util.List;

public class TEST {

	public static void main(String[] args) {
		AwsMysqlDAO dao = new AwsMysqlDAO();
		
		String corpCode = dao.getcorpCode("세아제강");
		List<StatementsVo> statements3yr  = dao.get3yrStatements(corpCode, "CFS");
		
		
		for (StatementsVo statement : statements3yr) {
			System.out.println(statement);
		}
		
		


		
		
		

	}

}
