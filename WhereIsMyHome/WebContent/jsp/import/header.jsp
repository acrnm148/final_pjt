<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
            <a class="navbar-brand" href="<%=contextPath%>/jsp/index.jsp" style="color: white">구해줘 홈즈</a>

            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link" style="color: white" href="#">공지사항</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" style="color: white" href="#">오늘의 뉴스</a>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
    <!--navbar 끝-->