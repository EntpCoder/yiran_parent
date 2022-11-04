package com.yiran.user;

import com.yiran.model.entity.Comments;
import com.yiran.user.mapper.CommentsMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestApp {
    @Autowired
    public CommentsMapper commentsMapper;


    @Test
    public void test(){
//        Comments comment=new Comments();
//        comment.setCommentId("1001");
//        comment.setText("aaaaaaaaaa");
//        comment.setUserId("101");
//        comment.setProId("101");
//        comment.setOrderId("1576915919203557377");
//        int row=commentsMapper.insert(comment);
//        System.out.println(row);
    }
}
