package com.dart.dongyeol.toDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ConnectionMySQL {
	
	private Connection conn;
	private static final String USERNAME = "root";//DBMS접속 시 아이디
    private static final String PASSWORD = "BAE2095@";//DBMS접속 시 비밀번호
    private static final String URL = "jdbc:mysql://localhost:3306/finanace_data_kr";//DBMS접속할 db명
    
    public ConnectionMySQL() {
    	
    	try {
            System.out.println("생성자");
            //Class.forName("com.mysql.jdbc.Driver");
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
	
}
