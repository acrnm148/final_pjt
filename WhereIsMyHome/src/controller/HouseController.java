package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dto.HouseDealDetailDto;
import dto.HouseDealSimpleDto;
import service.HouseService;
import service.HouseServiceImpl;

@WebServlet("/house/*")
public class HouseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HouseService service = HouseServiceImpl.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		// 로그인 성공한 유저만 볼 수 있도록 함
		// 세션에 담긴 로그인 정보를 확인한다.
		HttpSession session = request.getSession();
//		UserDto userDto = (UserDto) session.getAttribute("userDto");
		
//		if(userDto != null) System.out.println("로그인 사용자 / 세션이 유효함");
//		else System.out.println("미로그인 사용자 / 세션이 유효하지 않음.");
		
		String contextPath = request.getContextPath();
		String path = request.getRequestURI().substring(contextPath.length());
		System.out.println(path);
		
		switch(path) {
			case "/house/houseListByDong": houseListByDong(request, response); break;
			case "/house/houseListByDongTotalCnt": houseListByDongTotalCnt(request, response); break;
			case "/house/houseListByAptName": houseListByAptName(request, response); break;
			case "/house/houseListByAptNameTotalCnt": houseListByAptNameTotalCnt(request, response); break;
			case "/house/houseDetail": houseDetail(request, response); break;
		}
	}
	private void houseListByDong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strLimit = request.getParameter("limit");
		String strOffset = request.getParameter("offset");
		String dong = request.getParameter("dong");
		
		int limit = Integer.parseInt(strLimit);
		int offset = Integer.parseInt(strOffset);

		Gson gson=new Gson();
		
		// dong 정보가 없다면 null 리스트
	    List<HouseDealSimpleDto> boardList = new ArrayList<>();
		if( !"".equals(dong) ) {
			HouseDealSimpleDto houseDealDto = new HouseDealSimpleDto();
			houseDealDto.setDong(dong);
			boardList = service.findHouseDealsByDongCode(houseDealDto, limit, offset);			
		}

		String jsonStr = gson.toJson(boardList);
		System.out.println("In BoardServlet: " + jsonStr);
		response.getWriter().write(jsonStr);		
	}
	private void houseListByDongTotalCnt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dong = request.getParameter("dong");
		
		HouseDealSimpleDto houseDealDto = new HouseDealSimpleDto();
		houseDealDto.setDong(dong);
		int totalCnt = service.houseDealsByDongCodeTotalCnt(houseDealDto);

		Gson gson=new Gson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("totalCnt", totalCnt);
		
		String jsonStr = gson.toJson(jsonObject);
		response.getWriter().write(jsonStr);		
	}
	private void houseDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strNo = request.getParameter("no");
		int no = Integer.parseInt(strNo);

		// dong 정보가 없다면 null 리스트
		HouseDealSimpleDto simpleDto = new HouseDealSimpleDto();
		simpleDto.setNo(no);
		HouseDealDetailDto detailDto = service.findHouseDealDetail(simpleDto);			

		Gson gson=new Gson();
		String jsonStr = gson.toJson(detailDto, HouseDealDetailDto.class);
		System.out.println("In BoardServlet: " + jsonStr);
		response.getWriter().write(jsonStr);		
	}
	private void houseListByAptName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strLimit = request.getParameter("limit");
		String strOffset = request.getParameter("offset");
		String searchWord = request.getParameter("searchWord");
		
		int limit = Integer.parseInt(strLimit);
		int offset = Integer.parseInt(strOffset);

		Gson gson=new Gson();
		
		// dong 정보가 없다면 null 리스트
	    List<HouseDealSimpleDto> boardList = new ArrayList<>();
		if( !"".equals(searchWord) ) {
			boardList = service.findHouseDealsByAptName(searchWord, limit, offset);			
		}

		String jsonStr = gson.toJson(boardList);
		System.out.println("In BoardServlet: " + jsonStr);
		response.getWriter().write(jsonStr);		
	}
	private void houseListByAptNameTotalCnt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchWord = request.getParameter("searchWord");
		
		int totalCnt = service.houseDealsByAptNameTotalCnt(searchWord);

		Gson gson=new Gson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("totalCnt", totalCnt);
		
		String jsonStr = gson.toJson(jsonObject);
		response.getWriter().write(jsonStr);		
	}
}
