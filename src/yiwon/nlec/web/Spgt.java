package yiwon.nlec.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/spgt")
public class Spgt extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String num_ = request.getParameter("num");// 임시변수
		int num = 10; // 기본값

		if (num_ != null && !num_.equals("")) // null 이나 빈문자열 체크
			num = Integer.parseInt(num_);

		String result;
		if (num % 2 != 0)
			result = "홀수";
		else
			result = "짝수";

		request.setAttribute("result", result);

		// 배열값 전달
		String[] names = { "yiwon", "junghoon" };
		request.setAttribute("names", names);
		
		Map<String, Object> notice = new HashMap<String, Object>();
		
		notice.put("id", 1);
		notice.put("title", "EL은 좋아요");
		request.setAttribute("notice", notice);

		// redirect - 새로운 요청
		// foward - 계속이어가는것
		RequestDispatcher dispatcher = request.getRequestDispatcher("spgt.jsp");
		dispatcher.forward(request, response);
	}

}
