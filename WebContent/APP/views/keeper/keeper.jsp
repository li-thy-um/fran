<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:script src="keeper/keeper.js"></l:script>

<div class="tabbable tabs-left">
  <ul class="nav nav-tabs">
    <li class="active app-tag" id="cashier_tag" data-click=".refresh-user">
       <a href="#cashier" data-toggle="tab" > Cashier </a>
    </li>
    <li class="app-tag" id="shop_tag" data-click="#shop_sales_tag">
      <a href="#shop" data-toggle="tab"> Shop </a>
    </li>
  </ul>
  <div class="tab-content">
     <div id="cashier" class="tab-pane active">
        <jsp:include page="cashier_manage.jsp" flush="true"/>
     </div>
     <div id="shop" class="tab-pane">
     	<jsp:include page="shop_manage.jsp" flush="true"/>
     </div>
  </div>
</div>