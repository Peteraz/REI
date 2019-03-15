package mis.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mail.sendMail;
import mis.dao.UserDAO;
import mis.entity.User;
import mis.dao.UserApplyDAO;
import mis.entity.UserApply;
import mis.dao.ResetpwdDAO;
import mis.entity.Resetpwd;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;

import common.BaseAction;
import common.Md5Utils;
//业务处理Action

//action是多实例的，对每一个请求都会new一个action,所以不必担心，参数会冲突
@SuppressWarnings("serial")
@Results( { @Result(name = "login", location = "/login.jsp"),                         //登录页面
	        @Result(name = "register", location = "/register.jsp"),                   //注册页面
	        @Result(name = "forget", location = "/forget.jsp"),                       //忘记密码页面
	        @Result(name = "main", location = "/WEB-INF/main.jsp"),                   //主页页面			
			@Result(name = "userinfo", location = "/WEB-INF/userinfo.jsp"),           //用户信息页面
            @Result(name = "changepwd", location = "/WEB-INF/changepwd.jsp"),         //修改密码页面
            @Result(name = "picture", location = "/WEB-INF/picture.jsp"),             //照片页面
            @Result(name = "cpicture", location = "/WEB-INF/cpicture.jsp"),           //上传证件照片页面
            @Result(name = "ipicture", location = "/WEB-INF/ipicture.jsp"),           //上传身份照片页面
            @Result(name = "reimbursement", location = "/WEB-INF/reimbursement.jsp"), //报销页面
            @Result(name = "outpatient", location = "/WEB-INF/outpatient.jsp"),       //门诊报销页面
            @Result(name = "hospital", location = "/WEB-INF/hospital.jsp"),           //住院报销页面
            @Result(name = "auditing", location = "/WEB-INF/auditing.jsp"),           //审核进度页面
            @Result(name = "history", location = "/WEB-INF/history.jsp"),             //历史记录页面            
            @Result(name = "manageruser", location = "/WEB-INF/manageruser.jsp"),     //已审核申请页面
            @Result(name = "hello", location = "/hello.jsp"),     					  //测试
            @Result(name = "home", location = "user!show.action",type="redirect")     //定向到
		  }) 
public class UserAction extends BaseAction {
	// ///////////////////////////////////////////////////////////////引入的DAO
    //spring自动注入
	@Autowired  
	private  HttpServletRequest request; 
	@Autowired
	private UserDAO dao;
	@Autowired
	private UserApplyDAO adao;
	@Autowired
	private ResetpwdDAO cdao;

	// ///////////////////////////////////////////////////////////////属性参数
	private List<User> list;

	// 拿实体类当组合参数，实际上是它的属性帮我们传参了，常用
	private User entity;            //用户实体
	private UserApply aentity;      //审核用户实体	
	private Resetpwd centity;

	//主界面
	public String show()throws Exception
	{
		return "main";
		
	}
	
