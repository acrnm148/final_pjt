<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.util.List, dto.CodeDto" %>
<%
	String contextPath = request.getContextPath();
	List<CodeDto> codeList = (List<CodeDto>)request.getAttribute("codeList");
%>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link rel="stylesheet" href="../css/basic.css" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
      crossorigin="anonymous"
    />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../css/signup.css" />
  </head>
  <body>
    <!--navbar 시작-->
    <div id="container">
      <nav class="navbar navbar-expand-lg navbar-light bg-black">
        <div class="container-fluid">
          <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarTogglerDemo01"
            aria-controls="navbarTogglerDemo01"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
            <a class="navbar-brand" href="../jsp/index.jsp" style="color: white">구해줘 홈즈</a>
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link" style="color: white" href="#">공지사항</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" style="color: white" href="#">오늘의 뉴스</a>
              </li>
            </ul>
            <!-- 
            <button class="btn btn-outline-success" onclick="location.href = 'signup.jsp' ">Sign up</button>
            <button class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#exampleModal">Log in</button>
            <button class="btn btn-outline-success" style="display: none">Log out</button>
             -->
          </div>
        </div>
      </nav>
    </div>
    <!--navbar 끝-->
    <!-- Login Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <form>
              <div class="mb-3">
                <label for="login-id" class="col-form-label">아이디</label>
                <input type="text" class="form-control" id="login-id" />
              </div>
              <div class="mb-3">
                <label for="login-password" class="col-form-label">비밀번호</label>
                <input type="password" class="form-control" id="login-password" />
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-success" data-bs-dismiss="modal">Close</button>
            <button type="button" class="btn btn-outline-success" data-bs-dismiss="modal">Log in</button>
          </div>
        </div>
      </div>
    </div>
    <!-- Login Modal 끝 -->

    <!--회원가입 폼 시작-->
    <div id="main">
      <main class="container">
        <div class="login-page">
          <h2>회원 정보 입력</h2>
          <div class="form">
            <form name="register-form" class="register-form">
              <form novalidate>
                <div class="mb-3">
                  <label for="userEmail" class="form-label">Email</label>
                  <input type="email" class="form-control" id="userEmail" placeholder="이메일을 입력해주세요." value="" />
                  <div class="valid-feedback">유효한 형식</div>
                  <div class="invalid-feedback">올바른 이메일 형식으로 입력.</div>
                </div>
                <div class="mb-3">
                  <label for="userPassword" class="form-label">Password</label>
                  <input type="password" class="form-control" id="userPassword" placeholder="비밀번호 입력해주세요." value="" />
                  <div class="valid-feedback">유효한 형식</div>
                  <div class="invalid-feedback">1개이상 특수문자, 영문, 숫자를 포함하고 길이은 8이상.</div>
                </div>
                <div class="mb-3">
                  <label for="userPassword2" class="form-label">Password Confirm</label>
                  <input type="password" class="form-control" id="userPassword2" placeholder="비밀번호를 다시 입력해주세요." value="" />
                  <div class="valid-feedback">유효한 형식</div>
                  <div class="invalid-feedback">패스워드가 다릅니다.</div>
                </div>
                <div class="mb-3">
                  <label for="userName" class="form-label">User Name</label>
                  <input type="text" class="form-control" id="userName" placeholder="이름을 입력해주세요." value="" />
                  <div class="valid-feedback">유효한 형식</div>
                  <div class="invalid-feedback">이름을 4글자 이상 입력하세요.</div>
                </div>
                <div>
                	<p>권한설정</p>
                	<div class="radio-wrapper">
	               	<% 
					for(int i=0; i<codeList.size(); i++){
						if( i==0){
	               	%>
	                  <label><input class="radios" type="radio" name="userClsf" value="<%=codeList.get(i).getCode() %>" checked><%=codeList.get(i).getCode_name() %> </label>
	                 <%	
						}
						else{
					%>
	                  <label><input class="radios" type="radio" name="userClsf" value="<%=codeList.get(i).getCode() %>"><%=codeList.get(i).getCode_name() %> </label>
	                  <%
						}
					}
	                  %>
                  </div>
                </div>
              </form>
              <button id="btnRegist" type="button">회원 등록</button>
            </form>
          </div>
        </div>
      </main>
    </div>
    <!--회원가입 폼 끝-->

    <!-- 푸터 시작 -->
    <footer>
      <div id="footer-wrap">
        <div id="footer-img"></div>
        <div>
          <div class="footer-text1">Find Us</div>
          <div class="footer-text2">(SSAFY) 서울시 강남구 테헤란로 멀티스퀘어</div>
          <div class="footer-text2">1544-9001</div>
        </div>
      </div>
    </footer>
    <!-- 푸터 끝 -->

    <script type="text/javascript" src="../js/signup.js"></script>
    
  </body>
</html>
