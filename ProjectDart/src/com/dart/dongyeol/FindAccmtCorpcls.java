package com.dart.dongyeol;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import lombok.Getter;

@Getter
public class FindAccmtCorpcls {
	
	
	@SuppressWarnings("unused")
	private List<String> corpCls;
	@SuppressWarnings("unused")
	private List<String> accMt;
	
	
	


	public FindAccmtCorpcls() {
		super();
		try {
			findCls_Accmt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void findCls_Accmt() throws Exception  {
		
		HashMap<String, String> corpName_corpCode = findCodeName();
		List<String> corpCls = new ArrayList<>();
		List<String> accMt = new ArrayList<>();
		
		for(String key:corpName_corpCode.keySet()) {
			
			//요청 url 생성
			String url = "https://opendart.fss.or.kr/api/company.json?crtfc_key=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx&corp_code=name";
			url = url.replace("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx","182023b0d0bb93439f2cdd9f8f8ed93215fb72dd");
			url = url.replace("name",corpName_corpCode.get(key));
			
			//연결후 인풋스트림 받은후 저장
			URL requesturl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) requesturl.openConnection();
			BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			StringBuffer sb = new  StringBuffer();
			String inputline = "";
			
			while((inputline=bf.readLine()) != null) sb.append(inputline);
			bf.close(); // inputstream을 받은 객체에서 클로스 메서드를 실행하면 연결이 해제된다.
			String response = sb.toString();
			
			//json 형식 응답데이터 처리
			JSONParser parser = new JSONParser();
			JSONObject corpIntroduction = (JSONObject) parser.parse(response);
			
			String corp_cls = corpIntroduction.get("corp_cls").toString();
			String acc_mt = corpIntroduction.get("acc_mt").toString();
			
			corpCls.add(corp_cls);
			accMt.add(acc_mt);
			
			Thread.sleep(250);
						
		}
		
		this.corpCls = corpCls;
		this.accMt=accMt;
		
	}


	public  HashMap<String, String> findCodeName() {
		HashMap<String, String> corpName_corpCode = new HashMap<>();
		
		try {	
			FileInputStream file  = new FileInputStream("./src/com/dart/dongyeol/CropCode.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			int rows = sheet.getPhysicalNumberOfRows();
			
			for(int i=1;i<rows;i++) {
				XSSFRow row = sheet.getRow(i);
				if(row == null) continue;
				String code = row.getCell(0).toString();
				String name = row.getCell(1).toString();
				
				corpName_corpCode.put(name, code);
				
			}
			
			file.close();
			workbook.close();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return corpName_corpCode;
		
	}

}