	public String hello() throws Exception{
		return "hello";
	}
    public String Login()throws Exception{
    	return "login";
    } 
	//注册
	public String register() throws Exception {
	        
		    //用户表                                  //md5加密
		    entity.setPassword(Md5Utils.md5(entity.getPassword()));      //MD5加密密码
		    entity.setSex(entity.getSex());                              //获取性别下拉选单的值
		    entity.setIdentitys(entity.getIdentitys());                  //获取身份下拉选单的值 
		    entity.setState("待审核");
		    
		    //用户审核表
		    aentity = new UserApply();
		    aentity.setAccount(entity.getAccount());                     //账号
		    aentity.setPassword(entity.getPassword());                   //密码
		    aentity.setEmail(entity.getEmail());                         //邮箱
		    aentity.setName(entity.getName());                           //名字
		    aentity.setSex(entity.getSex());                             //性别
		    aentity.setIdentitys(entity.getIdentitys());                 //身份
		    aentity.setCredential_number(entity.getCredential_number()); //证件号
		    aentity.setIdcard_number(entity.getIdcard_number());         //身份证号
		    aentity.setDepartment(entity.getDepartment());               //部门
		    aentity.setState("待审核");                                   //状态
		    if(entity.getAccount()==null||entity.getAccount().equals("")){
		    	request.setAttribute("msg", "请输入账号！");
		    	return "register";
		    }
		    else if(entity.getPassword()==null||entity.getPassword().equals("")){
		    	request.setAttribute("msg", "请输入密码！");
		    	return "register";
		    }
		    else if (entity.getEmail()==null||entity.getEmail().equals("")){
		    	request.setAttribute("msg", "请输入邮箱！");
		    	return "register";
		    }
		    else if (entity.getName()==null||entity.getName().equals("")){
		    	request.setAttribute("msg", "请输入名字！");
		    	return "register";
		    }
		    else if(entity.getSex().equals("没选")){
		    	request.setAttribute("msg", "请选择你的性别！");
		    	return "register";
		    	
			}
		    else if(entity.getIdentitys().equals("没选")){
		    	request.setAttribute("msg", "请选择你的身份！");
		    	return "register";
		    	
			}
		    else if (entity.getCredential_number()==null||entity.getCredential_number().equals("")){
		    	request.setAttribute("msg", "请输入证件号！");
		    	return "register";
		    }	
		    else if (entity.getIdcard_number()==null||entity.getIdcard_number().equals("")){
		    	request.setAttribute("msg", "请输入身份证！");
		    	return "register";
		    }
		    else if (entity.getDepartment()==null||entity.getDepartment().equals("")){
		    	request.setAttribute("msg", "请输入部门！");
		    	return "register";
		    }
		    else if(dao.checka(entity.getAccount())!=null){
		    	request.setAttribute("msg", "此账号已存在！");
		        return "register";
		    } 
		    else if(!UserDAO.checkEmail(entity.getEmail())){
		    	request.setAttribute("msg", "邮箱格式错误！");
		        return "register";
		    } 
		    else if(dao.checkmail(entity.getEmail())!=null){
		    	request.setAttribute("msg", "邮箱已存在！");
		        return "register";
		    } 
		    else if(dao.checkn(entity.getCredential_number())!=null){
		    	request.setAttribute("msg", "此证件已存在！");
		        return "register";
		    } 
			else{
		         dao.save(entity);            //添加到数据库
		         int uid=entity.getId();      //获取用户的id
		         aentity.setUid(uid);         //设置用户审核表的uid
		         adao.save(aentity);          //添加到数据库
		         request.setAttribute("msg", "注册成功！");
		         return "login";
			}
	        
		}
	
	//登陆
	public String login() throws Exception {
		HttpSession session = request.getSession(true);                          //true,若存在会话则返回该会话，否则新建一个会话。
		String checkCode = (String)session.getAttribute("checkcode");            //获取生成的验证码
		String checkcode= (String)request.getParameter("checkcode");             //获取用户输入的验证码
		
		if(checkcode.equals("")||checkcode==null){
			request.setAttribute("msg", "请输入验证码！");
	        return "login";
		}
		else if(!checkcode.equalsIgnoreCase(checkCode)){                          //忽略两个String的大小写进行比较
			request.setAttribute("msg", "验证码输入错误！");
	        return "login";
		}
        
	    //密码用了md5加密
	    User u=dao.login(entity.getAccount(),Md5Utils.md5(entity.getPassword()));  //查找用户
	    
	    if(u==null)
	    {
	    	request.setAttribute("msg", "用户名或密码错误");
	        return "login";
	        
	    }
	    else
	    {
	    	UserApply ua=new UserApply();
	    	ua=adao.checkuser(u.getId(),0,1);
	    	ActionContext ac = ActionContext.getContext();
			//使用Map模拟HttpSession
			Map<String,Object> sess = ac.getSession();
			sess.put("user", u);                                             //当前登录用户
			sess.put("usera", ua);                                           //当前登录用户在用户审核表对应的用户数据
	    	return "home";
	    }
	}
	
