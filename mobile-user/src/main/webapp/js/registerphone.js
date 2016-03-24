	
	$(".btn-log").attr("disabled", true);
	var count = 0;
	$(".reminds_confirm").click(function() {
		var newSrc, newColor, newBgc;
		count++;
		newSrc = count%2===0 ? "../imgs/checkbox_00.png" : "../imgs/checkbox_19.png";
		newColor = count%2===0 ? "#000" : "#fff";
		newBgc = count%2===0 ? "#E7E8EA" : "#F68120";
		dsiabl = count%2===0 ? true : false;
		$(this).attr("src", newSrc);
		$(".btn-log").css({"background-color": newBgc, "color": newColor}).attr("disabled", dsiabl);
	});
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
    
    function valdatePwd(pass){
    	var pwd_reg =/^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{6,18}$/;
    	var patt1=new RegExp(" ");
    	 if(!pass){
			doError("密码不能为空");
			return false;
		}else if(patt1.test(pass)==true){
			doError("密码中不能包含空格");
			return false;
		}else if(pass.length<6 || pass.length>18){
			doError("密码由6到18个字符组成");
			return false;
		}else if(!pwd_reg.test(pass)){
			doError("密码过于简单，包含数字，字母和符号组合");
			return false;
		}
    	return true;
    }
    
	// 发送验证码
	$(".receive_msg").click(function(){
		
		//alert('a')
	    var userPhoneNumb=$('#person').val();
		if(valdateNumber(userPhoneNumb)==true){
	
		$.ajax({
			type: 'POST',           
			contentType: 'application/x-www-form-urlencoded',
			url: context + "/producecode/produceCode",
			data:{"userPhoneNumb":userPhoneNumb},
			success: function(data, textStatus, jqXHR){
				if(data.status=='001'){
					$(".receive_msg").attr("disabled", true).css({"background" : "#E0E0E0 ", "color": "#000"});
					//var t2 = window.setInterval("settime()",1000);
					var t1 = window.setTimeout("settime()",1000);//使用字符串执行方法
					doSuccess(data.desc);
				}else{
				    doError(data.desc);}
				
			
			},                                                              
			error: function(jqXHR, textStatus, errorThrown){
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
	// 立即注册
	$(".btn-log").click(function(){
//		alert('a')
		var userPhoneNumb=$('#person').val();
		var reg =/^[0-9a-zA-Z\@\!\#\$\%\^\&\*\.\~]{6,18}$/;
		var userPassword=$('#pwd').val();
		var checkCode=$('#msg').val();
		// var userSrc="userSrc";
		// 先判断密码格式是否正确
		if (valdateNumber(userPhoneNumb) && valdatePwd(userPassword)) {
			// 约束当用户点击同意协议后才能进行注册
			if ($(".reminds_confirm").attr("src") === "../imgs/checkbox_19.png") {
				$(".mask_layer").css("display", "block");
				$.ajax({
					type: 'POST',
					contentType: 'application/x-www-form-urlencoded',
					url: context + "/h5/useroperate/registphone",
					data:{"userPhoneNumb":userPhoneNumb,
						  "userPassword":userPassword,
						  "checkCode":checkCode,
						  // "userSrc":userSrc
						  },
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
						doError("产生未知错误，请仔细检查网络");
					}
				});
			}else{
				doError("未同意用户注册协议");
			}
		    
		
		}
	});
	  $("#back").bind("click",function(){
		  geqinyangObj.finishSelf();
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

