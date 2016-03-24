// 发送验证码
  var phicommId;
	$(".receive_msg").click(function(){
		//alert('a')
	    var userPhoneNumb=$('#person').val();
		if(valdateNumber(userPhoneNumb)==true){
	
		$.ajax({
			type: 'POST',           
			contentType: 'application/x-www-form-urlencoded',
			url: context + "/producecode/sendCodeForResetPwd",
			data:{"userPhoneNumb":userPhoneNumb},
			success: function(data, textStatus, jqXHR){
				if(data.status=='001'){
					phicommId=data.data.phicommId;
					$(".receive_msg").attr("disabled", true).css({"background" : "#E0E0E0 ", "color": "#000"});
					//var t2 = window.setInterval("settime()",1000);
					var t1 = window.setTimeout("settime()",1000);//使用字符串执行方法
					doSuccess(data.desc);
				}else{
					doError(data.desc);
				}			
			
			},                                                              
			error: function(jqXHR, textStatus, errorThrown){
				doError("产生未知异常，请仔细检查网络");
			}
	    });
	}
});
//验证码验证
	$("#findpass").click(function(){
		var checkCode=$('#msg').val();
		var userPhoneNumb=$('#person').val();
		if(validateCode(checkCode)==true){
			$(".mask_layer").css("display", "block");
			$.ajax({
				type: 'POST',           
				contentType: 'application/x-www-form-urlencoded',
				url: context + "/h5/useroperate/findpassmobile",
				data:{"inputCode":checkCode,
					  },
				success: function(data, textStatus, jqXHR){
					$(".mask_layer").css("display", "none");
					if(data.status=='001'){
						doSuccess(data.desc);
						location.href=context+"/generalunify/tofindpassreset?phicommId="+phicommId+"&code="+checkCode;
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
	
	
	  var time=60;
		function settime(){
			time=time-1;
			if(time>0){
				$(".receive_msg").text(time);
				setTimeout(settime,1000);
			}else{
				$(".receive_msg").attr("disabled", false).css({"background" : "rgb(246, 129, 32) ", "color": "#fff"});
				$(".receive_msg").text('重新发送');
				time=60;
			}
		}
	//判断验证码
    function validateCode(inputCode){
    	var patt1=new RegExp(" ");
    	if(!inputCode){
    		doError("验证码不能为空"); 
    		return false; 
    	}else if(patt1.test(inputCode)==true){
    		doError("验证码不能包含空格");
    		return false; 
    	}
    	else{
    		return true;
    	}
    }
	//验证手机号
    function valdateNumber(num){
    	var myreg = /^(((13[0-9]{1})|(17[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    	if(num.length !== 11){
    		doError("手机号长度不对");
    		return false; 
    	}else if(!myreg.test(num)) 
		   { 
    		doError("手机号格式不对");
    		return false; 
		   }else{
			   
    	return true;}
    }
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