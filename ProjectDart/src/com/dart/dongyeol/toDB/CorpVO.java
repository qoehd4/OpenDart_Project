package com.dart.dongyeol.toDB;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of= {"corp_code"})
public class CorpVO {
	private String corp_code;
	private String stock_name;
	private String stock_code;
	private String corp_cls;
	private String induty_code;
	private String acc_mt;
	
	
}



