<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.mail.*" %>    
<%@ page import="javax.mail.internet.*" %>
<%@ page import="java.util.Date"%>
<%@ page import="util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.mail.Authenticator" %> 



<%! 
//發送e-mail
public void send(String mailserver,String from,String to,String subject,String msgtext){
//System.out.println(mailserver+":"+from+":"+to+":"+subject+":"+msgtext);
	InternetAddress[] address = null;	
    boolean sessionDebug = false;
    
	try{
 		
  		java.util.Properties props = System.getProperties();
  		props.put("mail.host",mailserver);
  		props.put("mail.transport.protocol","smtp");

  		props.put("mail.smtp.auth","true");
  		


  		
 		javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props,null);
 		mailSession.setDebug(sessionDebug);
 		Message msg = new MimeMessage(mailSession); 
 		
		from="finance@kcg.gov.tw";
		
		
 		msg.setFrom(new InternetAddress(from));
 			
 		address = InternetAddress.parse(to,false);
 		
 		msg.setRecipients(Message.RecipientType.TO, address);     		
 		
 		msg.setSubject(MimeUtility.encodeText(subject,"UTF-8",null));
 		msg.setSentDate(new Date()); 		
		//msg.setText(MimeUtility.encodeText(msgtext,"UTF-8",null));
			
		Multipart mp=new MimeMultipart();
        MimeBodyPart mbp1=new MimeBodyPart();         
        mbp1.setContent(msgtext, "text/html;charset=UTF-8");
        mp.addBodyPart(mbp1);
        msg.setContent(mp);
        
        Transport transport = mailSession.getTransport("smtp");     
		transport.connect(mailserver, "finance", "finance.2006");

		transport.sendMessage(msg, msg.getAllRecipients());
       
		//Transport.send(msg);

  	}catch (Exception e) {
    	e.printStackTrace();
  	}

}



%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="js/default.css" type="text/css">
<title>忘記密碼</title>
</head>

<% 
	String id=Common.checkGet(request.getParameter("id"));
	String email=Common.checkGet(request.getParameter("email"));	
	String mailserver=getServletContext().getInitParameter("SMTP");	
	String from=getServletContext().getInitParameter("SystemMail");
	boolean display=false;
	boolean error=false;		
	
	if(id!=null&&email!=null&&!id.equals("")&&!email.equals("")){						
		
		Database db=new Database();
		Connection conn=db.getConnection();
		PreparedStatement ps=conn.prepareStatement("select count(*) from SYSAP_EMP where EMPID=? and UPPER(EMPEMAIL)=?");		
		ps.setString(1,Common.esapi(id));
		ps.setString(2,Common.esapi(email.toUpperCase()));
		ResultSet rs1=ps.executeQuery();
		rs1.next();		
		
		if(rs1.getInt(1)==1){
			ResultSet rs2=db.querySQL("select * from SYSAP_Emp where EMPID='"+Common.esapi(id)+"'");
			rs2.next();
			String empemail=rs2.getString("EMPEMAIL");
			String msg="[財產管理資訊系統]<p>"+"您的帳號密碼如下";
			msg=msg+"<p>帳號: "+rs2.getString("EMPID")+"\n 密碼: "+rs2.getString("EMPPWD")+" \n";			
			display=true;
			send(mailserver,from,empemail,"財產管理資訊系統訊息通知",msg);		
		}else{
			error=true;	
		}								
	}
%>

<%if(display){ %>

<%--將密碼送至E-mail訊息--%>

<table class="bg" width="300" cellspacing="0" cellpadding="0" align="center"><tr><td>
<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" style="text-align:center">忘記密碼</td>
	</tr>
	
	
	<tr>
		<td class="">密碼已寄至你的E-mail</td>
	</tr>	
	<!-- toolbar-->
	<tr><td class="td_form" style="background-color:black"></td></tr>		
	<tr><td class="td_form" style="text-align:center">
		<input class="toolbar_default" type="button" value="關閉視窗" onclick="window.close();">		
	</td></tr>	
	<tr><td class="td_form" style="background-color:black"></td></tr>
	<!-- end toolbar-->
</table>
</td></tr></table>	
<%return;}else if(error){%>

<%--錯誤訊息 --%>

<table class="bg" width="300" cellspacing="0" cellpadding="0" align="center"><tr><td>
<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" style="text-align:center">忘記密碼</td>
	</tr>
	
	
	<tr>
		<td>帳號、E-mail輸入錯誤,請重新嘗試,或者請連繫財政處,取回您的帳號密碼</td>
	</tr>	
	<!-- toolbar-->
	<tr><td class="td_form" style="background-color:black"></td></tr>		
	<tr><td class="td_form" style="text-align:center">
		<input class="toolbar_default" type="button" value="關閉視窗" onclick="window.close();">		
	</td></tr>	
	<tr><td class="td_form" style="background-color:black"></td></tr>
	<!-- end toolbar-->
</table>
</td></tr></table>
<%return;}%>

<script language="javascript">

function checkField(){
	var msg;
	if(document.form1.id.value==""||document.form1.email.value==""){
		alert("請輸入帳號和E-mail！");
	}else{
	   var filter=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i
       if (!filter.test(document.form1.email.value)){
              alert("電子信箱格式錯誤！");              
       }else{
       		document.form1.submit();
       }
	}	 
	
}

</script>
<body>
<form id="form1" name="form1" method="post" action="getpwd.jsp">
<table class="bg" width="300" cellspacing="0" cellpadding="0" align="center"><tr><td>
<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">忘記密碼</td>
	</tr>
	
	
	<tr>
		<td class="queryTDLable">帳號：</td>
		<td class="queryTDInput"><input type="text" name="id" class="field_Q"></td>
	</tr>	
		
	<tr>
		<td class="queryTDLable">E-mail：</td>		
		<td class="queryTDInput" colspan="3">
		<input type="text" name="email" class="field_Q"></td>
	</tr>
	<!-- toolbar-->
	<tr><td class="td_form" colspan="2" style="background-color:black"></td></tr>		
	<tr><td class="td_form" colspan="2" style="text-align:center">
		<input class="toolbar_default" type="button" name="btn_ok"		value="確　　定" onclick="checkField();">
		<input class="toolbar_default" type="reset"  name="btn_reset"	value="重　　 設" >
		<input class="toolbar_default" type="button" value="關閉視窗" onclick="window.close();">
	</td></tr>	
	<tr><td class="td_form" colspan="2" style="background-color:black"></td></tr>
	<!-- end toolbar-->
</table>
</td></tr></table>	
</form>
</body>
</html>
