package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dto.EventDto;
import service.EventServiceImpl;


@WebServlet("/event/*")
public class EventController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	EventServiceImpl eventService= EventServiceImpl.getinstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath();
		String path = request.getRequestURI().substring(contextPath.length());
		System.out.println("path : "+path);
		response.setContentType("text/html; charset=utf-8");
		
		
		switch(path) {
			case "/event/getEvent":getEvent(request,response);break; //얘만 eventController에
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String contextPath = request.getContextPath();
		String path = request.getRequestURI().substring(contextPath.length());
		System.out.println("path : "+path);
		
		
		switch(path) {
			//나머지는 adminController에
			case "/event/registEvent":registEvent(request,response);break;
			case "/event/deleteEvent":deleteEvent(request,response);break;
		}
		
	}
	
	protected void getEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("getEvent 함수실행");
		
		List<EventDto> eventDto;
		eventDto = eventService.getEvent();
		
		Gson gson=new Gson();
		String jsonStr = gson.toJson(eventDto);
		response.getWriter().write(jsonStr);
	
	}
	
	protected void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("deleteEvent 함수실행");
		
		int eventSeq = Integer.parseInt(request.getParameter("eventSeq"));
		
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		int ret= eventService.eventDelete(eventSeq);
		
		if(ret==1) {
			jsonObject.addProperty("result", "success");
			
		}else {
			jsonObject.addProperty("result", "fail");
		}
		String jsonStr = gson.toJson(jsonObject);
		response.getWriter().write(jsonStr);
		
	}
	
	protected void registEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("registEvent 함수실행");
		
		String name=request.getParameter("name");
		int authorSeq = Integer.parseInt(request.getParameter("authorSeq"));
		String use_yn=request.getParameter("use_yn");
		String url=request.getParameter("url");
		String img=request.getParameter("img");
		
		EventDto eventDto = new EventDto();
		eventDto.setAuthor_seq(authorSeq);
		eventDto.setName(name);
		eventDto.setUse_yn(use_yn);
		eventDto.setUrl(url);
		eventDto.setImg(img);
		
		int ret=eventService.eventRegister(eventDto);
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		if(ret==1) {
			jsonObject.addProperty("result", "success");
			
		}else {
			jsonObject.addProperty("result", "fail");
		}
		String jsonStr = gson.toJson(jsonObject);
		response.getWriter().write(jsonStr);
	}


}
