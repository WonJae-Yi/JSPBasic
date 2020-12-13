package yiwon.nlec.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hi")
public class Nana extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		String cnt_ = req.getParameter("cnt");//�ӽú���
		 int cnt = 10; //�⺻��
		  
		 if(cnt_ != null && !cnt_.equals("")) //null �̳� ���ڿ� üũ
		 	cnt = Integer.parseUnsignedInt(cnt_);

		/*
		 * for (int i = 0; i < 100; i++) { out.println((i + 1) +
		 * ": �ȳ� Servlet!!!!<br>"); }
		 */
		 
		 for(int i=0; i<cnt;i++) 
		 { 
			 out.println((i+1)+":Hello(�ȳ�) Servlet!!<br >"); 
		 }		 
	}

}
