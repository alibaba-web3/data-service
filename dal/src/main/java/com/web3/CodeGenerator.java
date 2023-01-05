package com.web3;

import java.util.Collections;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */

public class CodeGenerator {

    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://alibaba-web3.mysql.polardb.singapore.rds.aliyuncs.com:3306/service", "web3", "Alibaba_web3_data")
            .globalConfig(builder -> {
                builder.author("mianyun") // 设置作者
                    //.enableSwagger() // 开启 swagger 模式
                    .outputDir("/Users/thomsonyang/Desktop/code/opensource/data-service/dal/src/main/java"); // 指定输出目录
            })
            .packageConfig(builder -> {
                builder.parent("com.web3") // 设置父包名
                    .moduleName("generate")// 设置父包模块名
                    .pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/thomsonyang/Desktop/code/opensource/data-service/dal/src/main/resources/mapper")); // 设置mapperXml生成路径
            })
            .strategyConfig(builder -> {
                builder
                    .addInclude("tags") // 设置需要生成的表名。多张表 , 分隔
                    .entityBuilder()
                    .logicDeleteColumnName("deleted")
                    .enableFileOverride()
                    .enableLombok()
                    .serviceBuilder()
                    .convertServiceFileName((entityName -> entityName + ConstVal.SERVICE)); // service 文件名
            })
            .execute();
    }
}
