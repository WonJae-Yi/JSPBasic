package yiwon.nlec.web.controller.notice;

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

@WebServlet("/notice/list")  //목로페이지요청
public class ListController extends HttpServlet{
	
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
		List<NoticeView> list = service.getNoticeList(field, query, page, "1");
		
		//전체 건수 가져오기
		int count = service.getNoticeCount(field, query, "1");
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);

		request.getRequestDispatcher("/WEB-INF/view/notice/list.jsp").forward(request, response);
		
	}

}
