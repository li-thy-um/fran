<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<l:modal_input_form label_define="" module="change_psw" field_define="
     ,Old Password:,old_psw&
     ,New Password:,psw&
     ,Confirm Password:,pswc" title="Change Password" app="user">
</l:modal_input_form>