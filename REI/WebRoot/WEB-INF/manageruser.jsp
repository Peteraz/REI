<%@ page language="java" import="java.util.*" contentType="text/html;charaset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户管理</title> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
	<meta http-equiv="pragma" content="no-cache">                         <!--这几句meta作用是清除浏览器中的缓存,再次进入曾经访问过的页面时，浏览器必须从服务端下载最新的内容，达到刷新的效果。 -->
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">     <!--最后两句meta,做seo用 -->
	<meta http-equiv="description" content="This is my page">
	
	<!--<link rel="stylesheet" type="text/css" href="styles.css">-->

	<link rel="stylesheet" type="text/css" href="./custom/uimaker/icon.css">
    <link rel="stylesheet" href="./custom/uimaker/easyui.css">
  </head>
  <body>
          <div  class="container">
               <table id="dg" >
                   <thead>
                          <tr></tr>
                   </thead>
               </table>
               <div id="tb" >
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="Edit()">修改</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="Insert()">添加</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="DestroyUser()">删除</a>
                  <input id="searchuser" name="searchuser" placeholder="请输入证件" style="width: 130px; vertical-align: middle;"></input>
                  <a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="Search()">查询</a>   
               </div>
            </div> 
            <div id="showimg" class="hide" style="display: none;">
                 <img src="" />
            </div>
            <!--对话框的按钮-->
            <div id="dlg-buttons">
                 <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="Submit()" style="width:90px">保存</a>
                 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
            </div>
            <div id="dlg" class="easyui-dialog" style="width:400px" closed="true" buttons="#dlg-buttons">
                <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
                     <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">用户信息</div>
                     <div style="margin-bottom:10px">
                           <label>账号:</label>
                           <input type="text" name="account" id="account" required="true"  style="width:100%">
                     </div>
                     <div style="margin-bottom:10px">
                           <label>密码:</label>
                           <input type="text" name="password" id="password" required="true" style="width:100%">
                     </div>
                     <div style="margin-bottom:10px">
                           <label>邮箱:</label>
                           <input type="text" name="email" id="email" required="true" style="width:100%">
                     </div>
                     <div style="margin-bottom:10px">
                           <label>姓名:</label>
                           <input type="text" name="name" id="name" required="true" style="width:100%">
                     </div>
                     <div style="margin-bottom:10px">
                           <label>性别:</label>
                           <input type="text" name="sex" id="sex" required="true" style="width:100%">
                     </div>
                      <div style="margin-bottom:10px">
                           <label>身份:</label>
                           <input type="text" name="identitys" id="identitys" required="true" style="width:100%">
                     </div>
                     <div style="margin-bottom:10px">
                           <label>证件号:</label>
                           <input type="text" name="credential_number" id="credential_number" required="true" style="width:100%">
                     </div>
                     <div style="margin-bottom:10px">
                           <label>身份证:</label>
                           <input type="text" name="idcard_number" id="idcard_number" required="true" style="width:100%">
                     </div>
                     <div style="margin-bottom:10px">
                           <label>部门:</label>
                           <input type="text" name="department" id="department" required="true" style="width:100%">
                     </div>
                     <input type="hidden" id="uid" />
                     <input type="hidden" id="state" />
                </form>
            </div>           
            
            <script type="text/javascript" src="./custom/jquery.min.js" charset="utf-8"></script>
	        <script type="text/javascript" src="./custom/jquery.cookie.js" charset="utf-8"></script>
	        <script type="text/javascript" src="./custom/jquery.easyui.min.js" charset="utf-8"></script>
	        <script type="text/javascript" src="./custom/easyui-lang-zh_CN.js" charset="utf-8"></script>
            <script type="text/javascript" src="./js/layer.js"></script>
            <script type="text/javascript">   			            
                 //用户表格
                 $('#dg').datagrid({
                     url:"user!managerusers.action",        //从远程站点请求数据的 URL。
                     title:'用户',                            //数据表名
                     toolbar: '#tb',
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
                     loadMsg: "正在加载数据...",                //当从远程站点加载数据时，显示的提示消息。
                     columns:[[                                //Column是一个数组对象，它的每个元素也是一个数组。
                         {field:'id',title:'用户id',width:100,hidden:'true'},
		                 {field:'account',title:'账号',width:100},
		                 {field:'password',title:'密码',width:230},
		                 {field:'email',title:'邮箱',width:120},
		                 {field:'name',title:'名字',width:70},
		                 {field:'sex',title:'性别',width:30},
		                 {field:'state',title:'状态',width:50}, 
		                 {field:'identitys',title:'身份',width:50},
		                 {field:'department',title:'部门',width:100},        
		                 {field:'credential_number',title:'证件',width:100},
		                 {field:'idcard_number',title:'身份证',width:100},
		                 {field:'cpicture',title:'证件照片',width:100,height:100,align:'center',                          //照片格子的大小
		                         formatter:function(value,row,index){
		                                   return '<img onclick="preview(' + "'" +value+"'"+ ')" style="height: 100px; width: 100px;" src="'+value+'"/>';    //照片的大小
		                         },
		                 },
		                 {field:'ipicture',title:'身份照片',width:100,height:100,align:'center',                          //照片格子的大小
		                         formatter:function(value,row,index){
		                                   return '<img onclick="preview(' + "'" +value+"'"+ ')" style="height: 100px; width: 100px;" src="'+value+'"/>';    //照片的大小
		                         },
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
                                       url:'user!reload.action',
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
                                       url:'user!reloadp.action',
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
                 
                //使用证件号搜索某个用户
                function Search(){
                   var options = $("#dg" ).datagrid("getPager" ).data("pagination" ).options;
                   var psize = options.pageSize;                                   //获取当前页面的数据条数
                   $.ajax({
                          //提交数据的方式 POST 或 GET
                          type:'POST',
                          //提交的地址
                          url:'user!searchuser.action',
                          //提交的数据
                          data:{
                                searchuser:$("#searchuser").val(),
                                psize:psize
                                },
                           //返回数据的格式
                           datatype:'json',//"xml", "html", "script", "json", "jsonp", "text".
                           //成功返回后调用的函数
                           success:function(data){
                                var data=JSON.parse(data);
                                if(data.msg=="1"){
                                    $('#dg').datagrid('loadData', data);
                                }else{
                                	$('#dg').datagrid('loadData', data);
                                }
                           }
                   });
                } 
                 
                //编辑按钮
                function Edit(){
                    var row = $('#dg').datagrid('getSelected');
                    if(row){
                        //初始化表单值
                        $("#uid").val(row.id);
                        $("#state").val(row.state);
                        $("#account").val(row.account);
                        $("#account").attr("disabled","disabled");                 //锁定不能修改
                        $("#password").val(row.password);
                        $("#password").attr("disabled","disabled");                //锁定不能修改
                        $("#email").val(row.email);
                        $("#email").attr("disabled","disabled");                   //锁定不能修改
                        $("#name").val(row.name);
                        $("#sex").val(row.sex);
                        $("#identitys").val(row.identitys);               
                        $("#department").val(row.department);
                        $("#credential_number").val(row.credential_number);
                        $("#credential_number").attr("disabled","disabled");       //锁定不能修改
                        $("#idcard_number").val(row.idcard_number); 
                        $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑');
                    }else{
                        $.messager.alert("提示","请先选中要编辑的行!");
                    }
                 }
                 
                //添加按钮
                function Insert(){
                    $('#fm').form('clear');                                         //清空表格
                    $("#account").removeAttr("disabled");                          //关闭锁定
                    $("#password").removeAttr("disabled");                          //关闭锁定
                    $("#email").removeAttr("disabled");                          //关闭锁定
                    $("#credential_number").removeAttr("disabled");                          //关闭锁定
                    $('#dlg').dialog('open').dialog('center').dialog('setTitle','添加');                         
                 }  
 
                //删除用户 
                function DestroyUser(){
                    var row = $('#dg').datagrid('getSelected');
                    var options = $("#dg" ).datagrid("getPager" ).data("pagination" ).options;
                    var pnumber = options.pageNumber;                               //获取当前页面号
                    var psize = options.pageSize;                                   //获取当前页面的数据条数
                    if(row){
                        $.messager.confirm('提示','你确定要删除这个用户?',function(r){ 
                             if(r){
                                  $.ajax({
                                       //提交数据的方式 POST 或 GET
                                       type:'POST',
                                       //提交的地址
                                       url:'user!destroyuser.action',
                                       //提交的数据
                                       data:{
                                           id:row.id,
                                           pnumber:pnumber,
                                           psize:psize
                                       },
                                       //返回数据的格式
                                       datatype:'json',//"xml", "html", "script", "json", "jsonp", "text".
                                       //成功返回后调用的函数
                                       success:function(data){
                                            var data=JSON.parse(data);
                                            if(data.msg=="删除成功"){
                                                 $("#dlg").dialog('close',false);
                                                 layer.alert(data.msg,{icon:6}, function() {
                                  	                    location.reload();
                                                 })
                                            }
                                       }
                                    });
                               } 
                         });
                    }else{
                         $.messager.alert('提示','请先选中要删除的行！');
                         return false;
                    }
                 }
                 
                //确定
                function Submit(){
                     var identitys=$("#identitys").val();
                     if(identitys!="学生" && identitys!="老师" && identitys!="在职" && identitys!="合同工" && identitys!="退休" && identitys!="管理员")
                     {
                           alert("请输入指定的其中一种身份!"); 
                           $("#identitys").focus();
                           return false;  
                     }else{
                            var id=$("#uid").val();
                            if(id != ""){
                                 EditUser(id);
                                 return true;
                            }else{
                                 AddUser();
                                 return true;
                            }
                     }

                 } 
                 
                //添加用户 
                function AddUser(){
                    var options = $("#dg" ).datagrid("getPager" ).data("pagination" ).options;
                    var pnumber = options.pageNumber;                               //获取当前页面号
                    var psize = options.pageSize;                                   //获取当前页面的数据条数 
                    $.ajax({
                        //提交数据的方式 POST 或 GET
                        type:'POST',
                        //提交的地址
                        url:'user!adduser.action',
                        //提交的数据
                        data:{
                             account:$("#account").val(),
                             password:$("#password").val(),
                             email:$("#email").val(),
                             name:$("#name").val(),
                             sex:$("#sex").val(),
                             identitys:$("#identitys").val(),
                             department:$("#department").val(),
                             credential_number:$("#credential_number").val(),
                             idcard_number:$("#idcard_number").val(),
                             pnumber:pnumber,
                             psize:psize
                        },
                        //返回数据的格式
                        datatype:'json',//"xml", "html", "script", "json", "jsonp", "text".
                        //成功返回后调用的函数
                        success:function(data){
                              var data=JSON.parse(data);
                              if(data.msg=="添加成功"){ 
                                     $("#dlg").dialog('close',false);
                                     $("#dg").datagrid('reload');
                              }
                              else if(data.msg=="添加失败"){
                                    
                              } 
                              $.messager.alert("提示",data.msgbox);
                        }
                     });
                 }
                 
                 //修改用户
                 function  EditUser(uid){
                     var options = $("#dg" ).datagrid("getPager" ).data("pagination" ).options;
                     var pnumber = options.pageNumber;                               //获取当前页面号
                     var psize = options.pageSize;                                   //获取当前页面的数据条数  
                     $.ajax({
                        //提交数据的方式 POST 或 GET
                        type:'POST',
                        //提交的地址
                        url:'user!edituser.action',
                        //提交的数据
                        data:{
                             id:$("#uid").val(),
                             account:$("#account").val(),
                             password:$("#password").val(),
                             email:$("#email").val(),
                             name:$("#name").val(),
                             sex:$("#sex").val(),
                             state:$("#state").val(),
                             identitys:$("#identitys").val(),
                             department:$("#department").val(),
                             credential_number:$("#credential_number").val(),
                             idcard_number:$("#idcard_number").val(),
                             pnumber:pnumber,
                             psize:psize
                        },
                        //返回数据的格式
                        datatype:'json',//"xml", "html", "script", "json", "jsonp", "text".
                        //成功返回后调用的函数
                        success:function(data){
                              var data=JSON.parse(data);
                              if(data.msg=="修改成功"){
                                  $("#dlg").dialog('close',false);
                                  $('#dg').datagrid('loadData', data);
                              }
                              $.messager.alert("提示",data.msgbox);
                        }
                     });
                 }
                 
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