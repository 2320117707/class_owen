package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.leyou.mapper")
public class LuceneApplication {
    public static void main(String[] args) {
        SpringApplication.run(LuceneApplication.class,args);
    }
}
