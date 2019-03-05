package mis.action;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mis.dao.ReimbursementDAO;
import mis.dao.UserApplyDAO;
import mis.dao.UserDAO;
import mis.entity.Reimbursement;
import mis.entity.User;
import mis.entity.UserApply;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import common.FileUpload;
import common.BaseAction;
//业务处理Action


//action是多实例的，对每一个请求都会new一个action,所以不必担心，参数会冲突
@SuppressWarnings("serial")
@Results( { @Result(name = "mainpage", location = "/WEB-INF/mainpage.jsp"),           //主页内容页面
	        @Result(name = "outpatient", location = "/WEB-INF/outpatient.jsp"),       //门诊报销页面
	        @Result(name = "hospital", location = "/WEB-INF/hospital.jsp"),           //住院报销页面
            @Result(name = "auditing", location = "/WEB-INF/auditing.jsp"),           //报销进度页面
            @Result(name = "examine", location = "/WEB-INF/examine.jsp"),             //待审批申请页面
            @Result(name = "alexamine", location = "/WEB-INF/alexamine.jsp"),         //已审批申请页面
            @Result(name = "pend", location = "/WEB-INF/pend.jsp"),                   //待审核申请页面
            @Result(name = "alpend", location = "/WEB-INF/alpend.jsp")                //已审核申请页面
		  })
public class ReimbursementAction extends BaseAction{
	@Autowired  
	private  HttpServletRequest request; 
	@Autowired
	private ReimbursementDAO dao;
	@Autowired
	private UserDAO udao;
	@Autowired
	private UserApplyDAO uadao;

	// ///////////////////////////////////////////////////////////////属性参数
	private List<Reimbursement> list;

	// 拿实体类当组合参数，实际上是它的属性帮我们传参了，常用
	private Reimbursement rentity;
	private User uentity;	
	private UserApply uaentity;
	
	//上传文件集合   
    private List<File> file;   

	//上传文件名集合   
    private List<String> fileFileName;  
    
    private FileUpload fu = new FileUpload();
    
