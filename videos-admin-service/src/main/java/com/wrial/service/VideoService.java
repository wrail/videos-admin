package com.wrial.service;


import com.wrial.pojo.Bgm;
import com.wrial.utils.PagedResult;

public interface VideoService {

	public void addBgm(Bgm bgm);

	public PagedResult queryBgmList(Integer page, Integer pageSize);

	void deleteBgm(String bgmId);

    public void updateVideoStatus(String videoId, Integer status);

    PagedResult queryReportList(Integer page, int i);
}
