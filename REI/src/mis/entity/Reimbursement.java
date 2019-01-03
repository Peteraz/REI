package mis.entity;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//实体类





import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "reimbursement")
// 表名
public class Reimbursement {
	    // 主键
		/**
		 * 
		 */
		@Id
		@GenericGenerator(name = "generator", strategy = "identity")    //@GeneratedValue的generator属性和@GenericGenerator的name属性要一样，strategy属性表示hibernate的主键生成策略
		@GeneratedValue(generator = "generator")
		private int id;                        //id
		private String tracking_number;        //订单号
		private int appuser_id;                //申请人id
		private String appuser_name;           //申请人姓名  
		private String appuser_identitys;      //申请人身份
	    private double money;                  //要报销的金额
	    private String approver_sign;          //审批人签名
	    private String financial_sign;         //财务部签名
	    private String state;                  //订单状态
	    private Date dtime;                    //订单申请时间
	    private String hospital_level;         //医院级别
	    private String medical_number;         //医保卡
	    private String bank_number;            //银行卡
	    private String approver_op;            //审批操作结果
	    private String financial_op;           //财务部审核操作结果 
	    private String deliver;                //送单人
	    private String operator;               //经办人
	    private String contacts;               //联系人
	    private String telephone;              //报联系人的电话
	    private String reason;                 //事由
	    private String result;                 //审批结果
	    private String picture1;               //照片1
	    private String picture2;               //照片2
	    private String picture3;               //照片3
	    private double remoney;                //最终报销金额
	    private String department;             //部门
	    private String type;                   //报销单的类型
	    
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getTracking_number() {
			return tracking_number;
		}
		public void setTracking_number(String tracking_number) {
			this.tracking_number = tracking_number;
		}
		public int getAppuser_id() {
			return appuser_id;
		}
		public void setAppuser_id(int appuser_id) {
			this.appuser_id = appuser_id;
		}
		public String getAppuser_name() {
			return appuser_name;
		}
		public void setAppuser_name(String appuser_name) {
			this.appuser_name = appuser_name;
		}
		public String getAppuser_identitys() {
			return appuser_identitys;
		}
		public void setAppuser_identitys(String appuser_identitys) {
			this.appuser_identitys = appuser_identitys;
		}
		public double getMoney() {
			return money;
		}
		public void setMoney(double money) {
			this.money = money;
		}
		public String getApprover_sign() {
			return approver_sign;
		}
		public void setApprover_sign(String approver_sign) {
			this.approver_sign = approver_sign;
		}
		public String getFinancial_sign() {
			return financial_sign;
		}
		public void setFinancial_sign(String financial_sign) {
			this.financial_sign = financial_sign;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public Date getDtime() {
			return dtime;
		}
		public void setDtime(Date dtime) {
			this.dtime = dtime;
		}
		public String getHospital_level() {
			return hospital_level;
		}
		public void setHospital_level(String hospital_level) {
			this.hospital_level = hospital_level;
		}
		public String getMedical_number() {
			return medical_number;
		}
		public void setMedical_number(String medical_number) {
			this.medical_number = medical_number;
		}
		public String getBank_number() {
			return bank_number;
		}
		public void setBank_number(String bank_number) {
			this.bank_number = bank_number;
		}
		public String getApprover_op() {
			return approver_op;
		}
		public void setApprover_op(String approver_op) {
			this.approver_op = approver_op;
		}
		public String getFinancial_op() {
			return financial_op;
		}
		public void setFinancial_op(String financial_op) {
			this.financial_op = financial_op;
		}
		public String getDeliver() {
			return deliver;
		}
		public void setDeliver(String deliver) {
			this.deliver = deliver;
		}
		public String getOperator() {
			return operator;
		}
		public void setOperator(String operator) {
			this.operator = operator;
		}
		public String getContacts() {
			return contacts;
		}
		public void setContacts(String contacts) {
			this.contacts = contacts;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getPicture1() {
			return picture1;
		}
		public void setPicture1(String picture1) {
			this.picture1 = picture1;
		}
		public String getPicture2() {
			return picture2;
		}
		public void setPicture2(String picture2) {
			this.picture2 = picture2;
		}
		public String getPicture3() {
			return picture3;
		}
		public void setPicture3(String picture3) {
			this.picture3 = picture3;
		}
		public double getRemoney() {
			return remoney;
		}
		public void setRemoney(double remoney) {
			this.remoney = remoney;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
}
