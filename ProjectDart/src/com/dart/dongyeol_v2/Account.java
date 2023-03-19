package com.dart.dongyeol_v2;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of= {"accountName","ord"})
public class Account implements Comparable<Account> {
	private String accountName;
	private long thisYearAmount;
	private int ord;
	
	@Override
	public int compareTo(Account account) {
		if(this.ord>account.ord) {
			return 1;
		}else if(this.ord==account.ord) {
			return 0;
		}else {
			return -1;
		}
	
	}

	@Override
	public String toString() {
		return ord +"번:"+accountName + "=" +thisYearAmount;
	}
}
