package us.mn.state.health.lims.customreport.action;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import us.mn.state.health.lims.analysis.dao.AnalysisDAO;
import us.mn.state.health.lims.analysis.daoimpl.AnalysisDAOImpl;
import us.mn.state.health.lims.analysis.valueholder.Analysis;
import us.mn.state.health.lims.common.action.BaseAction;
import us.mn.state.health.lims.common.util.DateUtil;
import us.mn.state.health.lims.common.util.SystemConfiguration;
import us.mn.state.health.lims.customreport.dao.OdooReportDao;
import us.mn.state.health.lims.customreport.daoimpl.OdooReportDaoImpl;
import us.mn.state.health.lims.customreport.valueholder.OdooReport;
import us.mn.state.health.lims.panel.dao.PanelDAO;
import us.mn.state.health.lims.panel.daoimpl.PanelDAOImpl;
import us.mn.state.health.lims.panel.valueholder.Panel;
import us.mn.state.health.lims.panelitem.dao.PanelItemDAO;
import us.mn.state.health.lims.panelitem.daoimpl.PanelItemDAOImpl;
import us.mn.state.health.lims.panelitem.valueholder.PanelItem;
import us.mn.state.health.lims.patient.dao.PatientDAO;
import us.mn.state.health.lims.patient.daoimpl.PatientDAOImpl;
import us.mn.state.health.lims.patient.valueholder.Patient;
import us.mn.state.health.lims.patientidentity.dao.PatientIdentityDAO;
import us.mn.state.health.lims.patientidentity.daoimpl.PatientIdentityDAOImpl;
import us.mn.state.health.lims.person.dao.PersonDAO;
import us.mn.state.health.lims.person.daoimpl.PersonDAOImpl;
import us.mn.state.health.lims.sample.dao.SampleDAO;
import us.mn.state.health.lims.sample.daoimpl.SampleDAOImpl;
import us.mn.state.health.lims.sample.valueholder.Sample;
import us.mn.state.health.lims.samplehuman.dao.SampleHumanDAO;
import us.mn.state.health.lims.samplehuman.daoimpl.SampleHumanDAOImpl;
import us.mn.state.health.lims.samplehuman.valueholder.SampleHuman;
import us.mn.state.health.lims.sampleitem.dao.SampleItemDAO;
import us.mn.state.health.lims.sampleitem.daoimpl.SampleItemDAOImpl;
import us.mn.state.health.lims.sampleitem.valueholder.SampleItem;
import us.mn.state.health.lims.samplesource.dao.SampleSourceDAO;
import us.mn.state.health.lims.samplesource.daoimpl.SampleSourceDAOImpl;
import us.mn.state.health.lims.samplesource.valueholder.SampleSource;
import us.mn.state.health.lims.statusofsample.util.StatusOfSampleUtil;
import us.mn.state.health.lims.systemuser.dao.SystemUserDAO;
import us.mn.state.health.lims.systemuser.daoimpl.SystemUserDAOImpl;
import us.mn.state.health.lims.test.dao.TestDAO;
import us.mn.state.health.lims.test.dao.TestSectionDAO;
import us.mn.state.health.lims.test.daoimpl.TestDAOImpl;
import us.mn.state.health.lims.test.daoimpl.TestSectionDAOImpl;
import us.mn.state.health.lims.test.valueholder.Test;
import us.mn.state.health.lims.test.valueholder.TestSection;
import us.mn.state.health.lims.typeofsample.dao.TypeOfSampleDAO;
import us.mn.state.health.lims.typeofsample.dao.TypeOfSamplePanelDAO;
import us.mn.state.health.lims.typeofsample.dao.TypeOfSampleTestDAO;
import us.mn.state.health.lims.typeofsample.daoimpl.TypeOfSampleDAOImpl;
import us.mn.state.health.lims.typeofsample.daoimpl.TypeOfSamplePanelDAOImpl;
import us.mn.state.health.lims.typeofsample.daoimpl.TypeOfSampleTestDAOImpl;
import us.mn.state.health.lims.typeofsample.valueholder.TypeOfSample;
import us.mn.state.health.lims.typeofsample.valueholder.TypeOfSamplePanel;
import us.mn.state.health.lims.typeofsample.valueholder.TypeOfSampleTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class PathologyReportAction extends BaseAction {
    private TestSectionDAO testSectDAO = new TestSectionDAOImpl();
    private SampleSourceDAO sampleSourceDAO = new SampleSourceDAOImpl();
    private SampleDAO sampleDAO = new SampleDAOImpl();
    private OdooReportDao odooReportDao = new OdooReportDaoImpl();
    //    private PatientDAO patientDAO = new PatientDAOImpl();
    private TypeOfSampleTestDAO typeOfSampleTestDAO = new TypeOfSampleTestDAOImpl();
    private TestDAO testDAO = new TestDAOImpl();
    private PanelDAO panelDAO = new PanelDAOImpl();
    private PanelItemDAO panelItemDAO = new PanelItemDAOImpl();
    private TypeOfSamplePanelDAO typeOfSamplePanelDAO = new TypeOfSamplePanelDAOImpl();
    private TypeOfSampleDAO typeOfSampleDAO = new TypeOfSampleDAOImpl();
    private SampleItemDAO sampleItemDAO = new SampleItemDAOImpl();
    private PatientDAO patientDAO = new PatientDAOImpl();
    private SampleHumanDAO sampleHumanDAO = new SampleHumanDAOImpl();
    private AnalysisDAO analysisDAO = new AnalysisDAOImpl();
    private SystemUserDAO systemUserDAO = new SystemUserDAOImpl();
    private PersonDAO personDAO = new PersonDAOImpl();
    private PatientIdentityDAO patientIdentityDAO = new PatientIdentityDAOImpl();

    @Override
    protected ActionForward performAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception, JsonMappingException {
        String forward = FWD_SUCCESS;

        DynaActionForm dynaForm = (DynaActionForm) form;
        List<TestSection> testSections = testSectDAO.getAllTestSections();
        request.setAttribute("testSections", testSections.stream().filter(testSection -> testSection.getIsActive().equals("Y")).collect(Collectors.toList()));
        Timestamp instant = Timestamp.from(Instant.now());

        String sysUserId = "1";
        java.sql.Date nowAsSqlDate = DateUtil.getNowAsSqlDate();

        List<OdooReport> groupOrderList = odooReportDao.listOdooOrderByGroupPaitent();
        List<OdooReport> allList = odooReportDao.getAllData();

        for (OdooReport odooReport : groupOrderList) {
            Set<TypeOfSample> typeOfSamples = new HashSet<>();
            Set<SampleItem> sampleItems = new HashSet<>();
            Set<Analysis> analyses = new HashSet<>();

            for (OdooReport report : allList) {

                if (odooReport.getInvoiceId().equals(report.getInvoiceId())) {
//                    System.out.println("product name :::: " + report.getProductName());
                    Test test = testDAO.getTestByName(report.getProductName());
                    if (test != null) {
                        TypeOfSampleTest typeOfSampleTest = typeOfSampleTestDAO.getTypeOfSampleTestForTest(test.getId());
                        if (typeOfSampleTest != null) {
                            TypeOfSample typeOfSample = typeOfSampleDAO.getTypeOfSampleById(typeOfSampleTest.getTypeOfSampleId());
                            typeOfSamples.add(typeOfSample);

                            SampleItem sampleItem = new SampleItem();
                            sampleItem.setTypeOfSample(typeOfSample);
                            sampleItem.setStatusId("19");
                            sampleItem.setSortOrder("1");
                            sampleItem.setLastupdated(instant);
                            sampleItems.add(sampleItem);

                            Analysis analysis = new Analysis();
                            analysis.setTest(test);
                            analysis.setSysUserId(sysUserId);
                            analysis.setTestSection(test.getTestSection());
                            analysis.setSampleItem(sampleItem);
                            analyses.add(analysis);
                        }
                    } else {
                        Panel panel = panelDAO.getPanelByName(report.getProductName());
                        if (panel != null) {
                            TypeOfSamplePanel typeOfSamplePanel = typeOfSamplePanelDAO.getTypeOfSamplePanelForPanel(panel.getId());
                            if (typeOfSamplePanel != null) {
                                TypeOfSample typeOfSample = typeOfSampleDAO.getTypeOfSampleById(typeOfSamplePanel.getTypeOfSampleId());
                                typeOfSamples.add(typeOfSample);

                                SampleItem sampleItem = new SampleItem();
                                sampleItem.setTypeOfSample(typeOfSample);
                                sampleItem.setStatusId("19");
                                sampleItem.setSortOrder("1");
                                sampleItem.setLastupdated(instant);
                                sampleItems.add(sampleItem);

                                List<PanelItem> panelItems = (List<PanelItem>) panelItemDAO.getPanelItemsForPanel(panel.getId());

                                for (PanelItem pi : panelItems) {
                                    Analysis analysis = new Analysis();
                                    analysis.setTest(pi.getTest());
                                    analysis.setTestSection(pi.getTest().getTestSection());
                                    analysis.setStartedDate(nowAsSqlDate);
                                    analysis.setAnalysisType("MANUAL");
                                    analysis.setLastupdated(instant);
                                    analysis.setSysUserId(sysUserId);
                                    analysis.setStatusId("4");
                                    analysis.setPanel(panel);
                                    analysis.setSampleItem(sampleItem);
                                    analyses.add(analysis);
                                }

                            }
                        }
                    }

                }
                report.setStatus(1);
                odooReportDao.insertData(report);
            }

            Patient patient = patientDAO.getPatientByUUID(odooReport.getPatientUUID());

            try {

                Sample sample = new Sample();
                sample.setSysUserId(sysUserId);
                sample.setAccessionNumber(null);
                SampleSource sampleSource = sampleSourceDAO.get("2");
                sample.setSampleSource(sampleSource);
                sample.setEnteredDate(new Date());
                sample.setReceivedDate(nowAsSqlDate);
                sample.setDomain(SystemConfiguration.getInstance().getHumanDomain());
                sample.setStatusId(StatusOfSampleUtil.getStatusID(StatusOfSampleUtil.OrderStatus.Entered));
                sample.setUUID(UUID.randomUUID().toString());
                //  sample.setPatient(patient);
//                sample.setPatient(patient);
                sampleDAO.insertData(sample);

                SampleHuman sampleHuman = new SampleHuman();
                sampleHuman.setSampleId(sample.getId());
                sampleHuman.setPatientId(patient.getId());
                sampleHuman.setProviderId("39");
                sampleHuman.setSysUserId(sysUserId);
                sampleHuman.setSampleId(sample.getId());
                sampleHumanDAO.insertData(sampleHuman);

                for (SampleItem sampleItem : sampleItems) {
                    sampleItem.setSample(sample);
                    sampleItem.setSysUserId(sysUserId);
                    sampleItemDAO.insertData(sampleItem);
                }

                for (Analysis ana : analyses) {
                    analysisDAO.insertData(ana, false);
                }

            } catch (Exception e) {
                System.out.println("Exception :::::::::: " + e);
            }
        }
        return mapping.findForward(forward);
    }

    @Override
    protected String getPageTitleKey() {
        return "Pathology Report";
    }

    @Override
    protected String getPageSubtitleKey() {
        return "Pathology Report";
    }

}
/*
commit*/