package mail;

public class sendMail {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*sendEmailForChangePw("xjh263153@qq.com","谢俊辉","2017-05-02 15:18:07","123");*/
	}
    /**
     * 发送重置密码邮件
     * @throws IOException
     */
    public static void sendEmailForChangePw(String email,String name,String date,String token) 
    {
        boolean flag = false;
        String result = "";
        try {
        		System.out.println("正在发送找回密码邮件给" + email);
                //发送邮件
                String mailHtml =""
                + "<tbody><tr>"
                + "<td style='background:#21b03d;padding:7px;'"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding: 60px 60px 30px;'>"
                + "<h1 style='font-size:18px;'>亲爱的<span style='color:#21b03d;'>"+name+"</span></h1>"
                + "<p style='font-size:14px;'>你在线报销系统官网申请<span style='color:#21b03d;'>重置密码</span>，点击下面按钮进入重置密码界面，有效期至"+date+"</p>"
                + "<p style='padding-left:10%;margin-top:50px;'><a href='http://localhost:8080/REI/resetpwd!resetpwd.action?token="+token+"' style='border:1px solid #21b03d;color:#21b03d;font-size:14px;height:30px;line-height:30px;padding:10px;text-decoration:none'>点击重置密码</a></p>"
                + "</td>"
                + "</tr>"
                +"<tr></tr>"
                + "</tbody>";
                // 这个类主要是设置邮件  
                MailSenderInfo mailInfo = new MailSenderInfo();  
                mailInfo.setMailServerHost("smtp.163.com");//使用163邮箱接口
                mailInfo.setMailServerPort("25");  
                mailInfo.setValidate(true);  
                mailInfo.setUserName("changepasword@163.com"); // 实际发送者  
                mailInfo.setPassword("a2510805");// 您的邮箱密码  
                mailInfo.setFromAddress("changepasword@163.com"); // 设置发送人邮箱地址  
                mailInfo.setToAddress(email); // 设置接受者邮箱地址  
                //mailInfo.setToAddress("851867807@qq.com"); // 设置接受者邮箱地址 
                mailInfo.setSubject("密码重置");  
                mailInfo.setContent(mailHtml);  
                // 这个类主要来发送邮件  
                SimpleMailSender sms = new SimpleMailSender();   
                sms.sendHtmlMail(mailInfo); // 发送html格式 
                flag = true;
                System.out.println("成功发送重置密码邮件至"+email);
                result = "邮件发送成功，请查看邮件以修改密码";
				} catch (Exception e) {
					result = "请求失败，请联系管理员";
				}
        } 
}
