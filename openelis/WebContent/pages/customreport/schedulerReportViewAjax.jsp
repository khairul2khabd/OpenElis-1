<%@ page import="us.mn.state.health.lims.common.action.IActionConstants" %><%--
  Created by IntelliJ IDEA.
  User: khair
  Date: 01/08/2021
  Time: 11:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="nested" uri="/tags/struts-nested" %>

<%!
    String path = "";
    String basePath = "";

%>

<%
    path = request.getContextPath();
    basePath = path + "/";
%>

<bean:define id="formName" value='<%= (String)request.getAttribute(IActionConstants.FORM_NAME) %>'/>

<link rel="stylesheet" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css"/>

<script>
    $(document).ready(function() {
        $('#example').DataTable();
        $("th").removeClass('sorting');
        $("th").removeClass('sorting_asc');
        $("#example_length").addClass("goRight");
        $("#example_length").removeClass("dataTables_length");



        $("#example_filter").addClass("goLeft");
        $("#example_filter").removeClass("dataTables_filter");

    } );

</script>


<%--
<script>
    function myFunction() {
        var input, filter, table, tr, td, i, txtValue,txtValue1;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("example");
        tr = table.getElementsByTagName("tr");
        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[0];
            td1 = tr[i].getElementsByTagName("td")[0];
            if (td) {
                txtValue = td.textContent || td.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }

            else if(td1){

                txtValue1 = td1.textContent || td1.innerText;
                if (txtValue1.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }

            }


        }
    }
</script>
--%>



<%--<input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for names.." title="Type in a name">--%>


<%--<bean:define id="allSampleList" name="<%=formName%>" property="sampleList" />--%>

<table class="display" style="color: #000;" id="example">
    <thead>

   <%-- <th>Id</th>
    <th>Product Id</th>--%>
    <th>Product Name</th>
    <th>Invoice Number</th>
    <th>Date ID</th>
    <th>Patient ID</th>
    <th>Patient UUID</th>


    </thead>
    <tbody>

    <c:forEach items="${odooList}" var="report">
        <tr>
           <%-- <td><c:out value="${report.id}"/></td>
            <td><c:out value="${report.productId}"/></td>--%>
            <td><c:out value="${report.productName}"/></td>
            <td><c:out value="${report.invoiceNumber}"/></td>
            <td><c:out value="${report.date}"/></td>
            <td><c:out value="${report.patientId}"/></td>
            <td><c:out value="${report.patientUUID}"/></td>

        </tr>
    </c:forEach>

    </tbody>
</table>
<%--
commit--%>
