package com.cloud.dips.gov.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;

/**
 * @author C.Z.H
 * @date 2018/08/15
 */
@Data
public class DeclareVO implements Serializable {
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
	 * 摘要
	 */
	private String summary;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 索引号
	 */
	private String reference;
	/**
	 * 发文号
	 */
	private String issue;
	/**
	 * 文体（1|通知 2|公告 3|报告 4|意见 5|办法 6|通报 7|其他）
	 */
	private String style;
	private String styleName;
	/**
	 * 发文单位（关联机构）
	 */
	private List<CommonVO> dispatchList = new ArrayList<CommonVO>();
	/**
	 * 联合发文单位（关联机构）
	 */
	private List<CommonVO> unionList = new ArrayList<CommonVO>();
	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级))
	 */
	private String level;
	private String levelName;
	/**
	 * 申报对象（复选 1|企业  2|个人  3|政府  4|科研机构  5|行业协会  6|园区  7|其他）
	 */
	private String target;
	/**
	 * 扶持方式（复选 1|事前  2|事后  3|其他）
	 */
	private String mode;
	/**
	 * 扶持形式（复选 1|奖补  2|股权  3|投资  4|减免  5|其他）
	 */
	private String formality;
	/**
	 * 支持方式（复选 1|资金扶持  2|降低成本  3|评选认定  4|简化审批  5|其他）
	 */
	private String support;
	/**
	 * 申报条件
	 */
	private String condition;
	/**
	 * 扶持标准
	 */
	private String standard;
	/**
	 * 办理流程
	 */
	private String process;
	/**
	 * 申报方式（1|自主申报  2|主管部门推荐）
	 */
	private String method;
	private String methodName;
	/**
	 * 材料递交要求
	 */
	private String requirement;
	/**
	 * 适用区域（关联地域）
	 */
	private List<CommonVO> regionList = new ArrayList<CommonVO>();
	/**
	 * 主题（复选  1|企业创办  2|人才引进和能力提升  3|投资融资  4|税收优惠  5|市场拓展  品牌建设  7|创新研发  8|科研课题  9|成果转化  10|知识产权  11|信息化建设  12|资质认定  13|产业发展  14|节能环保  15|转型升级  16|改制上市  17|并购重组  18|平台基地建设  19|孵化器及基地建设  20|招商引资  21|其他）
	 */
	private String theme;
	/**
	 * 申报状态（1|即将开始  2|申报中  3|已截止）
	 */
	private String status;
	private String statusName;
	/**
	 * 专项分类
	 */
	private String special;
	private String specialName;
	/**
	 * 扶持资金规模（1|0-10万  2|10-50万  3|50-100万  4|100-200万  5|200-500万  6|500-1000万  7|1000-2000万  8|2000万以上）
	 */
	private String fund;
	/**
	 * 成文时间
	 */
	private Date writeTime;
	/**
	 * 发文时间
	 */
	private Date publishTime;
	/**
	 * 生效时间
	 */
	private Date effectTime;
	/**
	 * 生成时间
	 */
	private Date createTime;
	/**
	 * 失效时间
	 */
	private Date invalidTime;
	/**
	 * 关联政策
	 */
	private List<CommonVO> policyList = new ArrayList<CommonVO>();
	/**
	 * 适用规模（复选  1|大型企业  2|规上企业  3|中小企业  4|小微企业）
	 */
	private String scale;
	/**
	 * 行业
	 */
	private String industry;
	/**
	 * 正文
	 */
	private String text;
	/**
	 * 标签
	 */
	private List<CommonVO> tagList = new ArrayList<CommonVO>();
	/**
	 * 附件（附件地址）
	 */
	private String attachment;
	/**
	 * 原文链接
	 */
	private String url;
	/**
	 * 点击量
	 */
	private Integer views;
	/**
	 * 创建者id
	 */
	private Integer creatorId;
	/**
	 * 创建者用户名
	 */
	private String creatorName;
	/**
	 * 后一篇
	 */
	private CommonVO next = new CommonVO();
	/**
	 * 前一篇
	 */
	private CommonVO prev = new CommonVO();
	/**
	 * 对象字典
	 */
	private List<String> targetList = new ArrayList<String>();
	/**
	 * 扶持方式字典
	 */
	private List<String> modeList = new ArrayList<String>();
	/**
	 * 扶持形式字典
	 */
	private List<String> formalityList = new ArrayList<String>();
	/**
	 * 支持方式字典
	 */
	private List<String> supportList = new ArrayList<String>();
	/**
	 * 主题字典
	 */
	private List<String> themeList = new ArrayList<String>();
	/**
	 * 资金规模字典
	 */
	private List<String> fundList = new ArrayList<String>();
	/**
	 * 规模字典
	 */
	private List<String> scaleList = new ArrayList<String>();
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
