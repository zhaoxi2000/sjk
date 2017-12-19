package com.ijinshan.sjk.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ijinshan.sjk.config.AppConfig;

@Controller
@RequestMapping("/admin")
public class DataAdminController {
    private static final Logger logger = LoggerFactory.getLogger(DataAdminController.class);

    @Resource(name = "appConfig")
    private AppConfig appConfig;

}
