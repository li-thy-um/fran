<%@ tag body-content="scriptless" %>
<%@ attribute name="module" required="true" type="java.lang.String" %>
<%@ attribute name="label_define" required="true" type="java.lang.String" %>
<%@ attribute name="field_define" required="true" type="java.lang.String" %>

<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>

<% String[] labels = label_define.split("&"); %>
<% String[] fields = field_define.split("&"); %>

<table class="${module} table table-bordered table-striped">
  <tbody>

  <% if( !( "".equals(label_define)) ) {%>
    <% for( int i = 0; i < labels.length; i++ ){ %>
      <% String[] prop = labels[i].split(","); %>
      <tr>
        <td width="40%" style="text-align:right;"><b><%= prop[0] %></b></td>
        <td class="${module} mlabel " style="text-align:center;" name="<%= prop[1] %>"></td>
      </tr>
    <%} %>
  <%} %>

  <% if( !( "".equals(field_define)) ) {%>
    <% for( int i = 0; i < fields.length; i++ ){ %>
      <% String[] prop = fields[i].split(","); %>
      <tr class="<%= prop[0] %>">
        <td width="25%" style="text-align:right;"><b><%= prop[1] %></b></td>
        <td class="${module} mfield " style="text-align:center;" >
          <a href="#" name="<%= prop[2] %>"></a>
        </td>
      </tr>
    <%} %>
  <%} %>

  <% if( fields.length > 0 ){ %>
    <form class="hide"></form>
  <%} %>

  </tbody>
</table>