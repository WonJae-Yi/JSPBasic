<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%

String cnt_ = request.getParameter("cnt");//임시변수
 int cnt = 10; //기본값
  
 if(cnt_ != null && !cnt_.equals("")) //null 이나 빈문자열 체크
 	cnt = Integer.parseUnsignedInt(cnt_);
%>   


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%for(int i=0; i < cnt; i++){ %>	
 Hello(안녕) Servlet!!<br >
<% } %>
</body>
</html>