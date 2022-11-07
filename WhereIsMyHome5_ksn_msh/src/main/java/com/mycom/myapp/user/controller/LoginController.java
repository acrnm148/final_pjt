package com.mycom.myapp.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.service.LoginService;

@RestController
public class LoginController {

    @Autowired
    LoginService service;

    // "result":"success" / "fail"
    @PostMapping(value="/login")
    public ResponseEntity<Map<String, String>> login(UserDto dto, HttpSession session){
    	
    	System.out.println("login:"+dto);
    	
        UserDto userDto = service.login(dto);
        System.out.println(userDto);

        Map<String, String> map = new HashMap<>();
        if( userDto != null ) { // login 성공
            session.setAttribute("userDto", userDto);
            map.put("result", "success");
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        }

        map.put("result", "fail");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
    }
}