
$(function(){
	/*
	 * 为登陆按钮加上单击事件
	 */
	$("#submit").click(function(){
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
});