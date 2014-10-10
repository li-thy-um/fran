<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>


<l:script src="manager/manager.js"></l:script>

<div class="tabbable tabs-left">
  <ul class="nav nav-tabs">
  	<li class="active app-tag" id="keeper_tag" data-click=".refresh-user" >
      <a href="#keeper" data-toggle="tab"> Keeper </a>
    </li>
    <li class="app-tag" id="shop_tag" data-click="#shop_basic_tag">
      <a href="#shop" data-toggle="tab"> Shop </a>
    </li>
    <li class="app-tag" id="product_tag" data-click="#product_basic_tag">
       <a href="#product" data-toggle="tab" > Product </a>
    </li>
  </ul>
  <div class="tab-content">
     <div id="keeper" class="tab-pane active">
        <jsp:include page="keeper_manage.jsp" flush="true"/>
     </div>
     <div id="shop" class="tab-pane">
        <jsp:include page="shop_manage.jsp" flush="true"/>
     </div>
     <div id="product" class="tab-pane">
        <jsp:include page="product_manage.jsp" flush="true"/>
     </div>
  </div>
</div>
