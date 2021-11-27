<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ page language="java"
         contentType="text/html; charset=utf-8"
         import="java.util.Date,
                 java.text.SimpleDateFormat,
                 us.mn.state.health.lims.common.action.IActionConstants" %>
<%@ page import="us.mn.state.health.lims.common.util.ConfigurationProperties" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/tags/sourceforge-ajax" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>

<%!
    String path = "";
    String basePath = "";
    String serverNow = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
    Boolean alwaysValidate = ConfigurationProperties.getInstance().isPropertyValueEqual(ConfigurationProperties.Property.ALWAYS_VALIDATE_RESULTS, "true");
%>

<%
    path = request.getContextPath();
    basePath = path + "/";
%>

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/custom.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap.min.css"/>
<script type="text/javascript" src="<%=basePath%>scripts/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/jquery_ui/jquery-ui-1.8.16.custom.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/jquery_ui/jquery.ui.tabs.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/slickgrid/dashboard.css"/>


<script type="text/javascript" src="<%=basePath%>scripts/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/jquerytable.min.js"></script>


<div id="todayStat">
    <table >
        <tr>
            <td> Collect Date :
                <input id="collectDate" type="date" name="date"/>
            </td>
            <td> Filter :
                <select name="select" id="selectId">
                    <option value="-1" selected>Select</option>
                    <option value="0">Will be sync</option>
                    <option value="1">Sync</option>
                    <option value="2">Synch?</option>

                </select>
            </td>
            <td>
                <select name="department" id="reportName" class="opdSelect1">
                    <option value="All">All  </option>
                    <c:forEach var="sec" items="${report}">
                        <option value="<c:out value="${sec.productName}"/>"><c:out value="${sec.productName}"/></option>
                    </c:forEach>
                </select>
            </td>





            <td>
                <button type="button" class="btn btn-info btn-sm" style="margin-top: -10px" onclick="getData()">Get</button>
            </td>
            <%--<td>--%>
            <%--<span class="badge badge-info btn-xs"> Department Wise List </span> &nbsp;--%>
            <%--<span class="badge badge-success btn-xs"> Single </span> &nbsp;--%>
            <%--<span class="badge badge-primary btn-xs"> ALL CS </span> &nbsp;--%>
            <%--<span class="badge badge-warning btn-xs"> Printed </span>--%>
            <%--</td>--%>
        </tr>
    </table>

</div>
<hr style="margin-top:0px">
<div id="responseView"></div>
<div id="reportView"></div>

<br>

<script type="text/javascript">

    jQuery(document).ready(function () {
        var todayy = new Date();
        var dd = String(todayy.getDate()).padStart(2, '0');
        var mm = String(todayy.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = todayy.getFullYear();

        todayy = yyyy + '-' + mm + '-' + dd;
        var clientNow = new Date('<%= serverNow %>');
        jQuery('#collectDate').val(todayy);
        getData();
        console.log(clientNow);
    })

    function getData() {
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = today.getFullYear();

        today = yyyy + '-' + mm + '-' + dd;


        jQuery("#responseView").show();
        var state=jQuery("#selectId").val();
        var name = jQuery("#reportName").val();
        var date = jQuery("#collectDate").val();
        if (date === null || date === "") {

            date=today;
            console.log("2:::::"+date);
            return false;
        }

        jQuery.ajax({
            type: 'GET',
            url: 'SchedulerReportOdoo.do',
            data: {
                state: state,
                name:name,
                date: date
            },
            success: function (response) {
                jQuery("#responseView").html(response);
                jQuery("#reportView").html("");
//                console.log(response);
            }
        });
    }


</script>

<%--commitn today--%>
<%--commitn today--%>