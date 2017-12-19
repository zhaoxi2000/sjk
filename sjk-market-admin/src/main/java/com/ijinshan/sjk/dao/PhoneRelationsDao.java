package com.ijinshan.sjk.dao;

import java.util.List;

import com.ijinshan.sjk.po.PhoneRelations;

public interface PhoneRelationsDao extends BaseDao<PhoneRelations> {

    List<PhoneRelations> findPhoneRelationsByParam(String productId, Integer phoneid);

}
