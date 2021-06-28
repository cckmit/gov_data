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
 * @author C.Z.H
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("scrapy_gov_policy_general")
public class ScrapyGovPolicyGeneral extends Model<ScrapyGovPolicyGeneral> {
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
	 * 时效性( 0 有效  1 失效）依据失效日期判断是否过期
	 */
	private String timeliness;
	/**
	 * 政策阶段状态（1|征求意见中  2|已印发）
	 */
	private String stage;
	/**
	 * 发文形式（1|直接印发  2|转发）
	 */
	private String formality;
	/**
	 * 有效年限（X年）
	 */
//	private String effective;
	/**
	 * 成文时间
	 */
//	private Date writeTime;
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
	 * 原文链接
	 */
	private String url;
	/**
	 * 0是正常，1是删除
	 */
	@TableField("is_deleted")
	private String delFlag;
	/**
	 * 关联标签
	 */
	private String tag;
	/**
	 * 适用主题
	 */
	private String target;
	/**
	 * 使用主题
	 */
	private String theme;
	/**
	 * 使用规模
	 */
	private String fund;
	/**
	 * 使用行业
	 */
	private String industry;
	/**
	 * 附件（附件地址）
	 */
	private String attachment;
	/**
	 * 上传者
	 */
	private Integer creatorId;
	/**
	 * 全文下载
	 */
	private String file;

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
	 * 地域
	 */
	@TableField("region")
	private String region;
	
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
