<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<div class="tabbable">
  <ul class="nav nav-tabs">
    <li class="active refresh-user" id="cashier_basic_tag">
       <a href="#cashier_basic" data-toggle="tab" > Account </a>
    </li>
    <li id="user_sales_tag">
       <a href="#user_sales" data-toggle="tab" > Sales </a>
    </li>
  </ul>
  <div class="tab-content">
     <div id="cashier_basic" class="tab-pane active">
         <jsp:include page="cashier_list.jsp" flush="true"/>
     </div>
     <div id="user_sales" class="tab-pane">
         <jsp:include page="../user/user_sales.jsp" flush="true"/>
     </div>
  </div>
</div>