    //主页面内容
  	public String mainpage() throws Exception {
  		HttpSession session=request.getSession(true);                                           //true,若存在会话则返回该会话，否则新建一个会话。
		User u=(User)session.getAttribute("user");		                                        //登录中的用户
		int id=u.getId();
		if(u.getIdentitys().equals("学生") || u.getIdentitys().equals("老师") || u.getIdentitys().equals("在职") || u.getIdentitys().equals("合同工") || u.getIdentitys().equals("退休"))
		{
			int auditing=dao.checkrs(id);                                                      //未完成报销的报销的单进度
	  		int history=dao.checkars(id);                                                      //报销历史记录条数
	  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());    //本地日期格式
            Date date=new Date();
            String today = sdf.format(date);
            Calendar c=Calendar.getInstance();
            c.setTime(date);
            int weekday=c.get(Calendar.DAY_OF_WEEK);
            String week=String.valueOf(weekday);
            if(week.equals("1")){
            	week="日";
            }
            else if(week.equals("2")){
            	week="一";
            }
            else if(week.equals("3")){
            	week="二";
            }
            else if(week.equals("4")){
            	week="三";
            }
            else if(week.equals("5")){
            	week="四";
            }
            else if(week.equals("6")){
            	week="五";
            }
            else if(week.equals("7")){
            	week="六";
            }
	  		ActionContext ac = ActionContext.getContext();
			//使用Map模拟HttpSession
			Map<String,Object> sess = ac.getSession();
			sess.put("auditing", auditing);
			sess.put("history", history);
			sess.put("today", today);  
		    sess.put("weekday", week);
			sess.put("user", u);  		     
		}
		else if(u.getIdentitys().equals("审批人"))
		{	
			int notdo=dao.checkes();                                                             //未处理的报销的报销单条数
	  		int dos=dao.checkaes(u.getName());                                                   //已处理的报销单条数
	  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());      //本地日期格式
            Date date=new Date();
            String today = sdf.format(date);                                                     //将Data转换成本地日期格式 
            Calendar c=Calendar.getInstance();                                                   //日历对象
            c.setTime(date);
	        int weekday=c.get(Calendar.DAY_OF_WEEK);                                             //获取今天是星期几        
	        String week=String.valueOf(weekday);                           
	        if(week.equals("1")){
            	week="星期日";
            }
            else if(week.equals("2")){
            	week="星期一";
            }
            else if(week.equals("3")){
            	week="星期二";
            }
            else if(week.equals("4")){
            	week="星期三";
            }
            else if(week.equals("5")){
            	week="星期四";
            }
            else if(week.equals("6")){
            	week="星期五";
            }
            else if(week.equals("7")){
            	week="星期六";
            }
	  		ActionContext ac = ActionContext.getContext();
			//使用Map模拟HttpSession
			Map<String,Object> sess = ac.getSession();
			sess.put("notdo", notdo);
			sess.put("dos", dos);
			sess.put("today", today);  
		    sess.put("weekday", week);
			sess.put("user", u);   		
		}
		else if(u.getIdentitys().equals("财务部"))
		{
			int notdo=dao.checkps();                                                             //未处理的报销的报销单条数
	  		int dos=dao.checkaps(u.getName());                                                   //已处理的报销单条数
	  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());      //本地日期格式
            Date date=new Date();
            String today = sdf.format(date);                                                     //将Data转换成本地日期格式 
            Calendar c=Calendar.getInstance();                                                   //日历对象
            c.setTime(date);
	        int weekday=c.get(Calendar.DAY_OF_WEEK);                                             //获取今天是星期几
	        String week=String.valueOf(weekday);
	        if(week.equals("1")){
            	week="星期日";
            }
            else if(week.equals("2")){
            	week="星期一";
            }
            else if(week.equals("3")){
            	week="星期二";
            }
            else if(week.equals("4")){
            	week="星期三";
            }
            else if(week.equals("5")){
            	week="星期四";
            }
            else if(week.equals("6")){
            	week="星期五";
            }
            else if(week.equals("7")){
            	week="星期六";
            }
	  		ActionContext ac = ActionContext.getContext();
			//使用Map模拟HttpSession
			Map<String,Object> sess = ac.getSession();
			sess.put("notdo", notdo);
			sess.put("dos", dos);
			sess.put("today", today);  
		    sess.put("weekday", week);
			sess.put("user", u);  
		}
		else if(u.getIdentitys().equals("管理员"))
		{
			int newu=uadao.checknewusers();                                                       //新申请的用户条数
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());       //本地日期格式
            Date date=new Date();
            String today = sdf.format(date);                                                      //将Data转换成本地日期格式
            Calendar c=Calendar.getInstance();                                                    //日历对象
            c.setTime(date);
	        int weekday=c.get(Calendar.DAY_OF_WEEK);                                              //获取今天是星期几
	        String week=String.valueOf(weekday);
	        if(week.equals("1")){
            	week="星期日";
            }
            else if(week.equals("2")){
            	week="星期一";
            }
            else if(week.equals("3")){
            	week="星期二";
            }
            else if(week.equals("4")){
            	week="星期三";
            }
            else if(week.equals("5")){
            	week="星期四";
            }
            else if(week.equals("6")){
            	week="星期五";
            }
            else if(week.equals("7")){
            	week="星期六";
            }
	  		ActionContext ac = ActionContext.getContext();
			//使用Map模拟HttpSession
			Map<String,Object> sess = ac.getSession();
			sess.put("newu", newu);
			sess.put("today", today);  
		    sess.put("weekday", week);
			sess.put("user", u);   
		}
  		
  		return "mainpage";
  	}
    
	//门诊报销
	public String applyop() throws Exception
	{
		Reimbursement rei=new Reimbursement();
		HttpSession session=request.getSession(true);                                             //true,若存在会话则返回该会话，否则新建一个会话。
		User u=(User)session.getAttribute("user");		                                          //登录中的用户
		long  id= Math.round(Math.random()*90000+1)+UUID.randomUUID().toString().hashCode();      //生成9-10位的数字订单号
		String ids=String.valueOf(Math.abs(id));                                                  //将数字订单号转成String类型
		Date date=new Date();                                                                     //报单生成时间    
		Timestamp time = new Timestamp(date.getTime());                                           //通过Timestamp对象转换后Date也可以显示时间
		rei.setAppuser_identitys(u.getIdentitys());                                               //赋值身份
		rei.setAppuser_name(u.getName());                                                         //赋值名
		rei.setDepartment(u.getDepartment());                                                     //部门
		rei.setTracking_number(ids);                                                              //单号
		rei.setAppuser_id(u.getId());                                                             //申请人的id
		rei.setReason(rentity.getReason());                                                       //事由
		rei.setMoney(rentity.getMoney());                                                         //金额   
		rei.setDeliver(rentity.getDeliver());                                                     //送单人
		rei.setOperator(rentity.getOperator());                                                   //经办人
		rei.setContacts(rentity.getContacts());                                                   //联系人
		rei.setTelephone(rentity.getTelephone());                                                 //联系人电话
		rei.setMedical_number(rentity.getMedical_number());                                       //医保卡
		rei.setBank_number(rentity.getBank_number());                                             //银行卡
		rei.setHospital_level(rentity.getHospital_level());                                       //医院级别
		rei.setRemoney(0);                                                                        //double不可以空
		rei.setState("待审批中");                                                                    //报销单的状态
		rei.setDtime(time);                                                                       //报销单生成时间
		rei.setType("门诊");                                                                       //报销单类型
		
		//上传照片
		if(file!=null){
			fu.setFile(file);                                                                      //上传文件集合
			fu.setFileFileName(fileFileName);                                                      //上传文件名集合 
			List<String> ls=fu.upload();                                                           //执行上传功能   
			rei.setPicture1(ls.get(0));	                                                           //设置报销单的第1张照片            
			rei.setPicture2(ls.get(1));	                                                           //设置报销单的第2张照片     
			rei.setPicture3(ls.get(2));	                                                           //设置报销单的第3张照片     
			request.setAttribute("uploadpstatus", "上传照片成功！");
		}else{
			request.setAttribute("uploadpstatus", "上传照片失败！"); 
		}
		
		dao.save(rei);                                                                            //保存到数据库
		request.setAttribute("applyopstatus", "报销已申请!");                                            
		return "outpatient";
	}
	
	//住院报销
	public String applyho() throws Exception
	{
		Reimbursement rei=new Reimbursement();
		HttpSession session=request.getSession(true);                                             //true,若存在会话则返回该会话，否则新建一个会话。
		User u=(User)session.getAttribute("user");		                                          //登录中的用户
		long  id= Math.round(Math.random()*90000+1)+UUID.randomUUID().toString().hashCode();      //生成9-10位的数字订单号
		String ids=String.valueOf(Math.abs(id));                                                  //将数字订单号转成String类型
		Date date=new Date();                                                                     //报单生成时间    
		Timestamp time = new Timestamp(date.getTime());                                           //通过Timestamp对象转换后Date也可以显示时间
		rei.setAppuser_identitys(u.getIdentitys());                                               //赋值身份
		rei.setAppuser_name(u.getName());                                                         //赋值名
		rei.setDepartment(u.getDepartment());                                                     //部门
		rei.setTracking_number(ids);                                                              //单号
		rei.setAppuser_id(u.getId());                                                             //申请人的id
		rei.setReason(rentity.getReason());                                                       //事由
		rei.setMoney(rentity.getMoney());                                                         //金额      
		rei.setDeliver(rentity.getDeliver());                                                     //送单人
		rei.setOperator(rentity.getOperator());                                                   //经办人
		rei.setContacts(rentity.getContacts());                                                   //联系人
		rei.setTelephone(rentity.getTelephone());                                                 //联系人电话
		rei.setMedical_number(rentity.getMedical_number());                                       //医保卡
		rei.setBank_number(rentity.getBank_number());                                             //银行卡
		rei.setHospital_level(rentity.getHospital_level());                                       //医院级别
		rei.setRemoney(0);                                                                        //double不可以空
		rei.setState("待审批中");                                                                  //报销单的状态
		rei.setDtime(time);                                                                       //报销单生成时间
		rei.setType("住院");                                                                      //报销单类型
		
		//上传照片
		if(file!=null){
			fu.setFile(file);                                                                     //上传文件集合
			fu.setFileFileName(fileFileName);                                                     //上传文件名集合
			List<String> ls=fu.upload();                                                          //执行上传功能  
			rei.setPicture1(ls.get(0));	                                                          //设置报销单的第1张照片 
			rei.setPicture2(ls.get(1));	                                                          //设置报销单的第2张照片 
			rei.setPicture3(ls.get(2));	                                                          //设置报销单的第3张照片 
			request.setAttribute("uploadpstatus", "上传照片成功！");
		}else{
			request.setAttribute("uploadpstatus", "上传照片失败！"); 
		}
		
		dao.save(rei);                                                                            //保存到数据库
		request.setAttribute("applyhostatus", "报销已申请!");                                          
		return "hospital";
	 }
	
	 //审核进度页面加载数据
	 public void progress() throws Exception{
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();
		User user=new User();
		HttpSession session=request.getSession(true);                                              //true,若存在会话则返回该会话，否则新建一个会话。
		user=(User)session.getAttribute("user");                                                   //获取当前登录用户  
		n=dao.checkrs(user.getId());                                                               //还在审核的报销单的总条数
	    rei=dao.checkr(user.getId(),0,5);                                                          //获取用户id
	    Map<String, Object> sess=new HashMap<String, Object>();
	    sess.put("total",n);
	    sess.put("rows",rei);
	    System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	 } 
	 
	//审核进度页面加载数据,下一页
	public void reloadprogress() throws Exception{
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();
		User user=new User();
		HttpSession session=request.getSession(true);                                              //true,若存在会话则返回该会话，否则新建一个会话。
		user=(User)session.getAttribute("user");                                                   //获取当前登录用户
		n=dao.checkrs(user.getId());                                                               //还在审核的报销单的总条数
		
		int pageNumber=Integer.valueOf(request.getParameter("pnumber"));                           //页号
	    int pageSize=Integer.valueOf(request.getParameter("psize"));                               //页的大小
	    int pn=(pageNumber-1)*pageSize;
	    
		rei=dao.checkr(user.getId(),pn,pageSize);                                                  //使用用户id来查询用户有哪些报销单还未完成
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "修改成功");                                                               //通知前台修改成功，执行成功操作
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	} 
	
	//审核进度页面加载数据,页的大小
	public void reloadprogressp() throws Exception{
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();
		User user=new User();
		HttpSession session=request.getSession(true);                                              //true,若存在会话则返回该会话，否则新建一个会话。
		user=(User)session.getAttribute("user");                                                   //获取当前登录用户
		
		n=dao.checkrs(user.getId());                                                               //还在审核的报销单的总条数
		
		int pageSize=Integer.valueOf(request.getParameter("psize"));                               //页的大小
		rei=dao.checkr(user.getId(),0,pageSize);                                                   //使用用户id来查询用户有哪些报销单还未完成
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "修改成功");                                                                //通知前台修改成功，执行成功操作
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	} 
		 
	 
	//历史记录页面加载数据
	public void done() throws Exception{
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();
		User user=new User();
		HttpSession session=request.getSession(true);                                              //true,若存在会话则返回该会话，否则新建一个会话。
		user=(User)session.getAttribute("user");                                                   //获取当前登录用户 
		n=dao.checkars(user.getId());                                                              //通过或不同过的报销单的总条数
		rei=dao.checkar(user.getId(),0,5);                                                         //获取用户id
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	}  
	
	//历史记录页面加载数据,下一页
	public void reloaddone() throws Exception{
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();
		User user=new User();
		HttpSession session=request.getSession(true);                                              //true,若存在会话则返回该会话，否则新建一个会话。
		user=(User)session.getAttribute("user");                                                   //获取当前登录用户  
		n=dao.checkars(user.getId());                                                              //通过或不同过的报销单的总条数
		int pageNumber=Integer.valueOf(request.getParameter("pnumber"));                           //页号
	    int pageSize=Integer.valueOf(request.getParameter("psize"));                               //页的大小
	    int pn=(pageNumber-1)*pageSize;
		rei=dao.checkar(user.getId(),pn,pageSize);                                                 //使用用户id来查询用户有哪些报销单已经完成
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "修改成功");                                                                //通知前台修改成功，执行成功操作
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	} 
	
	//历史记录页面加载数据,页的大小
	public void reloaddonep() throws Exception{
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();
		User user=new User();
		HttpSession session=request.getSession(true);                                              //true,若存在会话则返回该会话，否则新建一个会话。
		user=(User)session.getAttribute("user");                                                   //获取当前登录用户  
		n=dao.checkars(user.getId());                                                              //通过或不同过的报销单的总条数
		int pageSize=Integer.valueOf(request.getParameter("psize"));                               //页的大小
		rei=dao.checkar(user.getId(),0,pageSize);                                                  //使用用户id来查询用户有哪些报销单已经完成
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "修改成功");                                                                //通知前台修改成功，执行成功操作
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                   //将Json数据返回到前台
	} 
	 
	//待审批
	public String examine() throws Exception{
			return "examine";	
	} 
	 
	//待审批
	public void examines() throws Exception{
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();    
		n=dao.checkes();                                                                          //待审批的报销单的总条数	
		rei=dao.checke(0,5);                                                                      //第1页，5条数据                                                
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	} 
	
	//待审批,下一页
	public void reloadexamines() throws Exception{
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();    
		n=dao.checkes();                                                                          //待审批的报销单的总条数
		int pageNumber=Integer.valueOf(request.getParameter("pnumber"));                          //页号
	    int pageSize=Integer.valueOf(request.getParameter("psize"));                              //页的大小
	    int pn=(pageNumber-1)*pageSize;
		rei=dao.checke(pn,pageSize);                                                              //第n页，pageSize条数据                                                
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "修改成功");                                                              //通知前台修改成功，执行成功操作
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                 //将Json数据返回到前台
	} 
	
	//待审批,页的大小
	public void reloadexaminesp() throws Exception{
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();    
		n=dao.checkes();                                                                          //待审批的报销单的总条数
		int pageSize=Integer.valueOf(request.getParameter("psize"));                              //页的大小
		rei=dao.checke(0,pageSize);                                                               //第1页，pageSize条数据                                                
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "修改成功");                                                              //通知前台修改成功，执行成功操作
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                 //将Json数据返回到前台
	} 
	
	//审批完，刷新待审批
	public void reloadexamine() throws Exception{
		int n=0;
		int id=Integer.valueOf(request.getParameter("id"));                                       //id
        String approver_sign=request.getParameter("approver_sign");                               //审批人签名
        String approver_op=request.getParameter("approver_op");                                   //审批操作
        String result=request.getParameter("result");                                             //审批结果
        
        Reimbursement r=new Reimbursement();              
        r=dao.get(id);                                                                            //修改报销单
        r.setApprover_sign(approver_sign);                                                        //审批人签名
        r.setApprover_op(approver_op);                                                            //审批结果
        r.setResult(result);                                                                      //审批人给的意见
        if(approver_op.equals("通过")){
        	r.setState("待审核中");                                                               //报销单的进度
        }else if(approver_op.equals("不通过")){
        	r.setState("报销不通过");                                                             //报销单的进度
        }
        
        dao.update(r);                                                                             //更新
        
		List<Reimbursement> rei=new ArrayList<Reimbursement>(); 
		int pageNumber=Integer.valueOf(request.getParameter("pnumber"));                           //页号
	    int pageSize=Integer.valueOf(request.getParameter("psize"));                               //页的大小
	    int pn=(pageNumber-1)*pageSize;
	    n=dao.checkes();                                                                           //待审批的报销单的总条数 
	    rei=dao.checke(pn,pageSize);                                                               //第pageNumber页，pageSize条数据   
	    Map<String, Object> sess=new HashMap<String, Object>();
	    sess.put("total",n);
	    sess.put("rows",rei);
		sess.put("msg", "操作成功");
	    System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
    } 
	
	//已审批
	public String alexamine() throws Exception{
			return "alexamine";	
	} 
		 
	//已审批
	public void alexamines() throws Exception{
		HttpSession session = request.getSession(true);                                           //true,若存在会话则返回该会话，否则新建一个会话。
		User user=new User();
		user=(User)session.getAttribute("user");                                                  //获取当前登陆的用户
		
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();    
		n=dao.checkaes(user.getName());                                                           //已审批的报销单的总条数	
		rei=dao.checkae(0,5,user.getName());                                                      //第1页，pageSize条数据                            
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	} 
	
	//已审批，下一页
	public void reloadalexamines() throws Exception{
		HttpSession session = request.getSession(true);                                           //true,若存在会话则返回该会话，否则新建一个会话。
		User user=new User();
		user=(User)session.getAttribute("user");                                                  //获取当前登陆的用户
		
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();    
		n=dao.checkaes(user.getName());                                                           //已审批的报销单的总条数
		int pageNumber=Integer.valueOf(request.getParameter("pnumber"));                          //页号
	    int pageSize=Integer.valueOf(request.getParameter("psize"));                              //页的大小
	    int pn=(pageNumber-1)*pageSize;
		rei=dao.checkae(pn,pageSize,user.getName());                                              //第pageNumber页，pageSize条数据                       
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "修改成功");
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	} 
	
	//已审批，页的大小
	public void reloadalexaminesp() throws Exception{
		HttpSession session = request.getSession(true);                                           //true,若存在会话则返回该会话，否则新建一个会话。
		User user=new User();
		user=(User)session.getAttribute("user");                                                  //获取当前登陆的用户
		
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();    
		n=dao.checkaes(user.getName());                                                                         //已审批的报销单的总条数
		int pageSize=Integer.valueOf(request.getParameter("psize"));                              //页的大小
		rei=dao.checkae(0,pageSize,user.getName());                                                              //第1页，pageSize条数据                       
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "修改成功");
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	} 
	
	//待审核
	public String pend() throws Exception{
		return "pend";	
    } 
	
	
	//待审核
	public void pends() throws Exception{
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>(); 
		n=dao.checkps();                                                                           //待审核的报销单的总条数
	    rei=dao.checkp(0,5);                                                                       //第1页，5条数据                                                 
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	} 
	
	//待审核，下一页
	public void reloadpends() throws Exception{
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>(); 
		n=dao.checkps();                                                                          //待审核的报销单的总条数
		int pageNumber=Integer.valueOf(request.getParameter("pnumber"));                          //页号
	    int pageSize=Integer.valueOf(request.getParameter("psize"));                              //页的大小
	    int pn=(pageNumber-1)*pageSize;
		rei=dao.checkp(pn,pageSize);                                                              //第pageNumber页，pageSize条数据                                                 
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "修改成功");
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	}
	
	//待审核，页的大小
	public void reloadpendsp() throws Exception{
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>(); 
		n=dao.checkps();                                                                          //待审核的报销单的总条数
		int pageSize=Integer.valueOf(request.getParameter("psize"));                              //页的大小
		rei=dao.checkp(0,pageSize);                                                               //第1页，pageSize条数据                                                 
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "修改成功");
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	}
	
	//刷新待审核
	public void reloadpend() throws Exception{
		int n=0;
		int id=Integer.valueOf(request.getParameter("id"));                                        //id
        String financial_sign=request.getParameter("financial_sign");                              //财务部签名
        String financial_op=request.getParameter("financial_op");                                  //财务部审核结果
        double remoney=Double.valueOf(request.getParameter("remoney"));
        String result=request.getParameter("result");                                              //财务部审核结果        
        
        Reimbursement r=new Reimbursement();              
        r=dao.get(id);                                                                             //修改报销单
        r.setFinancial_sign(financial_sign);                                                       //财务部签名
        r.setFinancial_op(financial_op);                                                           //财务部审核结果
        r.setRemoney(remoney);                                                                     //报销金额
        r.setResult(result);                                                                       //审核的原因
        if(financial_op.equals("通过")){
        	r.setState("报销通过");                                                                //报销单的进度
        }else if(financial_op.equals("不通过")){
        	r.setState("报销不通过");                                                              //报销单的进度
        }

        dao.update(r);                                                                             //更新报销单数据
        
		List<Reimbursement> rei=new ArrayList<Reimbursement>();    
		n=dao.checkps();                                                                           //待审核的报销单的总条数
		int pageNumber=Integer.valueOf(request.getParameter("pnumber"));                           //页号
	    int pageSize=Integer.valueOf(request.getParameter("psize"));                               //页的大小
	    int pn=(pageNumber-1)*pageSize;                                                            
		rei=dao.checkp(pn,pageSize);                                                               //第pageNumber页 ，pageSize条数据                                                  
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "操作成功");
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	} 
	
	//已审核
	public String alpend() throws Exception{
		return "alpend";	
	} 
		
		
	//已审核
	public void alpends() throws Exception{
		HttpSession session = request.getSession(true);                                           //true,若存在会话则返回该会话，否则新建一个会话。
		User user=new User();
		user=(User)session.getAttribute("user");                                                  //获取当前登陆的用户
		
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();   
		n=dao.checkaps(user.getName());                                                           //已审核完的报销单的条数                       
		rei=dao.checkap(0,5,user.getName());                                                      //第1页 ，5条数据                 
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	} 
	
	//已审核页面,下一页
	public void reloadalpends() throws Exception{
		HttpSession session = request.getSession(true);                                           //true,若存在会话则返回该会话，否则新建一个会话。
		User user=new User();
		user=(User)session.getAttribute("user");                                                  //获取当前登陆的用户
		
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();   
		n=dao.checkaps(user.getName());                                                                         //已审核完的报销单的条数    
		int pageNumber=Integer.valueOf(request.getParameter("pnumber"));                          //页号
	    int pageSize=Integer.valueOf(request.getParameter("psize"));                              //页的大小
	    int pn=(pageNumber-1)*pageSize; 
		rei=dao.checkap(pn,pageSize,user.getName());                                                             //第pageNumber页 ，pageSize条数据                 
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "修改成功");
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                  //将Json数据返回到前台
	} 
	
	//已审核页面,页的 大小
	public void reloadalpendsp() throws Exception{
		HttpSession session = request.getSession(true);                                           //true,若存在会话则返回该会话，否则新建一个会话。
		User user=new User();
		user=(User)session.getAttribute("user");                                                  //获取当前登陆的用户
		
		int n=0;
		List<Reimbursement> rei=new ArrayList<Reimbursement>();   
		n=dao.checkaps(user.getName());                                                            //已审核完的报销单的条数    
		int pageSize=Integer.valueOf(request.getParameter("psize"));                              //页的大小
		rei=dao.checkap(0,pageSize,user.getName());                                               //第1页 ，pageSize条数据                 
		Map<String, Object> sess=new HashMap<String, Object>();
		sess.put("total",n);
		sess.put("rows",rei);
		sess.put("msg", "修改成功");
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                                 //将Json数据返回到前台
	} 
	
////////////////////////////////////////////////////////////////////实现get/set
	
	public List<Reimbursement> getList() {
		return list;
	}

	public void setList(List<Reimbursement> list) {
		this.list = list;
	}

	public Reimbursement getRentity() {
		return rentity;
	}

	public void setRentity(Reimbursement rentity) {
		this.rentity = rentity;
	}

	public User getUentity() {
		return uentity;
	}

	public void setUentity(User uentity) {
		this.uentity = uentity;
	}
	
	public UserApply getUaentity() {
		return uaentity;
	}

	public void setUaentity(UserApply uaentity) {
		this.uaentity = uaentity;
	}

	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}
}