package com.ijinshan.sjk.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ijinshan.sjk.po.User;
import com.ijinshan.sjk.service.UserService;
import com.ijinshan.sjk.util.PWDHelper;
import com.ijinshan.sjk.util.SvrResult;

@Controller
@RequestMapping(value = "/admin/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private JSONStyle jsonStyle = new JSONStyle(JSONStyle.FLAG_PROTECT_4WEB);

    @Autowired
    private UserService service;

    @RequestMapping(value = "/del.d", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String delete(@RequestParam("id") int id) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        try {
            boolean result = service.deleteByIds(Arrays.asList(id));
            if (result) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", SvrResult.ERROR.getMsg());
            }
        } catch (Exception e) {
            server.put("code", -1.);
            server.put("msg", e.getMessage());
            logger.error("Exception:", e);
        }
        return output.toJSONString();
    }

    @RequestMapping(value = { "/save.d", "/edit.d" })
    @ResponseBody
    public String saveOrUpdate(HttpServletRequest request, User entity) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        String reqPath = request.getServletPath();
        output.put("result", server);
        try {
            // 密码编码
            String newPwd = PWDHelper.escape(entity.getPassword());
            entity.setPassword(newPwd);
            if (reqPath.endsWith("/save.d")) {
                if (service.isExistsUser(entity.getName(), entity.getEmail())) {
                    server.put("code", 2);
                    server.put("msg", "该用户已经存在，不用再添加了。");
                    return output.toJSONString();
                }
            } else {
                User oldEntity = service.get(entity.getId());
                if (oldEntity != null)
                    entity.setPassword(oldEntity.getPassword());
            }
            boolean result = service.saveOrUpdate(entity);
            if (result) {
                server.put("code", SvrResult.OK.getCode());
                server.put("msg", SvrResult.OK.getMsg());
            } else {
                server.put("code", SvrResult.ERROR.getCode());
                server.put("msg", SvrResult.ERROR.getMsg());
            }
        } catch (UnsupportedOperationException e) {
            server.put("code", -1.);
            server.put("msg", e.getMessage());
        }
        return output.toJSONString();
    }

    /* 【搜索】 */
    @RequestMapping(value = "/search.json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String search(HttpServletRequest request, @RequestParam int page, @RequestParam int rows,
            @RequestParam(required = false) String keywords, @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order) {
        JSONObject output = new JSONObject();
        JSONObject server = new JSONObject();
        output.put("result", server);
        List<User> list = service.searchList(page, rows, keywords, order, sort);
        long count = service.countForSearching(keywords);
        output.put("rows", list);
        output.put("total", count);
        return output.toJSONString();
    }

    /**
     * 登录
     * 
     * @param username
     * @param pwd
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/doLogin.d", method = RequestMethod.POST)
    public @ResponseBody
    String doLogin(@RequestParam("username") String username, @RequestParam("pwd") String pwd,
            HttpServletRequest request, Model model, HttpSession session) {
        pwd = PWDHelper.escape(pwd);
        User user = service.login(username, pwd);
        JSONObject json = new JSONObject();
        boolean status = false;
        if (user == null) {
            logger.info("login fail ...{}", username);
        } else {
            logger.info("login success ...{}", username);
            session.setAttribute("user", user);
            status = true;
        }
        json.put("status", status);
        return json.toString();
    }

    /**
     * 普通用户修改密码
     * 
     * @param keywords
     * @param request
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/update-pwd.d", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody
    String updatePWD(@RequestParam("o_pwd") String oldPwd, @RequestParam("n_pwd") String newPwd, HttpSession session,
            Model model, HttpServletResponse resp) throws IOException {
        User u = (User) session.getAttribute("user");
        JSONObject json = new JSONObject();
        if (u == null) {
            json.put("status", false);
            json.put("msg", "登陆超时，请重新登陆。");
            return json.toJSONString();
        }
        if (!u.getPassword().equals(PWDHelper.escape(oldPwd))) {
            json.put("status", false);
            json.put("msg", "原密码不对");
            return json.toJSONString();
        } else {
            newPwd = PWDHelper.escape(newPwd);
            service.updatePwd(u.getId(), newPwd);
            json.put("status", true);
            json.put("msg", "更新成功");
            return json.toJSONString();
        }
    }

    /**
     * 管理员修改密码
     * 
     * @param password
     * @param id
     * @param session
     * @param model
     * @param resp
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/update-pwd-admin.d", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody
    String updatePWDByAdmin(@RequestParam String password, @RequestParam int id, HttpSession session, Model model,
            HttpServletResponse resp) throws IOException {
        User u = (User) session.getAttribute("user");
        JSONObject json = new JSONObject();
        if (u == null) {
            json.put("status", false);
            json.put("msg", "登陆超时，请重新登陆。");
            return json.toJSONString();
        }
        // 如果当前用户为自己或者管理员，才可以修改密码
        logger.debug("传入的id:{},当前登录用户id:{}", id, u.getId());
        if (u.getId().equals(id) || u.getUserType() == 1) {
            password = PWDHelper.escape(password);
            service.updatePwd(id, password);
            json.put("status", true);
            json.put("msg", "操作成功");
        } else {
            json.put("status", false);
            json.put("msg", "您无权限修改他人密码");
        }
        return json.toJSONString();
    }

    /**
     * 注销
     */
    @RequestMapping(value = "/doLogoff.d", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String doLogoff(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        JSONObject json = new JSONObject();
        json.put("status", true);
        return json.toJSONString();
    }

    /**
     * 获取当前用户的信息
     * 
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/get-curuser.d", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody
    String getCurrentUserInfo(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        JSONObject json = new JSONObject();
        json.put("result", user);
        return json.toString();
    }
}
