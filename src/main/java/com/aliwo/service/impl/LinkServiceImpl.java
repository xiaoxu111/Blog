package com.aliwo.service.impl;

import com.aliwo.dao.LinkDao;
import com.aliwo.entity.Link;
import com.aliwo.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 友情链接Service实现类
 * @author Administrator
 *
 */
@Service("linkService")
public class LinkServiceImpl implements LinkService {

	@Autowired
	private LinkDao linkDao;
	
	@Override
    public int add(Link link) {
		return linkDao.add(link);
	}

	@Override
	public int update(Link link) {
		return linkDao.update(link);
	}

	@Override
	public List<Link> list(Map<String, Object> map) {
		return linkDao.list(map);
	}

	@Override
    public Long getTotal(Map<String, Object> map) {
		return linkDao.getTotal(map);
	}

	@Override
    public Integer delete(Integer id) {
		return linkDao.delete(id);
	}

}
