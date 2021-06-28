package com.cloud.dips.gov.api.dto;

import com.cloud.dips.gov.api.entity.GovOrganization;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author BlakcR
 * @date 2018-10-09 14:00:07
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class OrganizationDTO extends GovOrganization {

	/**
	 * 机构标签
	 */
	private List<String> tagList;
}
