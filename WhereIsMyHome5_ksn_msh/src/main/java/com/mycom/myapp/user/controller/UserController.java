package com.mycom.myapp.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	private final int SUCCESS = 1;
	private final int FAIL = -1;
	
	// HttpStatus Code로 처리 결과 Wrapping하기 위해 ResponseEntity를 사용
	@PostMapping(value="/users")
	public ResponseEntity<Map<String, String>> regist(UserDto dto) {
		System.out.println(dto);
		UserResultDto userResultDto = userService.userRegister(dto);
		Map<String, String> map = new HashMap<>();
		
		if (userResultDto.getResult() == SUCCESS) {
			map.put("result", "success");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		} else {
			map.put("result", "fail");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/users")
	public ResponseEntity<UserResultDto> getInfo(HttpSession session) {
		UserDto userDto = (UserDto)session.getAttribute("userDto");
		System.out.println("getInfo:"+userDto);
		
		//UserResultDto userResultDto = userService.userInfo(userDto);
		//Map<String, String> map = new HashMap<>();
		UserResultDto userResultDto = new UserResultDto();
		
		if (userDto != null) {
			userResultDto.setResult(SUCCESS);
			userResultDto.setDto(userDto);
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.OK);
		} else {
			userResultDto.setResult(FAIL);
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.NOT_FOUND);
		}
	}
}
