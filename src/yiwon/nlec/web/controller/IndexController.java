package yiwon.nlec.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yiwon.nlec.web.entity.NoticeView;
import yiwon.nlec.web.service.NoticeService;

@WebServlet("/index")  //페이지요청
public class IndexController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		NoticeService service = new  NoticeService();
		List<NoticeView> list = service.getNoticeList();
		
		request.setAttribute("list", list);
		
		request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
	}

}
