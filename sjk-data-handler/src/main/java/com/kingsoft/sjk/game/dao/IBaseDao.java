package com.kingsoft.sjk.game.dao;

public interface IBaseDao<T> {

    int deleteById(Integer id);

    /**
     * @param entity
     *            泛型实体对象.
     */
    void delete(T entity);

    /**
     * @param vid
     *            泛型实体对象.
     * @param pid
     *            TODO
     * @param names
     *            TODO
     * @param models
     *            TODO
     * @return 是否存在
     */
    boolean isExist(String vid, String pid, String names, String models);

    boolean isExist(String vid, String pid, Integer os_version, Integer os_bit);

    /**
     * @param entity
     *            泛型实体对象.
     * @return 是否保存成功
     */
    Integer save(T entity);

    /**
     * @param entity
     *            泛型实体对象.
     */
    void update(T entity);

    /**
     * @param entity
     *            泛型实体对象.
     * @return 放回查询到的实体对象
     */
    T find(T entity);

    /**
     * @param entity
     *            泛型实体对象.
     * @return 放回查询到的实体对象
     */
    T findById(Integer id);

}
