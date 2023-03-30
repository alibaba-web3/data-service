package com.web3.message;

import java.util.List;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/30
 */
public interface Consumer<IRule> {

    void consume();

    List<IRule> getSubscribeRules();

}
