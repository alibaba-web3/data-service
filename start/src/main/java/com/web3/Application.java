package com.web3;

import java.util.TimeZone;

import com.web3.framework.utils.DateUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @author smy
 */

@SpringBootApplication(scanBasePackages = {"com.web3"}, exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        /**
         * 配置当前应用默认时区为GMT+00:00
         */
        System.setProperty("user.timezone", DateUtils.ZERO_TIMEZONE);
        TimeZone.setDefault(DateUtils.getZeroTimeZone());
        SpringApplication.run(Application.class, args);
    }
}
