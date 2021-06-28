package com.cloud.dips.gov.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 申报政策模型
 *
 * @author C.Z.H
 * @date 2018-09-11 11:23:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_policy_declare")
public class GovPolicyDeclare extends Model<GovPolicyDeclare> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 标题
	 */
	@TableField("title")
	private String title;
	/**
	 * 摘要
	 */
	@TableField("summary")
	private String summary;
	/**
	 * 来源
	 */
	@TableField("source")
	private String source;
	/**
	 * 索引号
	 */
	@TableField("reference")
	private String reference;
	/**
	 * 发文号
	 */
	@TableField("issue")
	private String issue;
	/**
	 * 文体（1|通知 2|公告 3|报告 4|意见 5|办法 6|通报 7|其他）
	 */
	@TableField("style")
	private String style;
	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级))
	 */
	@TableField("level")
	private String level;
	/**
	 * 申报条件
	 */
	@TableField("condition")
	private String condition;
	/**
	 * 扶持标准
	 */
	@TableField("standard")
	private String standard;
	/**
	 * 办理流程
	 */
	@TableField("process")
	private String process;
	/**
	 * 申报方式（1|自主申报  2|主管部门推荐）
	 */
	@TableField("method")
	private String method;
	/**
	 * 材料递交要求
	 */
	@TableField("requirement")
	private String requirement;
	/**
	 * 申报状态（1|即将开始  2|申报中  3|已截止）
	 */
	@TableField("status")
	private String status;
	/**
	 * 专项类型（工业类：
	 * 1|环境治理专项资金
	 * 2|工业发展资金
	 * 3|科技创新资金
	 * 4|科技三项经费
	 * 5|专利专项资金
	 * 6|企业更新改造资金
	 * 7|外贸企业出口奖励补贴
	 * 8|重大项目前期工作经费
	 * 9|散装水泥专项资金
	 * 农林水类：
	 * 10|康庄工程建设资金
	 * 11|森林生态效益资金
	 * 12|新农村建设资金
	 * 13|农发资金
	 * 14|农技专项资金
	 * 15|国有土地出让金
	 * 16|水利资金
	 * 文教类：
	 * 17|改善教育教学条件专项资金
	 * 18|文化发展、文物保护专项资金
	 * 社会保障类：
	 * 19|最低生活保障资金
	 * 20|再就业资金
	 * 21|新型农村合作医疗资金
	 * 22|社会医疗救助资金
	 * 23|优抚金
	 * 24|城镇退役士兵就业安置金
	 * 其他类：
	 * 25|养路费专项资金
	 * 26|旅游发展资金
	 * 27|粮食补助专项资金
	 * 28|招商引资专项资金
	 * 29|人才开发专项资金
	 * 30|政法装备专项资金
	 * 31|其他专项资金）
	 */
	@TableField("special")
	private String special;
	/**
	 * 成文时间
	 */
	@TableField(value = "write_time", strategy = FieldStrategy.IGNORED)
	private Date writeTime;
	/**
	 * 发文时间
	 */
	@TableField(value = "publish_time", strategy = FieldStrategy.IGNORED)
	private Date publishTime;
	/**
	 * 生效时间
	 */
	@TableField(value = "effect_time", strategy = FieldStrategy.IGNORED)
	private Date effectTime;
	/**
	 * 失效时间
	 */
	@TableField(value = "invalid_time", strategy = FieldStrategy.IGNORED)
	private Date invalidTime;
	/**
	 * 正文
	 */
	@TableField("text")
	private String text;
	/**
	 * 附件（附件地址）
	 */
	@TableField("attachment")
	private String attachment;

	/**
	 * 原文链接
	 */
	@TableField("url")
	private String url;
	/**
	 * 点击量
	 */
	@TableField("views")
	private Integer views;
	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@TableField("is_deleted")
	private String delFlag;
	/**
	 * 创建者
	 */
	@TableField("creator_id")
	private Integer creatorId;
	/**
	 * 对象
	 */
	@TableField("target")
	private String target;
	/**
	 * 扶持方式
	 */
	@TableField("mode")
	private String mode;
	/**
	 * 扶持形式
	 */
	@TableField("formality")
	private String formality;
	/**
	 * 支持方式
	 */
	@TableField("support")
	private String support;
	/**
	 * 主题
	 */
	@TableField("theme")
	private String theme;
	/**
	 * 资金规模
	 */
	@TableField("fund")
	private String fund;
	/**
	 * 规模
	 */
	@TableField("scale")
	private String scale;
	/**
	 * 行业
	 */
	@TableField("industry")
	private String industry;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@TableField(value = "modified_time", update = "now()")
	private Date modifiedTime;
	/**
	 * 标记
	 */
	@TableField("mark")
	private String mark;
	
	/**
	 * 地域
	 */
	@TableField("region")
	private String region;
	
	/**
	 * 地域编码
	 */
	@TableField("region_arr")
	private String regionArray;

	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
	 * 提交时间
	 */
	@TableField("commit_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date commitTime;
}