	//忘记密码页面
	public String forget() throws Exception{
		HttpSession session = request.getSession(true);                       //true,若存在会话则返回该会话，否则新建一个会话。
		String checkCode = (String)session.getAttribute("checkcode");         //获取生成的验证码
		String checkcode= (String)request.getParameter("checkcode");          //获取用户输入的验证码
		String email=request.getParameter("email");                           //获取用户输入的邮箱
		
		//判断验证是否正确
		if(checkcode.equals("")||checkcode==null){
			request.setAttribute("emailinfo", "请输入验证码！");
	        return "forget";
		}
		else if(!checkcode.equalsIgnoreCase(checkCode)){                      //忽略两个String的大小写，进行比较，这是判断不一样情况
			request.setAttribute("emailinfo", "验证码输入错误！");
	        return "forget";
		}
		else if(UserDAO.checkEmail(email)){                                   //判断是不是一个邮箱
			  if(dao.checkmail(email)!=null){                                 //存在
				 User user=new User();
				 user=dao.checkmail(email);                                   //获取对应的用户
				 String token=UserDAO.createToken();                          //token
				 
				 Date date=new Date();                                       //过期时间    
				 date=UserDAO.getNextDay(date);                              
				 Timestamp time = new Timestamp(date.getTime());             //通过Timestamp对象转换后Date也可以显示时间  
				 Resetpwd cpwd=new Resetpwd();
				 cpwd.setUid(user.getId());                                  //设置token表的uid
				 cpwd.setToken(token);                                       //设置token表的token
				 cpwd.setEndtime(time);                                      //设置token表的过期时间
				 cdao.save(cpwd);
				 
				 sendMail.sendEmailForChangePw(email, user.getName(),time.toString(), token);     //发送邮件
				 request.setAttribute("emailinfo","重置密码邮件已发送，请其邮箱检查!");
		      }else{
				 request.setAttribute("emailinfo","邮箱不存在!");
			  }
	    }else{
			request.setAttribute("emailinfo","邮箱格式错误!");
		}
		return "forget";
	}	
	
	//忘记密码页面
	public String resetpass() throws Exception{
			
		return "forget";
	}	
	
	//用户信息页面
	public String userinfo() throws Exception{
		return "userinfo";
	}		
	
	//跳转修改密码页面
	public String changepwd() throws Exception{
		return "changepwd";
	}
	
	//修改密码方法
	public String changepwds() throws Exception{
	    HttpSession session = request.getSession(true);                   //true,若存在会话则返回该会话，否则新建一个会话。
	    String checkCode = (String)session.getAttribute("checkcode");     //获取生成的验证码
		String checkcode= (String)request.getParameter("checkcode");      //获取用户输入的验证码
		
		if(checkcode.equals("")||checkcode==null){
			request.setAttribute("msg", "请输入验证码！");
	        return "changepwd";
		}
		else if(!checkcode.equalsIgnoreCase(checkCode)){                          //忽略两个String的大小写进行比较
			request.setAttribute("msg", "验证码输入错误！");
	        return "changepwd";
		}
		
	    User user = (User)session.getAttribute("user");                   //获取当前登录的用户
	    user.setPassword(Md5Utils.md5(entity.getPassword()));             //修改当前用户的密码
	    dao.update(user);                                                 //更新当前用户的数据
	    
	    if(!user.getIdentitys().equals("管理员")){                          //不是管理员的用户
	    	//修改与用户对应的在用户审核对应的用户的密码
	    	UserApply ua=new UserApply();
	    	String hql="from UserApply n where ";                          //hql查询语句
	    	List<Object> obj=new ArrayList<Object>(); 
	    	hql+="n.uid = ? ";                                             // 组合hql，使用uid查询
	    	obj.add(user.getId());                                         // 组合参数
	    	List<UserApply> uas=adao.getListByHQL(hql,obj);
	    	ua=uas.get(0);
	    	ua.setPassword(Md5Utils.md5(entity.getPassword()));             //修改与当前用户相对应的用户审核表的用户的密码  
	    	adao.update(ua);                                                //更新与当前用户相对应的用户审核表的用户的数据
	    }
		
	    request.setAttribute("changepwstatus", "密码修改成功！");
	    return "changepwd";
	}
	
	//退出登录
	public String logout() throws Exception 
	{
			HttpSession sess = request.getSession();
			sess.invalidate();
	    	return "login";
	}
	
	//照片
	public String picture() throws Exception
	{
		return "picture";
	}
	
	//证件照片
	public String cpicture() throws Exception
	{
		return "cpicture";
	}
	
	//身份证照片
	public String ipicture() throws Exception
	{
		return "ipicture";
	}
	
