package com.lzr.service;

import com.lzr.entity.HeadLine;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface HeadLineService {

    /**
     * 根据传入的条件返回指定的头条列表
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    List<HeadLine> getHandLineList(HeadLine headLineCondition)throws IOException;
}
