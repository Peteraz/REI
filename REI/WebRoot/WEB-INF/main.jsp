<%@ page language="java" import="java.util.*" contentType="text/html;charaset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>医疗报销系统</title> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
	<meta http-equiv="pragma" content="no-cache">                         <!--这几句meta作用是清除浏览器中的缓存,再次进入曾经访问过的页面时，浏览器必须从服务端下载最新的内容，达到刷新的效果。 -->
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">     <!--最后两句meta,做seo用 -->
	<meta http-equiv="description" content="This is my page">
	
	<!-- <link rel="stylesheet" type="text/css" href="styles.css"> -->
	
   <link rel="stylesheet" href="./css/base.css" >
   <link rel="stylesheet" href="./css/platform.css" >
   <link rel="stylesheet" href="./custom/uimaker/easyui.css">
  </head>
  
  <body>
   <div class="container">
        <div id="pf-hd">
            <div class="pf-logo">
                <img src="./images/main/main_logo.png" alt="logo">
            </div>       

            <div class="pf-user">
                <div class="pf-user-photo">
                    <img src="./images/main/user.png" alt="">
                </div>
                <h4 class="pf-user-name ellipsis">${user.account}</h4>
                <i class="iconfont xiala">&#xe607;</i>

                <div class="pf-user-panel">
                    <ul class="pf-user-opt">
                        <li class="pf-userinfo">
                            <a>
                                <i class="iconfont">&#xe60d;</i>
                                <span>用户信息</span>
                            </a>
                        </li>
                        <li class="pf-changeuserinf">
                            <a>
                                <i class="iconfont">&#xe62d;</i>
                                <span>修改信息</span>
                            </a>
                        </li>
                        <li class="pf-modify-pwd">
                            <a>
                                <i class="iconfont">&#xe634;</i>
                                <span>修改密码</span>
                            </a>
                        </li>
                        <li>
                            <a href="user!logout.action">
                                <i class="iconfont">&#xe60e;</i>
                                <span>退出</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
      </div>

        <div id="pf-bd">
            <div id="pf-sider">
                <h2 class="pf-model-name">
                    <span class="iconfont">&#xe60d;</span>
                    <span class="pf-name">${user.identitys}</span>
                    <span class="toggle-icon"></span>
                </h2>

                <ul class="sider-nav">
                     <li id="picture">
                        <a href="javascript:;">
                            <span class="iconfont sider-nav-icon">&#xe65d;</span>
                            <span class="sider-nav-title">照片</span>
                            <i class="iconfont">&#xe642;</i>
                        </a>
                         <ul class="sider-nav-s">
                           <li class="changecolor"><a onclick="cpicture()">证件</a></li>
                           <li class="changecolor"><a onclick="ipicture()">身份证</a></li>
                           <%-- <li><a onclick="icon()">头像</a></li>--%>
                        </ul>
                     </li>
                     <li id="applyre">
                        <a href="javascript:;">
                            <span class="iconfont sider-nav-icon">&#xe6a8;</span>
                            <span class="sider-nav-title">申请报销</span>
                            <i class="iconfont">&#xe642;</i>
                        </a>
                        <ul class="sider-nav-s">
                           <li class="changecolor"><a onclick="outpatient()">门诊报销</a></li>
                           <li class="changecolor"><a onclick="hospital()">住院报销</a></li>
                        </ul>
                     </li>
                     <li id="pend">
                        <a onclick="auditing()">
                            <span class="iconfont sider-nav-icon">&#xe6da;</span>
                            <span class="sider-nav-title">审核进度</span>
                            <i class="iconfont">&#xe642;</i>
                        </a>
                     </li>
                     <li id="history">
                        <a onclick="history()">
                            <span class="iconfont sider-nav-icon">&#xe61e;</span>
                            <span class="sider-nav-title">历史记录</span>
                            <i class="iconfont">&#xe642;</i>
                        </a>
                     </li>
                     <li id="examinea">
                        <a onclick="examine()">
                            <span class="iconfont sider-nav-icon">&#xe6f0;</span>
                            <span class="sider-nav-title">待审批申请</span>
                            <i class="iconfont">&#xe642;</i>
                        </a>
                     </li>
                     <li id="alexaminea">
                        <a onclick="alexamine()">
                            <span class="iconfont sider-nav-icon">&#xe644;</span>
                            <span class="sider-nav-title">已审批申请</span>
                            <i class="iconfont">&#xe642;</i>
                        </a>
                     </li>
                     <li id="penda">
                        <a onclick="pend()">
                            <span class="iconfont sider-nav-icon">&#xe6f0;</span>
                            <span class="sider-nav-title">待审核申请</span>
                            <i class="iconfont">&#xe642;</i>
                        </a>
                     </li>
                     <li id="alpenda">
                        <a onclick="alpend()">
                            <span class="iconfont sider-nav-icon">&#xe644;</span>
                            <span class="sider-nav-title">已审核申请</span>
                            <i class="iconfont">&#xe642;</i>
                        </a>
                     </li>
                     <li id="manage">
                        <a onclick="managerUser()">
                            <span class="iconfont sider-nav-icon">&#xe6f1;</span>
                            <span class="sider-nav-title">用户管理</span>
                            <i class="iconfont">&#xe642;</i>
                        </a>
                     </li>
                     <li id="manager">
                        <a onclick="userapply()">
                            <span class="iconfont sider-nav-icon">&#xe6d0;</span>
                            <span class="sider-nav-title">用户审核</span>
                            <i class="iconfont">&#xe642;</i>
                        </a>
                     </li>
                 </ul> 
            </div>

            <div id="pf-page">
                <div class="easyui-tabs1" style="width:100%;height:100%;">
                </div>   
                
            </div>
        </div>
         
        <div id="pf-ft">
            <div class="system-name">
              <i class="iconfont">&#xe6fe;</i>
              <span>医疗报销系统</span>
            </div>
            <div class="copyright-name">
              <span>CopyRight&nbsp;2017&nbsp;&nbsp;关步青&nbsp;版权所有</span>
              <i class="iconfont" >&#xe6ff;</i>
            </div>   
        </div>

    <script type="text/javascript" src="./custom/jquery.min.js"></script>
    <script type="text/javascript" src="./custom/jquery.easyui.min.js"></script>
    <!-- <script type="text/javascript" src="js/menu.js"></script> -->
    <script type="text/javascript" src="./js/main.js"></script>
    <!--[if IE 7]>
      <script type="text/javascript">
        $(window).resize(function(){
          $('#pf-bd').height($(window).height()-76);
        }).resize();
        
      </script>
    <![endif]-->  

    
    <script type="text/javascript">
    //动态在主页的右边添加页面
    $(document).ready(function(){   
         $(".tabs-panels").html('<iframe class="page-iframe" src="reimbursement!mainpage.action" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>');
         $(".tabs-header").html('<button class="headr" onclick="headr()">首页</button>');
         var identitys="${user.identitys}";
         var state="${user.state}";
         
         //判断不同身份，见到不同的功能
         if(identitys=="学生" || identitys=="老师" || identitys=="合同工" || identitys=="在职" || identitys=="退休")
         {
           if(state=="待审核"){
              $("#applyre").hide();
              $("#pend").hide();
              $("#history").hide();
           }
           $("#examinea").hide();
           $("#alexaminea").hide();
           $("#penda").hide();
           $("#alpenda").hide();
           $("#manage").hide();
           $("#manager").hide();
         }
         else if(identitys=="审批人")
         {
            if(state=="待审核"){
             $("#examinea").hide();
             $("#alexaminea").hide();
            }
            $("#applyre").hide();
            $("#pend").hide();
            $("#history").hide();
            $("#penda").hide();
            $("#alpenda").hide();
            $("#manage").hide();
            $("#manager").hide();
         }
         else if(identitys=="财务部")
         {
            if(state=="待审核"){
             $("#penda").hide();
             $("#alpenda").hide();
            }
            $("#applyre").hide();
            $("#pend").hide();
            $("#history").hide();
            $("#examinea").hide();
            $("#alexaminea").hide();
            $("#manage").hide();
            $("#manager").hide();
         }
         else if(identitys=="管理员")
         {
            $("#picture").hide();
            $("#applyre").hide();
            $("#pend").hide();
            $("#history").hide();
            $("#examinea").hide();
            $("#alexaminea").hide();
            $("#penda").hide();
            $("#alpenda").hide();
         }
    });
    
    //按下证件或身份证或门诊或住院的标签，会改变标签的颜色
    $(".changecolor").click(function(){
    	$("li .active").removeClass("active");       //移除html元素使用了active class的li元素的active class。
    	$(this).addClass("active");                  //this是html元素，即点中的元素
    });
    
    //用户信息
    function userinfo(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'user!userinfo.action')
    }
    
    //主页
    function headr(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'reimbursement!mainpage.action')
    }
    
    //证件照片
    function cpicture(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'user!cpicture.action')
    }
    
    //身份证照片
    function ipicture(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'user!ipicture.action')
    }
     
    /*头像
    function icon(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'user!icon.action')
    }*/
    
    //门诊报销
    function outpatient(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'user!outpatient.action')
    }
    
    //住院报销
    function hospital(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'user!hospital.action')
    }
    
    //审核进度
    function auditing(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'user!auditing.action')
    }
    
    //历史记录
    function history(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'user!history.action')
    }
    
    //待审批申请
    function examine(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'reimbursement!examine.action')
    }
    
    //已审批申请
    function alexamine(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'reimbursement!alexamine.action')
    }
    
    //待审核申请
    function pend(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'reimbursement!pend.action')
    }
    
    //已审核申请
    function alpend(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'reimbursement!alpend.action')
    }
    
    //管理用户
    function managerUser(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'user!manageruser.action')
    }
    
    //用户申请
    function userapply(){
       $('#pf-page').find('iframe').eq(0).attr('src', 'user-apply!userapply.action')
    }
    
    $('.easyui-tabs1').tabs({
      tabHeight: 44,
      onSelect:function(title,index){
        var currentTab = $('.easyui-tabs1').tabs("getSelected");
        if(currentTab.find("iframe") && currentTab.find("iframe").size()){
            currentTab.find("iframe").attr("src",currentTab.find("iframe").attr("src"));
        }
      }
    })
    $(window).resize(function(){
          $('.tabs-panels').height($("#pf-page").height()-46);
          $('.panel-body').height($("#pf-page").height()-76)
    }).resize();

    var page = 0,
        pages = ($('.pf-nav').height() / 70) - 1;

    if(pages === 0){
      $('.pf-nav-prev,.pf-nav-next').hide();
    }
    $(document).on('click', '.pf-nav-prev,.pf-nav-next', function(){


      if($(this).hasClass('disabled')) return;
      if($(this).hasClass('pf-nav-next')){
        page++;
        $('.pf-nav').stop().animate({'margin-top': -70*page}, 200);
        if(page == pages){
          $(this).addClass('disabled');
          $('.pf-nav-prev').removeClass('disabled');
        }else{
          $('.pf-nav-prev').removeClass('disabled');
        }
        
      }else{
        page--;
        $('.pf-nav').stop().animate({'margin-top': -70*page}, 200);
        if(page == 0){
          $(this).addClass('disabled');
          $('.pf-nav-next').removeClass('disabled');
        }else{
          $('.pf-nav-next').removeClass('disabled');
        }
        
      }
    })
   
    // setTimeout(function(){
    //    $('.tabs-panels').height($("#pf-page").height()-46);
    //    $('.panel-body').height($("#pf-page").height()-76)
    // }, 200)
    </script>
</body> 
</html>