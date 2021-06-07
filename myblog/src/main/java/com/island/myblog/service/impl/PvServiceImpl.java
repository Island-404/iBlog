package com.island.myblog.service.impl;

import com.island.myblog.common.dto.PageViewDTO;
import com.island.myblog.entity.Pv;
import com.island.myblog.mapper.PvMapper;
import com.island.myblog.service.PvService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.island.myblog.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author island
 * @since 2021-05-23
 */
@Service
public class PvServiceImpl extends ServiceImpl<PvMapper, Pv> implements PvService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PvMapper pvMapper;


    @Scheduled(cron = " 0 0 0 * * ?")
    @Override
    public void saveUniqueView() {
        // 获取每天用户量
        Long count = redisTemplate.boundSetOps("ip_set").size();
        System.out.println(DateUtil.getSomeDay(new Date(), -1));
        // 获取昨天日期插入数据
        Pv pv = Pv.builder()
                .create_time(DateUtil.getSomeDay(new Date(), -1))
                .viewsCount(Objects.nonNull(count) ? count.intValue() : 0).build();
        pvMapper.insert(pv);
    }

    @Override
    public List<PageViewDTO> listPageViews() {
        String startTime = DateUtil.getMinTime(DateUtil.getSomeDay(new Date(), -7));
        String endTime = DateUtil.getMaxTime(new Date());
        return pvMapper.listPageViews(startTime,endTime);
    }
}
