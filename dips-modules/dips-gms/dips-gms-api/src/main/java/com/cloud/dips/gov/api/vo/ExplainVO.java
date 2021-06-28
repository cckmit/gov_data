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
public class ExplainVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 政策解读标题
	 */
	private String title;
	/**
	 * 解读时间
	 */
	private Date publishTime;
	/**
	 * 发布时间
	 */
	private Date createTime;
	/**
	 * 优先级
	 */
	private Integer priority;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级))
	 */
	private String level;

	private String levelName;
	/**
	 * 解读主体
	 */
	private String main;

	private String mainName;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 正文
	 */
	private String text;
	/**
	 * 标题图
	 */
	private String image;
	/**
	 * 原文链接
	 */
	private String url;
	/**
	 * 附件
	 */
	private  String file;

	/**
	 * 标签
	 */
	private List<CommonVO> tagList = new ArrayList<CommonVO>();

	/**
	 * 政策原文
	 */
	private List<CommonVO> policyList = new ArrayList<CommonVO>();

	/**
	 * 部门
	 */
	private List<CommonVO> organizationList = new ArrayList<CommonVO>();

	/**
	 * 主题(数据字典)
	 */
	private String theme;

	/**
	 * 适用行业(数据字典)
	 */
	private String industry;
	/**
	 * 浏览次数
	 */
	private Integer views;
	/**
	 * 后一篇
	 */
	private CommonVO next = new CommonVO();
	/**
	 * 前一篇
	 */
	private CommonVO prev = new CommonVO();
	/**
	 * 创建人id
	 */
	private Integer creatorId;
	/**
	 * 前一篇
	 */
	private String creatorName;
	/**
	 * 主题字典
	 */
	private List<String> themeList = new ArrayList<String>();
	/**
	 * 行业字典
	 */
	private List<String> industryList = new ArrayList<String>();
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
	private Date commitTime;
	
	/**
	 * 审核人姓名
	 */
	private  String examineUserName;
	/**
	 * 发文单位（关联机构）
	 */
	private List<CommonVO> dispatchList = new ArrayList<CommonVO>();
}
