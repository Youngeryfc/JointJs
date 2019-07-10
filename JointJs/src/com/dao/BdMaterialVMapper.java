package com.dao;

import java.util.List;

import com.entity.BdMaterialV;

public interface BdMaterialVMapper {
    List<BdMaterialV> selectByPrimary(BdMaterialV record);
}