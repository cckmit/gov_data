package com.cloud.dips.gov.api.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * @author C.Z.H
 */
@Data
public class DictVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String key;

	private String value;
}
