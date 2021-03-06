<%@ page language="java" import="java.util.*" contentType="text/html;charaset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>门诊报销</title> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
	<meta http-equiv="pragma" content="no-cache">                         <!--这几句meta作用是清除浏览器中的缓存,再次进入曾经访问过的页面时，浏览器必须从服务端下载最新的内容，达到刷新的效果。 -->
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">     <!--最后两句meta,做seo用 -->
	<meta http-equiv="description" content="This is my page">
	
	<!--<link rel="stylesheet" type="text/css" href="styles.css">-->
	
    <link href="css/outpatient.css" rel="stylesheet" >
    <link href="./custom/uimaker/easyui.css" rel="stylesheet" >
  </head>
  <body>
      <form action="reimbursement!applyop.action" method="post" enctype="multipart/form-data">
              <center>
              <table cellspacing="50">
                    <tr>
                        <td>部门:<input type="text" id="txt_department" disabled="disabled"   /></td>
                        <td>身份:<input type="text" id="txt_identitys" disabled="disabled"   /></td>
                    </tr>
                    <tr>
                        <td>姓名:<input type="text" id="txt_name" disabled="disabled"  /></td>
                        <td>事由:<input type="text" id="txt_reason" name="rentity.reason" /></td>  
                    </tr>
                    <tr>
                        <td>金额:<input type="text" id="money" name="rentity.money" /></td>
                        <td>
                                                            医院级别:<select  id="hospital_level" name="rentity.hospital_level">
                                                <option value="没有" selected="selected">请选择医院级别</option>
                                                <option value="甲">甲</option>
                                                <option value="乙">乙</option>
                                                <option value="丙">丙</option>
                                       </select>
                        </td>
                    </tr>
                    <tr>
                        <td>送单人:<input type="text"  id="txt_deliver" name="rentity.deliver" /></td>
                        <td>经办人:<input type="text"  id="txt_operator" name="rentity.operator" /></td>
                    </tr>
                    <tr>
                        <td>联系人:<input type="text"  id="txt_contacts" name="rentity.contacts" /></td>
                        <td>联系电话:<input type="text"  id="txt_telephone" name="rentity.telephone" /></td>
                    </tr>
                    <tr>
                        <td>医保卡:<input type="text"  id="medical_number" name="rentity.medical_number" /></td>
                        <td>银行卡:<input type="text"  id="bank_number" name="rentity.bank_number" /></td>
                    </tr>
                    <tr>
                         <td colspan="2"><center>药费单    请选择上传的文件：<input type="file" id="photo1" name="file" onchange="checkImg1()"/></center></td>
                    </tr>
                    <tr>
                         <td colspan="2"><center>病      历    请选择上传的文件：<input type="file" id="photo2" name="file" onchange="checkImg2()" /></center></td>
                    </tr>
                    <tr>
                         <td colspan="2"><center>补       充     请选择上传的文件：<input type="file" id="photo3" name="file" onchange="checkImg3()" /></center></td>
                    </tr>
                    <tr>
                        <td colspan="2"><center><input type="submit" class="submit" value="提交" onclick="return check()"/></center></td>
                    </tr>
              </table>
              </center>
        </form>
        <script type="text/javascript" src="./custom/jquery.min.js" charset="utf-8"></script>
	    <script type="text/javascript" src="./custom/jquery.cookie.js" charset="utf-8"></script>
	    <script type="text/javascript" src="./custom/jquery.easyui.min.js" charset="utf-8"></script>
	    <script type="text/javascript" src="./custom/easyui-lang-zh_CN.js" charset="utf-8"></script>
        <script type="text/javascript">
             $(document).ready(function(){
                 $("#txt_department").val("${user.department}");      //读取用户部门
                 $("#txt_identitys").val("${user.identitys}");        //读取用户身份
                 $("#txt_name").val("${user.name}");                  //读取用户名字
             });
             
             //操作结果
             var msg1 = "${uploadpstatus}";
             var msg2 = "${applyopstatus}";
             if(msg1!=null&&msg1!=""&&msg2!=null&&msg2!=""){
             	var msg=msg1+msg2;
      		 	$(function(){
      				$.messager.show({
      					title:"操作结果!",
      					msg:msg,
      					showType:'fade',
      					timeout:1500,
      					//中间
      					style:{ 
      						right:'',
      						bottom:''
      					}
      				});
      		    });   	
      		 }
             
             //检查报单有没有空的
             function check(){
                var img1=$("#photo1").val();
                var img2=$("#photo2").val();
                var img3=$("#photo3").val();
               
                if($("#txt_reason").val=="" || $("#txt_reason").val().length==0){
                    alert("请输入事由!"); 
                    $("#txt_reason").focus();
                    return false;                                                   //空的情况下，不提交报单
                }
                else if($("#money").val()=="" || $("#money").val().length==0)
                {
                    alert("请输入金额!"); 
                    $("#money").focus();
                    return false;                                                   //空的情况下，不提交报单
                }
                else if($("#hospital_level").val()=="没有")
                {
                    alert("请选择医院级别!"); 
                    $("#hospital_level").focus();
                    return false;
                }
                else if($("#txt_deliver").val()=="" || $("#txt_deliver").val().length==0)
                {
                    alert("请输入送单人!"); 
                    $("#txt_deliver").focus();
                    return false;
                }
                else if($("#txt_operator").val()=="" || $("#txt_operator").val().length==0)
                {
                    alert("请输入经办人!"); 
                    $("#txt_operator").focus();
                    return false;
                }
                else if($("#txt_contacts").val()=="" || $("#txt_contacts").val().length==0)
                {
                    alert("请输入联系人!"); 
                    $("#txt_contacts").focus();
                    return false;
                }
                else if($("#txt_telephone").val()=="" || $("#txt_telephone").val().length==0)
                {
                    alert("请输入联系人电话!"); 
                    $("#txt_telephone").focus();
                    return false;
                }
                else if($("#medical_number").val()=="" || $("#medical_number").val().length==0)
                {
                    alert("请输入医保卡!");
                    $("#medical_number").focus();
                    return false;
                }
                else if($("#bank_number").val()=="" || $("#bank_number").val().length==0)
                {
                    alert("请输入银行卡号!"); 
                    $("#bank_number").focus();
                    return false;
                }
                else if(img1.length==0){  
                    alert("请先选择上传的药费单!");  
                    return false;  
                }
                else if(img2.length==0){  
                    alert("请先选择上传的病历!");  
                    return false;  
                }
                else if(img3.length==0){  
                    alert("请先选择上传的补充!");  
                    return false;  
                }
                else
                {
                    return true; 
                }      
             }
             
            //检查文件是不是照片类型和大小限制
            function checkImg1(){    
                  var errMsg = "上传的附件文件不能超过2M！！！";  
                  var tipMsg="您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过2M，建议使用IE、FireFox、Chrome浏览器";
                  var maxsize = 2*1024*1024;    //不能超过2M
                  var filesize=0;               //w文件大小
                  var img=$("#photo1").val();    //根据id得到值
                  var index= img.indexOf(".");  //得到"."在第几位
                  img=img.substring(index);     //截断"."之前的，得到后缀
                  if(img!=".bmp" && img!=".png" && img!=".gif" && img!=".jpg" && img!=".jpeg"){  //根据后缀，判断是否符合图片格式
                     alert("不是指定图片格式,重新选择"); 
                     $("#photo1").val("");       // 不符合，就清除，重新选择
                  }else{
                       filesize = document.getElementById("photo").files[0].size;  //获取照片的大小  
                       if(filesize==-1){  
                          alert(tipMsg);  
                          return false;  
                       }else if(filesize>maxsize){  
                          alert(errMsg);  
                          $("#photo1").val("");       // 不符合，就清除，重新选择
                          return false;
                       }  
                  }  
            } 
             
            //检查文件是不是照片类型和大小限制
            function checkImg2(){    
                  var errMsg = "上传的附件文件不能超过2M！！！";  
                  var tipMsg="您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过2M，建议使用IE、FireFox、Chrome浏览器";
                  var maxsize = 2*1024*1024;    //不能超过2M
                  var filesize=0;               //w文件大小
                  var img=$("#photo2").val();    //根据id得到值
                  var index= img.indexOf(".");  //得到"."在第几位
                  img=img.substring(index);     //截断"."之前的，得到后缀
                  if(img!=".bmp" && img!=".png" && img!=".gif" && img!=".jpg" && img!=".jpeg"){  //根据后缀，判断是否符合图片格式
                     alert("不是指定图片格式,重新选择"); 
                     $("#photo2").val("");       // 不符合，就清除，重新选择
                  }else{
                       filesize = document.getElementById("photo").files[0].size;  //获取照片的大小  
                       if(filesize==-1){  
                          alert(tipMsg);  
                          return false;  
                       }else if(filesize>maxsize){  
                          alert(errMsg);  
                          $("#photo2").val("");       // 不符合，就清除，重新选择
                          return false;
                       }  
                  }  
            }
            
            //检查文件是不是照片类型和大小限制
            function checkImg3(){    
                  var errMsg = "上传的附件文件不能超过2M！！！";  
                  var tipMsg="您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过2M，建议使用IE、FireFox、Chrome浏览器";
                  var maxsize = 2*1024*1024;    //不能超过2M
                  var filesize=0;               //w文件大小
                  var img=$("#photo3").val();    //根据id得到值
                  var index= img.indexOf(".");  //得到"."在第几位
                  img=img.substring(index);     //截断"."之前的，得到后缀
                  if(img!=".bmp" && img!=".png" && img!=".gif" && img!=".jpg" && img!=".jpeg"){  //根据后缀，判断是否符合图片格式
                     alert("不是指定图片格式,重新选择"); 
                     $("#photo3").val("");       // 不符合，就清除，重新选择
                  }else{
                       filesize = document.getElementById("photo").files[0].size;  //获取照片的大小  
                       if(filesize==-1){  
                          alert(tipMsg);  
                          return false;  
                       }else if(filesize>maxsize){  
                          alert(errMsg);  
                          $("#photo3").val("");       // 不符合，就清除，重新选择
                          return false;
                       }  
                  }  
            }
        </script>
  </body>
</html>
