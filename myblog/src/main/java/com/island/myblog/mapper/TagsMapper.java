package com.island.myblog.mapper;

import com.island.myblog.entity.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author island
 * @since 2021-05-12
 */
@Component
public interface TagsMapper extends BaseMapper<Tags> {


    /**
     * 查询这些标签的id
     * @param dynamicTags
     * @return
     */
    List<Integer> getTagsIdByTagName(@Param("tagNames") String[] dynamicTags);

    /**
     * 保存文章标准列表
     * @param tids
     * @param aid
     * @return
     */
    int saveTagsArticleTags(@Param("tids") List<Integer> tids, @Param("aid") Integer aid);

    /**
     * 删除该文章目前所有的标签
     * @param aid
     * @return
     */
    int deleteTagsByAid(@Param("aid") Integer aid);


    /**
     * 将上传上来的标签全部存入数据库
     * @param tags
     * @return
     */
    int saveTags(@Param("tags") String[] tags);
}
