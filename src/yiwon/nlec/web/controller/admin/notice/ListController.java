package yiwon.nlec.web.controller.admin.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yiwon.nlec.web.entity.Notice;
import yiwon.nlec.web.entity.NoticeView;
import yiwon.nlec.web.service.NoticeService;

@WebServlet("/admin/board/notice/list")  //목로페이지요청
public class ListController extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] openIds = request.getParameterValues("open-id");
		String[] delIds = request.getParameterValues("del-id");
		String cmd = request.getParameter("cmd");
		String ids_ = request.getParameter("ids").trim();  //앞 뒤 공백제거
		String[] ids = ids_.split(" ");//공백을 기준으로 배열로

		
		NoticeService service = new  NoticeService();
		int result = 0;
		
		switch (cmd) {
		case "일괄공개":
			 
			result = service.pubNoticeAll(ids, openIds);
			break;
			
		case "일괄삭제":
			
			result = service.deleteNoticeAll(delIds);
			
			break;

		default:
			break;
		}
		
		response.sendRedirect("list");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String field_ = request.getParameter("f");
		String query_ = request.getParameter("q");
		String page_ = request.getParameter("p");
		
		String field = "title";
		if(field_ != null && !field_.equals("")) field = field_;
		
		String query = "";
		if(query_ != null && !query_.equals("")) query = query_;
		
		int page = 1;
		if(page_ != null && !page_.equals("")) page = Integer.parseInt(page_);
		

		NoticeService service = new  NoticeService();
		List<NoticeView> list = service.getNoticeList(field, query, page, "%");
		
		//전체 건수 가져오기
		int count = service.getNoticeCount(field, query, "%");
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);

		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp").forward(request, response);
		
	}

}
