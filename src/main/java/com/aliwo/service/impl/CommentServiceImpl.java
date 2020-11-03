package com.aliwo.service.impl;

import com.aliwo.dao.CommentDao;
import com.aliwo.entity.Comment;
import com.aliwo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 评论Service实现类
 *
 * @author Administrator
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public int add(Comment comment) {
        return commentDao.add(comment);
    }

    @Override
    public List<Comment> list(Map<String, Object> map) {
        return commentDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return commentDao.getTotal(map);
    }

    @Override
    public Integer delete(Integer id) {
        return commentDao.delete(id);
    }

    @Override
    public int update(Comment comment) {
        return commentDao.update(comment);
    }

}
