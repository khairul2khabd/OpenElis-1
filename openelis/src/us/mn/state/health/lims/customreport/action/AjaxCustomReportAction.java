package us.mn.state.health.lims.customreport.action;


import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.bahmni.feed.openelis.ObjectMapperRepository;
import us.mn.state.health.lims.analysis.valueholder.Analysis;
import us.mn.state.health.lims.common.action.BaseAction;
import us.mn.state.health.lims.customreport.valueholder.LabTest;
import us.mn.state.health.lims.patient.dao.PatientDAO;
import us.mn.state.health.lims.patient.daoimpl.PatientDAOImpl;
import us.mn.state.health.lims.patient.valueholder.Patient;
import us.mn.state.health.lims.patientidentity.dao.PatientIdentityDAO;
import us.mn.state.health.lims.patientidentity.daoimpl.PatientIdentityDAOImpl;
import us.mn.state.health.lims.patientidentity.valueholder.PatientIdentity;
import us.mn.state.health.lims.sample.dao.SampleDAO;
import us.mn.state.health.lims.sample.daoimpl.SampleDAOImpl;
import us.mn.state.health.lims.sample.valueholder.Sample;
import us.mn.state.health.lims.samplehuman.dao.SampleHumanDAO;
import us.mn.state.health.lims.sampleitem.valueholder.SampleItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


public class AjaxCustomReportAction extends BaseAction {

    private SampleDAO sampleDAO = new SampleDAOImpl();
    private PatientDAO patientDAO = new PatientDAOImpl();
    private PatientIdentityDAO identityDAO = new PatientIdentityDAOImpl();
    private SampleHumanDAO sampleHumanDAO;

    @Override
    protected ActionForward performAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception, JsonMappingException {
        DynaActionForm dynaForm = (DynaActionForm) form;
        String forward = FWD_SUCCESS;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat _sdf = new SimpleDateFormat("dd/MM/yyyy");

        String recievedDate = request.getParameter("date");
        String department = request.getParameter("dep");
        Date date = sdf.parse(recievedDate);

        List<LabTest> labTests = new ArrayList<>();

        List<Sample> sampleList = sampleDAO.getSamplesCollectOnAndStatus(_sdf.format(date), 1);
        dynaForm.set("sampleList", sampleList);
        request.setAttribute("_sampleList", sampleList);

        List<Analysis> analysisList = new ArrayList<>();
        List<Analysis> analysisArrayList = new ArrayList<>();


        for (Sample sample : sampleList) {
            String middleName = "";
            LabTest labTest = new LabTest();
//            Patient patient = sampleHumanDAO.getPatientForSample(sample);

            Patient patient = patientDAO.getPatientById(sample.getPatient().getId());
            System.out.println(patient.getId());
            if (patient != null) {
                PatientIdentity patientIdentities = identityDAO.getPatitentIdentityForPatientAndType(patient.getId(), "2");
                System.out.println("patientIdentities" + patientIdentities);
                if (patient.getPerson().getMiddleName() != null) {
                    middleName = patient.getPerson().getMiddleName();
                }
                labTest.setStNumber(patientIdentities.getIdentityData());
            }

            labTest.setPatientName(patient.getPerson().getFirstName() + " " + middleName
                    + " " + patient.getPerson().getLastName());

            labTest.setAccessionNumber(sample.getAccessionNumber());
            //labTest.setStatusId(sample.getStatusId());
            labTest.setCollectionDate(sample.getCollectionDate());

            String testName = "";

            Set panelTest = new HashSet();
            Set sampleStatus = new HashSet();
            Set analysisStatus = new HashSet();
            Set departmentName = new HashSet();
            Set analysisId = new HashSet();
            Set sampleItemId = new HashSet();

            for (SampleItem item : sample.getSampleItems()) {
                for (Analysis analysis : item.getAnalyses()) {
                    if (analysis.getPanel() != null) {
                        if (department.equals("All Test")) {
                            analysisArrayList.add(analysis);
                            panelTest.add(analysis.getPanel().getPanelName());
                            sampleStatus.add(item.getSample().getStatusId());
                            analysisStatus.add(analysis.getStatusId());
                            departmentName.add(analysis.getTestSection().getTestSectionName());
                            analysisId.add(analysis.getId());
                            sampleItemId.add(item.getId());
                        } else if (department.equals(analysis.getTestSection().getTestSectionName())) {
                            analysisArrayList.add(analysis);
                            panelTest.add(analysis.getPanel().getPanelName());
                            sampleStatus.add(item.getSample().getStatusId());
                            analysisStatus.add(analysis.getStatusId());
                            departmentName.add(analysis.getTestSection().getTestSectionName());
                            analysisId.add(analysis.getId());
                            sampleItemId.add(item.getId());
                        }
                    } else {
                        if (department.equals("All Test")) {
                            testName += analysis.getTest().getTestName() + ", ";
                            sampleStatus.add(item.getSample().getStatusId());
                            analysisStatus.add(analysis.getStatusId());
                            departmentName.add(analysis.getTestSection().getTestSectionName());
                            sampleItemId.add(item.getId());
                            analysisList.add(analysis);
                        } else if (department.equals(analysis.getTestSection().getTestSectionName())) {
                            testName += analysis.getTest().getTestName() + ", ";
                            sampleStatus.add(item.getSample().getStatusId());
                            analysisStatus.add(analysis.getStatusId());
                            departmentName.add(analysis.getTestSection().getTestSectionName());
                            sampleItemId.add(item.getId());
                            analysisList.add(analysis);
                        }

                    }
                }
            }
            labTest.setSampleId(sample.getId());
            labTest.setTestName(testName + " " + String.join(", ", panelTest));
            labTest.setSampleStatus(String.join(",", sampleStatus));
            labTest.setAnalysisStatus(String.join(",", analysisStatus));
            labTest.setSampleItemId(sampleItemId.toString());
            labTests.add(labTest);
        }

//        dynaForm.set("labTests", labTests);
//        List<LabTest> alll = labTests.stream().filter(labTest ->)
        request.setAttribute("labTests", labTests);
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