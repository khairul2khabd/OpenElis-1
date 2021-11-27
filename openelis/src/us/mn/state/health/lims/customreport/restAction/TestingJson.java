//package us.mn.state.health.lims.customreport.restAction;
//
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//import org.apache.struts.action.DynaActionForm;
//import org.json.simple.JSONObject;
//import us.mn.state.health.lims.analysis.dao.AnalysisDAO;
//import us.mn.state.health.lims.analysis.daoimpl.AnalysisDAOImpl;
//import us.mn.state.health.lims.analysis.valueholder.Analysis;
//import us.mn.state.health.lims.common.util.ConfigurationProperties;
//import us.mn.state.health.lims.common.util.SystemConfiguration;
//import us.mn.state.health.lims.customreport.dao.OdooReportDao;
//import us.mn.state.health.lims.customreport.daoimpl.OdooReportDaoImpl;
//import us.mn.state.health.lims.customreport.valueholder.OdooReport;
//import us.mn.state.health.lims.login.action.LoginBaseAction;
//import us.mn.state.health.lims.sample.dao.SampleDAO;
//import us.mn.state.health.lims.sample.daoimpl.SampleDAOImpl;
//import us.mn.state.health.lims.sample.valueholder.Sample;
//import us.mn.state.health.lims.samplehuman.dao.SampleHumanDAO;
//import us.mn.state.health.lims.samplehuman.daoimpl.SampleHumanDAOImpl;
//import us.mn.state.health.lims.samplehuman.valueholder.SampleHuman;
//import us.mn.state.health.lims.sampleitem.dao.SampleItemDAO;
//import us.mn.state.health.lims.sampleitem.daoimpl.SampleItemDAOImpl;
//import us.mn.state.health.lims.sampleitem.valueholder.SampleItem;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TestingJson extends LoginBaseAction {
//
//    protected ActionForward performAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//
//        String sampleId = "";
//        OdooReport odooReport = new OdooReport();
//        String uuid = request.getParameter("uuid");
//        String invoiceNumber = request.getParameter("invoiceNumber");
//        String productName = request.getParameter("productName");
//        String forward = FWD_SUCCESS;
//        Sample sample = new Sample();
//        SampleDAO sampleDAO = new SampleDAOImpl();
//        SampleItemDAO sampleItemDAO = new SampleItemDAOImpl();
//        AnalysisDAO analysisDAO = new AnalysisDAOImpl();
//        List<Sample> sampleList = new ArrayList<>();
//        List<SampleItem> sampleItems = new ArrayList<>();
//        List<SampleHuman> sampleHumanList = new ArrayList<>();
//        SampleHuman sampleHuman = new SampleHuman();
//        SampleHumanDAO sampleHumanDAO = new SampleHumanDAOImpl();
//        OdooReportDao odooReportDao = new OdooReportDaoImpl();
//        List<Analysis> analyses = new ArrayList<>();
//
//
//        if ("true".equals(ConfigurationProperties.getInstance().getPropertyValue(ConfigurationProperties.Property.allowLanguageChange))) {
//            String language = request.getParameter("lang");
//
//            if (language != null) {
//                SystemConfiguration.getInstance().setDefaultLocale(language);
//            }
//        }
//
//
//        odooReport = odooReportDao.getByUUIDId(uuid, invoiceNumber, productName);
//        odooReport.setSampleId(odooReport.getSampleId());
//        sampleId = odooReport.getSampleId();
//        JSONObject json = new JSONObject();
//
//
//     /*   json.put("sample UUID", sample.getUUID());
//        json.put("sample Item", sample.getSampleItems());*/
//
//        sample = sampleDAO.getSampleByID(sampleId);
//        sampleList.add(sample);
//
//        sampleHumanDAO.deleteSampleHumanBySampleId(sampleId);
//        sampleItems = sampleItemDAO.getSampleItemsBySampleId(sampleId);
//
//
//        analyses = analysisDAO.getAnalysesBySampleId(sampleId);
////        analysisDAO.deleteAnalysisDataBySampleItemId(sampleId);
//        sampleItemDAO.deleteSampleItemBySampleId(sampleId);
//        sampleDAO.deleteSampleBySampleId(sampleId);
//
//        odooReport.setStatus(4);
//
//        odooReportDao.insertData(odooReport);
//        json.put("Status", "refunded");
//        System.out.println(sample);
//        PrintWriter out = response.getWriter();
//        try {
//            out.println(json);
//        } finally {
//            out.close();
//        }
//        DynaActionForm dynaForm = (DynaActionForm) form;
//        // initialize the form
//        dynaForm.initialize(mapping);
//
//        return mapping.findForward(forward);
//    }
//
//    /**
//     * Cleanup all the session variables
//     *
//     * @param request is HttpServletRequest
//     */
//    private void cleanUpSession(HttpServletRequest request) {
//        if (request.getSession().getAttribute(USER_SESSION_DATA) != null)
//            request.getSession().removeAttribute(USER_SESSION_DATA);
//    }
//
//    protected String getPageTitleKey() {
//        return "login.title";
//    }
//
//    protected String getPageSubtitleKey() {
//        return "login.subTitle";
//    }
//}
//
//
