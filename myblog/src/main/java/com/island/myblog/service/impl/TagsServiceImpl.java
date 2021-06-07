package com.island.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.island.myblog.common.dto.PageDTO;
import com.island.myblog.entity.Tags;
import com.island.myblog.mapper.TagsMapper;
import com.island.myblog.service.TagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    @Autowired
    private TagsMapper tagsMapper;

    @Transactional
    @Override
    public PageDTO<Tags> listTags() {
        List<Tags> tagsList = tagsMapper.selectList(null);
        Integer count = tagsMapper.selectCount(null);
        return new PageDTO<Tags>(tagsList, count);
    }
}
