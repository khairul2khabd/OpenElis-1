package us.mn.state.health.lims.customreport.daoimpl;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import us.mn.state.health.lims.common.exception.LIMSRuntimeException;
import us.mn.state.health.lims.common.log.LogEvent;
import us.mn.state.health.lims.customreport.dao.OdooReportDao;
import us.mn.state.health.lims.customreport.valueholder.OdooReport;
import us.mn.state.health.lims.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static us.mn.state.health.lims.hibernate.HibernateUtil.getSession;


public class OdooReportDaoImpl implements OdooReportDao {

    private OdooReportDao odooReportDao;


    @Override
    public OdooReport getById(Integer id) throws LIMSRuntimeException {
        Criteria criteria = HibernateUtil.getSession().createCriteria(OdooReport.class);
        criteria.add(Restrictions.eq("id", id));
        flushAndClear();
        return (OdooReport) criteria.uniqueResult();
    }

    @Override
    public OdooReport getByUUIDId(String uuid,String invoiceNumber,String productName) throws LIMSRuntimeException {
        Criteria criteria = HibernateUtil.getSession().createCriteria(OdooReport.class);
        criteria.add(Restrictions.eq("patientUUID", uuid));
        criteria.add(Restrictions.eq("invoiceNumber", invoiceNumber));
        criteria.add(Restrictions.eq("productName", productName));
        criteria.add(Restrictions.eq("status", 1));
        OdooReport odooReports = (OdooReport) criteria.uniqueResult();

        return odooReports;
    }


    @Override
    public void update(OdooReport odooReport) throws LIMSRuntimeException {
        if (odooReport != null) {
            HibernateUtil.getSession().saveOrUpdate(odooReport);
            flushAndClear();
        }
    }

    @Override
    public boolean insertData(OdooReport odooReport) throws LIMSRuntimeException {
        Transaction tx = getSession().beginTransaction();
        HibernateUtil.getSession().saveOrUpdate(odooReport);
        flushAndClear();
        tx.commit();
        return true;
    }

    @Override
    public void deleteData(List samples) throws LIMSRuntimeException {

    }

    @Override
    public List getAllData() throws LIMSRuntimeException {
        Criteria criteria = HibernateUtil.getSession().createCriteria(OdooReport.class);
        criteria.add(Restrictions.eq("status", 0));
//        List<OdooReport> odooReports = criteria.list();
        return criteria.list();
    }

    @Override
    public List listOdooOrderByGroupPaitent() throws LIMSRuntimeException {
//        String hql = "select oo.invoiceId, oo.invoiceNumber, oo.patientId from OdooReport oo group by oo.invoiceId, oo.invoiceNumber, oo.patientId";
//        Query query = getSession().createQuery(hql);
//        List list = query.list();
//        HibernateUtil.getSession().flush();
//        HibernateUtil.getSession().clear();
//        return list;


        Criteria criteria = HibernateUtil.getSession().createCriteria(OdooReport.class);
        criteria.add(Restrictions.eq("status", 0));
        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.groupProperty("invoiceId"))
                .add(Projections.groupProperty("invoiceNumber"))
                .add(Projections.groupProperty("patientId"))
                .add(Projections.groupProperty("patientIdentifier"))
                .add(Projections.groupProperty("patientUUID"));

        criteria.setProjection(proList);
        List<Object> lst = criteria.list();
        List<OdooReport> list = new ArrayList<OdooReport>();
        for (int i = 0; i < lst.size(); i++) {
            Object[] row = (Object[]) lst.get(i);
            OdooReport p = new OdooReport();
            p.setInvoiceId((Integer) row[0]);
            p.setInvoiceNumber((String) row[1]);
            p.setPatientId((String) row[2]);
            p.setPatientIdentifier((String) row[3]);
            p.setPatientUUID((String) row[4]);
            list.add(p);
        }
        criteria.setMaxResults(1);
        list.forEach(System.out::println);
        return list;
    }

    @Override
    public List<OdooReport> getDataByFiltering(Integer state, String name, Date date) {
        Criteria criteria = HibernateUtil.getSession().createCriteria(OdooReport.class);
  /*     String hql = "FROM OdooReport E WHERE E.status = :state and E.productName= :productName";
       Query query = getSession().createQuery(hql);
       query.setParameter("state",state);
       query.setParameter("productName",name);
        List results = query.list();*/

        if (state.equals(-1)) {

            criteria.add(Restrictions.eq("date", date));
            List<OdooReport> odooReports = criteria.list();
            return odooReports;
        } else if (name.equals("All")) {

            criteria.add(Restrictions.eq("status", state.equals("0") || state.equals("1")));
          /*  criteria.add(Restrictions.eq("productName", name));*/
            criteria.add(Restrictions.eq("date", date));
            List<OdooReport> odooReports = criteria.list();
            return odooReports;
        } else {

            criteria.add(Restrictions.eq("status", state));
            criteria.add(Restrictions.eq("productName", name));
            criteria.add(Restrictions.eq("date", date));
            List<OdooReport> odooReports = criteria.list();
            return odooReports;
        }
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
        HibernateUtil.getSession().flush();
        HibernateUtil.getSession().clear();
    }





    public void deleteOdooReport(OdooReport odooReport) throws LIMSRuntimeException {



            try {


                HibernateUtil.getSession().delete(odooReport);
                //	String qryString = "DELETE FROM SampleHuman s where s.sampleId=:sampleId";


                HibernateUtil.getSession().flush();
                HibernateUtil.getSession().clear();
                //transaction.commit();
            } catch (Exception e) {

                LogEvent.logError("AnalysisDAOImpl", "getAllAnalysisByTestAndStatuses()", e.toString());
                throw new LIMSRuntimeException("Error in Analysis getAllAnalysisByTestAndStatuses()", e);
            }


    }
}







//    Criteria criteria = HibernateUtil.getSession().createCriteria(OdooReport.class);
//    ProjectionList proList = Projections.projectionList();
//        proList.add(Projections.groupProperty("invoiceId"))
//                .add(Projections.property("id"))
//                .add(Projections.property("productId"))
//                .add(Projections.property("productName"))
//                .add(Projections.property("invoiceNumber"))
//                .add(Projections.property("status"))
//                .add(Projections.property("date"))
//                .add(Projections.property("patientId"))
//                .add(Projections.property("patientIdentifier"))
//                .add(Projections.property("patientUUID"));
//
//                criteria.setProjection(proList);
//                List<Object> lst = criteria.list();
//        List<OdooReport> list = new ArrayList<OdooReport>();
//        for (int i = 0; i < lst.size(); i++) {
//        Object[] row = (Object[]) lst.get(i);
//        OdooReport p = new OdooReport();
//        p.setId((Integer) row[1]);
//        p.setInvoiceId((Integer) row[0]);
//        p.setProductId((Integer) row[2]);
//        p.setProductName((String) row[3]);
//        p.setInvoiceNumber((String) row[4]);
//        p.setStatus((Integer) row[5]);
//        p.setDate((Date) row[6]);
//        p.setPatientId((String) row[7]);
//        p.setPatientIdentifier((String) row[8]);
//        p.setPatientUUID((String) row[9]);
//        list.add(p);
//        }
//        return list;