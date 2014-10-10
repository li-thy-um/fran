<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:modal_input_form label_define="" module="new_inventory" field_define="
     ,Product:,inventory.product_id&
     ,Amount,inventory.amount" title="New Inventory" app="inventory">
</l:modal_input_form>