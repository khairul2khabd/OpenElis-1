package us.mn.state.health.lims.customreport.action;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.bahmni.feed.openelis.ObjectMapperRepository;
import us.mn.state.health.lims.common.action.BaseAction;
import us.mn.state.health.lims.customreport.dao.OdooReportDao;
import us.mn.state.health.lims.customreport.daoimpl.OdooReportDaoImpl;
import us.mn.state.health.lims.customreport.valueholder.OdooReport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AjaxSchedulerReportAction extends BaseAction {
   private OdooReportDao odooReportDao= new OdooReportDaoImpl();

    @Override
    protected ActionForward performAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception, JsonMappingException {
        DynaActionForm dynaForm = (DynaActionForm) form;
        String forward = FWD_SUCCESS;
        Integer state = Integer.valueOf(request.getParameter("state"));
        String name = request.getParameter("name");
        String recievedDate = request.getParameter("date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(recievedDate);
        List<OdooReport> odooRepList=odooReportDao.getDataByFiltering(state, name,date);
        request.setAttribute("odooList", odooRepList);
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