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
    	
        UserDto userDto = service.login(dto);

        Map<String, String> map = new HashMap<>();
        if( userDto != null ) { // login 성공
            // session 에 userDto 를 저장
            session.setAttribute("userDto", userDto);


            // client 에게 성공 결과를 json 으로 전달
            map.put("result", "success");

            // html 로 client 를 구성하므로 html 에서 server session 에 접근 X
            // 로그인 성공 직후에 client 에게 client 가 필요로 하는 사용자 정보를 내려줘야 한다.
            //map.put("userName", userDto.getUserName());
            //map.put("userProfileImageUrl", userDto.getUserProfileImageUrl());

            return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
        }

        map.put("result", "fail");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
    }
}