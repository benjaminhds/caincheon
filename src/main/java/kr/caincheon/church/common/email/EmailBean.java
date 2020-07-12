// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailBean.java

package kr.caincheon.church.common.email;


public class EmailBean
{

	private String from;
    private String fromName;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String content;
    
    public EmailBean() {
        fromName = "인천교구발신전용";
        from = "no-reply@caincheon.or.kr";
    }

    public EmailBean(String fromNm, String fromEmail) {
        fromName = fromNm;
        from = fromEmail;
    }
    
    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String reciver) {
        to = reciver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String senderName) {
        fromName = senderName;
    }

    public String toString() {
        return "from : "+fromName+"<"+from+">, to : <"+to+">, cc : <"+cc+">, bcc : <"+bcc+">, subject="+subject+", content="+content;
    }

}
