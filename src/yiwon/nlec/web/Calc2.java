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

		//ServletContext application = req.getServletContext(); //application ��ü
		//HttpSession session = req.getSession(); //���ǰ�ä - ����ڸ��� ��������� �޶�����.
		Cookie[] cookies = req.getCookies(); //��Ű
		
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");		
		PrintWriter out = resp.getWriter();
		
		String num_ = req.getParameter("num");
		String op = req.getParameter("operator");
		
		int num = 0;		
		if (!num_.equals("")) num = Integer.parseInt(num_);
		
		int result = 0;
		if(op.equals("=")) {
			//���
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
			//����			
			//application.setAttribute("value", num);
			//application.setAttribute("op", op);
			//session �����ϱ�
			//session.setAttribute("value", num);
			//session.setAttribute("op", op);
			//��Ű �����ϱ�
			Cookie valueCookie = new Cookie("value", String.valueOf(num));
			Cookie opCookie = new Cookie("op", op);
			valueCookie.setPath("/");
			//valueCookie.setMaxAge(24*60*60);  //�ʴ���, �������� �ݾƵ� ������ �ð������� ������� �ʴ´���.
			opCookie.setPath("/");
			resp.addCookie(valueCookie);
			resp.addCookie(opCookie);
			
			resp.sendRedirect("calc2.html");
		}
				
	}

}
