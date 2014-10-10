<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:modal_input_form label_define="" module="new_shop" field_define="
	     ,Name:,shop.name&
	     ,Address:,shop.address&
	     ,Keeper:,shop.keeper_id&
	     ,Description:,shop.detail" title="New Shop" app="manager">
</l:modal_input_form>