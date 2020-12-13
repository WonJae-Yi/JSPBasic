package yiwon.nlec.web.controller.admin.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yiwon.nlec.web.entity.Notice;
import yiwon.nlec.web.service.NoticeService;

@WebServlet("/admin/board/notice/detail")
public class DetailController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		NoticeService service = new  NoticeService();
		Notice notice = service.getNotice(id);

		request.setAttribute("n", notice);
		
		//이전글
		Notice preNotice = service.getPrevNotice("title", "",  "%", id);
		request.setAttribute("prevn", preNotice);
		
		//다음글 
		Notice nextNotice = service.getNextNotice("title", "", "%", id);
		request.setAttribute("nextn", nextNotice);
		
		//foward : 이어서 실행
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/detail.jsp").forward(request, response);
		
	}

}
