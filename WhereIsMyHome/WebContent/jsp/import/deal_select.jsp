<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
    /** 시/구/군 option 리스트 가져오기*/
    async function gugunList(sidoName){
      let url = "<%= contextPath%>/region/gugunList";
      let urlParams = new URLSearchParams({
        sidoName: sidoName,
      });
      let fetchOptions = {
        method: "POST",
        body: urlParams,
      };
      try{
        let response = await fetch(url, fetchOptions);
        let data = await response.json();
        makeGugunListHtml(data);
      } catch( error ){
        console.log(error);
        alertify.error("시/구/군 목록을 조회하는 과정에서 오류가 발생하였습니다.");
      }
    }

    function makeGugunListHtml(data){
      let optionsHTML = `<option selected value="">시/구/군</option>`;
      data.forEach( (gugun) => {
        let gugunName = gugun.name;
        optionsHTML += `<option value="\${gugunName}">\${gugunName}</option>`;
      });
      let gugunSelect = document.querySelector("#gugun");
      gugunSelect.innerHTML = optionsHTML;
    }

    /** 법정동 option 리스트 가져오기*/
    async function dongList(gugunName){
      let url = "<%= contextPath%>/region/dongList";
      let urlParams = new URLSearchParams({
        gugunName: gugunName,
      });
      let fetchOptions = {
        method: "POST",
        body: urlParams,
      };
      try{
        let response = await fetch(url, fetchOptions);
        let data = await response.json();
        makeDongListHtml(data);
      } catch( error ){
        console.log(error);
        alertify.error("동 목록을 조회하는 과정에서 오류가 발생하였습니다.");
      }
    }

    function makeDongListHtml(data){
      let optionsHTML = `<option selected value="">동</option>`;
      data.forEach( (dong) => {
        let dongName = dong.name;
        optionsHTML += `<option value="\${dongName}">\${dongName}</option>`;
      });
      let dongSelect = document.querySelector("#dong");
      dongSelect.innerHTML = optionsHTML;
    }
</script>