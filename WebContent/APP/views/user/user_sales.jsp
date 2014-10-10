<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:script src="user/user_sales.js"></l:script>

<l:modal_chart module="user_sale_chart" app="user" />

<div class="img-polaroid img-rounded" style="margin-top:10px;">
  <div class="loading" style="margin-bottom:10px">Loading...</div>
  <div id="user_sales_grid" style="margin-top:20px" ></div>
</div>
