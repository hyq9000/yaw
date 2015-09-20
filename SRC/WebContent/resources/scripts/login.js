$(function(){
	/*
	 * 登陆
	 */
	$("#submit_login").click(function(){	
		var un=$.trim($("#loginName").val());
		var pwd=$.trim($("#pwd").val());
		var cap=$.trim($("#captche").val());
		if(un.length==0 || pwd.length==0){
			alert("用户名或密码不能为空!");
			return;
		}
		if(cap.length<4){
			alert("请输入正确验证码!");
			return;
		}
		$.post(
			'/act/member!login.action',
			{
				"loginName":loginName,
				"pwd":pwd,
				"captche":cap
			},
			function(data){
				alert(data);
				if(data.code==1){
					location.assign(data.data.url);
				}else{
					alert(data.error);
				}
				
			}
		);
	})
	
	/*
	 * 提交注册
	 */
	$("#submit_regist").click(function(){	
		var type=$("#mt_s").context.checked?1:-1;
		type=$("#mt_t").context.checked?0:-1;
		var un=$.trim($("#ln").val());
		var pwd=$.trim($("#pwd").val());
		var sex=$("#sex_m").context.checked?"男":-1;
		sex=$("#sex_w").context.checked?"女":-1;
		
		var cap=$.trim($("#captcha").val());
		if(un.length==0 || pwd.length==0){
			alert("用户名不能为空!");
			return;
		}
		if(cap.length<4){
			alert("请输入正确验证码!");
			return;
		}
		if(type==-1){
			alert("用户类型没有选择!");
			return;
		}
		if(sex==-1){
			alert("性别没有选择!");
			return;
		}
	});
});
