<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "java.io.*,java.util.*" %>
    <%@ page import="game.Room"%>
    
<%@ page import="game.GameManager"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
         <h2>Auto Refresh Header Example</h2>
         <%
        
         int RoomId = Integer.parseInt(((String)request.getAttribute("id")).substring(5));
         
         %>
         
         
         <%
       //  Room r = GameManager.getInstance().getWaitingRooms().get(RoomId);
 		
            // Set refresh, autoload time as 5 seconds
            response.setIntHeader("Refresh", 5);
            out.println("aq");
            
            
            // Get current time
            Calendar calendar = new GregorianCalendar();
            String am_pm;
            
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            
            if(calendar.get(Calendar.AM_PM) == 0)
               am_pm = "AM";
            else
               am_pm = "PM";
            String CT = hour+":"+ minute +":"+ second +" "+ am_pm;
            out.println("Crrent Time: " + CT + "\n");
         %>
      
</body>
</html>