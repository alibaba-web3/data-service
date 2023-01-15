package com.web3.crawler.annotation;

import com.web3.crawler.constants.Interval;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author: smy
 * @Date: 2023/1/12 7:42 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ProcessorConfig {

    Interval value() default Interval.Hour;

    String name();
}
