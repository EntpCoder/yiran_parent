package com.yiran.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.entity.Comments;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiran.model.entity.User;
import com.yiran.model.vo.CommentVo;
import com.yiran.user.mapper.*;
import com.yiran.user.service.ICommentsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private final UserMapper userMapper;


    public CommentsServiceImpl(CommentsMapper commentsMapper, UserMapper userMapper) {
        this.commentsMapper = commentsMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<CommentVo> selectbyproId(String proId) {
        List<CommentVo> commentVoList=new ArrayList<>();
        QueryWrapper<Comments> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pro_id",proId);
        List<Comments> commentsList=commentsMapper.selectList(queryWrapper);


        for (Comments c:commentsList
             ) {
            CommentVo commentVo=new CommentVo();
            BeanUtils.copyProperties(c,commentVo);
            //商品评分的平均分
            //不能直接加减。这里用add()函数
            BigDecimal num=new BigDecimal(3);
            BigDecimal describeScore=c.getDescribeScore();
            BigDecimal serviceScore=c.getServiceScore();
            BigDecimal logisticsScore=c.getLogisticsScore();
            BigDecimal score1=describeScore.add(serviceScore);
            BigDecimal score2=score1.add(logisticsScore);
            BigDecimal avgScore=score2.divide(num);
            commentVo.setSatisfactionScore(avgScore);
            commentVo.setCreateTime(c.getTime());
            //找出评论这个商品的用户
            User user=userMapper.selectById(c.getUserId());
            commentVo.setUserName(user.getUsername());
            commentVo.setImage(user.getImage());
            commentVo.setUserId(user.getUserId());
            commentVoList.add(commentVo);


        }
        return commentVoList;
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
