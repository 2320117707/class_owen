package com.leyou.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "job_info")
public class JobInfo {
    @Id
    private Long id;
    private String  companyName;
    private String  companyAddr;
    private String  jobName;
    private String  jobAddr;
    @Column(name = "salary_min")  //列表和属性名的对应
    private Integer  salary;
    private String  url;
}
