package com.wrial.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wrial.ZKUtil.ZKCurator;
import com.wrial.mapper.BgmMapper;
import com.wrial.mapper.VideosMapper;
import com.wrial.pojo.Bgm;
import com.wrial.pojo.BgmExample;
import com.wrial.service.VideoService;
import com.wrial.utils.JsonUtils;
import com.wrial.utils.PagedResult;
import enums.BGMOperatorTypeEnum;
import org.n3r.idwroker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /*
    为什么使用Map呢？
    1.简化小程序后端的字符串切割（如果使用字符串转为Json格式，需要切割字符串）
    2.在删除操作时，如果没有预先保存，是在后边是查不到的，因此使用Map也能更简单的保存值，并传递到小程序后端
     */
    @Override
    public void addBgm(Bgm bgm) {
        String bgmId = sid.nextShort();
        bgm.setId(bgmId);
        bgmMapper.insert(bgm);

        Map<String, String> map = new HashMap<>();
        map.put("operType", BGMOperatorTypeEnum.ADD.type);
        map.put("path", bgm.getPath());

        zkCurator.sendBgmOperator(bgmId, JsonUtils.objectToJson(map));
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
        Bgm bgm = bgmMapper.selectByPrimaryKey(id);
        Map<String, String> map = new HashMap<>();
        map.put("operType", BGMOperatorTypeEnum.DELETE.type);
        map.put("path", bgm.getPath());

        zkCurator.sendBgmOperator(id, JsonUtils.objectToJson(map));
        bgmMapper.deleteByPrimaryKey(id);

    }
}
