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
<title>이벤트 관리</title>

<!-- css -->
<link rel="stylesheet" href="<%=contextPath%>/css/basic.css" />
<link rel="stylesheet" href="<%=contextPath%>/css/index.css" />
<!-- bootstrap -->
<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
<link href="css/styles.css" rel="stylesheet" />
<!-- alertify -->
<script	src="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/alertify.min.js"></script>
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/alertify.min.css" />
<link rel="stylesheet"	href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/default.min.css" />
<link rel="stylesheet"	href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/semantic.min.css" />

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
					<a class="navbar-brand" href="./index.jsp" style="color: white">구해줘 홈즈</a>

					<ul class="navbar-nav me-auto mb-2 mb-lg-0">
						<li class="nav-item"><a class="nav-link" style="color: white"
							href="#">공지사항</a></li>
						<li class="nav-item"><a class="nav-link" style="color: white"
							href="#">오늘의 뉴스</a></li>
						<c:if test="${!empty userDto && userDto.user_clsf.equals('003') }">
							<li class="nav-item">
								<button class="btn btn-secondary" onclick="location.href='../eventuser?action=list';">이벤트 참여 사용자 관리</button>
							</li>
							<li class="nav-item">
								<button class="btn btn-secondary" onclick="location.href='./eventList.jsp';">이벤트 관리</button>
							</li>
						</c:if>
					</ul>
					<button id="btnNavInfo" class="btn btn-outline-success"
						data-bs-toggle="modal" data-bs-target="#infoModal"
						style="display: none">내정보</button>
					<button id="btnNavSignup" class="btn btn-outline-success signup"
						onclick="location.href = '../user/signup' ">Sign up</button>
					<button id="btnNavLogin" class="btn btn-outline-success login"
						data-bs-toggle="modal" data-bs-target="#loginModal">Log
						in</button>
					<button id="btnNavLogout" class="btn btn-outline-success logout"
						data-bs-toggle="modal" data-bs-target="#logoutModal"
						style="display: none">Log Out</button>
				</div>
			</div>
		</nav>
	</div>
	<!--navbar 끝-->
	<main>
	<div class="container-fluid px-4">
		<h1 class="mt-4">
			<strong>이벤트 관리</strong>
		</h1>
		<br><button class="btn btn-dark" type="button" id="btnInsertPage">이벤트 등록</button><br>
		<div>
				<table class="table">
					<thead>
						<tr>
							<th>번호</th>
							<th>이벤트명</th>
							<th>작성자</th>
							<th>사용여부</th>
							<th>url</th>
							<th>이미지</th>
							<th>작성일자</th>
						</tr>
					</thead>
					

					<tbody id="boardTbody"></tbody>
				</table>
			</div>
		</div>
	</div>
	</main>

	<!-- Modal Insert-->
	<div class="modal fade" id="boardInsertModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">이벤트 등록</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="mb-3">
						<label for="nameInsert" class="form-label"><strong>이벤트명*</strong></label> 
						<input type="text" class="form-control" id="nameInsert">
					</div>
					<div class="mb-3">
						<label for="authorSeqInsert" class="form-label"><strong>작성자</strong></label> 
						<input type="text" class="form-control" id="authorSeqInsert">
					</div>
					<div class="mb-3">
						<label for="use_ynInsert" class="form-label"><strong>사용여부*</strong></label> 
						<input type="text" class="form-control" id="use_ynInsert" placeholder="Y/N">
					</div>
					<div class="mb-3">
						<label for="urlInsert" class="form-label"><strong>url</strong></label> 
						<input type="text" class="form-control" id="urlInsert"></input>
					</div>
					<div class="mb-3">
						<label for="imgInsert" class="form-label"><strong>이미지</strong></label> 
						<input type="text" class="form-control" id="imgInsert"></input>
					</div>
					<button id="btnBoardInsert" type="button"
						class="btn btn-sm btn-primary float-end">등록</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal Detail-->
	<div class="modal fade" id="boardDetailModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">글 상세</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<table class="table table-hover">
						<tbody>
							<tr>
								<td>번호</td>
								<td id="eventSeqDetail">#</td>
							</tr>
							<tr>
								<td>이벤트명</td>
								<td id="nameDetail">#</td>
							</tr>
							<tr>
								<td>작성자</td>
								<td id="authorSeqDetail">#</td>
							</tr>
							<tr>
								<td>사용여부</td>
								<td id="useYnDetail">#</td>
							</tr>
							<tr>
								<td>url</td>
								<td id="urlDetail">#</td>
							</tr>
							<tr>
								<td>이미지</td>
								<td id="imgDetail">#</td>
							</tr>
							<tr>
								<td>작성일자</td>
								<td id="createdAtDetail">#</td>
							</tr>
						</tbody>
					</table>

					<button id="btnBoardUpdateForm" type="button" class="btn btn-sm btn-primary">수정</button>
					<button id="btnBoardDeleteConfirm" type="button" class="btn btn-sm btn-warning">삭제</button>
				</div>
			</div>
		</div>
	</div>
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
	<script type="text/javascript" src="../js/index.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
	<script src="js/scripts.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
	<script src="js/datatables-simple-demo.js"></script>
</body>

