package com.leyou.service;

import com.leyou.entity.JobInfo;
import com.leyou.mapper.JobInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobInfoServiceImpl {

    @Autowired
    private JobInfoMapper jobInfoMapper;

    public List<JobInfo> findAll(){
        List<JobInfo> list = jobInfoMapper.selectAll();
        return list;
    }
}
