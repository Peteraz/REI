package mis.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//实体类




import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Resetpwd")
// 表名
public class Resetpwd {

	// 主键
	/**
	 * 
     */
	@Id
	@GenericGenerator(name = "generator", strategy = "identity")   //@GeneratedValue的generator属性和@GenericGenerator的name属性要一样，strategy属性表示hibernate的主键生成策略
	@GeneratedValue(generator = "generator")
	private int id;                    //id
	private int uid;                   //账号
	private String token;              //密码
	private Date endtime;            //邮箱
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
}
