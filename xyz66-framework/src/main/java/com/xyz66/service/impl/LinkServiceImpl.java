package com.xyz66.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyz66.constants.SystemConstants;
import com.xyz66.domain.ResponseResult;
import com.xyz66.domain.entity.Link;
import com.xyz66.domain.vo.LinkVo;
import com.xyz66.domain.vo.PageVo;
import com.xyz66.mapper.LinkMapper;
import com.xyz66.service.LinkService;
import com.xyz66.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author xyz66 Email:2910223554@qq.com
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    
    @Override
    public ResponseResult getAllLink() {
//        //查询所有审核通过的
//        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
//        List<Link> links = list(queryWrapper);
//        //转换成vo
//        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
//        //封装返回
        List<Link> list = this.lambdaQuery()
                // 友链状态正常的
                .eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL)
                .list();
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(link.getName()),Link::getName, link.getName());
        queryWrapper.eq(Objects.nonNull(link.getStatus()),Link::getStatus, link.getStatus());

        Page<Link> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<Link> categories = page.getRecords();

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(categories);
        return pageVo;
    }
}

