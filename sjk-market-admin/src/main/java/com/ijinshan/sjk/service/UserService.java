package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.User;

public interface UserService {

    boolean isExistsUser(String name, String email);

    boolean updatePwd(Integer id, String newPwd);

    boolean deleteByIds(List<Integer> ids);

    User login(String username, String pwd);

    User get(int id);

    boolean saveOrUpdate(User entity);

    List<User> searchList(int page, int rows, String keyword, String order, String sort);

    long countForSearching(String keyword);

}
