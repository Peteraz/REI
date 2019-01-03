<%@ page language="java" import="java.util.*" contentType="text/html;charaset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户信息</title> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
	<meta http-equiv="pragma" content="no-cache">                          <!--这几句meta作用是清除浏览器中的缓存,再次进入曾经访问过的页面时，浏览器必须从服务端下载最新的内容，达到刷新的效果。 -->
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">      <!--最后两句meta,做seo用 -->
	<meta http-equiv="description" content="This is my page">
	
	<!--<link rel="stylesheet" type="text/css" href="styles.css">-->
	<link href="css/base.css" rel="stylesheet">
	<link rel="stylesheet" href="css/userinfo.css" >
  </head>
  <body>
       <form  class="userinfo">
             <center><h1 style="font-size: x-large!important;">用户信息</h1></center>
             <br/>
             <div>
                 <label>姓名:</label>
                 <input type="text" id="txt_name" disabled="disabled" />          <!--disabled="disabled"作用禁止修改姓名-->
             </div>
             <br/>
             <br/>
             <br/>
             <div>
                 <label>性别:</label>
                 <input type="text" id="txt_sex" disabled="disabled" />           <!--disabled="disabled"作用禁止修改性别-->
             </div>
             <br/>
             <br/>
             <br/>
             <div>
                 <label>邮箱:</label>
                 <input type="text" id="txt_email" disabled="disabled" />       <!--disabled="disabled"作用禁止修改身份-->
             </div>
             <br/>
             <br/>
             <br/>
             <div>
                 <label>身份:</label>
                 <input type="text" id="txt_identitys" disabled="disabled" />       <!--disabled="disabled"作用禁止修改身份-->
             </div>
             <br/>
             <br/>
             <br/>
             <div>
                 <label>证件号:</label>
                 <input type="text" id="txt_credential_number" disabled="disabled"/>      <!--disabled="disabled"作用禁止修改证件号-->
             </div>
             <br/>
             <br/>
             <br/>
             <div>
                 <label>身份证:</label>
                 <input type="text" id="txt_idcard_number" disabled="disabled"/>          <!--disabled="disabled"作用禁止修改证件号-->
             </div>
             <br/>
             <br/>
             <br/>
             <div>
                 <label>部门:</label>
                 <input type="text" id="txt_department" disabled="disabled"/>           <!--disabled="disabled"作用禁止修改证件号-->
             </div>
             <br/>
             <br/>
             <br/>
             <br/>
             <center><b style="color: rgba(0, 0, 255, 0.94);">${changeuserstatus}</b></center>
       </form>
       <script type="text/javascript" src="./custom/jquery.min.js"></script>
       <script type="text/javascript">
             $(document).ready(function(){
                $("#txt_name").val("${user.name}");                                   //读取名字
                $("#txt_sex").val("${user.sex}");                                     //读取性别
                $("#txt_email").val("${user.email}");                                 //邮箱
                $("#txt_identitys").val("${user.identitys}");                         //读取身份
                $("#txt_credential_number").val("${user.credential_number}");         //读取证件
                $("#txt_idcard_number").val("${user.idcard_number}");                 //读取身份证
                $("#txt_department").val("${user.department}");                       //读取部门
             });
       </script>
  </body>
</html>
