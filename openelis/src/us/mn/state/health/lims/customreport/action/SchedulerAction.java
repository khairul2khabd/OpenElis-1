package us.mn.state.health.lims.customreport.action;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import us.mn.state.health.lims.common.action.BaseAction;
import us.mn.state.health.lims.customreport.dao.OdooReportDao;
import us.mn.state.health.lims.customreport.daoimpl.OdooReportDaoImpl;
import us.mn.state.health.lims.customreport.valueholder.OdooReport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class SchedulerAction extends BaseAction {

    private OdooReportDao odooReportDao= new OdooReportDaoImpl();

    @Override
    protected ActionForward performAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception, JsonMappingException {
        String forward = FWD_SUCCESS;
        DynaActionForm dynaForm = (DynaActionForm) form;
        List<OdooReport> odooList=odooReportDao.getAllData();
        request.setAttribute("report", odooList);
        odooList.forEach(System.out::println);

       System.out.println("Scheduler Report");
        return mapping.findForward(forward);
    }

    @Override
    protected String getPageTitleKey() {
        return "Scheduler Report";
    }

    @Override
    protected String getPageSubtitleKey() {
        return "Scheduler Report";
    }
}
/*
commit*/