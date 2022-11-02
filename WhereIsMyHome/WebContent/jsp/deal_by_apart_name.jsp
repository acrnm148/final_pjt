<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <%@ include file="/jsp/import/common_import.jsp" %>

    <!--custom -->
    <link href="<%= contextPath %>/css/basic.css" rel="stylesheet">
    <link href="<%= contextPath %>/css/index.css" rel="stylesheet">
    <link href="<%= contextPath %>/css/deal.css" rel="stylesheet">
   <title>Document</title>
</head>
<body>
   <%@ include file="/jsp/import/header.jsp" %>

    <div class="selection_container d-flex justify-content-evenly align-items-center p-4">
      <!-- 아파트 이름 시작-->
      <div class="input-group">
        <input id="search-word" type="text" class="form-control" placeholder="아파트 명 검색">
        <button class="btn btn-success" type="button" id="search-btn">검색</button>
      </div>
      <!-- 아파트 이름 끝-->
    </div>
    <div id="map"></div>

    <section>
      <!-- 전체 페이지 -->
        <div class="deal-info-container">
          <div class="deal-info-list">
            <div id="deal-info-container-title">거래 정보</div>
            <div class="deal-info-list-elem"></div> 
          </div>
           
          <!-- 페이지네이션 --> 
          <div id="paginationWrapper"></div>  
        </div>

        <!-- 상세 페이지 -->
        <div class="deal-info-container detail-info-container" style="display:none;">
        </div>
    </section>
    
    <script type="text/javascript" src="<%=contextPath %>/js/util.js"></script>
    <%@ include file="/jsp/import/deal_detail.jsp"%>
    <%@ include file="/jsp/import/kakaomap.jsp"%>
    <script>
    var OFFSET = 0;

    var LIST_ROW_COUNT = 10; // 한페이지에 보여줄 페이지 수 ; 백엔드의 limit에 해당
    var PAGE_LINK_COUNT = 5; // pagination link를 몇개 만들건지
    var TOTAL_LIST_ITEM_COUNT = 0; // 총 건수
    var CURRENT_PAGE_INDEX = 1;

    window.onload = function(){
    	document.querySelector("#search-btn").onclick = function(){
    	  // console.log("push search button");
    	  if(validateHouseListByAptName()){
    	    houseListByAptName();
    	  } else{
    	  }
    	};
    }


/////////////////////////////////////////////
    ////////////////////// 법명동으로 아파트 정보 찾기 //////
    function validateHouseListByAptName(){
      let searchWord = document.querySelector("#search-word").value;
      if(searchWord.length == 0){
        alertify.warning("검색어를 입력해주세요");
        return false;
      }
      return true;
    }

    /** 법명동 아파트 조회 리스트 */
    async function houseListByAptName(){
      let searchWord = document.querySelector("#search-word").value;

      let url = "<%= contextPath %>/house/houseListByAptName";
      let urlParams = new URLSearchParams({
        searchWord: searchWord,
        limit: LIST_ROW_COUNT,
        offset: OFFSET
      });
      let fetchOptions = {
        method: "POST",
        body: urlParams
      };

      try{
        let response = await fetch(url, fetchOptions);
        let data = await response.json();
        // console.log(data);
        makeHouseListByAptNameHtml(data);
        // 페이지네이션
        houseDealByAptNameTotalCnt(searchWord);
      } catch(error){
        console.log(error);
        alertify.error("글 조회 과정에서 문제가 생겼습니다.");
      }

    }
    
    function makeHouseListByAptNameHtml(data){
        let dealInfoList = document.querySelector(".deal-info-list-elem");
        let innerList = ``;
        if(data.length == 0){
          innerList = `
          <hr>
          <div class="d-flex justify-content-center align-content-center">
            <div>검색 결과가 없어요</div>
          </div>
          `;
        } else {
          data.forEach((info) => {
            // console.log(info);
            let no = info.no;
            let dong = info.dong;
            let aptName = info.aptName;
            let code = info.code;
            let dealAmount = info.dealAmount.trim();
            let buildYear = info.buildYear;
            let dealYear = info.dealYear;
            let dealMonth = info.dealMonth;
            let dealDay = info.dealDay;
            let area = info.area;
            let floor = info.floor;
            let jibun = info.jibun;
            let houseNo = info.houseNo;
            
            let dealDate = makeDateStr(dealYear, dealMonth, dealDay, '/');
            let divId = "deal" + no;
            innerList += `
                  <hr>  
                  <div id="\${divId}" data-no=\${no} class="deal-info" detail-toggle="off">
                      <div class="name">\${aptName}</div>
                      <div class="cost">거래금액 : \${dealAmount}만원</div>
                      <div class="area">면적 : \${area}</div>
                      <div class="date">
                          <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" fill="currentColor" class="bi bi-calendar3 date-icon" viewBox="0 0 16 16">
                          <path d="M14 0H2a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM1 3.857C1 3.384 1.448 3 2 3h12c.552 0 1 .384 1 .857v10.286c0 .473-.448.857-1 .857H2c-.552 0-1-.384-1-.857V3.857z"/>
                          <path d="M6.5 7a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 3a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 3a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                          </svg>
                      \${dealDate}</div>
                  </div>
                `;
          });
        }
    dealInfoList.innerHTML = innerList;

    /** 상세 조회 이벤트 등록*/
    makeHouseDetailHtmlEventHandler();
  };

/////////////////////////////////// 페이지네이션
    /** 법정동으로 검색한 아파트 거래 정보 리스트 총 개수 */
    async function houseDealByAptNameTotalCnt(searchWord){
      let url = "<%= contextPath %>/house/houseListByAptNameTotalCnt";
      let urlParams = new URLSearchParams({
        searchWord: searchWord
      });

      let fetchOptions = {
        method: 'POST',
        headers: {
          'async' : 'true' 
        },
        body: urlParams,
      };
      
      try{
        let response = await fetch(url, fetchOptions);
        let data = await response.json();
        // console.log(data);
        TOTAL_LIST_ITEM_COUNT = data.totalCnt;
        makePaginationHtml(LIST_ROW_COUNT, PAGE_LINK_COUNT, CURRENT_PAGE_INDEX, TOTAL_LIST_ITEM_COUNT, "paginationWrapper"); // util.js에 있음
        
      } catch(error){ // js에서는 이렇게 error를 받아오나보다!
        console.log(error);
        alertify.error("글 조회 과정에서 문제가 생겼습니다.");
      }
    }


    /** 페이지를 옮겼을 때, 해당 페이지에 있는 게시글 목록을 보여준다. 
     * util.js의 makePaginationHtml() 에서 호출됨
    */
    function movePage(pageIndex){
      OFFSET = (pageIndex - 1) * LIST_ROW_COUNT; // 현재 페이지 인덱스 전까지는 다 뛰어넘음
      CURRENT_PAGE_INDEX = pageIndex;
      houseListByAptName(); // 바뀐 페이지를 다시 보여줌
    }

    </script>
</body>
</html>