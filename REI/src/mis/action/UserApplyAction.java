package mis.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mis.dao.UserApplyDAO;
import mis.dao.UserDAO;
import mis.entity.User;
import mis.entity.UserApply;

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
@Results( {  @Result(name = "userapply", location = "/WEB-INF/userapply.jsp"),          //用户申请页面
             @Result(name = "changeinfo", location = "/WEB-INF/changeinfo.jsp")         //用户信息修改页面
		  }) 
public class UserApplyAction extends BaseAction{
	@Autowired  //@Autowired是根据类型进行自动装配，进行注入
	private  HttpServletRequest request; 
	@Autowired
	private UserApplyDAO adao;
	@Autowired
	private UserDAO dao;
	
	// ///////////////////////////////////////////////////////////////属性参数
	private List<UserApply> list;

	// 拿实体类当组合参数，实际上是它的属性帮我们传参了，常用
	private UserApply aentity;      //审核用户实体	
	private User entity;          //用户实体	
	
	//用户信息修改页面
	public String changeinfo() throws Exception{
		return "changeinfo";
	}
	
	//用户信息修改方法
	public String changeinfos() throws Exception{
		HttpSession session=request.getSession(true);                      //true,若存在会话则返回该会话，否则新建一个会话。
		User user=(User)session.getAttribute("user");
		if(aentity.getIdentitys().equals("管理员")){                       //管理员不用审核，所以没有用户审核表的数据
	        user.setName(aentity.getName());                               //姓名
	        user.setSex(aentity.getSex());                                 //性别
	        user.setEmail(aentity.getEmail());                             //邮箱
	        user.setIdentitys(aentity.getIdentitys());                     //身份
	        user.setCredential_number(aentity.getCredential_number());     //证件号
	        user.setIdcard_number(aentity.getIdcard_number());             //身份证
	        user.setDepartment(aentity.getDepartment());                   //部门
	        dao.update(user);
			request.setAttribute("changeuserstatus", "修改成功！");
		}else{
		   UserApply u=new UserApply();
		   u=adao.checkuser(user.getId(), 0, 1);                           //查找对应的用户
		   u.setName(aentity.getName());                                   //姓名
		   u.setSex(aentity.getSex());                                     //性别
		   u.setEmail(aentity.getEmail());                                 //邮箱
		   u.setIdentitys(aentity.getIdentitys());                         //身份
		   u.setCredential_number(aentity.getCredential_number());         //证件号
		   u.setIdcard_number(aentity.getIdcard_number());                 //身份证
		   u.setDepartment(aentity.getDepartment());                       //部门
		   u.setResult("");
		   u.setState("待审核");    
		   adao.update(u);
		   
		   user.setState("待审核");                                         //管理员以外的用户都要改变用户状态
		   dao.update(user);		   
		   request.setAttribute("changeuserstatus", "修改已提交正在等待审核！");
		}
		return "changeinfo";
    }
	
