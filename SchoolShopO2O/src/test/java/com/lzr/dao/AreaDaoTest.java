package com.lzr.dao;

import com.lzr.BaseTest;
import com.lzr.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaDaoTest extends BaseTest{
    @Autowired
    private AreaDao areaDao;
    @Test
    public void testFindAll(){
        List<Area> allArea = areaDao.findAllArea();
        assertEquals(2,allArea.size());
    }
}
