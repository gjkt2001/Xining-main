package com.xyz66.controller;


import com.xyz66.config.SwaggerConfig;
import com.xyz66.domain.ResponseResult;
import com.xyz66.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@Api(tags = {SwaggerConfig.TAG_1})
@Api(tags = SwaggerConfig.TAG_1)
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @ApiOperation(value = "获取热门文章", notes = "获取热门文章")
    public ResponseResult hotArticleList() {

        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    @ApiOperation(value = "分页查询文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
            @ApiImplicitParam(name = "categoryId", value = "分类ID"),
    })
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @PutMapping("/updateViewCount/{id}")
    @ApiOperation(value = "更新浏览次数+1")
    @ApiImplicitParam(name = "id",value = "文章ID")
    public ResponseResult updateViewCount(@PathVariable("id") Long id) {
        return articleService.updateViewCount(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取文章详细信息")
    @ApiImplicitParam(name = "id", value = "文章ID")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }
}
