package com.lzr.service;

import com.lzr.entity.Area;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AreaService {
    List<Area> getAreaList();
}
