package us.mn.state.health.lims.customreport.dao;

import us.mn.state.health.lims.common.dao.BaseDAO;
import us.mn.state.health.lims.common.exception.LIMSRuntimeException;
import us.mn.state.health.lims.customreport.valueholder.OdooReport;

import java.util.Date;
import java.util.List;


public interface OdooReportDao extends BaseDAO {

    public OdooReport getById(Integer id) throws LIMSRuntimeException;
    public OdooReport getByUUIDId(String id, String invoiceNumber, String productName) throws LIMSRuntimeException;

    public void update(OdooReport odooReport) throws LIMSRuntimeException;

    public boolean insertData(OdooReport odooReport) throws LIMSRuntimeException;

    public void deleteData(List samples) throws LIMSRuntimeException;
    public void deleteOdooReport(OdooReport odooReport) throws LIMSRuntimeException;

    public List getAllData() throws LIMSRuntimeException;
 
    public List listOdooOrderByGroupPaitent() throws LIMSRuntimeException;

    List<OdooReport> getDataByFiltering(Integer state, String name, Date date);
}

