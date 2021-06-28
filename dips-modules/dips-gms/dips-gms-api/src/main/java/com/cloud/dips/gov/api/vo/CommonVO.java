package com.cloud.dips.gov.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author C.Z.H
 */
@Data
public class CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer commonId;

	private String commonName;
	
	private Integer dispatchId;
	
    private String dispatchName;
    
    private Integer tagId;
	
    private String tagName;
    
    private Integer relationId;
}
