package com.dart.dongyeol_v2;

import java.util.List;
import java.util.TreeSet;

import org.json.simple.JSONObject;

import lombok.Getter;

@Getter
public class CfsVo {
	
	private String rceptNo;
	private boolean exist;
	private int accountNumber;
	private TreeSet<Account> accountsCFS;
	
	public CfsVo(boolean exist) {
		this.exist = exist;
	}
	
	public CfsVo(List<JSONObject> cfs,int accountNumber) {
		
		this.accountNumber = accountNumber;
		exist = true;
		accountsCFS = new TreeSet<>();
		
		if(cfs.size()!=0) {
			JSONObject first = cfs.get(0);
			String rceptNo = first.get("rcept_no").toString();
			this.rceptNo = rceptNo.substring(0,8);	
		} else rceptNo = "00000000";
		
		cfs.stream().forEach(accountJobj ->{
			String accountName = accountJobj.get("account_nm").toString();
			
			String amount = accountJobj.get("thstrm_amount").toString();
			long thisYearAmount;
			if(amount.equals("")) thisYearAmount=0;
			else thisYearAmount = Long.parseLong(amount);
			
			int ord =Integer.parseInt( accountJobj.get("ord").toString());   
			
			accountsCFS.add(new Account(accountName, thisYearAmount, ord));
			
		});
		
	}
	
	public void showEveryAccounts() {
		if(exist) accountsCFS.stream().forEach(a -> System.out.println(a));
		else System.out.println("현금흐름표가 없습니다.(해당 재무제표 작성대상 법인이 아닙니다.)");	
		
	}
	
	public long getAmount(String accountName) {
		long amount=0;		
		if(exist) {
			
			for (Account account : accountsCFS) {
				if(account.getAccountName().equals(accountName)) amount = account.getThisYearAmount();
			}
			
			return amount;
			
		} else {
			return amount;
		}	
		
	}
	
	public long getAmount(String accountName,int ord) {
		long amount=0;		
		if(exist) {
			
			for (Account account : accountsCFS) {
				if(account.getAccountName().equals(accountName) && account.getOrd()==ord) {
					amount = account.getThisYearAmount();
				}
			}
			
			return amount;
			
		} else {
			return amount;
		}	
		
	}
	
	public long getAmount(int ord) {
		long amount=0;		
		if(exist) {
			
			for (Account account : accountsCFS) {
				if(account.getOrd()==ord) {
					amount = account.getThisYearAmount();
				}
			}
			return amount;
			
		} else {
			return amount;
		}	
		
	}
	
	
	public long getAmountBysimilar(String accountName) {
		long amount=0;		
		if(exist) {
			
			for (Account account : accountsCFS) {
				if(account.getAccountName().contains(accountName)) amount = account.getThisYearAmount();
			}
			
			return amount;
			
		} else {
			return amount;
		}	
		
	}

}

