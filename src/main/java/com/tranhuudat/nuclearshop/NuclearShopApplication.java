package com.tranhuudat.nuclearshop;

import com.tranhuudat.nuclearshop.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class NuclearShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(NuclearShopApplication.class, args);
    }

}
