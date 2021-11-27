package us.mn.state.health.lims.customreport.dao;

import us.mn.state.health.lims.common.dao.BaseDAO;
import us.mn.state.health.lims.common.exception.LIMSRuntimeException;
import us.mn.state.health.lims.customreport.valueholder.OdooExceptionHandling;

import java.util.Date;
import java.util.List;

public interface OdooExceptionHandlingDao extends BaseDAO {

    public OdooExceptionHandling getById(Integer id) throws LIMSRuntimeException;

    public void update(OdooExceptionHandling odooExceptionHandling)throws LIMSRuntimeException;

    public boolean insertData(OdooExceptionHandling odooExceptionHandling) throws LIMSRuntimeException;

    public void deleteData(List samples) throws LIMSRuntimeException;

    public List getAllData() throws LIMSRuntimeException;

    List<OdooExceptionHandling> getDataByFiltering(Integer state, String name, Date date);



}
