package com.lzr.service.impl;

import com.lzr.dao.AreaDao;
import com.lzr.entity.Area;
import com.lzr.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        List<Area> areaList = areaDao.findAllArea();
        return areaList;
    }
}
