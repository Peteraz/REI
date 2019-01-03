<%@ page language="java" import="java.util.*" contentType="text/html;charaset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>重置密码</title> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
	<meta http-equiv="pragma" content="no-cache">                          <!--这几句meta作用是清除浏览器中的缓存,再次进入曾经访问过的页面时，浏览器必须从服务端下载最新的内容，达到刷新的效果。 -->
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">      <!--最后两句meta,做seo用 -->
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link href="css/base.css" rel="stylesheet">
    <link href="css/resetpasswd.css" rel="stylesheet">
    <link href="./custom/uimaker/easyui.css" rel="stylesheet" >
  </head>
  <body>
     <form action="resetpwd!resetpasswd.action" method="post">
        <c:if test="${status==true}">
		    <div class="resetpasspwd">
			        <center><h1 style="font-size: x-large!important;">重置密码</h1></center>
			        <br/>
			        <center>
			                <div>
					             <input type="password" id="pwd1" name="uentity.password" required="true"  placeholder="请输入新密码"  style="width:250px;height:35px;" />
					        </div>
					</center>
					<br/>
					<br/>
					<center>
			                <div>
					             <input type="password" id="pwd2" name="pssword" required="true"  placeholder="请再次输入新密码" style="width:250px;height:35px;"/>
					             <br/>
					             <span id="error"></span>
					        </div>
					</center>
					<br/>
					<br/>
					<center>
			                <div>					             
								 <input type="text" placeholder="验证码" name ="checkcode" style="width:150px;height:35px;vertical-align:middle;" />	 
					             <span ><img src="PictureCheckCode.Servlet" id="ckcode" onclick="refresh()" style="width:100px;height:36px;vertical-align:middle;"></img></span>
					        </div>
					</center>
					<br/>
					<br/>
				    <center><input type="submit"  class="submit" onclick="return check()"value="提交" /></center>
				    <br/>
                    <br/>
		    </div>
		 </c:if>
		 <center>
		         <div class="info">
		             <b style="color: rgba(0, 0, 255, 0.94);">${resetinfo}</b>
		         </div>
		 </center>
	  </form>
      
      <script type="text/javascript" src="./custom/jquery.min.js" charset="utf-8"></script>
	  <script type="text/javascript" src="./custom/jquery.cookie.js" charset="utf-8"></script>
	  <script type="text/javascript" src="./custom/jquery.easyui.min.js" charset="utf-8"></script>
	  <script type="text/javascript" src="./custom/easyui-lang-zh_CN.js" charset="utf-8"></script>
	  <script type="text/javascript">
	    //刷新验证码
        function refresh()
        {
           var code=document.getElementById("ckcode");
           code.src='PictureCheckCode.Servlet?now='+Math.random();
        }
        
        //操作结果
        var msg = "${resetinfos}";
        if(msg!=null&&msg!=""){
      	   $(function(){
      			$.messager.show({
      				title:"操作结果!",
      				msg:msg,
      				showType:'fade',
      				timeout:1500,
      				style:{
      					right:'',
      					bottom:''
      				}
      			});
      		});      	
        }
        
        //检查两次密码是否一样
    	function check(){
    		var pwd = $("#pwd1").val();
    		var cpwd = $("#pwd2").val();
    		if(pwd != cpwd){
    			alert("两次输入的密码不一致!");
    			$("pwd1").val("");
    			$("pwd2").val("");
    			return false;
    		}
    	 }
	</script>
  </body>
</html>
