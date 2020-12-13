package yiwon.nlec.web.controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yiwon.nlec.web.entity.Notice;
import yiwon.nlec.web.service.NoticeService;

@WebServlet("/member/confirm")
public class ConfirmController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		request.getRequestDispatcher("/WEB-INF/view/member/confirm.jsp").forward(request, response);

	}
}
