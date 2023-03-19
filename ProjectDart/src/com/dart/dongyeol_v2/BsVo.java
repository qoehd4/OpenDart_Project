package com.dart.dongyeol_v2;

import java.util.List;
import java.util.TreeSet;

import org.json.simple.JSONObject;

import lombok.Getter;

@Getter
public class BsVo {
	
	private  String rceptNo;
	private TreeSet<Account> accountsBS;
	
	public BsVo(List<JSONObject> bs) {
		accountsBS = new TreeSet<>();
		
		if(bs.size()!=0) {
			JSONObject first = bs.get(0);
			String rceptNo = first.get("rcept_no").toString();
			this.rceptNo = rceptNo.substring(0,8);	
		} else rceptNo = "00000000";
		
		bs.stream().forEach(accountJobj ->{
			String accounName = accountJobj.get("account_nm").toString();
			
			String amount = accountJobj.get("thstrm_amount").toString();
			long thisYearAmount;
			if(amount.equals("")) thisYearAmount=0;
			else thisYearAmount = Long.parseLong(amount);
			
			int ord =Integer.parseInt( accountJobj.get("ord").toString());   
			
			accountsBS.add(new Account(accounName, thisYearAmount, ord));
			
		});
	}
	
	public void showEveryAccounts() {
		accountsBS.stream().forEach(a -> System.out.println(a));
	}
}
