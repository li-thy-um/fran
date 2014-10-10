<%@ tag body-content="scriptless" %>
<%@ attribute name="app" required="true" type="java.lang.String" %>
<%@ attribute name="module" required="true" type="java.lang.String" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="footer" required="true" type="java.lang.String" %>
<%@ attribute name="header" type="java.lang.String" %>


<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:script src="${app}/${module}.js"></l:script>

<div id="${module}_modal" class="common-modal modal hide fade">

	<div class="modal-header">
	  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
        &times;
      </button>
      <h3>${title}</h3>
	</div>
	<div class="modal-body basic">
		<jsp:doBody/>
	</div>
	<% if( "confirm".equals(footer) ){%>
		<div class="modal-footer">
		  <l:submit_btn label='<i class="icon-ok icon-white"></i>'/>
		  <a href="#" class="btn btn-cancel"><i class='icon-remove '></i></a>
		</div>
	<%}else if( "none".equals(footer) ){%>

	<%} %>
</div>