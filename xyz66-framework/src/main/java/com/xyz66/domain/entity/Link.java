package com.xyz66.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 友链(Link)表实体类
 *
 * @author makejava
 * @since 2022-02-03 12:22:50
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "友链实体类")
@TableName("sg_link")
public class Link  {
    
    @ApiModelProperty(notes = "友链id")
    @TableId
    private Long id;

    @ApiModelProperty(notes = "友链名称")
    private String name;
    
    @ApiModelProperty(notes = "友链logo")
    private String logo;
    
    @ApiModelProperty(notes = "友链描述")
    private String description;
    //网站地址
    @ApiModelProperty(notes = "网站地址")
    private String address;
    //审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
    @ApiModelProperty(notes = "审核状态")
    private String status;
    
    @ApiModelProperty(notes = "创建人")
    private Long createBy;
    
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
    
    @ApiModelProperty(notes = "更新人")
    private Long updateBy;
    
    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    @ApiModelProperty(notes = "删除标志")
    private Integer delFlag;
}
