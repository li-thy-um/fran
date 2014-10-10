<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>


<l:script src="user/login.js"></l:script>

<h1>Welcome to FRAN</h1>
<div class="container login" style="width:500px;float:center;padding-top:60px; ">
<l:input_form module="login"
   label_define = ""
   field_define = "
     ,E-Mail:,user.email&
     ,Password:,user.psw"
/>

<div class="pull-right">
    <l:submit_btn label='Login' clz="btn-large"/>
</div>
</div>