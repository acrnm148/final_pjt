package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dto.CodeDto;
import dto.UserDto;
import service.CodeService;
import service.CodeServiceImpl;
import service.UserService;
import service.UserServiceImpl;


@WebServlet("/user/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserService userService = UserServiceImpl.getinstance();
	CodeService codeService = CodeServiceImpl.getInstance();
	
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath();
		String path = request.getRequestURI().substring(contextPath.length());
		System.out.println("path : "+path);
		response.setContentType("text/html; charset=utf-8");
		
		
		switch(path) {
			case "/user/getInfo":getInfo(request,response);break;
			case "/user/logout":logout(request,response);break;
			case "/user/signup":signUp(request,response);break;
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String contextPath = request.getContextPath();
		String path = request.getRequestURI().substring(contextPath.length());
		System.out.println("path : "+path);
		
		switch(path) {
		case "/user/regist":regist(request,response);break;
		case "/user/login":login(request,response);break;
		case "/user/change":change(request,response);break;
		case "/user/unregist":unregist(request,response);break;
		
		}
	}
	
	protected void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("signup!!");
		List<CodeDto> codeList = codeService.getCodeList();
		
		request.setAttribute("codeList", codeList);
		request.getRequestDispatcher("/page/signup.html").forward(request, response);
	}
	
	protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("userName");
		String userEmail = request.getParameter("userEmail");
		String userPassword = request.getParameter("userPassword");
		String userClsf = request.getParameter("userClst");
		System.out.println(userClsf);
		UserDto userDto = new UserDto();
		userDto.setUser_name(userName);
		userDto.setUser_email(userEmail);
		userDto.setUser_password(userPassword);
		userDto.setUser_clsf(userClsf);
		

		System.out.println("???????????? ?????? email "+userEmail);
		
		int ret;
		ret = userService.userDupCheck(userDto);
		
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();

		if(ret>0) { //?????? ????????? ??????
			System.out.println("????????? ??????");
			jsonObject.addProperty("result", "duplicate");
			String jsonStr = gson.toJson(jsonObject);
			response.getWriter().write(jsonStr);
			
			return;
		}
		ret = userService.userRegister(userDto);
		        
		if(ret==1) { //db??? ?????? ??????
			jsonObject.addProperty("result", "success");
		}else {
			jsonObject.addProperty("result", "fail");
		}
		
		String jsonStr = gson.toJson(jsonObject);
		response.getWriter().write(jsonStr);

	}
	
	protected void unregist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        UserDto userDto = (UserDto)session.getAttribute("userDto");
        
        Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		
        if(userDto==null) {
        	System.out.println("????????? ????????? ??????????????????");
        	jsonObject.addProperty("result", "unlogin");
        	String jsonStr = gson.toJson(jsonObject);
    		response.getWriter().write(jsonStr);
    		return;
        }
		String userName = request.getParameter("userName");
		String userEmail = userDto.getUser_email();
		String userPassword = request.getParameter("userPassword");
		if(!userName.equals(userDto.getUser_name()) || !userPassword.equals(userDto.getUser_password())) {
			System.out.println("????????? ?????? ?????? ????????????");
        	jsonObject.addProperty("result", "mismatch");
        	String jsonStr = gson.toJson(jsonObject);
    		response.getWriter().write(jsonStr);
    		return;
		}	
    		
		System.out.println("?????? ?????? ??????");	
		int ret;
		ret = userService.userUnregist(userEmail, userPassword);
		if(ret==1) { //db??? ?????? ??????
			jsonObject.addProperty("result", "success");
			session.invalidate(); //?????? ?????? ??????
		}else {
			jsonObject.addProperty("result", "fail");
		}
		
		String jsonStr = gson.toJson(jsonObject);
		response.getWriter().write(jsonStr);
		
		
		
	}
	
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginEmail = request.getParameter("loginEmail");
		String loginPassword = request.getParameter("loginPassword");
		
		UserDto userDto;
		userDto = userService.userLogin(loginEmail, loginPassword);
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();

		if(userDto!=null) { 
			System.out.println("????????? ?????? "+userDto.getUser_email());
			
			//????????? ????????????
			HttpSession session = request.getSession();
            session.setAttribute("userDto", userDto);
			
			jsonObject.addProperty("result", "success");
			
		}else {
			jsonObject.addProperty("result", "fail");
		}
		
		String jsonStr = gson.toJson(jsonObject);
		response.getWriter().write(jsonStr);
		
	}
	
	protected void getInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        UserDto userDto = (UserDto)session.getAttribute("userDto");
        
        Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
        
        if(userDto==null) { //????????? ?????? ??????
        	jsonObject.addProperty("result", "fail");
        }else {
        	jsonObject.addProperty("result", "success");
        	jsonObject.addProperty("userEmail", userDto.getUser_email());
        	jsonObject.addProperty("userName", userDto.getUser_name());
        	System.out.println("????????? ????????? : "+userDto.getUser_name());
        	jsonObject.addProperty("userProfile", userDto.getUser_profile_image_url());
        	jsonObject.addProperty("userSeq", userDto.getUser_seq());
        	jsonObject.addProperty("userRegisterDate", userDto.getUser_register_date().toString());
        	jsonObject.addProperty("userClsf", userDto.getUser_clsf());
        }
        
        String jsonStr = gson.toJson(jsonObject);
		response.getWriter().write(jsonStr);
        
	}
	
	protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
        UserDto userDto = (UserDto)session.getAttribute("userDto");
        if(userDto==null) {
        	System.out.println("?????? ???????????????");
        	session.invalidate();
        }else {
        	session.invalidate();
        	System.out.println("?????? ???????????????");
        }
        Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
        
       
    	jsonObject.addProperty("result", "success");
    	String jsonStr = gson.toJson(jsonObject);
		response.getWriter().write(jsonStr);
	}
	
	protected void change(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
        UserDto userDto = (UserDto)session.getAttribute("userDto");
        Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
        if(userDto==null) {
        	System.out.println("????????? ????????? ??????????????????");
        	jsonObject.addProperty("result", "unlogin");
        	String jsonStr = gson.toJson(jsonObject);
    		response.getWriter().write(jsonStr);
    		return;
        }
        String userEmail = userDto.getUser_email();
        String changePassword = request.getParameter("userPassword");
		String changeUserName = request.getParameter("userName");
		System.out.println("?????? ????????? :"+changePassword);
		int ret;
		ret = userService.userChange(userEmail,changePassword,changeUserName);
		if(ret==1) {
			jsonObject.addProperty("result", "success");
	    	String jsonStr = gson.toJson(jsonObject);
			response.getWriter().write(jsonStr);
			
			UserDto newUserDto= userService.userLogin(userEmail, changePassword);
			if(newUserDto!=null) { 
				session.setAttribute("userDto", newUserDto); //?????? ?????????
			}else {
				System.out.println("????????? ?????? ???????????? ??????");
			}
			
		}else {
			System.out.println("????????????, ????????? ????????? : "+ret);
			jsonObject.addProperty("result", "fail");
	    	String jsonStr = gson.toJson(jsonObject);
			response.getWriter().write(jsonStr);
		}
		
		
	}
	

}
