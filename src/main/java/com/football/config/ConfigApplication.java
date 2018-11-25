package com.football.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;

@EnableAsync
@SpringBootApplication
@EnableScheduling
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.football"})
public class ConfigApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(ConfigApplication.class, args);
//    }

    private static final Logger LOGGER = LogManager.getLogger(ConfigApplication.class);

    public static void main(String[] args) {
        long id = System.currentTimeMillis();
        LOGGER.info("[B][" + id + "] >>>>>>>>>>>>>>>>>>>>>>>>>> Start ConfigApplication ...");
        SpringApplication app = new SpringApplication(ConfigApplication.class);

        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String ipServer = "localhost";
        try {
            ipServer = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            ipServer = env.getProperty("server.address") != null ? env.getProperty("server.address") : "localhost";
        }
        LOGGER.info("----------------------------------------------------------");
        LOGGER.info("   Application         : " + env.getProperty("spring.application.name"));
        LOGGER.info("   Url                 : " + protocol + "://" + ipServer + ":" + env.getProperty("server.port"));
        LOGGER.info("   Profile(s)          : " + env.getActiveProfiles()[0]);
        LOGGER.info("----------------------------------------------------------");

        LOGGER.info("[E][" + id + "][Duration = " + (System.currentTimeMillis() - id) + "] >>>>>>>>>>>>>>>>>>>>>>>>>> End Start ConfigApplication ...");
    }
}
