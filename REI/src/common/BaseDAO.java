package common;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

//使用泛型
@Transactional 
public class BaseDAO<T> {

	//spring里的注解，实现自动注入(自动new)
	@Resource
	private SessionFactory sessionFactory;
	
	
	/*
	 * 得到session，所有增删改查都从session开始
	 */
    
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session gs() {
		return this.sessionFactory.getCurrentSession();
	}
	
	
	
	/*
	 * 根据T得到真正的class类型，使用反射
	 */
	protected Class<T> entityClass;

	protected Class getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}
	
	@Transactional
	public List<T> getListByHQL(final String hqlString) {
		Query query = gs().createQuery(hqlString);
		return query.list();
	}


	


	/*
	 * 添加
	 */
	@Transactional
	public void add(T t) {
		 gs().save(t);
	}
	
	/*
	 * 更新
	 */
	@Transactional
	public void update(T t) {
		gs().update(t);
	}

	/*
	 * 删除
	 */
	@Transactional
	public void delete(Integer id) {
		gs().delete(this.get(id));
	}
	
	
	

	/*
	 * 查询
	 */
	@Transactional
	public T get(Integer id) {
		T instance = (T) gs().get(getEntityClass(), id);
		return instance;
       
	}
	


	/*
	 * 自动判断添加或修改，保存
	 */
	@Transactional
	public void save(T t) {
		gs().saveOrUpdate(t);
	}
	
	
	/*
	 * 查询全部
	 */
	@Transactional
	public List<T> listall() {
		List<T> result=gs().createCriteria(getEntityClass()).list();
		return result;
	}
	
	
	/*
	 * 通用hql查询
	 */	
	@Transactional
	public List<T> getListByHQL(final String hqlString, final List values) {
		Query query = gs().createQuery(hqlString);
		if (values != null) {
			Object[] vvs = values.toArray(new Object[values.size()]);
			for (int i = 0; i < vvs.length; i++) {
				query.setParameter(i, vvs[i]);
			}
		}
		return query.list();
	}
	
	/*
	 * 不用参数的hql查询
	 */	
	@Transactional
	public List<T> getListByHQLA(final String hqlString) {
		Query query = gs().createQuery(hqlString);
		return query.list();
	}
	
	/*
	 * 不用参数的hql查询条数
	 */	
	@Transactional
	public int getListByHQLN(final String hqlString) {
		Query query = gs().createSQLQuery(hqlString);
		List list=query.list();
		return Integer.parseInt(list.get(0).toString());
	}
	
	
	/*
	 * 用参数的hql查询条数
	 */	
	@Transactional
	public int getListByHQLNS(final String hqlString,final List values) {
		Query query = gs().createSQLQuery(hqlString);
		if (values != null) {
			Object[] vvs = values.toArray(new Object[values.size()]);
			for (int i = 0; i < vvs.length; i++) {
				query.setParameter(i, vvs[i]);
			}
		}
		List list=query.list();
		return Integer.parseInt(list.get(0).toString());
	}	
	
	/*
	 * 限制的hql查询1
	 */	
	@Transactional
	public List<T> getListByHQLL(final String hqlString,final int s,final int l) {
		Query query = gs().createQuery(hqlString);
		query.setFirstResult(s);          //s是int值，是开始查询的位置 
		query.setMaxResults(l);           //限制查找数据条数
		return query.list();
	}
	
	/*
	 * 限制的hql查询2
	 */	
	@Transactional
	public List<T> getListByHQLT(final String hqlString,final List values,final int s,final int l) {
		Query query = gs().createQuery(hqlString);
		if (values != null) {
			Object[] vvs = values.toArray(new Object[values.size()]);
			for (int i = 0; i < vvs.length; i++) {
				query.setParameter(i, vvs[i]);
			}
		}
		query.setFirstResult(s);          //s是int值，是开始查询的位置 
		query.setMaxResults(l);           //限制查找数据条数
		return query.list();
	}

	/* 执行复杂的sql查询
	 * 
	 *  得到一列：List<String>
	 * 
	 * 得到多列：List<String[]>
	 */
	@Transactional(readOnly = true)
	public List getListBySQL(final String sqlString, final List values) {
		Query query = gs().createSQLQuery(sqlString);
		if (values != null) {
			Object[] vvs = values.toArray(new Object[values.size()]);
			for (int i = 0; i < vvs.length; i++) {
				query.setParameter(i, vvs[i]);
			}
		}
		return query.list();
	}
	
	

	
}
