package com.yiran.user.controller;

import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.vo.CommentVo;
import com.yiran.user.service.ICommentsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author weiyuwen
 */
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final ICommentsService commentsService;

    public CommentController(ICommentsService commentsService) {
        this.commentsService = commentsService;
    }
    @GetMapping("/selectbyproId/{proId}")
    public R<List<CommentVo>> selectbyproId(@PathVariable("proId") String proId){
        List<CommentVo> commentsList=commentsService.selectbyproId(proId);
        return commentsList==null?R.fail(ResultCodeEnum.FAIL):R.ok("ok",commentsList);
    }
    @PostMapping("/addComment")
    public R<Boolean> addComment(@RequestHeader("userId") String userId,String proId,String content,String addr,Float describe,Float service,Float logisties){
        return commentsService.addComment(userId,proId,content,addr,describe,service,logisties)?R.ok("ok",true):R.fail(ResultCodeEnum.FAIL);
    }
}
