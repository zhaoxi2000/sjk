package com.ijinshan.sjk.config;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ijinshan.sjk.po.AppAdmin;

@Repository
public class ChangeOutputImpl {
    private static final Logger logger = LoggerFactory.getLogger(ChangeOutputImpl.class);

    public void setAuditStatus(AppAdmin app) {
        // App状态设置
        int status = app.getStatus();
        if ((status & AppStatus.AUDIT_MASK.getVal()) == AppStatus.AUDIT_YES.getVal()) {
            app.setAuditStatus(1);
        } else if ((status & AppStatus.AUDIT_MASK.getVal()) == AppStatus.AUDIT_NO_NEED.getVal()) {
            app.setAuditStatus(2);
        } else if ((status & AppStatus.AUDIT_MASK.getVal()) == AppStatus.AUDIT_NO.getVal()) {
            app.setAuditStatus(0);
        }
    }

}
