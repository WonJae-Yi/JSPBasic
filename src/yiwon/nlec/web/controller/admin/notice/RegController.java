package yiwon.nlec.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import yiwon.nlec.web.entity.Notice;
import yiwon.nlec.web.service.NoticeService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 50 * 5)
@WebServlet("/admin/board/notice/reg") // 페이지요청
public class RegController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp").forward(request, response);
	}

	@Override  //넘겨 받는거
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String isOpen = request.getParameter("open");  //체크되었을때만 true가 넘어옴
		String pub = "0";
		if(isOpen != null)			
			pub = "1";	
				
		//파일등록 
		Collection<Part> parts = request.getParts(); 
		StringBuilder builder =	new StringBuilder();
		  
		for(Part p : parts) {
		  
			  if(!p.getName().equals("file")) continue; 
			  if(p.getSize() == 0) continue;
			  
			  Part filePart = p; 
			  String fileName = filePart.getSubmittedFileName();
			  builder.append(fileName); 
			  builder.append(","); 
			  InputStream fis = filePart.getInputStream();			  
			  String realPath = request.getServletContext().getRealPath("/upload");			  
			  
			  //동일한  파일명이 존재한다면 
			  String filePath = realPath + File.separator + fileName;
			  
			  //System.out.println(filePath); 
			  File path = new File(realPath);
			  if(!path.exists()) path.mkdirs();
			  			  
			  FileOutputStream fos = new FileOutputStream(filePath);
			  
			  byte[] buf = new byte[1024]; 
			  int size = 0; 
			  while((size = fis.read(buf)) != -1) 
				  fos.write(buf,0,size);
			  
			  fos.close(); 
			  fis.close();
			  
		  }
		  
		  builder.delete(builder.length() - 1, builder.length()); //마지막 콤마 자르기
		 		
		
		  //단일파일 저장시 사용
		/*
		 * Part filePart = request.getPart("file"); //client에서 보낸 name값 String fileName
		 * = filePart.getSubmittedFileName(); InputStream fis =
		 * filePart.getInputStream(); String realPath =
		 * request.getServletContext().getRealPath("/upload"); //실제 물리경로를 얻는다.
		 * System.out.println(realPath);
		 * 
		 * 
		 * String filePath = realPath + File.separator + fileName; FileOutputStream fos
		 * = new FileOutputStream(filePath);
		 * 
		 * byte[] buf = new byte[1024]; int size = 0; while((size = fis.read(buf)) !=
		 * -1) fos.write(buf,0,size); fos.close(); fis.close();
		 */
		 	
		
		int result = 0;
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterid("yiwon");
		notice.setFiles(builder.toString());
		
		NoticeService service = new NoticeService();
		result = service.insertNotice(notice);
	
		response.sendRedirect("list");
	}
}
