<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<div class="tabbable">
  <ul class="nav nav-tabs">
    <li class="active" id="product_basic_tag">
       <a href="#product_basic" data-toggle="tab" > Basic </a>
    </li>
    <li id="product_sales_tag">
      <a href="#product_sales" data-toggle="tab"> Sales </a>
    </li>
  </ul>
  <div class="tab-content">
     <div id="product_basic" class="tab-pane active">
        <jsp:include page="product_list.jsp" flush="true"/>
     </div>
     <div id="product_sales" class="tab-pane">
        <jsp:include page="product_sales.jsp" flush="true"/>
     </div>
  </div>
</div>