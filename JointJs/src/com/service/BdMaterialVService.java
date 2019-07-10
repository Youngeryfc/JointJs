package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.BdMaterialVMapper;
import com.entity.BdMaterialV;
@Service
public class BdMaterialVService implements BdMaterialVMapper {
    @Autowired
    private BdMaterialVMapper BdMaterialV;
    @Override
    public List<BdMaterialV> selectByPrimary(BdMaterialV record) {
        // TODO Auto-generated method stub
        return BdMaterialV.selectByPrimary(record);
    }
}
