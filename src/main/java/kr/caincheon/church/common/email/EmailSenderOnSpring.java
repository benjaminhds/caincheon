package kr.caincheon.church.common.email;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/*
 * 
 */
@Component
@Service("emailSenderOnSpring")
public class EmailSenderOnSpring
{

	private static final Logger _logger = LoggerFactory.getLogger(EmailSenderOnSpring.class);
	
    @Autowired // @Resource(name="mailSender")
    private JavaMailSender mailSender;

    /**
     * 메일 보내기 함수
     * @param to
     * @param from
     * @param fromName
     * @param subject
     * @param content
     * @throws Exception
     */
    public void SendEmail(String to, String from, String fromName, String subject, String content)
            throws Exception {
    	
        EmailBean bean = new EmailBean();
        bean.setTo(to);
        bean.setFrom(from);
        bean.setFromName(fromName);
        bean.setSubject(subject);
        bean.setContent(content);
        this.SendEmail(bean);
    }
    
    
    /**
     * 메일 발송 OP
     * @param email
     * @throws Exception
     */
    public void SendEmail(EmailBean email)
        throws Exception
    {
    	_logger.debug("[LINE:"+Thread.currentThread().getStackTrace()[1].getLineNumber()+"] MAIL Info::" + email.toString());
    	
        if(email.getFrom() == null || email.getFrom().length() < 5)
        	throw new Exception("메일 'from' 전송에 필요한 정보 부족으로 전송 할 수 없습니다.");
        if(email.getTo() == null || email.getTo().length() < 5)
        	throw new Exception("메일 'to' 전송에 필요한 정보 부족으로 전송 할 수 없습니다.");
        
        boolean isAttachedFile = false;
        MimeMessage mime = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mime, isAttachedFile, "UTF-8");
        messageHelper.setFrom(email.getFrom(), email.getFromName());
        messageHelper.setTo(email.getTo());
        if(email.getCc() != null && email.getCc().trim().length() > 0)
            messageHelper.setCc(email.getCc());
        if(email.getBcc() != null && email.getBcc().trim().length() > 0)
            messageHelper.setBcc(email.getBcc());
        messageHelper.setSubject(email.getSubject());
        messageHelper.setText(email.getContent(), true);
        
        _logger.debug("[LINE:"+Thread.currentThread().getStackTrace()[1].getLineNumber()+"] MAIL Try to sending...");
        
        mailSender.send(mime);
        
        _logger.debug("[LINE:"+Thread.currentThread().getStackTrace()[1].getLineNumber()+"] MAIL Sended.");
    }


//    public static void main(String[] args) throws Exception {
//    	EmailSenderOnSpring impl = new EmailSenderOnSpring();
//    	
//    	if(impl.mailSender == null) {
//    		// WEB-INF/classes/spring/appServlet/servlet-context.xml ==> classpath:spring/appServlet/servlet-context.xml
//    		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:spring/appServlet/servlet-context.xml");
//    		impl.mailSender = ctx.getBean("mailSender", JavaMailSender.class );
//    	}
//    	
//    	String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        EmailBean bean = new EmailBean();
//        bean.setTo("benjamin.hds@gmail.com");
//        bean.setSubject("휴면처리 안내메일");
//        bean.setContent(EmailConst.getEMAIL_CONTENTS_DORMANT(bean.getTo()));
//        impl.SendEmail(bean);
//	}
    
}
