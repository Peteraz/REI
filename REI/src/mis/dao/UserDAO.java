package mis.dao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mis.entity.User;
import mis.entity.UserApply;

import org.hibernate.id.GUIDGenerator;
import org.springframework.stereotype.Repository;

import common.BaseDAO;

//业务逻辑DAO

//spring自动注入
@Repository
public class UserDAO  extends BaseDAO<User> {
	
	//用户登录账户和密码是否正确
	public User login(String account,String pwd)
	{
		User result=null;
		String hql = "from User n where ";

		List<Object> obj = new ArrayList<Object>();

		if (account != null && !account.equals("")) {
			hql += "n.account = ? ";              // 组合hql
			obj.add(account);                     // 组合参数
		}
		if (pwd != null && !pwd.equals("")) {
			hql += "and n.password=?";            // 组合hql
			obj.add(pwd);                         // 组合参数
		}

		List<User> lu=this.getListByHQL(hql, obj); // 使用basedao封装的getListByHQL
        if(lu!=null&&lu.size()>0)
		  result=lu.get(0);
		return result;
	}
	
	//生成token
	public static String createToken(){
		String s = UUID.randomUUID().toString();
		String token=s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);   //去掉-,生成32位随机token
		return token;
	}  
	
	
	//明天时间
	public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }
	  
	
	/**
     * 判断是否是邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email)
    {// 验证邮箱的正则表达式 
     String format = "\\w{2,20}[@][a-z0-9]{2,}[.]\\p{Lower}{2,}";
     //p{Alpha}:内容是必选的，和字母字符[\p{Lower}\p{Upper}]等价。如：200896@163.com不是合法的。
     //w{2,15}: 2~15个[a-zA-Z_0-9]字符；w{}内容是必选的。 如：dyh@152.com是合法的。
     //[a-z0-9]{3,}：至少三个[a-z0-9]字符,[]内的是必选的；如：dyh200896@16.com是不合法的。
     //[.]:'.'号时必选的； 如：dyh200896@163com是不合法的。
     //p{Lower}{2,}小写字母，两个以上。如：dyh200896@163.c是不合法的。
     //\u4e00-\u9fa5判断是否是中文
     if (email.matches(format))
      { 
       return true;// 邮箱名合法，返回true 
      }
     else
      {
       return false;// 邮箱名不合法，返回false
      }
    }      
   	
	//检查账号是否存在
	public User checka(String account){
		User result=null;
		String hql="from User n where ";
			
		List<Object> obj=new ArrayList<Object>();
			
		if(account!=null && !account.equals("")){
			hql+="n.account = ? ";// 组合hql
			obj.add(account);// 组合参数
		}
			
		List<User> lu=this.getListByHQL(hql , obj);  // 使用basedao封装的getListByHQL
		if(lu!=null&&lu.size()>0)
			result=lu.get(0);
		return result;
	}
	
	//检查邮箱是否存在
	public User checkmail(String eamil){
		User result=null;
		String hql="from User n where ";
				
		List<Object> obj=new ArrayList<Object>();
				
		if(eamil!=null && !eamil.equals("")){
				hql+="n.email = ? ";                 // 组合hql
				obj.add(eamil);						 // 组合参数
		}
				
		List<User> lu=this.getListByHQL(hql , obj);  // 使用basedao封装的getListByHQL
		if(lu!=null&&lu.size()>0)
				result=lu.get(0);
		 return result;
	}
	
	//检查证件是否存在
	public User checkn(String credential_number){
		User result=null;
		String hql="from User n where ";
		
		List<Object> obj=new ArrayList<Object>();
		
		if(credential_number!=null && !credential_number.equals("")){
			hql+="n.credential_number = ? ";         // 组合hql
			obj.add(credential_number);              // 组合参数
		}
		
		List<User> lu=this.getListByHQL(hql , obj);  // 使用basedao封装的getListByHQL
		if(lu!=null&&lu.size()>0)
			result=lu.get(0);
		return result;
	}	
	
	//使用证件号查找某一个用户
	public List<User> checkoneuser(String credential_number,int start,int limilt){
		List<User> users=null;
		String hql="from User n where ";
		
		List<Object> obj=new ArrayList<Object>();
		
		if(credential_number!=null && !credential_number.equals("")){
		    hql+="n.credential_number = ? ";						// 组合hql
		    obj.add(credential_number);								// 组合参数
		}
		
		List<User> u=this.getListByHQLT(hql,obj,start,limilt);     // 使用basedao封装的getListByHQL
		if(u!=null&&u.size()>0)
			users=u;
		return users;
	}
	
	//查询除了管理员的所有用户的条数
	public int checkusers(){
		int n=0;
		String hql="select count(0) from User n where n.state='通过' and n.identitys <> '管理员' ";
		int x=this.getListByHQLN(hql);     					// 使用basedao封装的getListByHQL
			n=x;
		return n;
	}
	
	//查询除了管理员的所有用户
	public List<User> checkuser(int start,int limilt){
		List<User> users=null;
		String hql="from User n where n.state='通过' and n.identitys <> '管理员' ";
		List<User> u=this.getListByHQLL(hql,start,limilt);     // 使用basedao封装的getListByHQL
		if(u!=null&&u.size()>0)
			users=u;
		return users;
	}
	
	//查询除了管理员的所有新用户的条数
	public int checknewusers(){
		int n=0;
		String hql="select count(0) from User n where n.identitys <> '管理员' ";
		int x=this.getListByHQLN(hql);     // 使用basedao封装的getListByHQL
			n=x;
		return n;
	}
	
	//查询新申请用户
	public List<User> checknewuser(int start,int limilt){
		List<User> users=null;
		String hql="from User n where n.identitys <> '管理员' ";
		List<User> u=this.getListByHQLL(hql,start,limilt);     // 使用basedao封装的getListByHQL
		if(u!=null&&u.size()>0)
			users=u;
		return users;
	}
	
	//获取ip地址
	public String getIpAddr(HttpServletRequest request) { 
        String ip = request.getHeader("x-forwarded-for"); 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        return ip; 
    } 

}