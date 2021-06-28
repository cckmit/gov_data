package com.cloud.dips.gov.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author johan
 */
@Data
public class ComparisonListVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String comparisonKey;

	private String comparisonName;
}
