$(function(){
	/*
	 * 为登陆按钮加上单击事件
	 */
	$("#submit_login").click(function(){
		var ln=$.trim($("#loginName").val());
		var pwd=$.trim($("#pwd").val());
		var captcha=$.trim($("#captcha").val());
		
		if(ln==null || ln.length==0 ){	
			alert("用户名不能为空");
			$("#loginName").context.focus();
			return ;
		}
		if(pwd==null || pwd.length==0 ){
			alert("密码不能为空");
			$("#pwd").context.focus();
			return ;
		}		
		if(captcha==null || captcha.length!=4){
			alert("验证码输入格式不正确");
			$("#captcha").context.focus();
			return ;
		}
		
		$.post("act/member!login.action",{
				loginName:ln,
				pwd:pwd,
				captcha:captcha
				},
				function(data){
					var jo=eval("("+data+")");
					if(jo.code==1){
						location.assign(jo.data.url);
					}else{
						alert(jo.error);
					}
				}
		);

	});
	
	/*
	 * 提交注册
	 */
	$("#submit_regist").click(function(){			
		var type=$("#mt_s").get(0).checked?1:-1;
		if(type==-1)
			type=$("#mt_t").get(0).checked?0:-1;

		var ln=$.trim($("#ln").val());
		var pwd=$.trim($("#pwd").val());
		var sex=$("#sex_m").get(0).checked?"男":-1;
		if(sex==-1)
			sex=$("#sex_w").get(0).checked?"女":-1;
		
		var cap=$.trim($("#captcha").val());
		if(ln.length==0){
			alert("用户名不能为空!");
			return;
		}
		if(pwd.length==0){
			alert("密码不能为空!");
			return;
		}
		if(cap.length!=4){
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
		
		$.post("act/member!regist.action",{
			ln:ln,
			pwd:pwd,
			captcha:cap,
			sex:sex,
			mt:type
			},
			function(data){
				var jo=eval("("+data+")");
				if(jo.code==1){
					location.assign(jo.data.url);
				}else{
					alert(jo.error);
				}
			}
		);
	});
	
	/*
	 * 当用输入"@"时，将常用的邮件服务商后辍弹出供选择
	 */
	$("#ln").change(function(){		
		if(this.value.indexOf("@")!=-1){
			//TODO: 未完成的（输入@时，弹选择
		}
	});
	
	
	
	/*
	 * 忘记密码第一步，提交	 
	 */
	$("#submit_regist").click(function(){
		var rs=checkCaptcha("captcha");
		if(!rs)
			return ;
		
		var rs=checkCaptcha("email");
		if(!rs)
			return ;
	});
});
