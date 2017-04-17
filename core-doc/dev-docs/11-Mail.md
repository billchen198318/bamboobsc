<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/10-BPMN.md">⇦ 
Previous section 10-BPMN</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/12-Job.md">⇨ 
Next section 12 - Job</a>



# 11 - Mail
# Introduction
Send email method description.<br>


***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>

# Use CORE-system job to send mail
it will send mail by job, and pre job max 50s mail to send, a job task wait 2 min.<br/>
Example:
```JAVA
TemplateResultObj result = TemplateUtils.getResult(TemplateCode.TPLMSG0001, note);
String content = result.getContent();
if (StringUtils.isBlank(content)) {
  content = note.getNote();
}
SysMailHelperVO mailHelper = new SysMailHelperVO();
mailHelper.setMailId( this.sysMailHelperService.findForMaxMailIdComplete(mailId) );
mailHelper.setMailFrom( MailClientUtils.getDefaultFrom() );
mailHelper.setMailTo(toMail);
mailHelper.setSubject( note.getTitle() );
mailHelper.setText( content.getBytes("utf8") );
mailHelper.setRetainFlag(YesNo.NO);
mailHelper.setSuccessFlag(YesNo.NO);
this.sysMailHelperService.saveObject(mailHelper);
```

# Use MailClientUtils send mail
MailClientUtils is send mail tool for bambooBSC.


| Name | return |description |
| --- | --- | --- |
| getDefaultFrom() | String | get system default from |
| send(String from, String to, String subject, String text) | void | send mail |
| send(String from, String to, String cc[], String subject, String text) | void | send mail, this args `text` is mail content |
| send(String from, String to, String cc[], String bcc[], String subject, String text) | void | send mail |
| send(String from, String to, String cc, String bcc, String subject, String text) | void | send mail |
| send(String from, String to, String cc[], String bcc[], String fileNames[], File files[], String subject, String text) | void | send mail, the `fileNames[]` and `files[]` is mail attachment |

Example:

```JAVA
MailClientUtils.send("root@localhost", "tiffany@localhost", "Test", "hello world!");
```


# Use Spring JavaMailSender
Use JavaMailSender . <br/>
Example:
```JAVA
JavaMailSender mailSender = (JavaMailSender)AppContext.getBean("mailSender");
MimeMessage message = mailSender.createMimeMessage();
MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
helper.setFrom(from);
helper.setTo(to);
helper.setSubject(subject);
helper.setText(text, true);
if (null!=cc && cc.length>0) {
  helper.setCc(cc);
}
if (null!=bcc && bcc.length>0) {
  helper.setBcc(bcc);
}
for (int i=0; fileNames!=null && i<fileNames.length; i++) {
  helper.addAttachment(fileNames[i], files[i]);
}
mailSender.send(message);
```

# Config for mail, applicationContext-mail.properties

mail.host=localhost<br/> 
mail.port=25<br/>
mail.username=root<br/>
mail.password=password<br/>


| Name | description |
| --- | --- |
| mail.host | SMTP server address |
| mail.port | SMTP server port |
| mail.username | account for use SMTP |
| mail.password | account's password for use SMTP |


