<%@ page language="java" import="config.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<div class="tabbable">
  <ul class="nav nav-tabs">
    <li class="active refresh-user" id="manager_basic_tag">
       <a href="manager_basic" data-toggle="tab" > Basic </a>
    </li>
  </ul>
  <div class="tab-content">
     <div id="manager_basic" class="tab-pane active">
        <jsp:include page="../user/user_list.jsp" flush="true"/>
     </div>
  </div>
</div>