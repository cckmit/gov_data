package com.cloud.dips.gov.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;

/**
 * @author C.Z.H
 */
@Data
public class SenseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 关键词
	 */
	private String keywords;
	/**
	 * 描述
	 */
	private String introduce;
	/**
	 * 发文时间
	 */
	private Date publishTime;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 简介
	 */
	private String summary;
	/**
	 * 正文
	 */
	private String text;
	/**
	 * 标签
	 */
	private List<CommonVO> tagList;
	/**
	 * 后一篇
	 */
	private CommonVO next;
	/**
	 * 前一篇
	 */
	private CommonVO prev;
	/**
	 * 创建者id
	 */
	private Integer creatorId;
	/**
	 * 创建者用户名
	 */
	private String creatorName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 标记
	 */
	private String mark;
	/**
	 * 采集库关联ID
	 */
	private Integer scrapyId;
	/**
	 * 审核状态
	 */
	private Integer examineStatus;
	
	/**
	 * 审核人
	 */
	@TableField("examine_user_id")
	private Integer examineUser;
	
	/**
	 * 审核日期
	 */
	private Date examineDate;
	
	/**
	 * 退回次数
	 */
	private Integer retreatCount;
	
	/**
	 * 退回人
	 */
	private Integer retreatUser;
	
	/**
	 * 退回原因
	 */
	private String retreatContent;
	
	/**
	 * 加工者
	 */
	private Integer processorId;
}