	//重新审核
	public void repend() throws Exception
	{
		HttpSession session=request.getSession(true);                      //true,若存在会话则返回该会话，否则新建一个会话。
		User user=(User)session.getAttribute("user");                      //获取当前用户
	    
		user.setState("待审核");                                           //将当前用户状态改为审核状态
	    dao.update(user);                                                  //更新用户数据
	    
	    UserApply ua=new UserApply();
	    ua=adao.checkuser(user.getId(), 0, 5);                             //查找在用户审核表对应的用户数据
	    ua.setResult("");
	    ua.setState("待审核");                                             //将当前在用户审核表对应的用户数据改为审核状态
	    adao.update(ua);                                                   //更新用户审核数据	
		Map<String, Object> sess = new HashMap<String, Object>();
		sess.put("changeuserstatus", "账号已重新进入审核状态！");
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));           //返回到前台
		getResponse().getWriter().flush();
		getResponse().getWriter().close();
	}
	
	//新用户的申请页面
	public String userapply() throws Exception
	{
		return "userapply";
	}
	
	//新的用户申请页面，加载用户
	public void userapplys() throws Exception
	{
		int n=0;
		List<UserApply> user=new ArrayList<UserApply>();
		n=adao.checknewusers();                                                 //新用户总共条数
		user=adao.checknewuser(0,5);                                            //第1页，5条数据
		Map<String, Object> sess = new HashMap<String, Object>();
		sess.put("total", n);
		sess.put("rows", user);
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));               //返回到前台
	}
			
	//通过审核，刷新用户列表
	public void yuserapply() throws Exception
	{
		//用户审核
		int id=Integer.valueOf(request.getParameter("id"));                     //id
		UserApply u=new UserApply();
		User user=new User();
		u=adao.get(id);                                                          
		u.setResult("通过");                                                     //用户审核表结果	
		u.setState("已处理");                                                    //用户审核表状态   
		
		//用户
		user=dao.get(u.getUid());
		user.setAccount(u.getAccount());                                         //账号
		user.setPassword(u.getPassword());                                       //密码
		user.setEmail(u.getEmail());                                             //邮箱
		user.setName(u.getName());                                               //姓名
		user.setSex(u.getSex());                                                 //性别
		user.setIdentitys(u.getIdentitys());                                     //身份
		user.setCredential_number(u.getCredential_number());                     //证件号
		user.setIdcard_number(u.getIdcard_number());                             //身份证号
		user.setCpicture(u.getCpicture());                                       //证件照片	
		user.setIpicture(u.getIpicture());                                       //身份证照片	
		user.setDepartment(u.getDepartment());                                   //部门
		user.setState("通过");                                                   //用户状态
		adao.update(u);
		dao.update(user);
		        
		int n=0;
		List<UserApply> users=new ArrayList<UserApply>();
		n=dao.checknewusers();                                                   //新用户待审核的总共条数
		int pageNumber=Integer.valueOf(request.getParameter("pnumber"));         //页号
		int pageSize=Integer.valueOf(request.getParameter("psize"));             //页的大小
		int pn=(pageNumber-1)*pageSize;
		users=adao.checknewuser(pn,pageSize);                                    //第pageNumber页，pageSize条数据
		Map<String, Object> sess = new HashMap<String, Object>();
		sess.put("total", n);
		sess.put("rows", users);
		sess.put("msg", "操作成功");                                              //通知前台修改成功，执行成功操作
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));                 //返回到前台
	}
	
	//不通过审核，刷新用户列表
	public void nuserapply() throws Exception
	{
		//用户审核
		int id=Integer.valueOf(request.getParameter("id"));                        //id
		UserApply u=new UserApply();
		User user=new User();
		u=adao.get(id);
		u.setResult("不通过");
		u.setState("已处理");
			
		//用户
		user=dao.get(u.getUid());
		user.setState("待审核");                                                   //修改用户状态
		adao.update(u);                                                            //更新用户审核表的数据
		dao.update(user);                                                          //更新用户的数据
			        
		int n=0;
		List<UserApply> users=new ArrayList<UserApply>();
		n=dao.checknewusers();                                                     //新用户总共条数
		int pageNumber=Integer.valueOf(request.getParameter("pnumber"));           //页号
		int pageSize=Integer.valueOf(request.getParameter("psize"));               //页的大小
		int pn=(pageNumber-1)*pageSize;
		users=adao.checknewuser(pn,pageSize);                                      //第pageNumber页，pageSize条数据
		Map<String, Object> sess = new HashMap<String, Object>();
		sess.put("total", n);
		sess.put("rows", users);
		sess.put("msg", "操作成功");                                            	    //通知前台修改成功，执行成功操作
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));              	    //返回到前台
	}
			
	//新用户申请分页宫功能的下一页
	public void reloaduserapplys() throws Exception
	{        
		int n=0;
		List<UserApply> users=new ArrayList<UserApply>();
		n=dao.checknewusers();                                                  //新用户总共条数
		int pageNumber=Integer.valueOf(request.getParameter("pnumber"));        //页号
		int pageSize=Integer.valueOf(request.getParameter("psize"));            //页的大小
		int pn=(pageNumber-1)*pageSize;
		users=adao.checknewuser(pn,pageSize);                                   //第pageNumber页，pageSize条数据
		Map<String, Object> sess = new HashMap<String, Object>();
		sess.put("total", n);
		sess.put("rows", users);
		sess.put("msg", "修改成功");                                            //通知前台修改成功，执行成功操作
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));              //返回到前台
	}
			
	//新用户申请分页功能的页大小
	public void reloaduserapplyp() throws Exception
	{			        
		int n=0;
		List<UserApply> users=new ArrayList<UserApply>();
		n=dao.checknewusers();                                                  //新用户总共条数
		int pageSize=Integer.valueOf(request.getParameter("psize"));            //页的大小
		users=adao.checknewuser(0,pageSize);                                    //第1页，pageSize条数据
		Map<String, Object> sess = new HashMap<String, Object>();
		sess.put("total", n);
		sess.put("rows", users);
		sess.put("msg", "修改成功");                                            //通知前台修改成功，执行成功操作
		System.err.println(JSON.toJSONString(sess));
		getResponse().getWriter().write(JSON.toJSONString(sess));               //返回到前台
	}
	
////////////////////////////////////////////////////////////////////实现get/set
	
	public List<UserApply> getList() {
		return list;
	}

	public void setList(List<UserApply> list) {
		this.list = list;
	}

	public UserApply getAentity() {
		return aentity;
	}

	public void setAentity(UserApply aentity) {
		this.aentity = aentity;
	}

	public User getEntity() {
		return entity;
	}

	public void setEntity(User entity) {
		this.entity = entity;
	}
}