<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>


<l:script src="cashier/cashier.js"></l:script>

<jsp:include page="new_transaction.jsp" flush="true"/>


<div class="row-fluid">
	<div class="input-prepend btn-block span12">
		<span class="add-on"><i class="icon-search"></i></span>
		<input id="product" style="width:90%" autocomplete="off" type="text"
		 placeholder="Please input product bar code( At least 3 digitals ) to start a transaction...">
	</div>

</div>

<div class="loading hide" style="margin-bottom:10px">Loading...</div>
<div class="not-found hide" style="margin-bottom:10px">Sorry, not Found.</div>


<div>
<table class="table table-hover">
<thead class="hide">
<tr>
   <th> Product Name </th>
   <th> Bar Code </th>
   <th> Price </th>
   <th style='text-align:center;'> Amount </th>
   <th style='text-align:right;'> Total Price </th>
   <th></th>

   </tr>
</thead>

<tbody id="product_list">

</tbody>
</table>
</div>
<div class="row-fluid">
<div class="span2 offset10 hide" id="submit_trans">
		<l:submit_btn
			label='<i class="icon-shopping-cart icon-white"></i> <span class="label label-inverse" id="total">Total: 0.00</span>'
			clz="btn-block"
		/>
</div>
</div>
