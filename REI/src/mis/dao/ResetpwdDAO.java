package mis.dao;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mis.entity.Resetpwd;
import mis.entity.User;

import org.springframework.stereotype.Repository;

import common.BaseDAO;

//业务逻辑DAO

//spring自动注入
@Repository
public class ResetpwdDAO extends BaseDAO<Resetpwd>{

	//使用token查询存在吗
	public Resetpwd checktoken(String token){
		Resetpwd cpwd=null;
		String hql= "from Resetpwd n where ";
		
		List<Object> obj = new ArrayList<Object>();

		if (token != null && !token.equals("")) {
			hql += "n.token = ? ";                       // 组合hql
			obj.add(token);                              // 组合参数
		}

		List<Resetpwd> lu=this.getListByHQL(hql, obj);  // 使用basedao封装的getListByHQL
        if(lu!=null&&lu.size()>0)
		  cpwd=lu.get(0);
		return cpwd;
	}
}