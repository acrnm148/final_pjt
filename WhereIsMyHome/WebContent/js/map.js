// 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

/* 카카오맵 */
var mapContainer = document.getElementById("map"), // 지도를 표시할 div
  mapOption = {
    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    level: 3, // 지도의 확대 레벨
  };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
function makeOverListener(map, marker, infowindow) {
  return function () {
    infowindow.open(map, marker);
  };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow) {
  return function () {
    infowindow.close();
  };
}

///////////////////////////////////////////////////////
function markInMap(lat, lng, aptName) {
  console.log("좌표", lat, lng);
  if (marker != null) {
    marker.setMap(null); // 이전 마커를 없앰
  }

  var coords = new kakao.maps.LatLng(lat, lng);

  // 결과값으로 받은 위치를 마커로 표시합니다
  var marker = new kakao.maps.Marker({
    map: map,
    position: coords,
  });

  // 인포윈도우로 장소에 대한 설명을 표시합니다
  var infowindow = new kakao.maps.InfoWindow({
    content: `<h5 style="font-weight:600;text-align:center;">\${aptName}</h5>`,
  });

  // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
  // 이벤트 리스너로는 클로저를 만들어 등록합니다
  // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
  kakao.maps.event.addListener(
    marker,
    "mouseover",
    makeOverListener(map, marker, infowindow)
  );
  kakao.maps.event.addListener(marker, "mouseout", makeOutListener(infowindow));
  // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
  marker.setMap(map);
  map.setCenter(coords);
}
