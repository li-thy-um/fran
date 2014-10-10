<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:script src="manager/shop_sales.js"></l:script>

<div id="shop_sales_list">
	<div class="img-polaroid img-rounded" style="margin-top:10px;">
	  <div class="loading" style="margin-bottom:10px">Loading...</div>
	  <div id="shop_sales_grid" style="margin-top:20px" ></div>
	</div>
</div>

<div id="shop_sale_detail" class="hide">
     <jsp:include page="shop_sale_detail.jsp" flush="true"/>
</div>
