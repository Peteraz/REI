<%@ page language="java" import="java.util.*" contentType="text/html;charaset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户申请</title> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
	<meta http-equiv="pragma" content="no-cache">                         <!--这几句meta作用是清除浏览器中的缓存,再次进入曾经访问过的页面时，浏览器必须从服务端下载最新的内容，达到刷新的效果。 -->
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">     <!--最后两句meta,做seo用 -->
	<meta http-equiv="description" content="This is my page">
	
	<!-- <link rel="stylesheet" type="text/css" href="styles.css"> -->
    <link rel="stylesheet" href="./custom/uimaker/easyui.css">
  </head>
  <body>
        <div  class="container">
             <table id="dg">
                   <thead>
                          <tr></tr>
                   </thead>
             </table>
        </div>
        <div id="showimg" class="hide" style="display: none;">
                 <img src="" />
        </div>
      <script type="text/javascript" src="./custom/jquery.min.js"></script>
      <script type="text/javascript" src="./custom/jquery.easyui.min.js"></script>
      <script type="text/javascript" src="./js/layer.js"></script>
      <script type="text/javascript">
       $('#dg').datagrid({
                     url:"user-apply!userapplys.action",        //从远程站点请求数据的 URL。
                     title:'待审批',                               //数据表名
                     method: 'post',
                     width: 'auto',
                     height: '780px',
                     singleSelect: true,                       //设置为 true，则显示带有行号的列。
                     fitColumns: false,                        //设置为 true，则会自动扩大或缩小列的尺寸以适应网格的宽度并且防止水平滚动。
                     pagination: true,                         //设置为 true，则在数据网格（datagrid）底部显示分页工具栏。
                     pageSize: 5,                              //初始页的大小
                     pageList:[5,10,15,20,25,30,35],           //当设置了 pagination 属性时，初始化页面尺寸的选择列表。
                     pageNumber: 1,                            //当设置了 pagination 属性时，初始化页码。
                     rownumbers: true,                         //设置为 true，则显示带有行号的列。
                     autoRowHeight:true,                       //定义是否设置基于该行内容的行高度。设置为 false，则可以提高加载性能。
                     loadMsg: "正在加载数据...",                   //当从远程站点加载数据时，显示的提示消息。
                     columns:[[                                //Column是一个数组对象，它的每个元素也是一个数组。
                         {field:'id',title:'id',width:100},
		                 {field:'account',title:'账号',width:100},
		                 {field:'name',title:'姓名',width:70},
		                 {field:'sex',title:'性别',width:50},
		                 {field:'identitys',title:'身份',width:50},
		                 {field:'credential_number',title:'证件号',width:100},
		                 {field:'idcard_number',title:'身份证号',width:100},
		                 {field:'cpicture',title:'证件照片',width:100,height:100,align:'center',                          //照片格子的大小
		                         formatter:function(value,row,index){
		                                   return '<img onclick="preview(' + "'" +value+"'" + ')" style="height: 100px; width: 100px;" src="'+value+'"/>';    //照片的大小
		                         } 
		                 },
		                 {field:'ipicture',title:'身份证照片',width:100,height:100,align:'center',                          //照片格子的大小
		                         formatter:function(value,row,index){
		                                   return '<img onclick="preview(' + "'" +value+"'" + ')" style="height: 100px; width: 100px;" src="'+value+'"/>';    //照片的大小
		                         } 
		                 },
		                 {field:'department',title:'部门',width:100},
		                 {field:'state',title:'状态',width:50},
		                 {field:'_operate1',title:'操作',width:100,align:'center',
		                           formatter :function(value, row, index) {
            	                           var str = "";
            	                           str += '<button onclick="relaodY('+index+')" >通过</button>';	
            	                           return str;
                                   }
                         },
                         {field:'_operate2',title:'操作',width:100,align:'center',
		                           formatter :function(value, row, index) {
            	                           var str = "";
            	                           str += '<button onclick="relaodN('+index+')" >不通过</button>';	
            	                           return str;
                                   }
                         }
                      ]],
                      //分页功能实现
                  }).datagrid("getPager").pagination({
                      //上一页或下一页
                     onSelectPage:function(pageNumber, pageSize){
		                   $.ajax({
                                       //提交数据的方式 POST 或 GET
                                       type:'POST',
                                       //提交的地址
                                       url:'user-apply!reloaduserapplys.action',
                                       //提交的数据
                                       data:{
                                           pnumber:pageNumber,
                                           psize:pageSize,
                                       },
                                       //返回数据的格式
                                       datatype:'json',//"xml", "html", "script", "json", "jsonp", "text".
                                       //成功返回后调用的函数
                                       success:function(data){
                                            var data=JSON.parse(data);
                                            if(data.msg=="修改成功"){
                                  	               $('#dg').datagrid('loadData', data);
                                            }
                                       }
                           });
	                  },
                      //修改页面大小
	                  onChangePageSize:function(pageSize){
                           var pageSize=pageSize;  
	                       $.ajax({
                                       //提交数据的方式 POST 或 GET
                                       type:'POST',
                                       //提交的地址
                                       url:'user-apply!reloaduserapplyp.action',
                                       //提交的数据
                                       data:{
                                           psize:pageSize,
                                       },
                                       //返回数据的格式
                                       datatype:'json',//"xml", "html", "script", "json", "jsonp", "text".
                                       //成功返回后调用的函数
                                       success:function(data){
                                            var data=JSON.parse(data);
                                            if(data.msg=="修改成功"){
                                  	             $('#dg').datagrid('loadData', data);
                                            }
                                       }
                           });  
	                  }
                 });
                       
                 //通过按钮
                 function relaodY(index){
                    $('#dg').datagrid('selectRow',index);
                    var row = $('#dg').datagrid('getSelected');
                    var options = $("#dg" ).datagrid("getPager" ).data("pagination" ).options;
                    var pnumber = options.pageNumber;                               //获取当前页面号
                    var psize = options.pageSize;                                   //获取当前页面的数据条数
                    if(row){
                        $.ajax({
                             //提交数据的方式 POST 或 GET
                             type:'POST',
                             //提交的地址
                             url:'user-apply!yuserapply.action',
                             //提交的数据
                             data:{
                                     id:row.id,
                                     pnumber:pnumber,
                                     psize:psize,
                             },
                             //返回数据的格式
                             datatype:'json',//"xml", "html", "script", "json", "jsonp", "text".
                             //成功返回后调用的函数
                             success:function(data){
                                     var data=JSON.parse(data);
                                     if(data.msg=="操作成功"){
                                          $("#dlg").dialog('close',false);
                                          layer.alert(data.msg,{icon:6}, function() {
                                  	             location.reload();
                                          })
                                     }
                              }
                        });
                    }else{
                        $.messager.alert("提示","请先选中要编辑的行!");
                    }
                 } 
                 
                 //不通过按钮
                 function relaodN(index){
                    $('#dg').datagrid('selectRow',index);
                    var row = $('#dg').datagrid('getSelected');
                    var options = $("#dg" ).datagrid("getPager" ).data("pagination" ).options;
                    var pnumber = options.pageNumber;                               //获取当前页面号
                    var psize = options.pageSize;                                   //获取当前页面的数据条数
                    if(row){
                        $.ajax({
                             //提交数据的方式 POST 或 GET
                             type:'POST',
                             //提交的地址
                             url:'user-apply!nuserapply.action',
                             //提交的数据
                             data:{
                                     id:row.id,
                                     pnumber:pnumber,
                                     psize:psize,
                             },
                             //返回数据的格式
                             datatype:'json',//"xml", "html", "script", "json", "jsonp", "text".
                             //成功返回后调用的函数
                             success:function(data){
                                     var data=JSON.parse(data);
                                     if(data.msg=="操作成功"){
                                          $("#dlg").dialog('close',false);
                                          layer.alert(data.msg,{icon:6}, function() {
                                  	             location.reload();
                                          })
                                     }
                              }
                        });
                    }else{
                        $.messager.alert("提示","请先选中要编辑的行!");
                    }
                 }       
                  
                  function preview(img){
                 	$("#showimg>img").attr("src",img);
                      layer.open({
                      type: 1,
                      title: false,
                      closeBtn: 0,
                      area: ['70%', '95%'],
                      skin: 'layui-layer-nobg', //没有背景色
                      shadeClose: true,
                      content: $("#showimg")
                      });
                 }
         </script>
  </body>
</html>
