package com.ijinshan.sjk.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijinshan.sjk.dao.UserDao;
import com.ijinshan.sjk.po.User;
import com.ijinshan.sjk.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao dao;

    @Override
    public User get(int id) {
        return dao.get(id);
    }

    @Override
    public boolean saveOrUpdate(User entity) {
        if (entity.getId() > 0) {
            dao.update(entity);
        } else {
            dao.save(entity);
        }
        return true;
    }

    @Override
    public List<User> searchList(int page, int rows, String keyword, String order, String sort) {
        return dao.queryUserList(page, rows, keyword, order, sort);
    }

    @Override
    public long countForSearching(String keyword) {
        return dao.countForSearching(keyword);
    }

    @Override
    public boolean deleteByIds(List<Integer> ids) {
        return dao.deleteByIds(ids);
    }

    @Override
    public User login(String username, String pwd) {
        return dao.login(username, pwd);
    }

    @Override
    public boolean isExistsUser(String name, String email) {
        return dao.isExistsUser(name, email);
    }

    @Override
    public boolean updatePwd(Integer id, String pwd) {
        return dao.updatePwd(id, pwd);
    }
}
