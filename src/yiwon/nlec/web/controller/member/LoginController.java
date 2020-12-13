package yiwon.nlec.web.controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javafx.scene.control.Alert;
import yiwon.nlec.web.entity.Member;
import yiwon.nlec.web.entity.Notice;
import yiwon.nlec.web.service.MemberService;
import yiwon.nlec.web.service.NoticeService;

@WebServlet("/member/login")
public class LoginController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		// foward : 이어서 실행
		request.getRequestDispatcher("/WEB-INF/view/member/login.jsp").forward(request, response);

		System.out.println("LoginController");
	}
	
	@Override  //넘겨 받는거
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		System.out.println(">>> doPost로 실행");
		 
		String id = request.getParameter("username");		
		String pwd = request.getParameter("password");

		String result = "";
		
		
		MemberService service = new MemberService();
		result = service.getLogin(id, pwd);
		
		if(result != "0")
			System.out.println(result);
		else
			response.sendRedirect("/notice/list");
	}	
}
	