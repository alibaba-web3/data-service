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

    /**
     * 调度间隔
     * @return
     */
    Interval value() default Interval.Hour;

    /**
     * 处理器名称
     * @return
     */
    String name();

    /**
     * 单次调度数据周期
     * @return
     */
    Interval dataInterval() default Interval.Day;
}
