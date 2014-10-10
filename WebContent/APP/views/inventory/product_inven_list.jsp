<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<l:script src="inventory/product_inven_list.js"></l:script>

<div>
	<a href="#" class="btn btn-primary" id="back_shop_inven">
      <i class="icon-arrow-left icon-white"></i>
      Back
    </a>
    <c:if test="${user._role eq 'keeper' }">
    <jsp:include page="new_inventory.jsp" flush="true"/>
    <a href="#" class="btn btn-primary" id="new_product_inven">
      <i class="icon-plus icon-white"></i>
      New Inventory
    </a>	
    </c:if>
</div>
<div class="img-polaroid img-rounded" style="margin-top:10px;">
	  <div class="loading" style="margin-bottom:10px">Loading...</div>
	  <div id="product_inven_grid" style="margin-top:20px" ></div>
</div>