	//报销
	public String reimbursement() throws Exception{
		return "reimbursement";
	}
		
	//门诊报销
	public String outpatient() throws Exception
	{
		return "outpatient";
	}
	
	//住院报销
	public String hospital() throws Exception
	{
		return "hospital";
	}
	
	//审核进度
	public String auditing() throws Exception
	{
		return "auditing";
	}
	
	//历史记录
	public String history()throws Exception
	{
		return "history";
	}
	
	//待审批申请
	public String examine() throws Exception
	{
		return "examine";
	}
	
	//已审批申请
	public String alexamine() throws Exception
	{
		return "alexamine";
	}
		
	//待审核申请
	public String pend() throws Exception
	{
		return "pend";
	}
	
	//已审核申请
	public String alpend() throws Exception
	{
		return "alpend";
	}
	
	//用户管理
	public String manageruser() throws Exception
	{
		return "manageruser";
	}
	
	
	//用户管理加载用户
	public void managerusers() throws Exception
	{
		int n=0;
		List<User> user=new ArrayList<User>();
		n=dao.checkusers();                                                  //除了管理员外的用户条数
		user=dao.checkuser(0,5);                                             //第1页，5条数据
		Map<String, Object> sess = new HashMap<String, Object>();
		sess.put("total", n);
		sess.put("rows", user);
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));              //返回到前台	
	}
	
	//一下页，重新加载用户
	public void reload() throws Exception
	{
		int n=0;
		List<User> user=new ArrayList<User>();
		n=dao.checkusers();                                                     //除了管理员外的用户条数
		int pageNumber=Integer.valueOf(request.getParameter("pnumber"));        //页号
	    int pageSize=Integer.valueOf(request.getParameter("psize"));            //页的大小
	    int pn=(pageNumber-1)*pageSize;
		user=dao.checkuser(pn,pageSize);                                        //第pageNumber页，pageSize条数据
		Map<String, Object> sess = new HashMap<String, Object>();
		sess.put("total", n);
		sess.put("rows", user);
		sess.put("msg", "修改成功");                                            //通知前台修改成功，执行成功操作
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));              //返回到前台
	}
	
	//页size,重新加载用户
	public void reloadp() throws Exception
	{
		int n=0;
	    List<User> user=new ArrayList<User>();
	    n=dao.checkusers();                                                     //除了管理员外的用户条数                           
		int pageSize=Integer.valueOf(request.getParameter("psize"));            //页的大小
		user=dao.checkuser(0,pageSize);                                         //第1页，pageSize条数据
		Map<String, Object> sess = new HashMap<String, Object>();
		sess.put("total", n);
		sess.put("rows", user);
		sess.put("msg", "修改成功");                                            //通知前台修改成功，执行成功操作
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));              //返回到前台
	}
	
	//搜索某个用户
	public void searchuser() throws Exception
	{
		List<User> user=new ArrayList<User>();
		String searchuser=request.getParameter("searchuser");                   //要搜索的用户
		int pageSize=Integer.valueOf(request.getParameter("psize"));            //页的大小
		user=dao.checkoneuser(searchuser,0,pageSize);                           //第1页，pageSize条数据
		if(user==null)
		{
			Map<String, Object> sess = new HashMap<String, Object>();
			sess.put("total", 0);
			sess.put("rows", null);
			sess.put("msg", "0");  
			System.err.println(JSON.toJSONString(sess));
			getResponse().getWriter().write(JSON.toJSONString(sess));               //返回到前台	
		}else{
			Map<String, Object> sess = new HashMap<String, Object>();
			sess.put("total", user.size());
			sess.put("rows", user);
			sess.put("msg", "1");                                            //通知前台修改成功，执行成功操作
			System.err.println(JSON.toJSONString(sess));
			getResponse().getWriter().write(JSON.toJSONString(sess));               //返回到前台	
		}	
	}

	//编辑用户
	public void edituser() throws Exception{
		int n=0;
		n=dao.checkusers();
		int id=Integer.valueOf(request.getParameter("id"));                      //id
	    String account=request.getParameter("account");                          //账号
	    String password=request.getParameter("password");                        //密码
	    String email=request.getParameter("email");                              //邮箱
	    String name=request.getParameter("name");                                //姓名
	    String sex=request.getParameter("sex");                                  //性别
	    String state=request.getParameter("state");                              //状态
	    String identitys=request.getParameter("identitys");                      //身份
	    String department=request.getParameter("department");                    //部门
	    String credential_number=request.getParameter("credential_number");      //证件号
	    String idcard_number=request.getParameter("idcard_number");              //身份证
	        
	    int pageNumber=Integer.valueOf(request.getParameter("pnumber"));         //页号
	    int pageSize=Integer.valueOf(request.getParameter("psize"));             //页的大小
	        
	    User u=new User();
	    u=dao.get(id);                                                           //获取用户
	    u.setAccount(account);                                                   //账号
	    u.setPassword(password);                                                 //密码	
	    u.setEmail(email);                                                       //邮箱
	    u.setName(name);                                                         //姓名                                                          
	    u.setSex(sex);                                                           //性别
	    u.setState(state);                                                       //状态
	    u.setIdentitys(identitys);                                               //身份      
	    u.setDepartment(department);                                             //部门
	    u.setCredential_number(credential_number);                               //证件号
	    u.setIdcard_number(idcard_number);                                       //身份证号
	    dao.update(u);
	    
	    //修改与用户对应的在用户审核对应的数据
	    UserApply ua=new UserApply();
	    String hql="from UserApply n where ";
		List<Object> obj=new ArrayList<Object>();
	    hql+="n.uid = ? ";                                                       // 组合hql
		obj.add(id);                                                             // 组合参数
		List<UserApply> uas=adao.getListByHQL(hql,obj);
		ua=uas.get(0);                                                            //获取对应在用户审核表的数据
		ua.setAccount(account);                                                   //账号
		ua.setPassword(password);                                                 //密码	
		ua.setEmail(email);                                                       //邮箱
		ua.setName(name);                                                         //姓名                                                          
		ua.setSex(sex);                                                           //性别
		ua.setState(state);                                                       //状态
		ua.setIdentitys(identitys);                                               //身份      
		ua.setDepartment(department);                                             //部门
		ua.setCredential_number(credential_number);                               //证件号
		ua.setIdcard_number(idcard_number);                                       //身份证号
		adao.update(ua);                                                          //更新用户审核表的用户的数据        
	        
	    List<User> user=new ArrayList<User>();
		int pn=(pageNumber-1)*pageSize;
		user=dao.checkuser(pn,pageSize);                                         //重加载编辑过的数据，第pageNumber页，pageSize条数据
		Map<String, Object> sess = new HashMap<String, Object>();
		sess.put("total", n);
		sess.put("rows", user);
		sess.put("msg", "修改成功");
		sess.put("msgbox", "修改成功");
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                //返回到前台
	}
	
	//添加用户
	public void adduser() throws Exception{
        String account=request.getParameter("account");                          //账号
        String password=request.getParameter("password");                        //密码
        String email=request.getParameter("email");                              //邮箱
        String name=request.getParameter("name");                                //姓名
        String sex=request.getParameter("sex");                                  //性别
        String identitys=request.getParameter("identitys");                      //身份
        String department=request.getParameter("department");                    //部门
        String credential_number=request.getParameter("credential_number");      //证件
        String idcard_number=request.getParameter("idcard_number");              //身份证
        
        int pageSize=Integer.valueOf(request.getParameter("psize"));             //页的大小
        
        //判断是否某些数据存在
        if(dao.checka(account)!=null){
        	Map<String, Object> sess = new HashMap<String, Object>();
            sess.put("msg", "添加失败");
            sess.put("msgbox", "此账号已存在！");
            System.err.println(JSON.toJSONString(sess));
            getResponse().getWriter().write(JSON.toJSONString(sess));             //返回到前台
	    } 
	    else if(dao.checkmail(email)!=null){
	    	Map<String, Object> sess = new HashMap<String, Object>();
	        sess.put("msg", "添加失败");
	        sess.put("msgbox", "邮箱已存在！");    	
	        System.err.println(JSON.toJSONString(sess));
	        getResponse().getWriter().write(JSON.toJSONString(sess));              //返回到前台
	    } 
	    else if(dao.checkn(credential_number)!=null){
	    	Map<String, Object> sess = new HashMap<String, Object>();
	        sess.put("msg", "添加失败");
	        sess.put("msgbox", "此证件已存在！");	        
	        System.err.println(JSON.toJSONString(sess));
	        getResponse().getWriter().write(JSON.toJSONString(sess));              //返回到前台	    	
	    } 
	    else{
        
            //新用户
            User u=new User();
            u.setAccount(account);                                                   //账号
            u.setPassword(Md5Utils.md5(password));                                   //密码
            u.setEmail(email);                                                       //邮箱
            u.setName(name);                                                         //姓名
            u.setSex(sex);                                                           //性别  
            u.setIdentitys(identitys);                                               //身份证  
            u.setDepartment(department);                                             //部门  
            u.setCredential_number(credential_number);                               //证件号
            u.setIdcard_number(idcard_number);                                       //身份证
            u.setState("通过");                                                      //状态
            dao.save(u);
        
            int id=u.getId();                                                        //获取新用id
            
            if(!u.getIdentitys().equals("管理员")){                                   //不是管理员的用户
                //审核用户
                UserApply ua=new UserApply();
                ua.setUid(id);                                                        //id
                ua.setAccount(account);                                               //账号
                ua.setPassword(Md5Utils.md5(password));                               //密码
                ua.setEmail(email);                                                   //邮箱
                ua.setName(name);                                                     //姓名
                ua.setSex(sex);                                                       //性别  
                ua.setIdentitys(identitys);                                           //身份证  
                ua.setDepartment(department);                                         //部门  
                ua.setCredential_number(credential_number);                           //证件号
                ua.setIdcard_number(idcard_number);                                   //身份证
                ua.setResult("通过");                                                  //结果      
                ua.setState("已处理");                                                 //状态
                adao.save(ua);
            }
        
            int n=0;
            n=dao.checkusers();
            List<User> user=new ArrayList<User>();
            user=dao.checkuser(0,pageSize);                                            //第1页，pageSize条数据
            Map<String, Object> sess = new HashMap<String, Object>();
            sess.put("total", n);
            sess.put("rows", user);
            sess.put("msg", "添加成功");
            sess.put("msgbox", "添加成功");
            System.err.println(JSON.toJSONString(sess));
            getResponse().getWriter().write(JSON.toJSONString(sess));                   //返回到前台
	    }
	 }
	
	//删除用户
	public void destroyuser() throws Exception{	 
		UserApply ua=new UserApply(); 
	    String id=request.getParameter("id");                                           //id   
	    int pageSize=Integer.valueOf(request.getParameter("psize"));             		//页的大小
	    
	    dao.delete(Integer.valueOf(id));                                         		//删除用户	   
	    
	    //删除与用户对应的在用户审核对应的数据
	    String hql="from UserApply n where ";                                           //hql查询语句
		List<Object> obj=new ArrayList<Object>();
	    hql+="n.uid = ? ";                                                       		// 组合hql
		obj.add(Integer.valueOf(id));                                            		// 组合参数
		List<UserApply> uas=adao.getListByHQL(hql,obj);                                 //通过uid查询用户审核表的用户
		ua=uas.get(0);
		adao.delete(ua.getId());                                                 		//删除用户审核表的用户                   
	        
	    int n=0;
	    List<User> user=new ArrayList<User>();
		n=dao.checkusers();                                                      		//查询所有用户，除了管理员以外的用户的条数
		user=dao.checkuser(0,pageSize);                                          		//第1页，pageSize条数据
		Map<String, Object> sess = new HashMap<String, Object>();
		sess.put("total", n);
		sess.put("rows", user);
		sess.put("msg", "删除成功");
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                        //返回到前台
	 }		
	
////////////////////////////////////////////////////////////////////实现get/set
		public List<User> getList() {
			return list;
		}

		public void setList(List<User> list) {
			this.list = list;
		}

		public User getEntity() {
			return entity;
		}

		public void setEntity(User entity) {
			this.entity = entity;
		}
		
		public UserApply getAentity() {
			return aentity;
		}

		public void setAentity(UserApply aentity) {
			this.aentity = aentity;
		}
		
		public Resetpwd getCentity() {
			return centity;
		}

		public void setCentity(Resetpwd centity) {
			this.centity = centity;
		}
}