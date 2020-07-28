/**
 * 
 */


$(document).ready(function(){

      btn = $('.following-box'); //버튼 아이디 변수 선언

      layer = $('.following-list'); //레이어 아이디 변수 선언

      btn.click(function(){

         layer.toggle(

           function(){layer.addClass('show')}, //클릭하면 show클래스 적용되서 보이기

           function(){layer.addClass('hide')} //한 번 더 클릭하면 hide클래스가 숨기기

         );

       });

     });