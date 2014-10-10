<%@ tag body-content="scriptless" %>
<%@ attribute name="action_define" required="false" type="java.lang.String" %>

<% String[] actions = {};%>
<% if( action_define != null ){ %>
<% actions = action_define.split("&"); %>
<%} %>

<ul class="nav">
	<li class="dropdown">
	  <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	      ${user._name} <b class="caret"></b>
	  </a>
	  <ul class="dropdown-menu"  >
	    <% for( int i = 0; i < actions.length; i++ ){ %>
        <% String[] prop = actions[i].split(","); %>
	    <li>
	      <a href="#" id="<%= prop[0] %>">
            <i class="<%= prop[1] %>"></i> <%= prop[2] %>
          </a>
        </li>
	    <%} %>
	    <li>
	      <a href="#" class="profile" >
	        <i class="icon-user"></i> Change Password
	      </a>
	    </li>
	    <li>
	      <a href="#" class="log-out">
	        <i class="icon-off"></i> Logout
	      </a>
	    </li>
	  </ul>
	</li>
</ul>
