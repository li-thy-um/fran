<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:modal_input_form label_define="" module="new_product" field_define="
	     ,Product Code:,product.cd&
	     ,Name:,product.name&
	     ,Price:,product.price&
	     ,Description:,product.detail" title="New Product" app="manager">
</l:modal_input_form>