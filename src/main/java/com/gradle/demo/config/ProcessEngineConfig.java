package com.gradle.demo.config;

import lombok.Data;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @User: Administrator
 * @Time: 2021/5/10
 * @Description: activiti配置类 一般来说，activiti会加载activiti.cfg.xml的配置文件，以加载数据库以及相关配置信息
 */


@Data
@Configuration
@ConfigurationProperties(prefix = "activiti.datasource")
public class ProcessEngineConfig {


    /**
     * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
     */
    private String driverClassName;

    /**
     * JDBC URL of the database.
     */
    private String url;

    /**
     * Login username of the database.
     */
    private String username;

    /**
     * Login password of the database.
     */
    private String password;

    /**
     * @see org.activiti.engine.ProcessEngineConfiguration
     * @return activiti配置bean
     */
    @Bean(name = "processEngineConfiguration")
    public StandaloneProcessEngineConfiguration getProcessEngineConfiguration() {
        StandaloneProcessEngineConfiguration engineConfiguration = new StandaloneProcessEngineConfiguration();
        // 数据库配置
        engineConfiguration.setJdbcUrl(url);
        engineConfiguration.setJdbcDriver(driverClassName);
        engineConfiguration.setJdbcUsername(username);
        engineConfiguration.setJdbcPassword(password);

        // 邮箱配置 进入新流程时，就会向该邮箱发送信息
        engineConfiguration.setMailServerHost("");
        engineConfiguration.setMailServerPort(25);
        engineConfiguration.setMailServerDefaultFrom("");
        engineConfiguration.setMailServerUsername("");
        engineConfiguration.setMailServerPassword("");


        engineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        return engineConfiguration;
    }
}
