package mis.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mis.dao.ResetpwdDAO;
import mis.dao.UserApplyDAO;
import mis.dao.UserDAO;
import mis.entity.Resetpwd;
import mis.entity.Reimbursement;
import mis.entity.User;
import mis.entity.UserApply;

import org.apache.commons.lang3.StringUtils;
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
@Results( { @Result(name = "forget", location = "/forget.jsp"),                       //忘记密码页面
            @Result(name = "resetpasswd", location = "/WEB-INF/resetpasswd.jsp"),     //重置密码页面
	        @Result(name = "main", location = "/WEB-INF/main.jsp"),                   //主页页面			
		  }) 
public class ResetpwdAction extends BaseAction{
	@Autowired  
	private  HttpServletRequest request;
	@Autowired
	private ResetpwdDAO cdao;
	@Autowired
	private UserDAO udao;
	@Autowired
	private UserApplyDAO uadao;
	
	// ///////////////////////////////////////////////////////////////属性参数
	private List<Resetpwd> list;

	// 拿实体类当组合参数，实际上是它的属性帮我们传参了，常用
	private Resetpwd rentity;               //重置密码实体
	private User uentity;                   //用户实体	
	private UserApply uaentity;                   //用户实体

	//重置密码页面
	public String resetpwd() throws Exception{
		String token=request.getParameter("token");
		boolean status = false;
		if(StringUtils.isNotBlank(token)){                                                                //token是不是空 
			if(cdao.checktoken(token)!=null){                                                             //token存不存在，这是存在的话才执行下面的语句
				Resetpwd cpwd=new Resetpwd();
				cpwd=cdao.checktoken(token);                                                              //获取重密码的数据
				Date date=new Date();                                                                     //当前时间    
				Timestamp nowtime = new Timestamp(date.getTime());                                        //通过Timestamp对象转换后Date也可以显示时间
				Timestamp endtime=Timestamp.valueOf(cpwd.getEndtime().toString());                        //将修改期限转换成Timestamp类型      
				
				if(nowtime.getTime()<endtime.getTime())	{                                                 //将修改期限与当前时间对比，是否过期了
					User user=new User();
				    user=udao.get(cpwd.getUid());                                                         //没有过期就获取要修改密码的用户  
				    status= true;                                                                         //证明成功    
				    ActionContext ac = ActionContext.getContext();                                             
				    Map<String,Object> sess = ac.getSession();                                            //使用Map模拟HttpSession
				    sess.put("uid", user.getId());                                                        //保存要修改密码用户的id
				    sess.put("token", token);                                                             //token
				    request.setAttribute("status",status);
				}else{
					request.setAttribute("status",status);
					request.setAttribute("resetinfo","超过修改期限！");                                     //当前时间已经超过了修改时间
				}
				     
			}
			else{
				request.setAttribute("status",status);
				request.setAttribute("resetinfo","用户不存在！");                                           //当找token，证明没有这个用户
			}
		}
		else{
			request.setAttribute("status",status);                                                         
			request.setAttribute("resetinfo","失败！");                                                     //当token为空的时候，证明失败了
		}
			return "resetpasswd";
	}
	
	//重置密码方法
	public String resetpasswd() throws Exception{
		HttpSession session = request.getSession(true);                                                      //true,若存在会话则返回该会话，否则新建一个会话。
		String checkCode = (String)session.getAttribute("checkcode");                                        //获取生成的验证码
		String checkcode= (String)request.getParameter("checkcode");                                         //获取用户输入的验证码
		int uid=Integer.valueOf(session.getAttribute("uid").toString());                                     //用户id		
		String token=(String)session.getAttribute("token");
		boolean status = false;
		if(checkcode.equals("")||checkcode==null){
			status = true;
			request.setAttribute("status",status);
			request.setAttribute("resetinfos", "请输入验证码！");
	        return "resetpasswd";
		}
		else if(!checkcode.equalsIgnoreCase(checkCode)){                                                      //忽略两个String的大小写，进行比较，这是判断不一样情况
			status = true;
			request.setAttribute("status",status);
			request.setAttribute("resetinfos", "验证码输入错误！");
	        return "resetpasswd";
		}
		else if(StringUtils.isNotBlank(token)){                                                                //token是否为空
			User user=new User();
			user=udao.get(uid);
			user.setPassword(Md5Utils.md5(uentity.getPassword()));
			udao.update(user);
			
			if(!user.getIdentitys().equals("管理员")){                                                         //不是管理员的用户
		    	//修改与用户对应的在用户审核对应的用户的密码
		    	UserApply ua=new UserApply();
		    	String hql="from UserApply n where ";                          								   //hql查询语句
		    	List<Object> obj=new ArrayList<Object>(); 
		    	hql+="n.uid = ? ";                                             								   // 组合hql，使用uid查询
		    	obj.add(user.getId());                                         								   // 组合参数
		    	List<UserApply> uas=uadao.getListByHQL(hql,obj);
		    	ua=uas.get(0);
		    	ua.setPassword(Md5Utils.md5(uentity.getPassword()));             							   //修改与当前用户相对应的用户审核表的用户的密码  
		    	uadao.update(ua);                                                							   //更新与当前用户相对应的用户审核表的用户的数据
		    }
			
			status=true;
			request.setAttribute("status",status);
			request.setAttribute("resetinfos","密码重置成功！");
		}
		else{
			request.setAttribute("resetinfos","重置失败！");
		}
		return "resetpasswd";
	}
	
////////////////////////////////////////////////////////////////////实现get/set
	
	public List<Resetpwd> getList() {
		return list;
	}

	public void setList(List<Resetpwd> list) {
		this.list = list;
	}

	public Resetpwd getRentity() {
		return rentity;
	}

	public void setRentity(Resetpwd rentity) {
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
}