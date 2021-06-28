package com.cloud.dips.gov.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 申报政策模型（采集表）
 *
 * @author BigPan
 * @date 2018-10-24 11:25:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scrapy_gov_policy_declare")
public class ScrapyGovPolicyDeclare extends Model<ScrapyGovPolicyDeclare> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
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
	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级))
	 */
	private String level;
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
	/**
	 * 材料递交要求
	 */
	private String requirement;
	/**
	 * 申报状态（1|即将开始  2|申报中  3|已截止）
	 */
	private String status;
	/**
	 * 专项类型
	 */
	private String special;
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
	 * 失效时间
	 */
	private Date invalidTime;
	/**
	 * 正文
	 */
	private String text;
	/**
	 * 附件（附件地址）
	 */
	private String attachment;
	/**
	 * 全文下载
	 */
	private String file;
	/**
	 * 原文链接
	 */
	private String url;
	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@TableField("is_deleted")
	private String delFlag;
	/**
	 * 申报对象
	 */
	private String target;
	/**
	 * 扶持方式
	 */
	private String mode;
	/**
	 * 扶持形式
	 */
	private String formality;
	/**
	 * 支持方式
	 */
	private String support;
	/**
	 * 主题
	 */
	private String theme;
	/**
	 * 扶持资金规模
	 */
	private String fund;
	/**
	 * 适用规模
	 */
	private String scale;
	/**
	 * 行业
	 */
	private String industry;
	/**
	 * 标签
	 */
	private String tag;
	/**
	 * 创建者
	 */
	private Integer creatorId;

	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
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
