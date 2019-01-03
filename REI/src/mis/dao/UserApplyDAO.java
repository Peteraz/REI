package mis.dao;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mis.entity.User;
import mis.entity.UserApply;

import org.springframework.stereotype.Repository;

import common.BaseDAO;

//业务逻辑DAO

//spring自动注入
@Repository
public class UserApplyDAO extends BaseDAO<UserApply>{

	//查询除了管理员的所有新用户的条数
	public int checknewusers(){
		int n=0;
		String hql="select count(0) from UserApply n where n.state = '待审核' and n.cpicture is not null and n.ipicture is not null ";
		int x=this.getListByHQLN(hql);     // 使用basedao封装的getListByHQL
			n=x;
		return n;
	}
	
	//查询新申请用户
	public List<UserApply> checknewuser(int start,int limilt){
		List<UserApply> users=null;
		String hql="from UserApply n where n.state = '待审核' and n.cpicture is not null and n.ipicture is not null ";
		List<UserApply> u=this.getListByHQLL(hql,start,limilt);     // 使用basedao封装的getListByHQL
		if(u!=null&&u.size()>0)
			users=u;
		return users;
	}
	
	//查找一个待审核用户
	public UserApply checkuser(int uid,int start,int limilt){
		UserApply users=null;
		String hql="from UserApply n where ";
			
		List<Object> obj=new ArrayList<Object>();
			
		if(uid!=0){
			 hql+="n.uid = ? ";// 组合hql
			 obj.add(uid);// 组合参数
		}
			
		List<UserApply> u=this.getListByHQLT(hql,obj,start,limilt);     // 使用basedao封装的getListByHQL
		if(u!=null&&u.size()>0)
			users=u.get(0);
		return users;
	}
}