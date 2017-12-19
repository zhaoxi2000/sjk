/**
 * 
 */
package com.ijinshan.sjk.web;

import java.net.HttpURLConnection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ijinshan.sjk.service.DownloadStatService;

/**
 * @author LinZuXiong
 */
@Controller
@RequestMapping("/app/api/stat")
public class Stat {
    private static final Logger logger = LoggerFactory.getLogger(Stat.class);

    @Resource(name = "downloadStatServiceImpl")
    private DownloadStatService service;

    @RequestMapping(value = "/download/{id}.d")
    private void download(HttpServletRequest req, HttpServletResponse rsp, @PathVariable int id) {
        try {
            service.add(id);
            rsp.setStatus(HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            rsp.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
            logger.error("Exception", e);
        } finally {
        }
    }
}
