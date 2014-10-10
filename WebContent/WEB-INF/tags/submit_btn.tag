<%@ tag body-content="scriptless" %>
<%@ attribute name="label" required="true" type="java.lang.String" %>
<%@ attribute name="clz" required="false" type="java.lang.String" %>

<img class="hide" src="APP/assets/img/loading.gif"/>
<a href="#" class="btn btn-ok btn-inverse ${clz}">${label}</a>