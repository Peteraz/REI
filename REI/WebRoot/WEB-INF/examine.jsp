<%@ page language="java" import="java.util.*" contentType="text/html;charaset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>待审批申请</title> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
	<meta http-equiv="pragma" content="no-cache">                         <!--这几句meta作用是清除浏览器中的缓存,再次进入曾经访问过的页面时，浏览器必须从服务端下载最新的内容，达到刷新的效果。 -->
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">     <!--最后两句meta,做seo用 -->
	<meta http-equiv="description" content="This is my page">
	
	<!-- <link rel="stylesheet" type="text/css" href="styles.css"> -->
   <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
    <link rel="stylesheet" href="./custom/uimaker/easyui.css">
  </head>
  
  <body>
      <div  class="container">
             <table id="dg">
                   <thead>
                          <tr></tr>
                   </thead>
             </table>
             <div id="tb" >
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="Edit()">审批</a>
             </div>
      </div>
      <div id="showimg" class="hide" style="display: none;">
                 <img src="" />
      </div>
      <div id="dlg-buttons">
                 <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="Submit()" style="width:90px">保存</a>
                 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
            </div>
            <div id="dlg" class="easyui-dialog" style="width:400px" closed="true" buttons="#dlg-buttons">
                <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
                     <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">审批</div>
                     <div style="margin-bottom:10px">
                           <label>审批人签名:</label>
                           <input type="text" name="approver_sign" id="approver_sign" required="true"  style="width:100%">
                     </div>
                     <div style="margin-bottom:10px">
                           <label>审批操作:</label>
                           <input type="text" name="approver_op" id="approver_op" required="true" style="width:100%">
                     </div>  
                     <div style="margin-bottom:10px">
                           <label>结果:</label><br/>
                           <textarea name="result" id="result" rows="3" cols="20" style="height:50px;width:300px"></textarea>
                     </div>  
                     <input type="hidden" id="uid" />
                </form>
            </div>
      <script type="text/javascript" src="./custom/jquery.min.js"></script>
      <script type="text/javascript" src="./custom/jquery.easyui.min.js"></script>
      <script type="text/javascript" src="./js/layer.js"></script>
      <script type="text/javascript">
       $('#dg').datagrid({
                     url:"reimbursement!examines.action",        //从远程站点请求数据的 URL。
                     title:'待审批',                               //数据表名
                     toolbar: '#tb',
                     method: 'post',
                     width: 'auto',
                     height: '600px',
                     singleSelect: true,                       //设置为 true，则显示带有行号的列。
                     fitColumns: false,                        //设置为 true，则会自动扩大或缩小列的尺寸以适应网格的宽度并且防止水平滚动。
                     pagination: true,                         //设置为 true，则在数据网格（datagrid）底部显示分页工具栏。
                     pageSize: 5,                              //初始页的大小
                     pageList:[5,10,15,20,25,30,35],           //当设置了 pagination 属性时，初始化页面尺寸的选择列表。
                     pageNumber: 1,                            //当设置了 pagination 属性时，初始化页码。
                     rownumbers: true,                         //设置为 true，则显示带有行号的列。
                     autoRowHeight:true,                      //定义是否设置基于该行内容的行高度。设置为 false，则可以提高加载性能。
                     loadMsg: "正在加载数据...",                //当从远程站点加载数据时，显示的提示消息。
                     columns:[[                                //Column是一个数组对象，它的每个元素也是一个数组。
                         {field:'id',title:'id',width:100,hidden:'true'},
		                 {field:'tracking_number',title:'单号',width:100},
		                 {field:'appuser_name',title:'申请人',width:70},
		                 {field:'appuser_identitys',title:'身份',width:50},
		                 {field:'department',title:'部门',width:100},
		                 {field:'money',title:'金额',width:50},
		                 {field:'approver_sign',title:'审批人签名',width:100},
		                 {field:'approver_op',title:'审批操作',width:100},
		                 {field:'state',title:'进度',width:100},
		                 {field:'dtime',title:'申请时间',width:130,
		                       formatter:function(value,row,index){         //value字段的值,就是这一行，这列的数据。row这一行的对象，这个字段的索引
		                            var Timestamp=new Date(value);  
		                            return Timestamp.toLocaleString();      //转换时间格式
		                       } 
		                 },
		                 {field:'deliver',title:'送单人',width:70},
		                 {field:'operator',title:'经办人',width:70},
		                 {field:'contacts',title:'联系人',width:70},
		                 {field:'telephone',title:'联系人电话',width:100},
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
                                       url:'reimbursement!reloadexamines.action',
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
                                       url:'reimbursement!reloadexaminesp.action',
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
                       
                //编辑按钮
                function Edit(){
                    var row = $('#dg').datagrid('getSelected');
                    if(row){
                        //初始化表单
                        $("#uid").val(row.id);
                        $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑');        
                    }else{
                        $.messager.alert("提示","请先选中要编辑的行!");
                    }
                 }
                 
                //确定
                function Submit(){
                     var id=$("#uid").val();
                         EditUser(id); 
                 } 
                 
   
                 //编辑报销单
                 function  EditUser(uid){
                     var options = $("#dg" ).datagrid("getPager" ).data("pagination" ).options;
                     var pnumber = options.pageNumber;                               //获取当前页面号
                     var psize = options.pageSize;                                   //获取当前页面的数据条数  
                     $.ajax({
                        //提交数据的方式 POST 或 GET
                        type:'POST',
                        //提交的地址
                        url:'reimbursement!reloadexamine.action',
                        //提交的数据
                        data:{
                             id:uid,
                             approver_sign:$("#approver_sign").val(),
                             approver_op:$("#approver_op").val(),
                             result:$("#result").val(),
                             pnumber:pnumber,
                             psize:psize
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
                 }
                  
                  //显示图片
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
