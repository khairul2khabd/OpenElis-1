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

<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<bean:define id="formName" value='<%= (String)request.getAttribute(IActionConstants.FORM_NAME) %>'/>

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

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap.min.css" />

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/custom.css" />

<script type="text/javascript" src="<%=basePath%>scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/jquery.paginate.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/jquery.paginate.min.js"></script>
<%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">--%>
<%--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>--%>
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>--%>

<html>
<head>
    <title>Report Print</title>
</head>
<body>

<logic:iterate name="panelList" id="order">
    <button class="btn btn-success btn-lg" type="button"> <bean:write name="order" property="panelName"/> </button>
</logic:iterate>

<logic:iterate name="analysisList" id="order">
    <button   type="button">
          <bean:write name="order" property="test.testName"/>
    </button>
</logic:iterate>
<br><br>

<div id="divprint">
<table style="border-collapse: collapse;  border-radius: 10px; box-shadow: 0 0 0 1px #666; color: black" width="99%">
    <tr style="line-height:10px;">
        <td colspan="3" height="25"
            style="text-align:center; font-size:16px; padding-left:10px; width:18%; font-style: bold; border-bottom: 1px solid black ">
            Hematology Department
        </td>
    </tr>

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
            ID
            <span style="font-style:normal;">: <bean:write name="<%=formName%>" property="patient.id"/> </span></td>
        <td><span style=" font-size:14px;"> Bill No. : zsdfdsf</span></td>
        <td><span style=" font-size:14px;"> Lab No. : asdf</span></td>
    </tr>

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
            Name
            <span style="font-style:normal;">:
            <bean:write name="<%=formName%>" property="patient.person.firstName"/>
            <bean:write name="<%=formName%>" property="patient.person.middleName"/>
            <bean:write name="<%=formName%>" property="patient.person.lastName"/>
            </span></td>
        <td><span style=" font-size:14px;"> Age : <bean:write name="<%=formName%>"
                                                              property="sample.receivedDateForDisplay"/></span></td>
        <td><span style=" font-size:14px;"> Sex : <bean:write name="<%=formName%>" property="patient.gender"/> </span>
        </td>
    </tr>

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Contact
            No :
            <%--<bean:write name="<%=formName%>" property="patient.person.firstName"/>--%>
        </td>
        <td><span style=" font-size:14px;"> NID :  </span></td>
    </tr>

</table>
    </br>

    <table style="border-collapse: collapse;  border-radius: 0px; box-shadow: 0 0 0 1px #666; color: black" width="99%">


        <tr>
            <th class="line1" style="text-align: center">Test</th>
            <th class="line1" style="text-align: center">Result</th>
            <th class="line1" style="text-align: center">Referance Value</th>
        </tr>

        <tr style="line-height:10px;">
            <td height="25"
                style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
                ID
                <span style="font-style:normal;">: <bean:write name="<%=formName%>" property="patient.id"/> </span></td>
            <td><span style=" font-size:14px;"> Bill No. : zsdfdsf</span></td>
            <td><span style=" font-size:14px;"> Lab No. : asdf</span></td>
        </tr>

        <tr style="line-height:10px;">
            <td height="25"
                style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
                Name
                <span style="font-style:normal;">:
            <bean:write name="<%=formName%>" property="patient.person.firstName"/>
            <bean:write name="<%=formName%>" property="patient.person.middleName"/>
            <bean:write name="<%=formName%>" property="patient.person.lastName"/>
            </span></td>
            <td><span style=" font-size:14px;"> Age : <bean:write name="<%=formName%>"
                                                                  property="sample.receivedDateForDisplay"/></span></td>
            <td><span style=" font-size:14px;"> Sex : <bean:write name="<%=formName%>" property="patient.gender"/> </span>
            </td>
        </tr>

        <tr style="line-height:10px;">
            <td height="25"
                style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Contact
                No :
                <%--<bean:write name="<%=formName%>" property="patient.person.firstName"/>--%>
            </td>
            <td><span style=" font-size:14px;"> NID :  </span></td>
        </tr>

    </table>
<h4 class="table-content-design">Total Count</h4>
<table style="border-collapse: collapse;  border-radius: 0px; box-shadow: 0 0 0 1px #666; color: black" width="99%; background-color:rgba(0, 0, 0, 0);">

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
            ID
            <span style="font-style:normal;">: <bean:write name="<%=formName%>" property="patient.id"/> </span></td>
        <td><span style=" font-size:14px;"> Bill No. : zsdfdsf</span></td>
        <td><span style=" font-size:14px;"> Lab No. : asdf</span></td>
    </tr>

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
            Name
            <span style="font-style:normal;">:
            <bean:write name="<%=formName%>" property="patient.person.firstName"/>
            <bean:write name="<%=formName%>" property="patient.person.middleName"/>
            <bean:write name="<%=formName%>" property="patient.person.lastName"/>
            </span></td>
        <td><span style=" font-size:14px;"> Age : <bean:write name="<%=formName%>"
                                                              property="sample.receivedDateForDisplay"/></span></td>
        <td><span style=" font-size:14px;"> Sex : <bean:write name="<%=formName%>" property="patient.gender"/> </span>
        </td>
    </tr>

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Contact
            No :
            <%--<bean:write name="<%=formName%>" property="patient.person.firstName"/>--%>
        </td>
        <td><span style=" font-size:14px;"> NID :  </span></td>
    </tr>

</table>

