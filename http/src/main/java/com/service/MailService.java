package com.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    private static final Logger logger = Logger.getLogger(MailService.class);
    private static final String ENCODING = "utf-8";
    private JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendInHtml(String from, String name, String[] to, String subject, String text) {
        send(from, name, to, null, null, subject, text, true);
    }

    public void sendInPlain(String from, String name, String[] to, String subject, String text) {
        send(from, name, to, null, null, subject, text, false);
    }

    public void sendInPlainWithCc(String from, String name, String[] to, String[] cc, String subject, String text) {
        send(from, name, to, cc, null, subject, text, false);
    }

    public void sendInHtmlWithCc(String from, String name, String[] to, String[] cc, String subject, String text) {
        send(from, name, to, cc, null, subject, text, true);
    }

    public void sendInHtmlWithBcc(String from, String name, String[] to, String[] bcc, String subject, String text) {
        send(from, name, to, null, bcc, subject, text, true);
    }

    private void send(final String from, final String name, final String[] to, final String[] cc, final String[] bcc,
                      final String subject, final String text, final boolean htmlFormat) {
        try {
            MimeMessagePreparator preparator = new MimeMessagePreparator() {

                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, htmlFormat, ENCODING);
                    helper.setFrom(new InternetAddress(from, name, ENCODING));
                    helper.setTo(to);

                    if (cc != null) {
                        helper.setCc(cc);
                    }

                    if (bcc != null) {
                        helper.setBcc(bcc);
                    }

                    helper.setSubject(subject);
                    helper.setText(text, htmlFormat);
                }
            };

            mailSender.send(preparator);
            logger.info("send email {from=" + from + ", name=" + name + ", to=" + to + ", subject=" + subject + "}");
        } catch (MailException ex) {
            logger.error("Failed to send email from " + from + " to " + to
                    + ", subject: " + subject + ", exception: " + ex);
        }
    }
}
