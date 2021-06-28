package com.cloud.gds.gmsanalyse.service;

import com.cloud.gds.gmsanalyse.GdsGmsAnalyseApplication;
import com.cloud.gds.gmsanalyse.entity.PolicyDeconstruction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest(classes = GdsGmsAnalyseApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class GovAnalyseServiceTest {

	@Autowired
	private PolicyDeconstructionService policyDeconstructionService;

	@Test
	public void judge(){
		PolicyDeconstruction id = policyDeconstructionService.selectByPolicyId(238840L);
		if (id == null){
			System.out.println(2345678);
		}else {
			System.out.println("hdjsa");
		}
	}

}
