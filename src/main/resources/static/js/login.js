$(document).ready(function (){
     
    $("#loginbtn").click(function(){
        if($("#loginid").val() == ""){
            alert("로그인 아이디를 입력해주세요");
            $("#loginid").focus();
        }else if($("#loginpwd").val() == ""){
            alert("로그인 비밀번호를 입력해주세요");
            $("#loginpwd").focus();
        }else{
//            $("#loginfrm").attr("action", "url value='/user/login'");
            $("#loginfrm").submit();
        }
    });
         
});