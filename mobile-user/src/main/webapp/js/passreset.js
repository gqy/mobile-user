
function validatePass(pass,passconf,passold){
	var pwd_reg = /^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{6,18}$/;
	var patt1=new RegExp(" ");
	if(!passold){
		$(".error p").text('旧密码不能为空');
		$(".error").css("visibility", "visible"); 
		return false;
	}else if(!pass){
		$(".error p").text('新密码不能为空');
		$(".error").css("visibility", "visible"); 
		return false;
	}else if(patt1.test(passold)==true){
		$(".error p").text('旧密码中不能包含空字符');
		$(".error").css("visibility", "visible"); 
		return false;
	}else if(patt1.test(pass)==true){
		$(".error p").text('密码中不能包含空字符');
		$(".error").css("visibility", "visible"); 
		return false;
	}else if(patt1.test(passconf)==true){
		$(".error p").text('确认密码中不能包含空字符');
		$(".error").css("visibility", "visible"); 
		return false;
	}else if(pass.length<6 || pass.length>18){
		$(".error p").text('密码由6到18个字符组成');
		$(".error").css("visibility", "visible"); 
		return false;
	}else if(!pwd_reg.test(pass)){
		$(".error p").text('密码过于简单，包含数字，字母和符号组合');
		$(".error").css("visibility", "visible"); 
		return false;
	}else if(pass!=passconf){
		$(".error p").text('前后密码不一致，请重新输入');
		$(".error").css("visibility", "visible"); 
		return false;
	}else{
		return true;
	}
}

$(".log button").click(function(){
	var pass=$("#pwd").val();
	var passconf=$("#pwdconf").val();
	var passold=$("#pwdold").val();
	if(validatePass(pass,passconf,passold)== true){
		$(".error").css("visibility", "none"); 
		$(".mask_layer").css("display", "block");
		$.ajax({
			type: 'POST',           
			dataType : "json",
			url: context + "/h5/useroperate/changepass",
			 data : {
	                phicommId:phicommId,
	                pass:pass,
	                passold:passold,
	            },
			success: function(data, textStatus, jqXHR){
				$(".mask_layer").css("display", "none");
				if(data.status=='001'){
					$(".error p").text('密码修改成功');
					$(".error").css("visibility", "visible"); 
				}else if(data.status='000'){
					$(".error p").text('旧密码错误，请重新输入');
					$(".error").css("visibility", "visible"); 
				}
				else{
					$(".error p").text('重置密码失败，请重试');
					$(".error").css("visibility", "visible");
				}
			},                                                              
			error: function(jqXHR, textStatus, errorThrown){
				$(".mask_layer").css("display", "none");
				$(".error p").text('产生未知异常，请仔细检查网络');
				$(".error").css("visibility", "visible"); 
			}
		});
	}
	
});
