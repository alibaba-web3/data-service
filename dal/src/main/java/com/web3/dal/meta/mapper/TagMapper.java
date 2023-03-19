package com.web3.dal.meta.mapper;

import com.web3.dal.meta.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author mianyun
 * @since 2023-01-05
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> selectByParam(Tag query);
}
