package com.web3.service.Tag.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.entity.Tag;
import com.web3.mapper.TagMapper;
import com.web3.service.Tag.TagService;
import org.springframework.stereotype.Service;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public boolean create() {
        return false;
    }
}
