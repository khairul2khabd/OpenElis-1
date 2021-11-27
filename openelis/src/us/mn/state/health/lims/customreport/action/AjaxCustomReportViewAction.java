package us.mn.state.health.lims.customreport.action;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.bahmni.feed.openelis.ObjectMapperRepository;
import us.mn.state.health.lims.analysis.dao.AnalysisDAO;
import us.mn.state.health.lims.analysis.daoimpl.AnalysisDAOImpl;
import us.mn.state.health.lims.analysis.valueholder.Analysis;
import us.mn.state.health.lims.common.action.BaseAction;
import us.mn.state.health.lims.customreport.valueholder.PatientInfo;
import us.mn.state.health.lims.patient.dao.PatientDAO;
import us.mn.state.health.lims.patient.daoimpl.PatientDAOImpl;
import us.mn.state.health.lims.patient.valueholder.Patient;
import us.mn.state.health.lims.patientidentity.dao.PatientIdentityDAO;
import us.mn.state.health.lims.patientidentity.daoimpl.PatientIdentityDAOImpl;
import us.mn.state.health.lims.patientidentity.valueholder.PatientIdentity;
import us.mn.state.health.lims.sample.dao.SampleDAO;
import us.mn.state.health.lims.sample.daoimpl.SampleDAOImpl;
import us.mn.state.health.lims.sample.valueholder.Sample;
import us.mn.state.health.lims.sampleitem.dao.SampleItemDAO;
import us.mn.state.health.lims.sampleitem.daoimpl.SampleItemDAOImpl;
import us.mn.state.health.lims.sampleitem.valueholder.SampleItem;
import us.mn.state.health.lims.unitofmeasure.valueholder.UnitOfMeasure;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class AjaxCustomReportViewAction extends BaseAction {

    private SampleDAO sampleDAO = new SampleDAOImpl();
    private PatientDAO patientDAO = new PatientDAOImpl();
    private PatientIdentityDAO identityDAO = new PatientIdentityDAOImpl();
    private AnalysisDAO analysisDAO = new AnalysisDAOImpl();
    private SampleItemDAO sampleItemDAO = new SampleItemDAOImpl();
    private String[] singlePagePrint = {"a", "b", "c", "d"};
    private String[] listOfTest = {"a", "b"};

    @Override
    protected ActionForward performAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception, JsonMappingException {
        DynaActionForm dynaForm = (DynaActionForm) form;

        String forward = FWD_SUCCESS;
        String forwardnotsingle = "success";

        String accessionNumber = request.getParameter("accessionNumber");
        String sampleItemId = request.getParameter("sampleItemId").replace("[", "");
        String departmentName = request.getParameter("dep");
        Sample sample = sampleDAO.getSampleByAccessionNumber(accessionNumber);
        request.setAttribute("sample", sample);
        request.setAttribute("departmentName", departmentName);

        Patient patient = patientDAO.getPatientById(sample.getPatient().getId());
        PatientIdentity patientIdentities = identityDAO.getPatitentIdentityForPatientAndType(patient.getId(), "2");
        String middleName = "";

        PatientInfo patientInfo = new PatientInfo();
        if (patient.getPerson().getMiddleName() != null) {
            middleName = patient.getPerson().getMiddleName();
        }
        patientInfo.setPatientName(patient.getPerson().getFirstName() + " " + middleName
                + " " + patient.getPerson().getLastName());
        patientInfo.setStNumber(patientIdentities.getIdentityData());
        patientInfo.setAccessionNumber(sample.getAccessionNumber());
        patientInfo.setSampleCollectionDate(sample.getCollectionDate());
        patientInfo.setAccessionNumber(accessionNumber);
        patientInfo.setAge(String.valueOf(patient.getAge()));
        patientInfo.setGender(patient.getGender());

        request.setAttribute("patientInfo", patientInfo);

        String[] sampleIds = sampleItemId.replace("]", "").split(",");
        List<Analysis> analysisList = new ArrayList<>();


        List<Analysis> analylisTotalCount = new ArrayList<>();
        for (String s : sampleIds) {
            SampleItem sampleItem = sampleItemDAO.getData(s.trim());
            List<Analysis> list = analysisDAO.getAnalysesBySampleItem(sampleItem);
            analysisList.addAll(list);
        }

//        for (Analysis ana : analysisList) {
//            for (Result result : ana.getResults()) {
//                System.out.println("value :::::::::::::::::::: " + result.getValue());
//            }
//        }


        for (Iterator<Analysis> iterator = analysisList.iterator(); iterator.hasNext(); ) {
            Analysis value = iterator.next();
            UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
            if (value.getTest().getTestName().startsWith("Platelet") || value.getTest().getTestName().startsWith("Total count of WBC") || value.getTest().getTestName().startsWith("Total count of RBC")) {
                Analysis ana = new Analysis();
                ana.setTestName(value.getTest().getTestName());

                ana.setResults(value.getResults());
                /*   ana.getTest().getUnitOfMeasure().setUnitOfMeasureName(value.getTest().getUnitOfMeasure().getUnitOfMeasureName());*/
                analylisTotalCount.add(ana);
                iterator.remove();
            }
        }
        request.setAttribute("analysisList", analysisList);
        request.setAttribute("totalCount", analylisTotalCount);
        return mapping.findForward(forward);
    }

    @Override
    protected String getPageTitleKey() {
        return null;
    }

    @Override
    protected String getPageSubtitleKey() {
        return null;
    }

    private String asJson(Object o) throws IOException {
        String listJson = ObjectMapperRepository.objectMapper.writeValueAsString(o);
        return StringEscapeUtils.escapeEcmaScript(listJson);
    }
}
/*
commit*/