package us.mn.state.health.lims.customreport.daoimpl;

import us.mn.state.health.lims.common.exception.LIMSRuntimeException;
import us.mn.state.health.lims.customreport.dao.OdooExceptionHandlingDao;
import us.mn.state.health.lims.customreport.valueholder.OdooExceptionHandling;
import us.mn.state.health.lims.hibernate.ElisHibernateSession;
import us.mn.state.health.lims.hibernate.HibernateUtil;

import java.util.Date;
import java.util.List;

public class OdooExceptionHandlingDaoImpl implements OdooExceptionHandlingDao {
    @Override
    public OdooExceptionHandling getById(Integer id) throws LIMSRuntimeException {
        return null;
    }

    @Override
    public void update(OdooExceptionHandling odooExceptionHandling) throws LIMSRuntimeException {
        if (odooExceptionHandling != null) {
            HibernateUtil.getSession().saveOrUpdate(odooExceptionHandling);
            flushAndClear();
        }
    }

    @Override
    public boolean insertData(OdooExceptionHandling odooExceptionHandling) throws LIMSRuntimeException {
        HibernateUtil.getSession().saveOrUpdate(odooExceptionHandling);
        return true;
    }

    @Override
    public void deleteData(List samples) throws LIMSRuntimeException {

    }

    @Override
    public List getAllData() throws LIMSRuntimeException {
        return null;
    }

    @Override
    public List<OdooExceptionHandling> getDataByFiltering(Integer state, String name, Date date) {

        return null;
    }

    @Override
    public List getNextRecord(String id, String table, Class clazz) throws LIMSRuntimeException {
        return null;
    }

    @Override
    public List getPreviousRecord(String id, String table, Class clazz) throws LIMSRuntimeException {
        return null;
    }

    @Override
    public Integer getTotalCount(String table, Class clazz) throws LIMSRuntimeException {
        return null;
    }

    private void flushAndClear() {
        ElisHibernateSession session = (ElisHibernateSession) HibernateUtil.getSession();
        session.clearSession();
    }
}
