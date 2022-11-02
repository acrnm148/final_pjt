package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dto.EventUserDto;
import service.EventUserService;
import service.EventUserServiceImpl;

@WebServlet("/eventuser")
public class EventUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private final EventUserService eventUserService = EventUserServiceImpl.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        process(request, response);
	}
	private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
	        case "insert":
	            doInsert(request, response);
	            break;
            case "list":
                doList(request, response);
                break;
            case "detail":
                doDetail(request, response);
                break;
            case "remove":
                doRemove(request, response);
                break;
        }
    }
	
	private void doInsert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		int eventSeq = Integer.parseInt(request.getParameter("eventSeq"));
		int userSeq = Integer.parseInt(request.getParameter("userSeq"));
		
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		
        try {
        	eventUserService.insert(eventSeq, userSeq);
        	System.out.println("이벤트에 사용자 참여 완료");
        	jsonObject.addProperty("result", "success");
        	String jsonStr = gson.toJson(jsonObject);
    		response.getWriter().write(jsonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
            jsonObject.addProperty("result", "fail");
            String jsonStr = gson.toJson(jsonObject);
    		response.getWriter().write(jsonStr);
        }
    }
	
	private void doList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		System.out.println("doList 실행");
        try {
            List<EventUserDto> eventUsers = eventUserService.select();
            request.setAttribute("eventUsers", eventUsers);
            RequestDispatcher disp = request.getRequestDispatcher("./jsp/managerList.jsp"); //관리자 목록 jsp
            disp.forward(request, response);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
	private void doDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	int eventUserSeq = Integer.parseInt(request.getParameter("eventUserSeq"));

        try {
        	EventUserDto eventUserDto = eventUserService.detail(eventUserSeq);
        	System.out.println(eventUserDto);
            request.setAttribute("eventUserDto", eventUserDto);
            RequestDispatcher disp = request.getRequestDispatcher("./jsp/managerDetail.jsp"); //관리자 상세 jsp
            disp.forward(request, response);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
    
    private void doRemove(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	int eventUserSeq = Integer.parseInt(request.getParameter("eventUserSeq"));
    	
        try {
        	eventUserService.delete(eventUserSeq);
            RequestDispatcher disp = request.getRequestDispatcher("/eventuser?action=list"); //관리자 삭제 
            disp.forward(request, response);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
}
