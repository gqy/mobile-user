var person_val = $("#person").val();
	var person_plc = $("#person").attr("placeholder");
	var pwd_plc = $("#pwd").attr("placeholder");
	var that;
 
	$("#person, #pwd, #msg, #mail").focus(function(event) {
		event.preventDefault();
		
		if ($(this).attr("placeholder") === person_plc) {
			that = $(this);
//			console.log("1");
		} else if ($(this).attr("placeholder") === pwd_plc) {
			that = $(this);
//			console.log("2");
		} else {
			that = $(this);
//			console.log("3");
		}

		that.prev().css("color", "#f68121");
		that.css("color", "#000").keyup(function() {
			that.next(".btn_delete").css("visibility", "visible").bind("click", function(event) {
				$(this).prev().css("color", "#acacac").val("").blur().prev().css("color", "#000");
				$(".btn_delete").css("visibility", "hidden");
			});
		});
	})

	$("#person").blur(function(event) {
		event.preventDefault();
		setTimeout(function() {
			// console.log("blur");
			$(".btn_delete").css("visibility", "hidden");
			$("#person").prev().css("color", "#000"); 
			if ($("#person").val() === "") {
	 			$("#person").css("color", "#acacac");
	 		}
		}, 100);
	});

	$("#pwd").blur(function(event) {
		event.preventDefault();
		setTimeout(function() {
			// console.log("blur");
			$(".btn_delete").css("visibility", "hidden");
			$("#pwd").prev().css("color", "#000"); 
			if ($("#pwd").val() === "") {
	 			$("#pwd").css("color", "#acacac");
	 		}
		}, 100);
	});

	$("#msg").blur(function(event) {
		event.preventDefault();
		setTimeout(function() {
			// console.log("blur");
			$(".btn_delete").css("visibility", "hidden");
			$("#msg").prev().css("color", "#000"); 
			if ($("#msg").val() === "") {
	 			$("#msg").css("color", "#acacac");
	 		}
		}, 100);
	});

	$("#mail").blur(function(event) {
		event.preventDefault();
		setTimeout(function() {
			// console.log("blur");
			$(".btn_delete").css("visibility", "hidden");
			$("#mail").prev().css("color", "#000"); 
			if ($("#mail").val() === "") {
	 			$("#mail").css("color", "#acacac");
	 		}
		}, 100);
	});

	// 修改昵称
	$("#modify_name").focus(function(event) {
		event.preventDefault();
		$(this).prev().css("color", "#f68121");
		if ($(this).val() === person_val) {
			$(this).val("").css("color", "#000").keyup(function() {
				$(".btn_delete").css("visibility", "visible").bind("click", function(event) {
					event.preventDefault();
					$("#person").val("").focus();
					$(".btn_delete").css("visibility", "hidden");
				});
			});
		}
	});

	$("#modify_name").blur(function(event) {
		event.preventDefault();
		//$(".btn_delete").css("visibility", "hidden");
		$(this).prev().css("color", "#000");
		if ($(this).val() === "") {
			$(this).css("color", "#acacac").val(person_val);
		}	
	});




	
	/**
	 * 处理账户没有输入、账户格式不正确、账户不存在的情况
	 * @param count 用户输入的值
	 */
	function dealCount() {
		var count = $("#person").val();
		if (!count ) {
			$(".login button").click(function(event) {
				$(".mask_layer").css("display", "block");
			});
			$(".error").css("visibility", "visible");
			 $(".person label").css("color", "red");
		}
	}


	/**
	 * 处理密码输入错误的情况
	 */
	function dealError(pwd, condition) {
		if (pwd !== condition || !pwd) {
			var count = 0;
			$(".login button").click(function(event) {
				$(".mask_layer").css("display", "block");
			});
			$(".error").css("visibility", "visible");
			$(".pwd label").css("color", "red");
			$(".hide_switch").css("display", "inline-block").click(function() {
				count++;
				if (count % 2 == 1) {
					$("#pwd").attr("type", "text");
					$(".hide_switch img").attr("src", "imgs/btn_display_switch_press_down.png");
				} else {
					$("#pwd").attr("type", "password");
					$(".hide_switch img").attr("src", "imgs/btn_display_switch.png");
				}
			});
			$("#pwd").change(function() {
				$(".error").css("visibility", "hidden");
				$(".pwd label").css("color", "#f68121");
			});
		}
	}

	/**
	 * 加载页面遮罩层
	 */
	$(".login button").click(function(event) {
		$(".mask_layer").css("display", "block");
		$.ajax({
			type: 'POST',
			contentType: 'application/json',
			url: context + "/h5/useroperate/login",
			dataType: "json",
			data:  loginformToJSON(),
			success: function(data, textStatus, jqXHR){
				$(".mask_layer").css("display", "none");
				dealError(data.desc);
				if(data.status=='001'){
					geqinyangObj.loginSuccess(JSON.stringify(data));	
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert('产生未知错误,请刷新并仔细检查网络')
				
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
	 $(".back").click(function(event){
			
		 geqinyangObj.back();
		
	 });
	 

