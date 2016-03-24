
//验证用户名、邮箱、密码、验证码	
function valdate(username){
	if(!username){
		doError("用户名不能为空");
		return false; 	
	}else{
		return true;
	}
}
$("#findpass").click(function(){
	var person_val = $("#person").val();
	if(valdate(person_val)==true){
		$(".mask_layer").css("display", "block");
		$.ajax({
			type: 'POST',
			url: context+"/h5/useroperate/findpass",
			dataType: "json",
			data: {userName:person_val},
			success: function(data, textStatus, jqXHR){
				$(".mask_layer").css("display", "none");
				if(data.status=='001'){
					doSuccess(data.desc);
				}else{
					doError(data.desc);
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				$(".mask_layer").css("display", "none");
				doError("产生未知异常，请仔细检查网络");
			}
		});	
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
