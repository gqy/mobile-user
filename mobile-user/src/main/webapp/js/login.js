$(document).ready(function() {

			var person_val = $("#person").val(),
				pwd_val = $("#pwd").val();

			
  
	/**
	 * 处理密码输入错误的情况
	 */
	function dealError(param1,param) {
			var count = 0;
			var patt1=new RegExp(" ");
			var pwd= $('#pwd').val(),
			person_val = $("#person").val();
			
			$(".error p").text(param);
			$(".error").css("visibility", "visible");
			if(!person_val){
				$(".error p").text("账号不能为空");
				$(".error").css("visibility", "visible");
				$(".count label").css("color", "red");	
			}
			if (param1 === "021") {
				$(".count label").css("color","red");
				
			} else if(patt1.test(pwd)==true){
				$(".error p").text("密码不能包含空字符串");
				$(".error").css("visibility", "visible");
				$(".pwd label").css("color", "red");	
			} else if (param1 === "022") {
				$(".pwd label").css("color", "red");	
			}
			
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

	/**
	 * 加载页面遮罩层
	 */
	$(".log button").click(function(event) {
		$(".mask_layer").css("display", "block");
		$.ajax({
			type: 'POST',
			contentType: 'application/json',
			url: context+"/h5/useroperate/login",
			dataType: "json",
			data:  loginformToJSON(),
			success: function(data, textStatus, jqXHR){
				$(".mask_layer").css("display", "none");
				dealError(data.status,data.desc);
				if(data.status=='001'){
					geqinyangObj.loginSuccess(JSON.stringify(data));	
					
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				$(".mask_layer").css("display", "none");
				dealError('产生未知错误,请刷新并仔细检查网络');
				
			}
		});
	});

	 function loginformToJSON() {
		 	var phonenum = $.trim($('#person').val());
		 	return JSON.stringify({
		 		"userPhoneNumb": phonenum == "" ? null : phonenum, 
		 		"userPassword": $('#pwd').val(), 

		 		});
		 }

		})
		
	   $("#weixin").click(function(event){
		   geqinyangObj.weixinLogin();
	    });  
	  $("#back").bind("click",function(){
		  geqinyangObj.finishSelf();
	  });