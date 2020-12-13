package yiwon.nlec.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.glass.ui.Application;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//ServletContext application = req.getServletContext(); //application 객체
		//HttpSession session = req.getSession(); //세션객채 - 사용자마다 저장공간이 달라진다.
		Cookie[] cookies = req.getCookies(); //쿠키
		
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");		
		PrintWriter out = resp.getWriter();
		
		String num_ = req.getParameter("num");
		String op = req.getParameter("operator");
		
		int num = 0;		
		if (!num_.equals("")) num = Integer.parseInt(num_);
		
		int result = 0;
		if(op.equals("=")) {
			//계산
			//int x = (int) application.getAttribute("value");
			//int x = (int) session.getAttribute("value");

			int x = 0;
			
			for(Cookie c: cookies) {
				
				if(c.getName().equals("value")) {
					
					x = Integer.parseInt(c.getValue());	
					break;
				}
				
			}
			int y = num;
			
			//String operator = (String) application.getAttribute("op");
			//String operator = (String) session.getAttribute("op");
			String operator = "";
			
			for(Cookie c: cookies) {
				
				if(c.getName().equals("op")) {
					
					operator = c.getValue();	
					break;
				}
				
			}			
			
			if(operator.equals("+"))
				result = x+y;
			else
				result = x-y;
			
			out.println("Result is " + result);
		}
		else
		{
			//저장			
			//application.setAttribute("value", num);
			//application.setAttribute("op", op);
			//session 저장하기
			//session.setAttribute("value", num);
			//session.setAttribute("op", op);
			//쿠키 저장하기
			Cookie valueCookie = new Cookie("value", String.valueOf(num));
			Cookie opCookie = new Cookie("op", op);
			valueCookie.setPath("/");
			//valueCookie.setMaxAge(24*60*60);  //초단위, 브라우저를 닫아도 설정한 시간동안은 사라지지 않는댜ㅏ.
			opCookie.setPath("/");
			resp.addCookie(valueCookie);
			resp.addCookie(opCookie);
			
			resp.sendRedirect("calc2.html");
		}
				
	}

}
