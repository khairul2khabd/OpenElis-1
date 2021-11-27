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
<bean:define id="formName" value='<%= (String)request.getAttribute(IActionConstants.FORM_NAME) %>'/>

<%--<bean:define id="allSampleList" name="<%=formName%>" property="sampleList" />--%>

<table class="table table-bordered black" style="color: #000;">
    <thead>
    <th>Sl No</th>
    <th>Accession No</th>
    <th>Patient ID</th>
    <th>Patient Name</th>
    <th>Test</th>
    <th>Status</th>
    <th>Action</th>
    </thead>
    <tbody>

    <c:forEach items="${labTests}" var="lab" varStatus="index">
        <tr>
            <td><c:out value="${index.count}"/></td>
            <td><c:out value="${lab.accessionNumber}"/></td>
            <td><c:out value="${lab.stNumber}"/></td>
            <td><c:out value="${lab.patientName}"/></td>
            <td><c:out value="${lab.testName}"/> <> <c:out value="${lab.sampleItemId}"/></td>
            <td>
                <c:out value="${lab.sampleStatus}"/> <> <c:out value="${lab.analysisStatus}"/>
                <c:if test="${lab.analysisStatus eq '4' && lab.accessionNumber eq null}">
                    <span class="badge badge-success btn-xs"> Sample Not Collect </span>
                </c:if>
                <c:if test="${lab.analysisStatus eq '4' && lab.accessionNumber ne null}">
                    <span class="badge badge-info btn-xs"> Sample Collected  </span>
                </c:if>
                <c:if test="${lab.analysisStatus != '4' && lab.analysisStatus != '6' && lab.analysisStatus != '16'}">
                    <span class="badge badge-primary btn-xs"> Partial Pending </span>
                </c:if>

                <c:if test="${lab.analysisStatus == '16'}">
                    <span class="badge badge-warning btn-xs"> Validation Pending </span>
                </c:if>

                <c:if test="${lab.analysisStatus eq '6'}">
                    <span class="badge badge-success btn-xs"> Finalized </span>
                </c:if>
            </td>
            <td>
                <c:if test="${lab.analysisStatus eq '4' && lab.accessionNumber eq null}">
                    <a class="btn btn-link btn-sm" id='result'
                       href="SamplePatientEntry.do?id=<c:out value="${lab.sampleId}"/>&patientId=<c:out value="${lab.stNumber}"/> ">
                        Sample Collect
                    </a>
                </c:if>
                <c:if test="${lab.analysisStatus eq '4' && lab.accessionNumber ne null}">
                    <a class="btn btn-link btn-sm" id='result'
                       href="AccessionResults.do?accessionNumber=<c:out value="${lab.accessionNumber}"/>&referer=LabDashboard">Enter
                        Result</a>
                </c:if>
                <c:if test="${lab.analysisStatus != '4' && lab.analysisStatus != '6' && lab.analysisStatus != '16'}">
                    <a class="btn btn-link btn-sm" id='result'
                       href="AccessionResults.do?accessionNumber=<c:out value="${lab.accessionNumber}"/>&referer=LabDashboard">Enter
                        Result</a>
                </c:if>

                <c:if test="${lab.analysisStatus == '16'}">
                    <a class="btn btn-link btn-sm" id='result'
                       href="ResultValidationForAccessionNumber.do?accessionNumber=<c:out value="${lab.accessionNumber}"/>&patientId=<c:out value="${lab.stNumber}"/>&referer=LabDashboard&type=&test= ">
                        Validate
                    </a>
                </c:if>

                <c:if test="${lab.analysisStatus eq '6'}">
                    <button class="btn btn-success btn-xs" type="button"
                            onclick="reportView( '<c:out value="${lab.accessionNumber}"/>', '<c:out
                                    value="${lab.sampleItemId}"/>' )">View
                    </button>
                </c:if>
            </td>
        </tr>
    </c:forEach>

    </tbody>
</table>


<script type="text/javascript">

    jQuery(document).ready(function () {

    })

    function reportView(accessionNumber, sampleItemId) {
        var dep = jQuery("#department").val();
        jQuery.ajax({
            type: 'GET',
            url: 'AjaxReportView.do',
            data: {
                accessionNumber: accessionNumber,
                sampleItemId: sampleItemId,
                dep:dep
            },
            success: function (response) {
                alert(response);
                jQuery("#responseView").html("");
                jQuery("#reportView").html(response);
            }
        });
    }


</script>