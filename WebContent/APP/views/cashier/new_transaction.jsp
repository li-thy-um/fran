<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:modal_input_form module="new_transaction"
         label_define="
         Total Price:,total&
	     Change Back:,change"
         field_define=",Total Pay:,total.pay"
	     title="Comfirm Transation" app="cashier">
</l:modal_input_form>