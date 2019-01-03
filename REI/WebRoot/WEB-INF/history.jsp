<%@ page language="java" import="java.util.*" contentType="text/html;charaset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>历史记录</title> 
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
          <table id="dg" >
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
                 //历史记录    
                 $('#dg').datagrid({
                     url:"reimbursement!done.action",       //从远程站点请求数据的 URL。
                     title:'报销历史',                         //数据表名
                     toolbar: '#tb',                        //工具栏
                     method: 'post',
                     width: 'auto',
                     height: '600px',
                     singleSelect: true,                     //设置为 true，则显示带有行号的列。
                     fitColumns: false,                      //设置为 true，则会自动扩大或缩小列的尺寸以适应网格的宽度并且防止水平滚动。
                     pagination: true,                       //设置为 true，则在数据网格（datagrid）底部显示分页工具栏。
                     pageSize: 5,                            //初始页的大小
                     pageList:[5,10,15,20,25,30,35],         //当设置了 pagination 属性时，初始化页面尺寸的选择列表。
                     pageNumber: 1,                          //当设置了 pagination 属性时，初始化页码。
                     rownumbers: true,                       //设置为 true，则显示带有行号的列。
                     fitColumns:false,
                     autoSizeColumn:true,
                     autoRowHeight:true,                     //定义是否设置基于该行内容的行高度。设置为 false，则可以提高加载性能。
                     loadMsg: "正在加载数据...",              //当从远程站点加载数据时，显示的提示消息。
                     columns:[[                              //Column是一个数组对象，它的每个元素也是一个数组。
                         {field:'id',title:'id',width:100,hidden:'true'},
		                 {field:'tracking_number',title:'单号',width:100},
		                 {field:'appuser_name',title:'申请人',width:70},
		                 {field:'appuser_identitys',title:'身份',width:50},
		                 {field:'department',title:'部门',width:100},
		                 {field:'reason',title:'事由',width:100},
		                 {field:'money',title:'金额',width:50},	                 
		                 {field:'dtime',title:'申请时间',width:130,
		                       formatter:function(value,row,index){         //value字段的值,就是这一行，这列的数据。row这一行的对象，这个字段的索引
		                            var Timestamp=new Date(value);  
		                            return Timestamp.toLocaleString();
		                       } 
		                 },
		                 {field:'deliver',title:'送单人',width:70},
		                 {field:'operator',title:'经办人',width:70},
		                 {field:'contacts',title:'联系人',width:70},
		                 {field:'telephone',title:'联系人电话',width:100},
		                 {field:'state',title:'结果',width:100},
		                 {field:'result',title:'原因',width:100},
		                 {field:'remoney',title:'报销金额',width:50},
		                 {field:'hospital_level',title:'医院级别',width:50},
		                 {field:'medical_number',title:'医保卡',width:100},
		                 {field:'bank_number',title:'银行卡',width:100},
		                 {field:'type',title:'类型',width:50},
		                 {field:'picture1',title:'药费单',width:100,height:100,align:'center',                          //照片格子的大小
		                         formatter:function(value,row,index){
		                                   return '<img onclick="preview(' + "'" +value+"'" + ')" style="height: 100px; width: 100px;" src="'+value+'"/>';    //照片的大小
		                        }
		                  },
		                  {field:'picture2',title:'病历',width:100,height:100,align:'center',                          //照片格子的大小
		                         formatter:function(value,row,index){
		                                   return '<img onclick="preview(' + "'" +value+"'" + ')" style="height: 100px; width: 100px;" src="'+value+'"/>';    //照片的大小
		                        }
		                  },
		                  {field:'picture3',title:'补充',width:100,height:100,align:'center',                          //照片格子的大小
		                         formatter:function(value,row,index){
		                                   return '<img onclick="preview(' + "'" +value+"'" + ')" style="height: 100px; width: 100px;" src="'+value+'"/>';    //照片的大小
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
                                       url:'reimbursement!reloaddone.action',
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
                                       url:'reimbursement!reloaddonep.action',
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
                 
                 //放大图片
                 function preview(img){
                 	$("#showimg>img").attr("src",img);             //修改div里的img的src
                      layer.open({
                      type: 1,
                      title: false,
                      closeBtn: 0,
                      area: ['70%', '95%'],
                      skin: 'layui-layer-nobg',                    //没有背景色
                      shadeClose: true,
                      content: $("#showimg")
                      });
                 }
            </script>     
  </body>
</html>