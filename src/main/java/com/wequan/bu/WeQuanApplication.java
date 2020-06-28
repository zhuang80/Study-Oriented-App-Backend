package com.wequan.bu;

import com.wequan.bu.im.ServerLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ChrisChen
 */
@SpringBootApplication
//@EnableSwagger2
@EnableTransactionManagement
@EnableScheduling
public class WeQuanApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(WeQuanApplication.class, args);
    }

    @Autowired
    private ServerLauncher nettyServer;

    @Override
    public void run(String... args) throws Exception {
        final ServerLauncher sl = new ServerLauncher();
        try {
            nettyServer.startup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

