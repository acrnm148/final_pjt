async function getInfo() {
  let response = await fetch("<%= contextPath %>/user/getInfo");
  let data = await response.json(); //==Json.parse();

  if (data.result == "success") {
    console.log("로그인 상태");
    //내정보에 정보 넣기
    let myUserClsf = data.userClsf;
    return myUserClsf;
    //버튼 보이기
  } else if (data.result == "fail") {
    console.log("비로그인 상태");
    //버튼숨기기
  }
}

//////// 상세 조회 /////////

/** 법명동 아파트 상세 조회 - 이벤트 등록 */
function makeHouseDetailHtmlEventHandler() {
  document.querySelectorAll(".deal-info").forEach((el) => {
    el.onclick = function () {
      let no = this.getAttribute("data-no");
      console.log("click", no);
      houseDetail(no);
    };
  });
}

async function nearbyMarketList(guName) {
  let pageNo = 1;
  let offset = 100; // 페이지네이션 안함. 모든 병원 정보 다 가져옴.
  let url = "<%= contextPath %>/market";
  let urlParams = `?pageNo=\${pageNo}&offset=\${offset}&guName=\${guName}`;

  try {
    let response = await fetch(url + urlParams);
    let data = await response.json();
    console.log("market info: ", data);

    return data;
  } catch (error) {
    console.log(error);
  }
}
async function houseDetail(no) {
  let url = "<%= contextPath %>/house/houseDetail";
  let urlParams = new URLSearchParams({
    no: no,
  });
  let fetchOptions = {
    method: "POST",
    body: urlParams,
  };

  // console.log(url);

  try {
    let response = await fetch(url, fetchOptions);
    let data = await response.json();
    console.log("detail info : " + data);

    let marketData = await nearbyMarketList(data.gugunName);
    let userClsf = await getInfo(); // 회원 유형 - 준회원만 시장 정보 표시

    makeHouseDetailHtml(data, marketData, userClsf); // 상세 조회 페이지 표시

    // 모달 닫기 버튼
    let closeBtn = document.querySelector(".btn-close");
    closeBtn.addEventListener("click", function () {
      let detailInfo = document.querySelector(".detail-info-container");
      detailInfo.setAttribute("style", "display:none;");
    });
  } catch (error) {
    console.log(error);
    alertify.error("상세 정보 조회 과정에서 문제가 생겼습니다.");
  }
}

function makeHouseDetailHtml(data, marketDataList, userClsf) {
  let no = data.no;
  let lat = data.lat;
  let lng = data.lng;
  let houseNo = data.houseNo;
  console.log("In DetailHtml 좌표 ", lat, lng);
  /** 아파트 정보 */
  let dong = data.dong;
  let cityName = data.cityName;
  let gugunName = data.gugunName;
  let aptName = data.aptName;
  let address = cityName + " " + gugunName + " " + dong + " " + aptName;
  let jibun = data.jibun;
  let code = data.code;
  let buildYear = data.buildYear;
  let area = data.area;
  let floor = data.floor;

  /** 거래 정보 */
  let dealYear = data.dealYear;
  let dealMonth = data.dealMonth;
  let dealDay = data.dealDay;
  let dealAmount = data.dealAmount;
  let dealDate = makeDateStr(dealYear, dealMonth, dealDay, "/");

  /** 아파트 거래 정보 HTML */
  let apartInfo = [
    { name: "주소", value: address },
    { name: "지번", value: jibun },
    { name: "건축연도", value: buildYear + "년" },
    { name: "면적", value: area },
    { name: "층수", value: floor + " 층" },
    { name: "거래 금액", value: dealAmount + " 만원" },
    {
      name: "거래 날짜",
      value: `<svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" fill="currentColor" class="bi bi-calendar3 date-icon" viewBox="0 0 16 16">
                          <path d="M14 0H2a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM1 3.857C1 3.384 1.448 3 2 3h12c.552 0 1 .384 1 .857v10.286c0 .473-.448.857-1 .857H2c-.552 0-1-.384-1-.857V3.857z"/>
                          <path d="M6.5 7a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 3a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 3a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                          </svg>\${dealDate}`,
    },
  ];
  let apartInfoHtml = ``;
  apartInfo.forEach((info) => {
    apartInfoHtml += `
              <span class="list-group-item list-group-item-action" aria-current="true">
                  <div class="fs-5 fw-bolder mb-1">\${info.name}</div>
                  <small class="ps-2 mb-1 text-muted">\${info.value}</small>
              </span>
          `;
  });

  /** 근처 전통시장 상권 정보 HTML */
  let marketHTML = ``;
  marketDataList.forEach((marketData) => {
    let mName = marketData.mName;
    let guName = marketData.guName;
    let mAddr = marketData.mAddr;
    let mLat = marketData.lat;
    let mLng = marketData.lng;
    console.log(marketData);
    let marketInfo = [{ name: mName, value: mAddr }];
    marketInfo.forEach((info) => {
      marketHTML += `
                <span class="list-group-item list-group-item-action" aria-current="true">
                    <div class="fs-5 fw-bolder mb-1">\${info.name}</div>
                    <small class="ps-2 mb-1 text-muted">\${info.value}</small>
                </span>
            `;
    });
  });

  /** 상세 조회 화면이 나타나는 이벤트 */
  let detailInfo = document.querySelector(".detail-info-container");
  detailInfo.setAttribute("style", "display: flex; flex-direction: column;");
  let detailInfoHTML =
    `
          <div class="d-flex justify-content-between align-items-center nav-tabs" >
              <!-- 탭 -->
              <div class="nav" id="nav-tab" role="tablist">
                  <button class="nav-link active" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-home" 
                          type="button" role="tab" aria-controls="nav-home" aria-selected="true">거래 정보</button>
                  <button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-profile" 
                          type="button" role="tab" aria-controls="nav-profile" aria-selected="false">근처 시장 상권</button>
              </div>
              <!-- 닫기 버튼 -->
              <div class="px-2">
                  <button type="button" class="btn-close" aria-label="Close"></button>
              </div>
          </div>
          <!-- 탭 내용 -->
          <div class="flex-grow tab-content overflow-auto" id="nav-tabContent">
              <!-- 아파트 정보 -->
              <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
              ` +
    apartInfoHtml +
    `
              </div>
              <!-- 전통 시장 정보 -->
              <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                `;
  if (userClsf === "002") {
    detailInfoHTML += marketHTML;
  } else {
    detailInfoHTML += `
                <span class="list-group-item list-group-item-action" aria-current="true">
                    <small class="ps-2 mb-1 text-muted">준회원만 볼 수 있는 정보입니다.</small>
                </span>`;
  }
  detailInfoHTML += `
              </div>
                          
          </div>
  
        `;
  detailInfo.innerHTML = detailInfoHTML;

  // 지도에 표시
  markInMap(lat, lng, aptName);
}
