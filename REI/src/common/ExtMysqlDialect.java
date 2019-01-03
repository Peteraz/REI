package common;

import java.sql.Types;

import org.hibernate.dialect.MySQLDialect;


//解决mysql  text类型
public class ExtMysqlDialect extends MySQLDialect {

	 public ExtMysqlDialect() {
	  super();
	  registerHibernateType(Types.LONGVARCHAR, 65535, "text"); 
	 }
	}