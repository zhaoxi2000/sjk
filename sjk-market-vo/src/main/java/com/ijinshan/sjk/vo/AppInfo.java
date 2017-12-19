package com.ijinshan.sjk.vo;

import com.ijinshan.sjk.po.App;

public class AppInfo extends App {

    private static final long serialVersionUID = 7096162270890376367L;
    private transient String enumStatus;
    private transient boolean auditCatalog;
    private transient boolean hidden;
    private String subCatalogName;

    @Override
    public String getEnumStatus() {
        return enumStatus;
    }

    @Override
    public void setEnumStatus(String enumStatus) {
        this.enumStatus = enumStatus;
    }

    @Override
    public boolean isAuditCatalog() {
        return auditCatalog;
    }

    @Override
    public void setAuditCatalog(boolean auditCatalog) {
        this.auditCatalog = auditCatalog;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getSubCatalogName() {
        return subCatalogName;
    }

    public void setSubCatalogName(String subCatalogName) {
        this.subCatalogName = subCatalogName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
