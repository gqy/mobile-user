	
	$(".btn-log").attr("disabled", true);
	$("#msg").val("");
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
function dealError(param) {
			var count = 0;
			$(".error p").text(param);
			$(".error").css("visibility", "visible");
			$(".pwd label").css("color", "red");
			$(".hide_switch").css({"display": "inline-block", "visibility":"visible"}).click(function() {
				count++;
				if (count % 2 == 1) {
					$("#pwd").attr("type", "text");
					$(".hide_switch img").attr("src", "../imgs/btn_display_switch_press_down.png");
				} else {
					$("#pwd").attr("type", "password");
					$(".hide_switch img").attr("src", "../imgs/btn_display_switch.png");
				}
			});
			$("#pwd").change(function() {
				$(".error").css("visibility", "hidden");
				$(".pwd label").css("color", "#f68121");
			});
	}

	//生成六位数字验证码
	$(".code").html(null);
	function createCode() {
		var code = "";
		var length = 6;
		var i;
		var str = "";
		var content;
		var codeSpan = $(".code");
		var arrColor = ["blue", "red", "green", "brown", "gray", "pink", "red", "green", "brown", "blue"];
		codeSpan.html(null);
		for (i = 0; i < length; i++) {
			index = Math.floor(Math.random() * 6);
			code = Math.floor(Math.random() * 10);
			str += code;
			color = arrColor[index];
			content = $("<i></i>").html(code);
			content.appendTo(codeSpan);
			$(".code i").eq(index).css({"color": color});
		}
		codeSpan.attr("data-val", str);
	}
	
	$(".receive_code").click(createCode);


	//验证用户名、邮箱、密码、验证码	
	function valdateMail(username, mail, pass, validate, validate_input){
		var patt1=new RegExp(" ");
		var user_reg = /^\w{4,10}$/;
		var mail_reg=/^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
		//var mail_reg = /^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
		var pwd_reg =/^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{6,18}$/;
		if(username.length<4 || username.length>10){
			doError("用户名长度不正确");
			return false; 	
		}else if(!user_reg.test(username)){
			doError("用户名格式不正确"); 
			return false;
		}else if(patt1.test(username==true)){
			doError("用户名中不能包含空格"); 
			return false;
		}else if(!mail) {
			doError("邮箱不能为空");
			return false;
		}else if (!mail_reg.test(mail)) {
			doError("邮箱格式不正确");
			return false;
		}else if(patt1.test(mail)==true){
			doError("邮箱中不能包含空格"); 
			return false;
		}else if(!pass){
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
		}else if (!validate) {
			doError("验证码不能为空");
			return false; 
		}else if (validate_input !== validate) {
			doError("验证码输入错误");
			return false; 
		}
		else{
			$(".btn-log").attr("disabled", false);
			return true;
		}
	}
	// 发送验邮件
	$(".btn-log").click(function(){
		
		var mail=$("#mail").val(),
	        pwd=$("#pwd").val(),
	        username=$("#person").val(),
	        validate = $(".code").attr("data-val");
			validate_input = $("#msg").val();
			console.log(validate + "," + validate_input);
		if (valdateMail(username, mail, pwd, validate, validate_input) == true) {
			
			if ($(".reminds_confirm").attr("src") === "../imgs/checkbox_19.png") {
				$(".error").css("visibility", "none");  
				$(".mask_layer").css("display", "block");
				$.ajax({
					type: 'POST',           
					dataType : "json",
					url: context + "/h5/useroperate/registmail",
					 data : {
			                mail:mail,
			                pwd:pwd,
			                username:username,
			            },
					success: function(data, textStatus, jqXHR){
						$(".mask_layer").css("display", "none");
						if(data.status=='001'){
							doSuccess(data.desc);
						}else{
							doError(data.desc);}
					},                                                              
					error: function(jqXHR, textStatus, errorThrown){
						$(".mask_layer").css("display", "none");
						doSuccess("产生未知错误，请仔细检查网络");
					}
				});
			}
			
			
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
