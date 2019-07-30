$(function(){
   $(window).on('load resize',function () {
       var screen_height=$(window).height();
       var div=$('.login_center');
       div.css('top',screen_height/2-div.height()/2-60+"px");
   })
});
