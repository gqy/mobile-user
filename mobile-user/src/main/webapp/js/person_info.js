(function($) {
	$("#back").bind("click",function(){
		geqinyangObj.finishSelf()();
	});
    $("#changeHead").bind("click",function(){
    	
    	 geqinyangObj.updatePortrait();
    });
	$(".change").click(modifyPass);
    $(".sex").click(modifySex);
//    $(".nickname").click(modifyName);
    $(document).delegate(".nickname","click",function(){
    	$(".wrap1, .mask_layer").show();
    	var nickvar = $.trim($(".nick").text());
    	$("#modify_name").val(nickvar);
    	
    });
    
    $(document).delegate("#modify_name","focus",function(){
    	$("#nick_del").css("visibility","visible");
    });
   $("#modify_name").blur(function(){
//	   $("#nick_del").css("visibility","hidden");
   });
   
   $(document).delegate("#nick_del","click",function(){
	   $("#modify_name").val("");
	   $("#modify_name").focus();
   });
   
   $("#modify_name").keyup(function(){
	   var modifyName = $.trim($(this).val());
	   if(modifyName==""){
		   $("#nick_del").css("visibility","hidden");
	   } else{
		   $("#nick_del").css("visibility","visible");
	   }
	 });
   
   $(".birth").click(modifyBirth);
    $('#beginTime').date();
    function modifyPass(){
    	location.href=context+"/unify/topassreset?phicommId="+phicommId+"&openId="+openId+"&expireseIn="+expireseIn+"&accessToken="+accessToken;
    }
 
    function modifyName() {
        var person_val = $("#modify_name").val(),
        	nickName = $(".nick").text();
        $(".wrap1, .mask_layer").show();
        $("#modify_name").val("");
        $("#modify_name").focus(function(event) {
            event.preventDefault();
//            if ($(this).val() === nickName) {
                $(this).css("color", "#000").keyup(function() {
                    $(".btn_delete").css("visibility", "visible").bind("click", function(event) {
                        event.preventDefault();
                        $("#modify_name").val("").focus();
                        $(".btn_delete").css("visibility", "hidden");
                    });
                });
//            }
        });
        
        
    }

    function modifySex() {
        $(".wrap2, .mask_layer").show();
    }

    function modifyBirth() {
    	
        $('#beginTime').date();
    }

    $(".confirm").click(function() {
//    	$(".wrap, .mask_layer").hide();
        var newName = $("#modify_name").val(),
            newSex = $("input:checked").val();
        	phicommId = $("#phicomm_id").text(),
        	className = $(this).parent().attr("class");
        if (className.match(/wrap1/gi)) {
        	$.ajax({
	            url : context + "/h5/useroperate/updatebykey",
	            type : "post",
	            dataType : "json",
	            data : {
	                phicomm_id:phicommId,
	                key:'nickname',
	                value: newName,
	            },
	            success : function(data) {
//	            	alert(data.desc);
	            	$(".wrap3, .mask_layer").show();
	            	$(".info").text(data.desc);
	                if(data.status=='001'){
	                    $(".nick").text(newName);
	                    geqinyangObj.getUpdateInfo(phicommId,'nickname',newName);
	                } 
	            },
	            error : function() {
	            	$(".error p").text('产生未知异常，请仔细检查网络');
					$(".error").css("visibility", "visible"); 
	            }
        	});
        } else if (className.match(/wrap2/gi)) {
        	$.ajax({
	            url : context + "/h5/useroperate/updatebykey",
	            type : "post",
	            dataType : "json",
	            data : {
	                phicomm_id:phicommId,
	                key:'sex',
	                value: newSex,
	            },
	            success : function(data) {
//	            	alert(data.desc);
	            	$(".wrap3, .mask_layer").show();
	            	$(".info").text(data.desc);
	                if(data.status=='001'){
	                    $(".sex_a").text(newSex);
	                    geqinyangObj.getUpdateInfo(phicommId,'sex',newSex);
	                } 
	            },
	            error : function() {
	            	$(".error p").text('产生未知异常，请仔细检查网络');
					$(".error").css("visibility", "visible"); 
	            }
        	});
        }
        


        
        $(".wrap, .mask_layer").hide();
    });

    $(".delete").click(function() {
        $(".wrap, .mask_layer").hide();
    });

   

})(jQuery);

