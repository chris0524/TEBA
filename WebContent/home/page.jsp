<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script language="javascript">
var pageListener = new Object();
pageListener.beforeGotoPage = function(){
    // do nothing,for override.
}

function gotoPage(iPageNo)
{
	
	if (isObj(form1.queryone_enterOrg)) {
		$("input[name='queryone_enterOrg']").val("");
	}
	if (isObj(form1.queryone_ownership)) {
		$("input[name='queryone_ownership']").val("");
	}
	if (isObj(form1.queryone_caseNo)) {
		$("input[name='queryone_caseNo']").val("");
	}
	if (isObj(form1.queryone_differenceKind)) {
		$("input[name='queryone_differenceKind']").val("");
	}

	if (isObj(form1.pageSize1)) {
		$("input[name='pageSize1']").val("");
	}
	if (isObj(form1.totalPage1)) {
		$("input[name='totalPage1']").val("");
	}
	if (isObj(form1.currentPage1)) {
		$("input[name='currentPage1']").val("");
	}
	if (isObj(form1.totalRecord1)) {
		$("input[name='totalRecord1']").val("");
	}
	if (isObj(form1.recordStart1)) {
		$("input[name='recordStart1']").val("");
	}
	if (isObj(form1.recordEnd1)) {
		$("input[name='recordEnd1']").val("");
	}
	// 財產/物品增加單基本資料頁面, 切換列表頁數時清空紀錄頁數, 避免queryall時固定住列表頁數
	var jspname = CookieHelper.getJSPName(document.URL);
	if (jspname == "untch001f02.jsp" || jspname == "untne002f.jsp") {
		form1.mainPage1.value = "";
	}
	
	pageListener.beforeGotoPage();
	if (isObj(form1.state)) form1.state.value = "queryAll";
	form1.currentPage.value = iPageNo;
	beforeSubmit();
	form1.submit();
}
</script>
<%
   try{
   	util.QueryBean queryBean = (util.QueryBean)request.getAttribute("QueryBean");
   	int pageSize = queryBean.getPageSize();
   	int totalPage = queryBean.getTotalPage();
   	int currentPage = queryBean.getCurrentPage();
   	int totalRecord = queryBean.getTotalRecord();
   	int recordStart = queryBean.getRecordStart();
   	int recordEnd = queryBean.getRecordEnd();
   	int mainPage = queryBean.getMainPage();
   	int mainPage1 = queryBean.getMainPage1();
    String mainEnterOrg= queryBean.getMainEnterOrg();
    String mainOwnerShip= queryBean.getMainOwnerShip();
    String mainCaseNo= queryBean.getMainCaseNo();
    String mainDifferenceKind= queryBean.getMainDifferenceKind();
   	
   	String strField = "<input type='hidden' name='pageSize' value='" + pageSize + "'><input type='hidden' name='currentPage' value='" + currentPage + "'><input type='hidden' name='mainPage' value='" + mainPage + "'><input type='hidden' name='mainPage1' value='" + mainPage1 + "'><input type='hidden' name='mainEnterOrg' value='" + mainEnterOrg + "'><input type='hidden' name='mainOwnerShip' value='" + mainOwnerShip + "'><input type='hidden' name='mainCaseNo' value='" + mainCaseNo + "'><input type='hidden' name='mainDifferenceKind' value='" + mainDifferenceKind + "'><input type='hidden' name='listContainerActiveRowId' value='" + queryBean.getListContainerActiveRowId() + "'>";
    if((pageSize<=0) || (totalRecord<=0) || (totalPage<=1)) {
    	out.println(strField);
    	return;
    }
%>
	<input type="hidden" name="currentPageSize" value="<%=pageSize%>">
	<input type="hidden" name="currentPage" value="<%=currentPage%>"> 
	<input type="hidden" name="mainPage" value="<%=mainPage%>">
	<input type="hidden" name="mainPage1" value="<%=mainPage1%>">
	<input type="hidden" name="mainEnterOrg" value="<%=mainEnterOrg%>">
	<input type="hidden" name="mainOwnerShip" value="<%=mainOwnerShip%>">
	<input type="hidden" name="mainCaseNo" value="<%=mainCaseNo%>">
	<input type="hidden" name="mainDifferenceKind" value="<%=mainDifferenceKind%>"> 
	<input type="hidden" name="listContainerActiveRowId" value="<%=queryBean.getListContainerActiveRowId()%>">	
    <table width="99%" border="0" align="center">
      <tr>
        <td class="querytext" valign="middle"><span class="pagetext" >一頁<input type="text" class="field_Q" name="pageSize" maxlength="4" size="4" value="<%=pageSize%>" style="font-size: 12px;">筆, 共<%=totalRecord%>筆 第<%=recordStart%>到<%=recordEnd%>筆</span></td>
        
        <td class="querytext" valign="middle">
        <div align="right"><span class="pagetext" >
        			  共<%=totalPage%>頁</span>
				   [ <a class="pagetext" href="javascript: gotoPage(1)">第一頁</a>
				   <%
					for(int i=0;i<totalPage;i++)
					{
						if(i==currentPage-1)
						{
							out.println("<span class=\"pagetext\" style=\"color:red\">" + Integer.toString(i+1) + "</span>");

						}
						else if((currentPage-6<=i) && (i<=currentPage+5))
						{
							out.println("<a class=\"pagetext\" href=\"javascript:javascript: gotoPage("+(i+1)+")\">" + (i+1)+"</a>");
						}

					}
    
				%>
				<a class="pagetext" href="javascript: gotoPage(<%=totalPage%>)">末頁</a> ][
<%				
                if(currentPage>1){
%>
                <a class="pagetext" href="javascript: gotoPage(<%=currentPage-1%>)">上一頁</a>
<%
                }
				if(currentPage<totalPage){
%>
                <a class="pagetext" href="javascript: gotoPage(<%=currentPage+1%>)">下一頁</a>
<%			    
				}  
%>        		
                ]
	 	</div>
	 	</td>
      </tr>
    </table>
<%
    }catch(Exception x){
        x.printStackTrace();
    }

%>
