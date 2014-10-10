<%@ tag body-content="scriptless" %>

<%@ attribute name="app" required="true" type="java.lang.String" %>
<%@ attribute name="module" required="true" type="java.lang.String" %>

<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:script src="${app}/${module}.js"></l:script>

<div id="${module}_modal" class="common-modal modal hide fade"
	style="width:650px;"
>

	<div class="modal-header">
	  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
        &times;
      </button>
      <ul class="inline center">
      <li class="date-form " style="float:center;">      
        <a href="#" class="btn btn-act" data-act="subtract" style="text-align:center;">
        	<i class="icon-chevron-left"></i>
        </a>
        <b class="date"></b>
        <a href="#" class="btn btn-act" data-act="add" style="text-align:center;">
        	<i class="icon-chevron-right"></i>
        </a>
        <form class="hide"></form>
	  </li>
	  </ul>
	</div>
	<div class="modal-body basic" style="max-height:none">
	    <div class="hide center"><img src="APP/assets/img/loading.gif"/></div>
		<div class="chart-area" style="margin-top:10px;"></div>
	</div>
</div>