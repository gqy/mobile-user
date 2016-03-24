
function validatePass(pass,passconf){
	var pwd_reg = /^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{6,18}$/;
	var patt1=new RegExp(" ");
	if(!pass){
		doError("密码不能为空");
		return false;
	}else if(patt1.test(pass)==true){
		doError("密码中不能包含空格");
		return false;
	}else if(patt1.test(passconf)==true){
		doError("确认密码中不能包含空格");
		return false;
	}else if(pass.length<6 || pass.length>18){
		doError("密码由6到18个字符组成");
		return false;
	}else if(!pwd_reg.test(pass)){
		doError("密码过于简单，包含数字，字母和符号组合");
		return false;
	}else if(pass!=passconf){
		doError("前后密码不一致，请重新输入");
		return false;
	}else{
		return true;
	}
}

$(".log button").click(function(){
	var pass=$("#pwd").val();
	var passconf=$("#pwdconf").val();
	if(validatePass(pass,passconf)== true){
		$(".error").css("visibility", "none"); 
		$(".mask_layer").css("display", "block");
		$.ajax({
			type: 'POST',           
			dataType : "json",
			url: context + "/h5/useroperate/resetpass",
			 data : {
	                phicommId:phicommId,
	                pass:pass,
	            },
			success: function(data, textStatus, jqXHR){
				$(".mask_layer").css("display", "none");
				if(data.status=='001'){
					doSuccess("重置密码成功，可以登录");
				}
				else{
					doError("重置密码失败，请重试");
				}
			},                                                              
			error: function(jqXHR, textStatus, errorThrown){
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