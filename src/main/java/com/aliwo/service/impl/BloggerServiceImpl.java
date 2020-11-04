package com.aliwo.service.impl;

import com.aliwo.dao.BloggerDao;
import com.aliwo.entity.Blogger;
import com.aliwo.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 博主Service实现类
 * @author xuyy19
 *
 */
@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService {

	@Autowired
	private BloggerDao bloggerDao;

	@Override
    public Blogger find() {
		return bloggerDao.find();
	}

	@Override
	public Blogger getByUserName(String userName) {
		return bloggerDao.getByUserName(userName);
	}

	@Override
	public Integer update(Blogger blogger) {
		return bloggerDao.update(blogger);
	}
	
	
}
