package com.ijinshan.sjk.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ijinshan.sjk.po.Metro;

public interface MetroService {

    void saveOrUpdate(MultipartHttpServletRequest multipartReq, Metro metro) throws MalformedURLException,
            URISyntaxException, IOException;

    List<Metro> search(String type, Boolean hidden, Boolean deleted);

    boolean delete(int appId, String type) throws UnsupportedOperationException;

    boolean updateHide(List<Integer> appIds, String type);

    boolean updateShow(List<Integer> appIds, String type);

    /*
     * 逻辑删除操作， deleted=true 删除操作 deleted=false 还原操作
     */
    boolean updateDeleted(List<Integer> ids, String type, boolean deleted);

}
