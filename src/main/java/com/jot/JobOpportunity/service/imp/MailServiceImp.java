package com.jot.JobOpportunity.service.imp;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import com.jot.JobOpportunity.dto.autosearch.AutoSearchRunDto;
import com.jot.JobOpportunity.dto.post.PostAutoSearchDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.dto.account.AccountInforSendMail;
import com.jot.JobOpportunity.dto.account.AccountOtpSendMail;
import com.jot.JobOpportunity.service.MailService;

@Service
@Transactional
public class MailServiceImp implements MailService {

	private final Logger log = LoggerFactory.getLogger(MailServiceImp.class);

	private final SpringTemplateEngine templateEngine;

	private final JavaMailSender javaMailSender;

	public MailServiceImp(SpringTemplateEngine templateEngine, JavaMailSender javaMailSender) {
		this.templateEngine = templateEngine;
		this.javaMailSender = javaMailSender;
	}

	@Async
	@Override
	public void sendMailRegister(AccountInforSendMail account) {
		log.debug("Request send mail register to: " + account.getEmail());
		Locale locale = Locale.forLanguageTag("vn");
		Context context = new Context(locale);
		context.setVariable(Constants.ACCOUNT, account);
		String content = templateEngine.process("email/register-account", context);
		this.sendEmail(Constants.SUBJECT_CREATE_ACCOUNT, content, account.getEmail());
	}

	@Async
	@Override
	public void sendMailOtp(AccountOtpSendMail account) {
		log.debug("Request send mail register to: " + account.getEmail());
		Locale locale = Locale.forLanguageTag("vn");
		Context context = new Context(locale);
		context.setVariable(Constants.ACCOUNT, account);
		String content = templateEngine.process("email/otp", context);
		this.sendEmail(Constants.SUBJECT_OTP, content, account.getEmail());
	}

	@Async
	@Override
	public void sendMailSuccessOtp(AccountInforSendMail account){
		log.debug("Request send mail success otp to: " + account.getEmail());
		Locale locale = Locale.forLanguageTag("vn");
		Context context = new Context(locale);
		context.setVariable(Constants.ACCOUNT, account);
		String content = templateEngine.process("email/otp-success", context);
		this.sendEmail(Constants.SUCCESS_OTP_ACCOUNT, content, account.getEmail());
	}

	@Async
	void sendEmail(String subject, String content, String email) {
		log.debug("Send email to '{}' with subject '{}' and content={}", email, subject, content);
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
			message.setTo(email);
			message.setSubject(subject);
			message.setText(content, true);
			javaMailSender.send(mimeMessage);
			log.debug("Sent email to: " + email);
		} catch (MailException | MessagingException e) {
			log.debug("Fail to send mail to: " + email);
		}
	}
	@Async
	@Override
	public void sendMailAutoSearch(List<PostAutoSearchDto> posts, AutoSearchRunDto autoSearchRunDto){
		log.debug("Request send mail auto search: " + autoSearchRunDto.getEmail());
		Locale locale = Locale.forLanguageTag("vn");
		Context context = new Context(locale);
		context.setVariable(Constants.POSTS, posts);
		String content = templateEngine.process("email/auto-search", context);
		this.sendEmail(Constants.SUCCESS_AUTO_SEARCH, content, autoSearchRunDto.getEmail());
	}

}
