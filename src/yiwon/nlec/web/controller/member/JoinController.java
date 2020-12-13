package yiwon.nlec.web.controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import yiwon.nlec.web.entity.Member;

import yiwon.nlec.web.service.MemberService;

@WebServlet("/member/join")
public class JoinController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/view/member/join.jsp").forward(request, response);
	}
	
	@Override  //�Ѱ� �޴°�
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		System.out.println(">>> doPost�� ����");
		int cnt = 0;
		
		String cmd = request.getParameter("cmd");
		String id = request.getParameter("id");
		
		MemberService service = new MemberService();
		
		switch (cmd) {
		case "�ߺ�Ȯ��":
			 
			cnt = service.idChk(id);
			if (cnt == 0)
				System.out.println("��� ������ id �Դϴ�.");
			else
				System.out.println("�̹� ������� id �Դϴ�. ���ο� id�� ��� �ϼ���.");
											
			break;
			
		case "Ȯ��":
			
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			String birthday = request.getParameter("birthday");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			
			
			
			int result = 0;
			
			Member member = new Member();
			member.setId(id);
			member.setPwd(pwd);
			member.setName(name);
			member.setGender(gender);
			member.setBirthday(birthday);
			member.setPhone(phone);
			member.setEmail(email);
			
			
			result = service.insertMember(member);
		
			response.sendRedirect("confirm");
			
			break;

		default:
			break;
		}
		
	}	
}
