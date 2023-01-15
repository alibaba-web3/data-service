package com.web3.dal;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */

public class CodeGenerator {


    /**
     * 生成dal层，每次覆盖
     * @param type data | meta
     * @param tableName 表名称，多个用,隔开
     */
    public static void generate(String type, String tableName) {
        // 指定输出目录
        String outputDir = System.getProperty("user.dir") + "/dal/src/main/java";
        String mapperDir = System.getProperty("user.dir") + "/dal/src/main/resources/mapper";

        Map<OutputFile, String> pathInfo = new HashMap<>();
        // 设置mapperXml生成路径
        pathInfo.put(OutputFile.xml, mapperDir);

        String dbName = "service";
        if ("data".equals(type)) {
            dbName = "blockchain";
        }

        FastAutoGenerator.create("jdbc:mysql://alibaba-web3.mysql.polardb.singapore.rds.aliyuncs.com:3306/" + dbName, "web3", "Alibaba_web3_data")
                .globalConfig(builder -> {
                    // 设置作者
                    builder.author("system")
                            // 开启 swagger 模式
                            //.enableSwagger()
                            .outputDir(outputDir);
                })
                .packageConfig(builder -> {
                    // 设置父包名
                    builder.parent("com.web3.dal")
                            // 设置父包模块名
                            .moduleName(type)
                            .pathInfo(pathInfo);
                })
                .strategyConfig(builder -> {
                    // 设置需要生成的表名。多张表 , 分隔
                    builder.addInclude(tableName)
                            .entityBuilder()
                            .logicDeleteColumnName("deleted")
                            .enableFileOverride()
                            .disableSerialVersionUID()
                            .enableLombok()
                            .serviceBuilder()
                            // service 文件名
                            .convertServiceFileName((entityName -> entityName + "Mapper" + ConstVal.SERVICE));
                }).templateConfig(builder -> {
                    // 不生成controller
                    builder.controller("")
                            //.service("")
                            //.serviceImpl("")
                    ;
                })
                .execute();
    }

    public static void main(String[] args) {
        //generate("meta", "data_metric,crawler_task");
        generate("meta", "crawler_task");
        //generate("data", "ethereum_blocks");

    }

}
