const SUCCESS = 1;

function getContextPath() {
  var hostIndex = location.href.indexOf(location.host) + location.host.length;
  return location.href.substring(hostIndex, location.href.indexOf("/", hostIndex + 1));
}

let contextPath = getContextPath();

window.onload = function () {
	
	document.querySelector("#btnNavSignup").onclick = function() {
		window.location.href="/register";
	}
	
  getInfo();
  getEvent();
  document.querySelector("#btnLogin").onclick = function () {
    let userEmail = document.querySelector("#userEmail").value;
    let userPassword = document.querySelector("#userPassword").value;
    console.log( userEmail, userPassword )

    // 유효성 검사 => post 전송
    if (validate()) {
      login();
    } else {
      alertify.error("아이디나 비밀번호를 확인하세요.");
    }
  };

  document.querySelector("#btnLogout").onclick = function () {
    logout();
  };
};

function validate() {
  // return true / false
  let loginEmailValid = false;
  let loginPasswordValid = false;

  let userEmail = document.querySelector("#userEmail").value;
  if (userEmail.length > 0) {
    loginEmailValid = true;
  }
  let userPassword = document.querySelector("#userPassword").value;
  if (userPassword.length > 0) {
    loginPasswordValid = true;
  }

  if (loginEmailValid && loginPasswordValid) {
    return true;
  }
  return false;
}

async function login() {
	
  let userEmail = document.querySelector("#userEmail").value;
  let userPassword = document.querySelector("#userPassword").value;
  let urlParams = new URLSearchParams({
	  userEmail: userEmail,
	  userPassword: userPassword,
  });

  let fetchOptions = {
    method: "POST",
    body: urlParams,
  };

  let response = await fetch("/login", fetchOptions);
  let data = await response.json(); //==Json.parse();
  if (data.result == "success") {
    window.location.href = "/main";
  } else if (data.result == "fail") {
    alert("아이디나 비밀번호를 확인하세요.");
  }
}

async function logout() {
  let response = await fetch("/logout");
  let data = await response.json(); //==Json.parse();

  if (data.result == "success") {
    window.location.href = "/main";
  } else if (data.result == "fail") {
    alert("로그아웃 실패");
  }
}

async function getInfo() {
  let response = await fetch("/users");
  let data = await response.json(); //==Json.parse();

  if (data.result == SUCCESS) {
    console.log("로그인 성공");
    //내정보에 정보 넣기
    console.log(data); //test
    makeInfo(data);
    //버튼 보이기
    document.querySelector("#btnNavInfo").setAttribute("style", "display:block");
    document.querySelector("#btnNavSignup").setAttribute("style", "display:none");
    document.querySelector("#btnNavLogin").setAttribute("style", "display:none");
    document.querySelector("#btnNavLogout").setAttribute("style", "display:block");
  } else {
    console.log("비로그인 상태");
    //버튼숨기기
    document.querySelector("#btnNavInfo").setAttribute("style", "display:none");
    document.querySelector("#btnNavSignup").setAttribute("style", "display:block");
    document.querySelector("#btnNavLogin").setAttribute("style", "display:block");
    document.querySelector("#btnNavLogout").setAttribute("style", "display:none");
  }
}

function makeInfo(data) {
  let myEmail = data.userEmail;
  console.log(data.userEmail);
  document.querySelector("#myEmail").innerText = myEmail;
  let myName = data.userName;
  document.querySelector("#myName").innerText = myName;
  let myProfile = data.userProfile;
  document.querySelector("#myProfile").src = ".." + myProfile;
  let mySeq = data.userSeq;
  document.querySelector("#mySeq").innerText = mySeq;
  let myRegisterDate = data.userRegisterDate;
  document.querySelector("#myregisterDate").innerText = myRegisterDate;
  let myUserClsf = data.userClsf;
  document.querySelector("#myUserClsf").innerText = myUserClsf;
}

async function getEvent() {
  let response = await fetch("/event/getEvent");
  let data = await response.json(); //==Json.parse();

  let length = data.length;
  let eventWrapper = document.querySelector("#event-wrapper");
  if (length > 0) {
    for (i = 0; i < length; i++) {
      let event = data[i];
      let div = document.createElement("div");

      if (i == 0) {
        div.className = "carousel-item active";
      } else {
        div.className = "carousel-item";
      }
      let a = document.createElement("a");
      a.setAttribute("href", event.url);
      let img = document.createElement("img");
      img.className = "d-block w-100";
      img.setAttribute("src", event.img);
      a.appendChild(img);
      div.appendChild(a);
      eventWrapper.appendChild(div);
    }
  } else {
  }
}
