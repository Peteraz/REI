package common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




//登陆用过滤器
public class PermissionFilter implements Filter {

	 
	public void destroy() {
		// TODO Auto-generated method stub

	}

	 
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		       // 获得在下面代码中要用的request,response,session对象
		          HttpServletRequest servletRequest = (HttpServletRequest) request;
		          HttpServletResponse servletResponse = (HttpServletResponse) response;
		          HttpSession session = servletRequest.getSession();
		  
		         // 获得用户请求的URI
		          String path = servletRequest.getRequestURI();
		          
		          //http://127.0.0.1:8080/JDDemo/user!list.action
		          //http://127.0.0.1:8080/JDDemo/user.jsp
		          //System.out.println(path);
		          
		          //  
		          String u = (String) session.getAttribute("user");

		          // 登陆页面无需过滤
		          if(path.indexOf("/user/login.jsp") > -1) {
		              chain.doFilter(servletRequest, servletResponse);
		              return;
		          }
		          
		          
		  
		          //  就跳转到登陆页面
		          if (u == null||u=="") {
		              // 跳转到登陆页面
		              servletResponse.sendRedirect(servletRequest.getContextPath()+"/user/login.jsp");
		          } else {
		              // 已经登陆,继续此次请求
		              chain.doFilter(request, response);
		          }
	}

 
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
