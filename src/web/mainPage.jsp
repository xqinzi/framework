<%@ page errorPage="/errorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://www.concepting.com.br/framework/tags" prefix="concepting"%>

<concepting:page title="${loginSession.systemModule}" encoding="iso-8859-1">
	<div class="panel">
		<jsp:include page="/headerPage.jsp"/>
		<table class="content">
			<tr>
				<td align="center">
					<concepting:image value="/images/warningIcon.gif"/> TODO: Implementar tela principal!
	       		</td>
	       	</tr>
	    </table>
		<jsp:include page="/footerPage.jsp"/>
	</div>
</concepting:page>				