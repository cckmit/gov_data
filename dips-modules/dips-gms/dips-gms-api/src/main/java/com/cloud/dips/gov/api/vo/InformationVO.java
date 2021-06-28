package com.cloud.dips.gov.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;

/**
 * @author C.Z.H
 */
@Data
public class InformationVO implements Serializable {

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
	 * 发布时间
	 */
	private Date publishTime;
	/**
	 * 发布时间
	 */
	private Date createTime;

	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 浏览量
	 */
	private Integer views;
	/**
	 * 正文
	 */
	private String text;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 附件信息
	 */
	private String file;
	/**
	 * 原文链接
	 */
	private String url;
	/**
	 * 标签
	 */
	private List<CommonVO> tagList = new ArrayList<CommonVO>();
	/**
	 * 创建者id
	 */
	private Integer creatorId;
	/**
	 * 创建者name
	 */
	private String creatorName;
	/**
	 * 优先级
	 */
	private Integer priority;
	/**
	 * 后一篇
	 */
	private CommonVO next = new CommonVO();
	/**
	 * 前一篇
	 */
	private CommonVO prev = new CommonVO();
	/**
	 * 标记
	 */
	private String mark;

	/**
	 * 地域
	 */
	private String region;
	/**
	 * 地域编码
	 */
	private String regionArrString;
	
	/**
	 * 地域编码
	 */
	private List<String> regionArr = new ArrayList<String>();
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
	
	/**
	 * 采集库关联ID
	 */
	private Integer scrapyId;
	/**
	 * 文件下载名称
	 */
	private String newFile;
	/**
	 * 文件下载路径
	 */
	private String newUrl;

	/**
	 * 提交时间
	 */
	@TableField("commit_time")
	private Date commitTime;
	
	/**
	 * 相关标签
	 */
	private  String relatedTags;

	/**
	 * 标签相识度
	 */
	private float  similarityDegree;

	/**
	 * 审核人姓名
	 */
	private  String examineUserName;
}
