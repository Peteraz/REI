package mis.dao;
import java.util.ArrayList;
import java.util.List;

import mis.entity.Reimbursement;

import org.springframework.stereotype.Repository;

import common.BaseDAO;

//业务逻辑DAO

//spring自动注入
@Repository
public class ReimbursementDAO extends BaseDAO<Reimbursement>{
	
	//搜索未完成的审核的报销单的条数
	public int checkrs(int id){
		int n=0;
		String hql="select count(0) from Reimbursement r where r.state <> '报销通过' and r.state <> '报销不通过' ";
		
        List<Object> obj=new ArrayList<Object>();
		
		if(id!=0){
			hql+="and r.appuser_id = ? ";								// 组合hql
			obj.add(id);												// 组合参数
		}
		
		int x=this.getListByHQLNS(hql,obj);                             // 使用basedao封装的getListByHQL
			n=x;
		return n;
	}
	
	//搜索未完成的审核的报销单
	public List<Reimbursement> checkr(int id,int s,int l){
		List<Reimbursement> result=new ArrayList<Reimbursement>();
		String hql="from Reimbursement r where r.state <> '报销通过' and r.state <> '报销不通过' ";
		
		List<Object> obj=new ArrayList<Object>();
		
		if(id!=0){
			hql+="and r.appuser_id = ? ";							 	// 组合hql
			obj.add(id);												// 组合参数
		}
		
		List<Reimbursement> lr=this.getListByHQLT(hql , obj,s,l);   	// 使用basedao封装的getListByHQLT
		if(lr!=null && lr.size()>0)
				result=lr;
		return result;
	}
	
	//搜索通过或未通过的的报销单的条数(历史记录)
	public int checkars(int id){
		int n=0;
		String hql="select count(0) from Reimbursement r where ";
		
        List<Object> obj=new ArrayList<Object>();
		
		if(id!=0){
			hql+="(r.appuser_id = ? and r.state = '报销通过') or (r.appuser_id = ? and r.state = '报销不通过') ";	    // 组合hql
			obj.add(id);												 				                       // 组合参数
			obj.add(id);
		}
		
		int x=this.getListByHQLNS(hql,obj);                               				// 使用basedao封装的getListByHQLN
			n=x;
		return n;
	}
	
	//搜索通过或未通过的的报销单(历史记录)
	public List<Reimbursement> checkar(int id,int s,int l){
			List<Reimbursement> result=new ArrayList<Reimbursement>();
			String hql="from Reimbursement r where ";			
			
			List<Object> obj=new ArrayList<Object>();
			
			if(id!=0){
				hql+="(r.appuser_id = ? and r.state = '报销通过') or (r.appuser_id = ? and r.state = '报销不通过') ";// 组合hql
				obj.add(id);															                      // 组合参数
				obj.add(id);
			}
			
			List<Reimbursement> lr=this.getListByHQLT(hql , obj , s , l);  						// 使用basedao封装的getListByHQLT
			if(lr!=null && lr.size()>0)
				result=lr;
			return result;
	}
	
	//搜索待审批的报销单的条数
	public int checkes(){
		int n=0;
		String hql="select count(0) from Reimbursement r where r.approver_sign is null and r.approver_op is null ";
		int x=this.getListByHQLN(hql);                               // 使用basedao封装的getListByHQLN
			n=x;
		return n;
	}
	
	//搜索待审批的报销单
	public List<Reimbursement> checke(int s,int l){
		List<Reimbursement> result=new ArrayList<Reimbursement>();
		String hql="from Reimbursement r where r.approver_sign is null and r.approver_op is null ";
			
		List<Reimbursement> lr=this.getListByHQLL(hql,s,l);            // 使用basedao封装的getListByHQLL
		if(lr!=null && lr.size()>0)
			result=lr;
		return result;
	}
	
	//搜索已审批的报销单的条数
	public int checkaes(String name){
		int n=0;
		String hql="select count(0) from Reimbursement r where r.approver_sign is not null and r.approver_op is not null ";
		
		List<Object> obj = new ArrayList<Object>();

		if (name != null && !name.equals("")) {
			hql += "and r.approver_sign = ? ";                             // 组合hql
			obj.add(name);                                                 // 组合参数
		}
		
		int x=this.getListByHQLNS(hql,obj);                               // 使用basedao封装的getListByHQLNS
			n=x;
		return n;
	}
	
	//搜索已审批的报销单
	public List<Reimbursement> checkae(int s,int l,String name){
		List<Reimbursement> result=new ArrayList<Reimbursement>();
		String hql="from Reimbursement r where r.approver_sign is not null and r.approver_op is not null ";
		
		List<Object> obj = new ArrayList<Object>();

		if (name != null && !name.equals("")) {
			hql += "and r.approver_sign = ? ";                          // 组合hql
			obj.add(name);                                              // 组合参数
		}
				
		List<Reimbursement> lr=this.getListByHQLT(hql,obj,s,l);  		// 使用basedao封装的getListByHQLT
		if(lr!=null && lr.size()>0)
			result=lr;
		return result;
	}
	
	//搜索待审核的报销单的条数
	public int checkps(){
		int n=0;
		String hql="select count(0) from Reimbursement r where r.financial_sign is null and r.financial_op is null and r.state <> '报销不通过' and r.state <> '待审批中' ";
		int x=this.getListByHQLN(hql);                               // 使用basedao封装的getListByHQLN
			n=x;
		return n;
	}
	
	//搜索待审核的报销单
	public List<Reimbursement> checkp(int s,int l){
		List<Reimbursement> result=null;
		String hql="from Reimbursement r where r.financial_sign is null and r.financial_op is null and r.state <> '报销不通过' and r.state <> '待审批中' ";
		
		List<Reimbursement> lr=this.getListByHQLL(hql,s,l);  			// 使用basedao封装的getListByHQLL
		if(lr!=null && lr.size()>0)
			result=lr;
		return result;
	}
		
	//搜索已审核的报销单的条数
	public int checkaps(String name){
		int n=0;
		String hql="select count(0) from Reimbursement r where r.financial_sign is not null and r.financial_op is not null ";
		
		List<Object> obj = new ArrayList<Object>();

		if (name != null && !name.equals("")) {
			hql += "and r.financial_sign = ? ";                            // 组合hql
			obj.add(name);                                                 // 组合参数
		}
		
		int x=this.getListByHQLNS(hql,obj);                               // 使用basedao封装的getListByHQLNS
			n=x;
		return n;
	}
	
	//搜索已审核的报销单
	public List<Reimbursement> checkap(int s,int l,String name){
		List<Reimbursement> result=new ArrayList<Reimbursement>();
		String hql="from Reimbursement r where r.financial_sign is not null and r.financial_op is not null ";
		
		List<Object> obj = new ArrayList<Object>();

		if (name != null && !name.equals("")) {
			hql += "and r.financial_sign = ? ";                            // 组合hql
			obj.add(name);                                                 // 组合参数
		}
					
		List<Reimbursement> lr=this.getListByHQLT(hql,obj,s,l);           // 使用basedao封装的getListByHQLT
		if(lr!=null && lr.size()>0)
				result=lr;
		return result;
	}
}