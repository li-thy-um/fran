<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>
   
<l:script src="inventory/shop_inven_list.js"></l:script>

<div id="shop_inven_list">
	<div class="img-polaroid img-rounded" style="margin-top:10px;">
	  <div class="loading" style="margin-bottom:10px">Loading...</div>
	  <div id="shop_inven_grid" style="margin-top:20px" ></div>
	</div>
</div>

<div id="product_inven_list" class="hide">
	<jsp:include page="product_inven_list.jsp" flush="true"/>
</div>
