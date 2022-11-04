package com.yiran.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.entity.Comments;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiran.user.mapper.CommentsMapper;
import com.yiran.user.service.ICommentsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {
    private final CommentsMapper commentsMapper;

    public CommentsServiceImpl(CommentsMapper commentsMapper) {
        this.commentsMapper = commentsMapper;
    }

    @Override
    public List<Comments> selectbyproId(String proId) {
        QueryWrapper<Comments> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pro_id",proId);
        return commentsMapper.selectList(queryWrapper);
    }

    @Override
    public List<Comments> selectbyUserId(String userId) {
        QueryWrapper<Comments> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return commentsMapper.selectList(queryWrapper);
    }
    @Override
    public Boolean addComment(String userId,String proId,String content,String imgAddr,Float describe,Float service,Float logisties){
        Comments comment=new Comments();
        BigDecimal score1=new BigDecimal(describe);
        BigDecimal score2=new BigDecimal(service);
        BigDecimal score3=new BigDecimal(logisties);
        comment.setText(content);
        comment.setImg(imgAddr);
        comment.setDescribeScore(score1);
        comment.setServiceScore(score2);
        comment.setLogisticsScore(score3);
        int row=commentsMapper.insert(comment);
        return row>0;
    }
}
