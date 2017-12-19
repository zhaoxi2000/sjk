package com.ijinshan.sjk.service;

import java.util.List;

import com.ijinshan.sjk.po.PhoneRelations;

public interface PhoneRelationsService {
    List<PhoneRelations> findPhoneRelationsByParam(String productId, Integer phoneid);
}
