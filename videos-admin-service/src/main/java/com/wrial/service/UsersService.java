package com.wrial.service;

import com.wrial.pojo.Users;
import com.wrial.utils.PagedResult;

public interface UsersService {

	public PagedResult queryUsers(Users user, Integer page, Integer pageSize);
	
}
