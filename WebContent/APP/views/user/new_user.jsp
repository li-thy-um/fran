<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:modal_input_form label_define="" module="new_user" field_define="
     ,E-Mail:,user.email&
     ,Name:,user.name" title="New Account" app="user">
</l:modal_input_form>