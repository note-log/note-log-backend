package com.snowwarrior.notelog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author SnowWarrior
 */
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@MapperScan(basePackages = "com.snowwarrior.notelog.mapper")
@EnableTransactionManagement
public class NoteLogApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoteLogApplication.class, args);
    }
}
