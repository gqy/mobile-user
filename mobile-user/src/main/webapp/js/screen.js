
//滚动条在Y轴上的滚动距离
function getScrollTop(){
	return document.body.scrollTop;
}
//文档的总高度
function getScrollHeight(){
    return document.body.clientHeight;
}
//浏览器视窗口的宽度
function getWindowWidth(){
	var windowWidth = 0;
	     if(document.compatMode == "CSS1Compat"){//BackCompat：标准兼容模式关闭。 CSS1Compat：标准兼容模式开启
	    	 windowWidth = document.documentElement.clientWidth;
	     }
	     else {
	    	 windowWidth = document.body.clientWidth;
	     }
	     return windowWidth;
	}
//浏览器视口的高度
function getWindowHeight(){
var windowHeight = 0;
     if(document.compatMode == "CSS1Compat"){//BackCompat：标准兼容模式关闭。 CSS1Compat：标准兼容模式开启
    	 windowHeight = document.documentElement.clientHeight;
     }
     else {
    	 windowHeight = document.body.clientHeight;
     }
     return windowHeight;
}
//所有内容高度
function getAllDivHeight(){
	var height = 0;
	height=$("footer").outerHeight(true)+$("article").outerHeight(true)+$("header").outerHeight(true);
	return height;
}
//监听窗口变化
window.onresize = function(){
	$(".error p").text(getWindowWidth()+"......"+getWindowHeight()+"........"+getAllDivHeight());
	$(".error").css("visibility", "visible");
	//滑到底部时footer定于最下方,假定<footer>的height为60
	if((getWindowHeight()  - getAllDivHeight()) > 0)
	    $('footer').css('position','fixed');
	else
	    $('footer').css('position','relative');
}
////滑动监听
//window.onscroll = function(){
//	//滑到底部时footer定于最下方,假定<footer>的height为60
//	$(".error p").text(getScrollHeight()+"......"+getWindowHeight()+"........"+getAllDivHeight());
//	if((getScrollHeight()  - getAllDivHeight()) > 0)
//	    $('footer').css('position','fixed');
//	else
//	    $('footer').css('position','relative');
//}
$(document).ready(function(){ 
	$(".error p").text(getWindowWidth()+"......"+getWindowHeight()+"........"+getAllDivHeight());
	$(".error").css("visibility", "visible");
	//滑到底部时footer定于最下方,假定<footer>的height为60
	if((getWindowHeight()  - getAllDivHeight()) > 0)
	    $('footer').css('position','fixed');
	else
	    $('footer').css('position','relative');
});
//获取焦点