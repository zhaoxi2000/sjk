package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.User;

public interface UserDao extends BaseDao<User> {
    boolean isExistsUser(String name, String email);

    boolean updatePwd(Integer id, String newPwd);

    boolean deleteByIds(List<Integer> ids);

    User login(String username, String pwd);

    List<User> queryUserList(int page, int rows, String keyword, String order, String sort);

    long countForSearching(String keyword);
}
