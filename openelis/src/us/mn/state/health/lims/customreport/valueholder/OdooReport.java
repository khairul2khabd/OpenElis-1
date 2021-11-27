package us.mn.state.health.lims.customreport.valueholder;

import us.mn.state.health.lims.common.valueholder.BaseObject;

import java.io.Serializable;
import java.util.Date;

public class OdooReport extends BaseObject implements Serializable  {
    private static final long serialVersionUID = 1407387772068629053L;
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer invoiceId;
    private String invoiceNumber;
    private Integer status;
    private Date date;
    private String patientId;
    private String patientIdentifier;
    private String patientUUID;
    private String sampleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientUUID() {
        return patientUUID;
    }

    public void setPatientUUID(String patientUUID) {
        this.patientUUID = patientUUID;
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public OdooReport(Integer id, Integer productId, String productName, Integer invoiceId, Integer status, Date date, String patientId, String patientUUID) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.invoiceId = invoiceId;
        this.status = status;
        this.date = date;
        this.patientId = patientId;
        this.patientUUID = patientUUID;
    }

    public OdooReport() {
    }

    public OdooReport(Integer id) {
        this.id = id;
    }
}