<script>
window.onload = function(){
	boardList();
	// insert page
	document.querySelector("#btnInsertPage").onclick = function(){
		
		document.querySelector("#nameInsert").value = "";
		document.querySelector("#authorSeqInsert").value = "";
		document.querySelector("#use_ynInsert").value = "";
		document.querySelector("#urlInsert").value = "";
		document.querySelector("#imgInsert").value = "";
		
		let modal = new bootstrap.Modal(
				document.querySelector("#boardInsertModal")
		);
		
		modal.show();
	}
	
	// insert
	document.querySelector("#btnBoardInsert").onclick = function(){
		if( validateInsert() ){
			boardInsert();
		}else{
			alert("입력을 확인해 주세요.");
		}
	}
	// delete
	document.querySelector("#btnBoardDeleteConfirm").onclick = function(){
		alertify.confirm("삭제 확인", "글을 삭제하시겠습니까?", 
				function(){
					boardDelete();
				}, 
				function(){
					console.log('user cancel');
				});
	}
}

/*
 * list
 */
async function boardList(){
	let url = '<%=contextPath%>/event/getEvent';
	let fetchOptions = {
		method: 'GET',
	}
	
	try{
		let response = await fetch( url , fetchOptions);
		let data = await response.json();
		console.log( data );
		makeListHtml( data );
		
	}catch( error ){
		console.log(error);
	}
}

function makeListHtml( list ){
	console.log("makeListHTml 들어옴");
	let listHtml = ``;
	
	list.forEach( el => {
		let eventSeq = el.event_seq;
		let name = el.name;
		let authorSeq = el.author_seq;
		let createdAt = el.createdAt;
		//let createdAtStr = makeDateStr( createdAt.date.year, createdAt.date.month, createdAt.date.day, '/');
		let useYn = el.use_yn;
		let url = el.url;
		let img = el.img;
		console.log(list);
		listHtml +=
			`<tr style="cursor:pointer" data-eventSeq=\${eventSeq}><td>\${eventSeq}</td><td>\${name}</td><td>\${authorSeq}</td><td>\${useYn}</td><td>\${url}</td><td>\${img}</td><td>\${createdAt}</td></tr>`;
	});
	
	document.querySelector("#boardTbody").innerHTML = listHtml;
	
	makeListHtmlEventHandler();
	//boardListTotalCnt();
}

function makeListHtmlEventHandler(){
	document.querySelectorAll("#boardTbody tr").forEach( el => {
		el.onclick = function(){
			let eventSeq = this.getAttribute("data-eventSeq");
			
			alertify.confirm("삭제 확인", "이벤트를 삭제하시겠습니까?", 
					function(){
						boardDelete(eventSeq);
					}, 
					function(){
						console.log('user cancel');
					});
		}
	})
}

/* 
 * insert
 */ 
function validateInsert(){
    // return true / false
    let isTitleInsertValid = false;
    let isContentInsertValid = false;

    let titleInsertValue = document.querySelector("#nameInsert").value;
    if( titleInsertValue.length > 0 ){
    	isTitleInsertValid = true;
    }

    let contentInsertValue = document.querySelector("#use_ynInsert").value;
    if( contentInsertValue.length > 0 ){
    	isContentInsertValid = true;
    }

    if( isTitleInsertValid && isContentInsertValid ){
      return true;
    }
    return false;
  }

async function boardInsert(){
	let name = document.querySelector("#nameInsert").value;
    let authorSeq = document.querySelector("#authorSeqInsert").value;
    let use_yn = document.querySelector("#use_ynInsert").value;
    let url_ = document.querySelector("#urlInsert").value;
    let img = document.querySelector("#imgInsert").value;

    // parameter 
    let urlParams = new URLSearchParams({
    	name: name,
    	authorSeq: authorSeq,
    	use_yn: use_yn,
    	url: url_,
    	img: img,
    });
    // fetch options
    let fetchOptions = {
      method: "POST",
      body: urlParams
    }

    let url = "<%= contextPath%>/event/registEvent";
    try{  
        let response = await fetch( url, fetchOptions);
        let data = await response.json(); // json => javascript object <= JSON.parse()
        if( data.result == "success"){ // login.jsp => boardMain.jsp 로 페이지 이동 ( 새로운 페이지(html....) 요청)
        	alert('글이 등록되었습니다.');  
          boardList();
        }else if( data.result == "fail"){
        	alert('글 등록 과정에서 오류가 발생했습니다.1');
        }            	
    }catch(error){
    	alert('글 등록 과정에서 오류가 발생했습니다.2');
    }

}

/* 
 * delete 
 */
async function boardDelete(eventSeq){
    let url = "<%=contextPath%>/event/deleteEvent";
    let urlParams = "?eventSeq=" + eventSeq;
    
	let fetchOptions = {
			method: 'POST',
		}
    try{
        let response = await fetch( url + urlParams, fetchOptions); // post
        let data = await response.json(); // json => javascript object <= JSON.parse()
        if( data.result == "success"){ // login.jsp => boardMain.jsp 로 페이지 이동 ( 새로운 페이지(html....) 요청)
          alertify.success('이벤트가 삭제되었습니다.');
          boardList();
        }else if( data.result == "fail"){
      	  alertify.error('해당 이벤트에 참여 중인 사용자가 존재합니다.');
        }            	
    }catch(error){
    	alertify.error('글 삭제 과정에서 오류가 발생했습니다.');
    }
}

/*
 *detail 
 */
 async function boardDetail(eventSeq){
		let url = '<%=contextPath%>/event/deleteEvent';
		let urlParams = '?eventSeq=' + eventSeq;

		let fetchOptions = {
			method: 'POST',
		}
		
		try{
			let response = await fetch( url + urlParams, fetchOptions);
			let data = await response.json();
			console.log( data );
			//makeDetailHtml( data );
			
		}catch( error ){
			console.log(error);
			alert('글 조회 과정에서 문제가 생겼습니다.')
		}
	}
	
</script>


</html>
