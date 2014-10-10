<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<div class="tabbable">
  <ul class="nav nav-tabs">
    <li class="active" id="shop_sales_tag">
       <a href="#shop_sales" data-toggle="tab" > Sales </a>
    </li>
    <li id="shop_inventory_tag">
       <a href="#shop_inventory" data-toggle="tab" > Inventory </a>
    </li>
  </ul>
  <div class="tab-content">
     <div id="shop_sales" class="tab-pane active">
     	<jsp:include page="../manager/shop_sales.jsp" flush="true"/>
     </div>
     <div id="shop_inventory" class="tab-pane">
        <jsp:include page="../inventory/shop_inven_list.jsp" flush="true"/>
     </div>
  </div>
</div>