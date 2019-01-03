package mis.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//实体类


import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
// 表名
public class User {

	// 主键
	/**
	 * 
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "identity")   //@GeneratedValue的generator属性和@GenericGenerator的name属性要一样，strategy属性表示hibernate的主键生成策略
	@GeneratedValue(generator = "generator")
	private int id;                    //id
	private String account;            //账号
	private String password;           //密码
	private String email;              //邮箱
    private String name;               //姓名
    private String sex;                //性别
    private String identitys;          //身份
    private String credential_number;  //证件号
    private String idcard_number;      //身份证
    private String cpicture;           //证件照片
    private String ipicture;           //身份证照片
    private String department;         //部门
    private String state;              //状态
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdentitys() {
		return identitys;
	}
	public void setIdentitys(String identitys) {
		this.identitys = identitys;
	}
	public String getCredential_number() {
		return credential_number;
	}
	public void setCredential_number(String credential_number) {
		this.credential_number = credential_number;
	}
	public String getIdcard_number() {
		return idcard_number;
	}
	public void setIdcard_number(String idcard_number) {
		this.idcard_number = idcard_number;
	}
	public String getCpicture() {
		return cpicture;
	}
	public void setCpicture(String cpicture) {
		this.cpicture = cpicture;
	}
	public String getIpicture() {
		return ipicture;
	}
	public void setIpicture(String ipicture) {
		this.ipicture = ipicture;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
