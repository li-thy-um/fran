<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:script src="manager/shop_list.js"></l:script>
<jsp:include page="new_shop.jsp" flush="true"/>

<div>
	<a href="#" class="btn btn-primary" id="new_shop">
      <i class="icon-plus icon-white"></i>
      Add New Shop
    </a>
</div>
<div class="img-polaroid img-rounded" style="margin-top:10px;">
  <div class="loading" style="margin-bottom:10px">Loading...</div>
  <div id="shop_basic_grid" style="margin-top:20px" ></div>
</div>
