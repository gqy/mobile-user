$("#back").bind("click",function(){
		  geqinyangObj.finishSelf();
	  });

$('.wrapper .qq').bind('click',function(){
		if(openqq==''){
			location.href="http://cloud.czwap.net//mobile-user/union/toqqbind?phicommId="+phicommId;
		}else{
			$(".wrap1, .mask_layer").show();
		}
	});
	
//qq弹窗确定
$('#btn_bind_ok').bind('click',function(){
	if(userEmail=='' && userPhoneNumb==''){
		  $(".wrap, .mask_layer").hide();
		doError('无法解绑，仅有三方账号');
	}else{
		$.ajax({
			type: 'POST',           
			contentType: 'application/x-www-form-urlencoded',
			url: context + "/h5/useroperate/releaseqqbind",
			data:{"phicommId":phicommId},
			success: function(data, textStatus, jqXHR){
				 $(".wrap, .mask_layer").hide();
				if(data.status=='001'){
					//var t2 = window.setInterval("settime()",1000);
					doSuccess(data.desc);
				}else{
				    doError(data.desc);}
				
			
			},                                                              
			error: function(jqXHR, textStatus, errorThrown){
				 $(".wrap, .mask_layer").hide();
				doError("产生未知异常，请仔细检查网络");
			}
		});
	}
});
//弹框取消
$(".delete").bind('click',function() {
    $(".wrap, .mask_layer").hide();
});

$('.wrapper .weixin').bind('click',function(){
	if(openweixin==''){
		
	}else{
		$(".wrap1, .mask_layer").show();
	}
});
	
$(document).ready(function(){ 
		if(openqq==''){
			$("article .qq img").attr("src","../imgs/qq_dark.png");
			$("#q").text('未绑定');
			$("#q").css('color',"#fc8d32");
		}
	
		if(openweixin==''){
			$("article .weixin img").attr("src","../imgs/wechat_dark.png");
			$("#w").text('未绑定');
			$("#w").css('color',"#fc8d32");
		}
	});


function doSuccess(show){
	$(".error p").text(show);
	$(".error").css("visibility", "visible"); 
	$('.error img').attr("src","../imgs/success.png");
	$(".error p").css("color","#fc8d32");
}
function doError(show){
	$(".error p").text(show);
	$(".error").css("visibility", "visible"); 
	$('.error img').attr("src","../imgs/Error_prompt.png");
	$(".error p").css("color","red");
}