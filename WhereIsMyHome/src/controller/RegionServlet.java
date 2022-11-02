package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dto.DongCodeDto;
import dto.GugunCodeDto;
import service.RegionService;
import service.RegionServiceImpl;

/**
 * Servlet implementation class RegionServlet
 */
@WebServlet("/region/*")
public class RegionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	RegionService service = RegionServiceImpl.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String contextPath = request.getContextPath();
		String path = request.getRequestURI().substring(contextPath.length());
		switch(path) {
			case "/region/gugunList": gugunList(request, response); break;
			case "/region/dongList": dongList(request, response); break;
		}
	}
	
	private void gugunList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sidoName = request.getParameter("sidoName");
		
		List<GugunCodeDto> list = service.findGugunCodeList(sidoName);
		
		Gson gson=new Gson();
		String jsonStr = gson.toJson(list);
		response.getWriter().write(jsonStr);
	}

	private void dongList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String gugunName = request.getParameter("gugunName");
		
		List<DongCodeDto> list = service.findDongCodeList(gugunName);
		
		Gson gson=new Gson();
		String jsonStr = gson.toJson(list);
		response.getWriter().write(jsonStr);
	}
}
