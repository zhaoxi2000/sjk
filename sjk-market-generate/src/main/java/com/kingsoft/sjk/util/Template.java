package com.kingsoft.sjk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Template {
    private static final Logger logger = LoggerFactory.getLogger(Template.class);
    private String templateName = "detail.tml";
    private String templateDir = "/template";
    private String detailContent;

    public Template() {
        super();
    }

    public Template(String templateDir, String templateName) {
        if (!StringUtils.isEmpty(templateName)) {
            this.templateName = templateName;
        }
        if (!StringUtils.isEmpty(templateName)) {
            this.templateDir = templateDir;
        }
    }

    public String getDetailContent() {
        String fileUrl = this.templateDir + this.templateName;
        File file = new File(fileUrl);
        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            setDetailContent(IOUtils.toString(fileInput, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return detailContent;
    }

    public void setDetailContent(String detailContent) {
        this.detailContent = detailContent;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;

    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

}