<h4 class="table-content-design">DC of WBC</h4>
<table style="border-collapse: collapse;  border-radius: 0px; box-shadow: 0 0 0 1px #666; color: black" width="99%; background-color:rgba(0, 0, 0, 0);">

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
            ID
            <span style="font-style:normal;">: <bean:write name="<%=formName%>" property="patient.id"/> </span></td>
        <td><span style=" font-size:14px;"> Bill No. : zsdfdsf</span></td>
        <td><span style=" font-size:14px;"> Lab No. : asdf</span></td>
    </tr>

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
            Name
            <span style="font-style:normal;">:
            <bean:write name="<%=formName%>" property="patient.person.firstName"/>
            <bean:write name="<%=formName%>" property="patient.person.middleName"/>
            <bean:write name="<%=formName%>" property="patient.person.lastName"/>
            </span></td>
        <td><span style=" font-size:14px;"> Age : <bean:write name="<%=formName%>"
                                                              property="sample.receivedDateForDisplay"/></span></td>
        <td><span style=" font-size:14px;"> Sex : <bean:write name="<%=formName%>" property="patient.gender"/> </span>
        </td>
    </tr>

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Contact
            No :
            <%--<bean:write name="<%=formName%>" property="patient.person.firstName"/>--%>
        </td>
        <td><span style=" font-size:14px;"> NID :  </span></td>
    </tr>

</table>


<h4 class="table-content-design">Red Cell Indices</h4>
<table style="border-collapse: collapse;  border-radius: 0px; box-shadow: 0 0 0 1px #666; color: black" width="99%; background-color:rgba(0, 0, 0, 0);">

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
            ID
            <span style="font-style:normal;">: <bean:write name="<%=formName%>" property="patient.id"/> </span></td>
        <td><span style=" font-size:14px;"> Bill No. : zsdfdsf</span></td>
        <td><span style=" font-size:14px;"> Lab No. : asdf</span></td>
    </tr>

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
            Name
            <span style="font-style:normal;">:
            <bean:write name="<%=formName%>" property="patient.person.firstName"/>
            <bean:write name="<%=formName%>" property="patient.person.middleName"/>
            <bean:write name="<%=formName%>" property="patient.person.lastName"/>
            </span></td>
        <td><span style=" font-size:14px;"> Age : <bean:write name="<%=formName%>"
                                                              property="sample.receivedDateForDisplay"/></span></td>
        <td><span style=" font-size:14px;"> Sex : <bean:write name="<%=formName%>" property="patient.gender"/> </span>
        </td>
    </tr>

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Contact
            No :
            <%--<bean:write name="<%=formName%>" property="patient.person.firstName"/>--%>
        </td>
        <td><span style=" font-size:14px;"> NID :  </span></td>
    </tr>

</table>



<h4 class="table-content-design">Absolute Count of WBC's</h4>
<table style="border-collapse: collapse;  border-radius: 0px; box-shadow: 0 0 0 1px #666; color: black" width="99%; background-color:rgba(0, 0, 0, 0);">

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
            ID
            <span style="font-style:normal;">: <bean:write name="<%=formName%>" property="patient.id"/> </span></td>
        <td><span style=" font-size:14px;"> Bill No. : zsdfdsf</span></td>
        <td><span style=" font-size:14px;"> Lab No. : asdf</span></td>
    </tr>

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
            Name
            <span style="font-style:normal;">:
            <bean:write name="<%=formName%>" property="patient.person.firstName"/>
            <bean:write name="<%=formName%>" property="patient.person.middleName"/>
            <bean:write name="<%=formName%>" property="patient.person.lastName"/>
            </span></td>
        <td><span style=" font-size:14px;"> Age : <bean:write name="<%=formName%>"
                                                              property="sample.receivedDateForDisplay"/></span></td>
        <td><span style=" font-size:14px;"> Sex : <bean:write name="<%=formName%>" property="patient.gender"/> </span>
        </td>
    </tr>

    <tr style="line-height:10px;">
        <td height="25"
            style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Contact
            No :
            <%--<bean:write name="<%=formName%>" property="patient.person.firstName"/>--%>
        </td>
        <td><span style=" font-size:14px;"> NID :  </span></td>
    </tr>



</table>


    <h4 class="table-content-design">Peripheral Blood Film (PBF) Examination</h4>
    <table style="border-collapse: collapse;  border-radius: 0px; box-shadow: 0 0 0 1px #666; color: black" width="99%; background-color:rgba(0, 0, 0, 0);">

        <tr style="line-height:10px;">
            <td height="25"
                style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
                ID
                <span style="font-style:normal;">: <bean:write name="<%=formName%>" property="patient.id"/> </span></td>
        </tr>
        <tr style="line-height:10px;">
            <td height="25"
                style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
                ID
                <span style="font-style:normal;">: <bean:write name="<%=formName%>" property="patient.id"/> </span></td>
        </tr>

        <tr style="line-height:10px;">
            <td height="25"
                style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
                ID
                <span style="font-style:normal;">: <bean:write name="<%=formName%>" property="patient.id"/> </span></td>
        </tr>


    </table>

    <h4 class="table-content-design">Notes</h4>
    <table style="border-collapse: collapse;  border-radius: 0px; box-shadow: 0 0 0 1px #666; color: black" width="99%; background-color:rgba(0, 0, 0, 0);">

        <tr style="line-height:10px;">
            <td height="25"
                style="text-align:left; font-weight:bold;  font-size:13px; padding-left:10px;  font-style: bold; ">Patient
                ID
                <span style="font-style:normal;">: <bean:write name="<%=formName%>" property="patient.id"/> </span></td>
        </tr>


    </table>



</div>
<br>

<input type="button" align="middle" value="Print" onclick="printReport()"  />

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
