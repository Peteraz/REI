<%@ page language="java" import="java.util.*" contentType="text/html;charaset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    
    <title>Hello</title> 
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
    <link href="css/forget.css" rel="stylesheet">
    <link href="./custom/easyui.css" rel="stylesheet" type="text/css">
 </head>
<body>

	<script type="text/javascript" src="./custom/jquery.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="./custom/jquery.cookie.js" charset="utf-8"></script>
	<script type="text/javascript" src="./custom/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="./custom/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript">
       //操作错误提示
       $(function(){
      			$.messager.show({
      				title:"Test!",
      				msg:"Hello World!",
      				showType:'slide',
      				timeout:1500,
      				style:{  
            			right:'',  
            			bottom:''  
        			} 
      			});
      		});   

    </script>
</body> 
</html>