package com.dart.dongyeol.toDB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;


public class ConnectionMySQL {
	
	private Connection conn;
	private static final String USERNAME = "admin";//DBMS접속 시 아이디
    private static final String PASSWORD = "BAE20958746";//DBMS접속 시 비밀번호
    private static final String URL = "jdbc:mysql://finanacedb.cje5ijbkiorz.ap-northeast-2.rds.amazonaws.com:3306/finanace_data_kr";//DBMS접속할 db명
    
    public ConnectionMySQL() {
    	
    	try {
            System.out.println("생성자");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("드라이버 로딩 성공");
		} catch (Exception e) {		
            try {
                conn.close();
            } catch (SQLException e1) { }
			
		}
    	
    	
    	
    }
    
    
    
   
    
    public void insert(String corp_code,String year,String cfs_ofs,String json_response) {
    	String sql = "insert into finanace_statement values(?,?,?,?)";
    	
    	PreparedStatement pstmt = null;
    	try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, corp_code);
			pstmt.setString(2, year);
			pstmt.setString(3, cfs_ofs);
			pstmt.setString(4, json_response);
			
			int result = pstmt.executeUpdate();
            if(result==1) {
                System.out.println("데이터 삽입 성공!");
            }
		} catch (Exception e) {
            System.out.println("데이터 삽입 실패!");
		} finally {
			try {
                if(pstmt!=null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (Exception e2) {}
		}
    	
    }
    
    public void addColumn(String name) {
    	String sql = "AlTER TABLE market_kosdaq ADD name INT";
    	sql = sql.replace("name", "kosdaq"+name);
    	
    	try {
    		java.sql.Statement st = conn.createStatement();
    		int result =st.executeUpdate(sql);
    		System.out.println(result);
    		
		} catch (Exception e) {
            System.out.println("데이터 삽입 실패!");
            System.out.println(e.getMessage());
		} finally {
			
			try {

            
			} catch (Exception e2) {}
		}
    	
    	
    }
    
    public TreeMap<String, Integer> getColumn(String tableName) throws Exception{
			
    	
    	TreeMap<String, Integer> result = new TreeMap<>();
    	
    	String sql = "SELECT  COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setString(1, tableName);
    	ResultSet rs = pstmt.executeQuery();
    	
    	while(rs.next()) {
    		if(!rs.getString(1).equals("Date")) result.put(rs.getString(1).replaceAll("[^0-9]", ""), 0);
    	}
    
 	
    	return result;
    	
    }
	
    
    public void insertPrice(String queryInput) throws Exception {
    	String sql = "insert into market_kosdaq values(?)";
    	sql = sql.replace("?", queryInput);
    	Statement stmt = conn.createStatement();
    	try {
			int result =stmt.executeUpdate(sql);
            if(result==1) {
                System.out.println("데이터 삽입 성공!");
            }
		} catch (Exception e) {
            System.out.println("데이터 삽입 실패!");
            System.out.println(e.getMessage());
		} finally {
			try {
                if(stmt!=null && !stmt.isClosed()) {
                    stmt.close();
                }
            } catch (Exception e2) {}
		}
    	
    	
    }
    
    
}
