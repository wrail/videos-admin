package com.wrial.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wrial.ZKUtil.ZKCurator;
import com.wrial.mapper.BgmMapper;
import com.wrial.mapper.VideosMapper;
import com.wrial.pojo.Bgm;
import com.wrial.pojo.BgmExample;
import com.wrial.service.VideoService;
import com.wrial.utils.PagedResult;
import enums.BGMOperatorTypeEnum;
import org.n3r.idwroker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private BgmMapper bgmMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private ZKCurator zkCurator;

    @Override
    public void addBgm(Bgm bgm) {
        String bgmId = sid.nextShort();
        bgm.setId(bgmId);
        bgmMapper.insert(bgm);
        zkCurator.init();
        zkCurator.sendBgmOperator(bgmId, BGMOperatorTypeEnum.ADD.value);
    }

    @Override
    public PagedResult queryBgmList(Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        BgmExample example = new BgmExample();
        List<Bgm> list = bgmMapper.selectByExample(example);

        PageInfo<Bgm> pageList = new PageInfo<>(list);

        PagedResult result = new PagedResult();
        result.setTotal(pageList.getPages());
        result.setRows(list);
        result.setPage(page);
        result.setRecords(pageList.getTotal());
        return result;
    }

    /*
    删除BGM
     */
    @Override
    public void deleteBgm(String id) {
        bgmMapper.deleteByPrimaryKey(id);

    }
}
