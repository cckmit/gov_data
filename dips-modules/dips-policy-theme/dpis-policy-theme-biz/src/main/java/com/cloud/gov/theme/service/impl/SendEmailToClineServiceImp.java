package com.cloud.gov.theme.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.theme.api.dto.SendEmailToClineDTO;
import com.cloud.dips.theme.api.entity.WebUserPush;
import com.cloud.dips.theme.api.vo.SendEmailToClineVO;
import com.cloud.gov.theme.mapper.WebUserPushMapper;
import com.cloud.gov.theme.service.SendEmailToClineService;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service("SendEmailToClineService")
public class SendEmailToClineServiceImp implements SendEmailToClineService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private WebUserPushMapper webUserPushMapper;
	@Autowired
	private FreeMarkerConfigurer configurer;
	
	@Value("${spring.mail.username}")
	private String senderMail;
	@Override
	public void sendSimpleEmail() {
		
		try {
			List<SendEmailToClineDTO> userInformation = webUserPushMapper.selectEmailUser();
			if (userInformation.size() == 0) {
				return;
			}
			List<SendEmailToClineDTO> emailAddress = webUserPushMapper.selectEmailAddress();
			for (SendEmailToClineDTO address : emailAddress) {
				if (StringUtils.isBlank(address.getEmail())) {
					continue;
				}
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
			List<SendEmailToClineVO> emailContent = new ArrayList<SendEmailToClineVO>();
			  Map<String, Object> model = new HashMap<String, Object>();
				for (SendEmailToClineDTO user : userInformation) {
					if (address.getId().equals(user.getId()) && "general".equals(user.getMark())){
					SendEmailToClineVO sendEmailToClineVO =  new SendEmailToClineVO();
					sendEmailToClineVO.setTitle(user.getTitle());
					sendEmailToClineVO.setUrl("http://gc.govmade.cn/detail/general/"+user.getPolicyId());
					emailContent.add(sendEmailToClineVO);
					continue;
				}
					if (address.getId().equals(user.getId()) && "declare".equals(user.getMark())){
					SendEmailToClineVO sendEmailToClineVO =  new SendEmailToClineVO();
					sendEmailToClineVO.setTitle(user.getTitle());
					sendEmailToClineVO.setUrl("http://gc.govmade.cn/detail/declare/"+user.getPolicyId());
					emailContent.add(sendEmailToClineVO);
				}
			}
				if (emailContent.size() == 0) {
					continue;
				}
				model.put("emailContent", emailContent);
				model.put("userName", address.getUserName());
				String text;
				try {
					Template template = configurer.getConfiguration().getTemplate("emailTemplate.ftl");
					text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
					helper.setFrom(senderMail);
					helper.setTo(address.getEmail());
					helper.setSubject("国策-----已为您搜罗了最新政策");
					helper.setText(text, true);
					mailSender.send(message);
				} catch (TemplateNotFoundException e) {
					e.printStackTrace();
				} catch (MalformedTemplateNameException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TemplateException e) {
					e.printStackTrace();
				}
			
		}
			webUserPushMapper.updateForSet("is_push=1", new EntityWrapper<>(new WebUserPush()));
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	@Override
	public List<Integer> selectPushUser() {
		return webUserPushMapper.selectPushUser();
	}

}
