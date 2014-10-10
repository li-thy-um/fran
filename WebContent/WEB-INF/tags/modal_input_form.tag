<%@ tag body-content="scriptless" %>
<%@ attribute name="app" required="true" type="java.lang.String" %>
<%@ attribute name="module" required="true" type="java.lang.String" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="label_define" required="true" type="java.lang.String" %>
<%@ attribute name="field_define" required="true" type="java.lang.String" %>

<% String[] labels = label_define.split("&"); %>
<% String[] fields = field_define.split("&"); %>

<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:modal module="${module}"  title="${title}" app="${app}" footer="confirm">
	<l:input_form module="${module}"
		   label_define = "${label_define}"
		   field_define = "${field_define}"
		 />
</l:modal>