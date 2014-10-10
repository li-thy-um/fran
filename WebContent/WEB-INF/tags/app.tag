<%@ tag body-content="scriptless" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="path" required="true" type="java.lang.String" %>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>${title}</title>
    <jsp:include page="/APP/views/com/import.jsp" flush="true"/>
  </head>
  <body>
    <jsp:include page="/APP/views/layout/header.jsp" flush="true"/>
    <div class="app-container container">
      <div class="row-fluid">
        <div id="msg"></div>
        <jsp:include page="/APP/views/user/change_psw.jsp" flush="true"/>
        <jsp:include page="/APP/views/${path}" flush="true"/>
      </div>
    </div>
  </body>
</html>