package controller.proxy;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.google.gson.Gson;

import dto.MarketDto;
import util.market.MarketSAXHandler;

@WebServlet("/market")
public class MarketProxyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final String SERVICE_URL = "http://openAPI.seoul.go.kr:8088"; 
//			"http://data.seoul.go.kr/dataList/OA-1176/S/1/datasetView.do";
	private final String SERVICE_KEY = "70496f65686c616c3130325944626e6a";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNoStr = request.getParameter("pageNo");
		if(pageNoStr == null) pageNoStr = "1";
		String offsetStr = request.getParameter("offset");
		if(offsetStr == null) offsetStr = "1";
		String guName = request.getParameter("guName");
		if(guName == null) guName = "";
		int pageNo = Integer.parseInt(pageNoStr);
		int offset = Integer.parseInt(offsetStr);
		
		int startIndex = offset * (pageNo - 1) + pageNo;
		int endIndex = startIndex + offset;
		StringBuilder sb = new StringBuilder();
		sb.append(SERVICE_URL)
			.append("/").append(SERVICE_KEY)
			.append("/").append("xml") // type
			.append("/").append("ListTraditionalMarket") // service
			.append("/").append(startIndex) // start index
			.append("/").append(endIndex) // end index
			.append("/").append(guName); // gu name
		// dao -> service 에서 list<MarketDto> 얻어오기
		System.out.println(sb.toString());
		
		URL url = new URL(sb.toString());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		System.out.println(con.getResponseCode()); // 정상 확인
		
		BufferedReader br = null;
		if( con.getResponseCode() == 200 ) {
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else {// 200이 아닌 경우
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		StringBuilder result = new StringBuilder();
		String line;
		while((line = br.readLine()) != null) {
			result.append(line);
		}
		br.close();
		con.disconnect();
		
		// api response 확인
		System.out.println(result);
		
//		sendXML(response, result.toString());
		sendJson(response, result.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void sendXML(HttpServletResponse response, String result) throws ServletException, IOException {
		response.setContentType("application/xml; charset=utf-8");
		response.getWriter().write(result.toString());
	}
	
	private void sendJson(HttpServletResponse response, String result) throws ServletException, IOException {
		// xml => json		
		response.setContentType("applcation/json; charset=utf-8");
		
		// xml => java ArrayList 객체로 필요한 항목만 추출
		// SAX parser
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			MarketSAXHandler handler = new MarketSAXHandler();
			// 한글 처리에 대한 부분
			InputStream is = new ByteArrayInputStream( result.getBytes(StandardCharsets.UTF_8) );
			parser.parse( is, handler );
			
			List<MarketDto> marketList = handler.getMarketList();
			for(MarketDto market : marketList) {
				System.out.println(market);
			}
			
			// java List => json
			Gson gson = new Gson();
			String jsonStr = gson.toJson(marketList); // json
			response.getWriter().write(jsonStr);
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
}
