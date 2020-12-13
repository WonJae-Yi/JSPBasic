<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- <%

//MVC Model1
String num_ = request.getParameter("num");//임시변수
int num = 10; //기본값
  
if(num_ != null && !num_.equals("")) //null 이나 빈문자열 체크
	num = Integer.parseInt(num_);

String result;
if(num%2 != 0)
	result = "홀수";
else
	result = "짝수";

%>  --%>  
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%=request.getAttribute("result") %> 입니다.<br>
	${names[0]}<br>
	${names[1]}<br>
	${notice.id}<br>
	${notice.title}<br>

</body>
</html>