package com.dart.dongyeol_v2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;


public class AwsMysqlDAO {
	
	private static final String USERNAME = "admin";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://finanacedb.cje5ijbkiorz.ap-northeast-2.rds.amazonaws.com:3306/finanace_data_kr";
    
	private Connection conn;
	private PreparedStatement pst;
	private Statement st;
	private ResultSet rs;
	
	
	public String getcorpCode(String corpName)  {
		String corpCode = null;
		String sql = "SELECT corp_code FROM introduction WHERE stock_name = ?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			pst = conn.prepareStatement(sql);
			pst.setString(1, corpName);
			rs=pst.executeQuery();
			
			if(rs.next()) corpCode = rs.getString(1);
			
		} catch (Exception e) {
			System.out.println("기업코드를 조회하는데 문제가 발생했습니다.");
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("자원을 반납하는데 문제가 발생했습니다.");
				e.printStackTrace();
			}
			
			
		}
		
		
		
		return corpCode;
		
	}
	
	public List<StatementsVo> get3yrStatements(String corpCode,String type) {
		
		List<StatementsVo> threeYrStatements = new ArrayList<>();
		String sql = "SELECT json_statement FROM finanace_statement  WHERE corp_code=? AND year=? AND cfs_ofs=?";
		
		LocalDate now = LocalDate.now();
		int thisYear = now.getYear();
		LocalDate dDay = LocalDate.of(thisYear, 4, 29);
		int latestBnsYear=0;
		
		if(now.isBefore(dDay)) {
			latestBnsYear = thisYear-2;
		} else latestBnsYear = thisYear-1;
		
		String[] years = new String[] {String.valueOf(latestBnsYear),
				String.valueOf(latestBnsYear-1)
				,String.valueOf(latestBnsYear-2)};
		
		
		
		for (String year : years) {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				pst = conn.prepareStatement(sql);
				pst.setString(1, corpCode);
				pst.setString(2, year);
				pst.setString(3, type);
				
				rs=pst.executeQuery();
				if(rs.next()) {
					StatementsVo statement = new StatementsVo(rs.getString(1),year,type);
					threeYrStatements.add(statement);
				}
						
			} catch (Exception e) {
				System.out.println("3년 재무제표를 불러오는데 문제가 발생했습니다.");
				e.printStackTrace();
			} finally {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					System.out.println("자원을 반납하는데 문제가 발생했습니다.");
					e.printStackTrace();
				}
				
				
			}
						
		}
						
		return threeYrStatements;
		
	}
	
	public HashMap<String, String> getinfoOfAlllistedCompany() {
		HashMap<String, String> corpcodesAlllistedCompany = new HashMap<>();
		String corpCode = null;
		String corp_cls = null;
		String sql = "SELECT corp_code,corp_cls FROM introduction";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			pst = conn.prepareStatement(sql);
			rs=pst.executeQuery();
			
			while(rs.next()) {
				corpCode = rs.getString(1);
				corp_cls = rs.getString(2);
				corpcodesAlllistedCompany.put(corpCode, corp_cls);
			}
			
		} catch (Exception e) {
			System.out.println("기업코드를 조회하는데 문제가 발생했습니다.");
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("자원을 반납하는데 문제가 발생했습니다.");
				e.printStackTrace();
			}
			
			
		}
		
		
		
		return corpcodesAlllistedCompany;
		
	}
	
	public Set<String> getCorpcodeOfAllHoldings() {
		Set<String> corpcodesAllHoldings= new HashSet<>();
		String corpCode = null;
		String sql = "SELECT corp_code FROM holdings";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			pst = conn.prepareStatement(sql);
			rs=pst.executeQuery();
			
			while(rs.next()) {
				corpCode = rs.getString(1);
				corpcodesAllHoldings.add(corpCode);
			}
			
		} catch (Exception e) {
			System.out.println("기업코드를 조회하는데 문제가 발생했습니다.");
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("자원을 반납하는데 문제가 발생했습니다.");
				e.printStackTrace();
			}
			
			
		}
		
		
		
		return corpcodesAllHoldings ;
		
	}
	
	public TreeMap<Date, Integer> get10yrPrice(String col,String tableName) {
		String sql = "SELECT Date," + col+ " FROM " + tableName;
		TreeMap<Date, Integer> price10yr = new TreeMap<>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			st = conn.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()) {
				Date date=rs.getDate(1);
				int price=rs.getInt(2);
				price10yr.put(date, price);
			}
			

			
		} catch (Exception e) {
			System.out.println("기업코드를 조회하는데 문제가 발생했습니다.");
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("자원을 반납하는데 문제가 발생했습니다.");
				e.printStackTrace();
			}
			
			
		}
		
		return price10yr;
		
	}
	
	public List<String> get6yrJson(String corpCode,String type) {
		
		List<String> jsons = new ArrayList<>();
		String sql = "SELECT json_statement FROM finanace_statement  WHERE corp_code=? AND year=? AND cfs_ofs=?";
		
		LocalDate now = LocalDate.now();
		int thisYear = now.getYear();
		LocalDate dDay = LocalDate.of(thisYear, 4, 29);
		int latestBnsYear=0;
		
		if(now.isBefore(dDay)) {
			latestBnsYear = thisYear-2;
		} else latestBnsYear = thisYear-1;
		
		String[] years = new String[] {String.valueOf(latestBnsYear),
				String.valueOf(latestBnsYear-3)
				};
		
		for (String year : years) {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				pst = conn.prepareStatement(sql);
				pst.setString(1, corpCode);
				pst.setString(2, year);
				pst.setString(3, type);
				
				rs=pst.executeQuery();
				while(rs.next()) {
					jsons.add(rs.getString(1));
				}
						
			} catch (Exception e) {
				System.out.println("3년 재무제표를 불러오는데 문제가 발생했습니다.");
				e.printStackTrace();
			} finally {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					System.out.println("자원을 반납하는데 문제가 발생했습니다.");
					e.printStackTrace();
				}
				
				
			}
						
		}
		
		
		return jsons;
	}
	
	
	

}
