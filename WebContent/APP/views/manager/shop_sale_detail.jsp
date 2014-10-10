<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:script src="manager/shop_sale_detail.js"/>

<l:modal_chart module="shop_sale_chart" app="manager" />
<l:modal_chart module="shop_product_sale_chart" app="manager" />

<div>
	<a href="#" class="btn btn-primary" id="back_shop_sales">
      <i class="icon-arrow-left icon-white"></i>
      Back
    </a>
    <a href="#" class="btn btn-primary" id="shop_sale_chart">
      <i class="icon-search icon-white"></i>
      Shop Sales Chart
    </a>	
</div>
<div class="img-polaroid img-rounded" style="margin-top:10px;">
  <div class="loading" style="margin-bottom:10px">Loading...</div>
  <div id="shop_product_sales_grid" style="margin-top:20px" ></div>
</div>