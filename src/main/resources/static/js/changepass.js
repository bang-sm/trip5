var newpass = "";
var status = false;

// 클릭시
$(document).on('click', '.change', function(){
    
    console.log("버튼 눌러짐");
    newpass = $("#passinput").val();

    swal({
        title : "비밀번호를 변경하시겠습니까?",
        text : "",
        icon : "info",
        closeOnClickOutside : false,
        buttons :{
            cancle : {
                text : "변경하지 않기",
                value : false
            },
            confirm : {
                text : "변경하기",
                value : true,
            }
        }
    })
    .then((value) => {
        if(value){
            changepass(newpass)
            console.log("승인");
            swal("성공적으로 변경되었습니다!", {
                icon: "success",
            });
        }
    });
});

function changepass(newpass){
        
    $.ajax({
        type : "POST",
        url: "/user/passparam",
        // async: false,
        data: {
            "newPass" : newpass
        },
        success: function(data){
        }, error : function(request,status,error){
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
            
    });
}