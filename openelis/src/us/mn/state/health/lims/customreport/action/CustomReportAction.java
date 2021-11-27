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
import us.mn.state.health.lims.dashboard.dao.OrderListDAO;
import us.mn.state.health.lims.dashboard.daoimpl.OrderListDAOImpl;
import us.mn.state.health.lims.dashboard.valueholder.Order;
import us.mn.state.health.lims.panel.valueholder.Panel;
import us.mn.state.health.lims.patient.dao.PatientDAO;
import us.mn.state.health.lims.patient.daoimpl.PatientDAOImpl;
import us.mn.state.health.lims.patient.valueholder.Patient;
import us.mn.state.health.lims.sample.dao.SampleDAO;
import us.mn.state.health.lims.sample.daoimpl.SampleDAOImpl;
import us.mn.state.health.lims.sample.valueholder.Sample;
import us.mn.state.health.lims.sampleitem.valueholder.SampleItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class CustomReportAction extends BaseAction {
    private AnalysisDAO analysisDAO = new AnalysisDAOImpl();
    private static Set<Integer> analysisStatusIds;
    private OrderListDAO orderListDAO = new OrderListDAOImpl();
    private SampleDAO sampleDAO = new SampleDAOImpl();
    private PatientDAO patientDAO = new PatientDAOImpl();
    String message;
    protected Sample reportSample;
    @Override
    protected ActionForward performAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception, JsonMappingException {
        String forward = FWD_SUCCESS;
        DynaActionForm dynaForm = (DynaActionForm) form;

        String accessionNumber = request.getParameter("accessionNumber");
        Sample sample = sampleDAO.getSampleByAccessionNumber(accessionNumber);
        dynaForm.set("sample", sample);

        List<Analysis> analysisList = new ArrayList<>();
        List<Analysis> analysisArrayList = new ArrayList<>();

        for (SampleItem item : sample.getSampleItems()) {
            for (Analysis analysis : item.getAnalyses()) {
                if (analysis.getPanel() != null) {
                    analysisArrayList.add(analysis);
                } else {
//                    System.out.println(analysis.getResults());
                    analysisList.add(analysis);
                }
            }
        }

        Map<Panel, List<Analysis>> postsPerType = analysisArrayList.stream().collect(groupingBy(Analysis::getPanel));
        List<Panel> panelList = new ArrayList<Panel>(postsPerType.keySet());
        request.setAttribute("panelList", panelList);
        request.setAttribute("analysisList", analysisList);

        Patient patient = patientDAO.getPatientById(sample.getPatient().getId());
        dynaForm.set("patient", patient);

        List<Order> orderList = orderListDAO.getAllPendingBeforeToday().stream().filter(order -> order.getAccessionNumber().equals(accessionNumber)).collect(Collectors.toList());
        dynaForm.set("orderList", orderList);
        request.setAttribute("listUsers", orderList);

        return mapping.findForward(forward);
    }

    @Override
    protected String getPageTitleKey() {
        return "Report Print";
    }

    @Override
    protected String getPageSubtitleKey() {
        return "Report Print";
    }

    private String asJson(Object o) throws IOException {
        String listJson = ObjectMapperRepository.objectMapper.writeValueAsString(o);
        return StringEscapeUtils.escapeEcmaScript(listJson);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
/*
commit*/