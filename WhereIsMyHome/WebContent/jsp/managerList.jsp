<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%-- jstl의 core 라이브러리를 사용하기 위해 taglib를 이용한다. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<%
  String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />

<title>이벤트 참여 사용자 관리</title>

<!-- css -->
<link rel="stylesheet" href="<%=contextPath%>/css/basic.css" />
<link rel="stylesheet" href="<%=contextPath%>/css/index.css" />
<!-- bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
<link href="css/styles.css" rel="stylesheet" />
<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
	
</head>
<body>
	<!--navbar 시작-->
	<div id="container">
		<nav class="navbar navbar-expand-lg navbar-light bg-black">
			<div class="container-fluid">
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo01"
					aria-controls="navbarTogglerDemo01" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
					<a class="navbar-brand" href="./jsp/index.jsp" style="color: white">구해줘
						홈즈</a>

					<ul class="navbar-nav me-auto mb-2 mb-lg-0">
						<li class="nav-item"><a class="nav-link" style="color: white"
							href="#">공지사항</a></li>
						<li class="nav-item"><a class="nav-link" style="color: white"
							href="#">오늘의 뉴스</a></li>
						<c:if test="${!empty userDto && userDto.user_clsf.equals('003') }">
							<li class="nav-item">
								<button class="btn btn-secondary"
									onclick="location.href='./eventuser?action=list';">이벤트
									참여 사용자 관리</button>
							</li>
							<li class="nav-item">
								<button class="btn btn-secondary"
									onclick="location.href='./jsp/eventList.jsp';">이벤트 관리</button>
							</li>
						</c:if>
					</ul>
					
				</div>
			</div>
		</nav>
	</div>
	<!--navbar 끝-->
	<main>
	<div class="container-fluid px-4">
		<h1 class="mt-4">
			<strong>참여 사용자 관리</strong>
		</h1>

		<div class="card mb-4">
			<div class="card-header">
				<i class="fas fa-table me-1"></i>

			</div>
			<div class="card-body">
				<table id="datatablesSimple">
					<thead>
						<tr>
							<th>번호</th>
							<th>사용자</th>
							<th>사용자명</th>
							<th>이벤트</th>
							<th>생성일자</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<th>번호</th>
							<th>사용자</th>
							<th>사용자명</th>
							<th>이벤트</th>
							<th>생성일자</th>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach items="${eventUsers }" var="eventUser" varStatus="vs">
							<tr>
								<td>${vs.count }</td>
								<td><a
									href="eventuser?action=detail&eventUserSeq=${eventUser.eventUserSeq }">${eventUser.user_email }</a></td>
								<td>${eventUser.user_name }</td>
								<td>${eventUser.name }</td>
								<td>${eventUser.user_register_date }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	</main>

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
	
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="js/scripts.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest"
		crossorigin="anonymous"></script>
	<script src="js/datatables-simple-demo.js"></script>
	<script>
		function getContextPath() {
			  var hostIndex = location.href.indexOf(location.host) + location.host.length;
			  return location.href.substring(hostIndex, location.href.indexOf("/", hostIndex + 1));
			}
	
			let contextPath = getContextPath();

		window.onload = function () {
			  getInfo();
		}
		async function getInfo() {
		  let response = await fetch(contextPath + "/user/getInfo");
		  let data = await response.json(); //==Json.parse();

		  if (data.result == "success") {
		    console.log("로그인 상태")
		    //버튼 보이기
		    
		  } else if (data.result == "fail") {
		    console.log("비로그인 상태");
		    alert("로그인하세요")
		    window.location.href = "./jsp/index.jsp";
		    
		  }
		}
	</script>
	
</body>
</html>
