package com.kingsoft.sjk.util;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

public class VeTemplate {

    private org.apache.velocity.Template template;
    private String templateName = "app.tml";
    private String templateDir = "/template";

    public VeTemplate(String templateDir, String templateName) {
        this.templateName = templateName;
        this.templateDir = templateDir;
    }

    public org.apache.velocity.Template getTemplate() {
        Velocity.addProperty("file.resource.loader.path", templateDir);
        VelocityEngine ve = new VelocityEngine();
        // ve.setProperty("file.resource.loader.path", templateDir);
        Properties p = new Properties();
        p.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, templateDir);
        p.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        p.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
        ve.init(p);
        // template = Velocity.getTemplate(templateName, "UTF-8");
        template = ve.getTemplate(templateName, "UTF-8");
        return template;
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
