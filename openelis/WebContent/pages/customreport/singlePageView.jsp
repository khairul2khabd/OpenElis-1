<%@ page import="us.mn.state.health.lims.common.action.IActionConstants" %>
<%@ page import="us.mn.state.health.lims.common.util.ConfigurationProperties" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: khair
  Date: 26/07/2021
  Time: 9:34 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
    String serverNow = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    Boolean alwaysValidate = ConfigurationProperties.getInstance().isPropertyValueEqual(ConfigurationProperties.Property.ALWAYS_VALIDATE_RESULTS, "true");
%>

<%
    path = request.getContextPath();
    basePath = path + "/";
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/custom.css" />
<bean:define id="formName" value='<%= (String)request.getAttribute(IActionConstants.FORM_NAME) %>'/>

<html>
<head>
    <title>Report Print</title>
</head>
<body>

<hr style="margin-top:0px">
<div id="divprint">
    <table style=" border-collapse: collapse; box-shadow: 0 0 0 1px #666; color: black"
           width="99%">
        <tr style="line-height:10px;">
            <td colspan="3" height="25"
                style="text-align:center; font-size:16px; padding-left:10px; width:18%; font-style: bold; border-bottom: 1px solid black ">
                <c:out value="${departmentName}"/>
            </td>
        </tr>

        <tr style="line-height:10px;">
            <td height="25"
                style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">
                Patient
                ID
                <span style="font-style:normal;">: <c:out value="${patientInfo.stNumber}"/> </span></td>
            <td><span style=" font-size:14px;"> Bill No. : zsdfdsf</span></td>
            <td><span style=" font-size:14px;"> Lab No. : asdf</span></td>
        </tr>

        <tr style="line-height:10px;">
            <td height="25"
                style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">
                Patient
                Name
                <span style="font-style:normal;">: <c:out value="${patientInfo.patientName}"/>
            </span></td>
            <td><span style=" font-size:14px;"> Age : <c:out value="${patientInfo.age}"/> </span></td>
            <td><span style=" font-size:14px;"> Sex : <c:out value="${patientInfo.gender}"/>  </span>
            </td>
        </tr>

        <tr style="line-height:10px;">
            <td height="25"
                style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">
                Contact
                No :
            </td>
            <td><span style=" font-size:14px;"> NID :  </span></td>
        </tr>
        <tr style="line-height:10px;">
            <td height="25"
                style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">
                Sample Collection Date : <c:out value="${sample.enteredDate}"/>
            </td>
            <td height="25"
                style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">
                Report Delivery Date : <c:out value="${sample.enteredDate}"/>
            </td>

        </tr>
    </table>
    <br>
    <table style="border-collapse: collapse;" width="99%";>
        <thead>

        <th>Test</th>
        <th> Result</th>
        <th> Unit</th>
        <th> Reference Range</th>

        </thead>
        <tbody style="border-bottom:1px solid black; " class="tdTest">
        <tr>
            <%--<td></td>--%>
        </tr>
        <c:forEach items="${analysisList}" var="ana" varStatus="index">

            <tr >
                <td class="pathology-border" style="width:350px;color: black"><c:out value="${ana.test.testName}"/> <> <c:out value="${ana.panel.panelName}"/></td>
                <td class="pathology-border" style="width: 150px;color: black">
                    <c:forEach items="${ana.results}" var="result">
                        <c:out value="${result.value}"/>
                    </c:forEach>
                </td>
                <td class="pathology-border" style=" width: 250px;color: black">
                    <c:out value="${ana.test.unitOfMeasure.unitOfMeasureName}"/>
                </td>
                <td class="pathology-border" style=" width: 100px;color: black">
                    <c:forEach items="${ana.results}" var="result">
                        <c:out value="${result.minNormal}"/> / <c:out value="${result.maxNormal}"/>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        </tbody>

        <tr>
            <td style="text-align:left; font-weight:bold;  font-size:18px; padding-left:5px;  font-style:bold; color: black;">Total Counts</td>
        </tr>


        <tbody style="border-bottom:1px solid black;  border-top:1px solid black;">


        <c:forEach items="${totalCount}" var="ana" varStatus="index">
            <tr>
                <td class="pathology-border"  style=" width:350px;color:black"><c:out value="${ana.testName}"/></td>

                <td class="pathology-border" style="width:150px;color: black">
                    <c:forEach items="${ana.results}" var="result">
                        <c:out value="${result.value}"/>
                    </c:forEach>
                </td>
                <td class="pathology-border" style="width:250px;color: black">
                    <c:forEach items="${ana.results}" var="result">
                        <c:out value="${result.value}"/>
                    </c:forEach>
                </td>
                <td class="pathology-border" style="width:300px;color: black">
                    <c:forEach items="${ana.results}" var="result">
                        <c:out value="${result.value}"/>
                    </c:forEach>
                </td>


            </tr>
        </c:forEach>

        </tbody>
    </table>

</div>
<br>

<input type="button" align="middle" value="Print" onclick="printReport()"/>

<script type="text/javascript">
    function printReport() {
        var printer = window.open('left=0', 'top=0', 'width=300,height=300');
        printer.document.open("text/html");
        printer.document.write(document.getElementById('divprint').innerHTML);
        printer.print();
        printer.document.close();
        printer.window.close();
    }
</script>

</body>
</html>