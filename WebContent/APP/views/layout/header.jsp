<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>

<% Boolean login = (Boolean)(request.getSession().getAttribute("user") != null); %>

<header class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
	<div class="navbar-container">
	  <a href="#" id="logo">Fran</a>
      <% if(login){ %>
        <jsp:include page="../${user._role}/${user._role}_menu.jsp" flush="true"/>
      <%}%>
	</div>
  </div>
</header>
