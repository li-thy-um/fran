<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:modal_input_form label_define="" module="new_cashier" field_define="
     ,E-Mail:,user.email&
     ,Name:,user.name&
     ,Shop:,user.shop_id" title="New Cashier" app="keeper">
</l:modal_input_form>