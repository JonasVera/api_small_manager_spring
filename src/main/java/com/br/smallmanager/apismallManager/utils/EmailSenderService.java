package com.br.smallmanager.apismallManager.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailSenderService  {

    @Autowired
    private JavaMailSender mailSender;


    public void sendSimpleEmail(String toEmail,
                                String body,
                                String subject) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("contatosmallmanager@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Send...");
    }

    public void sendEmailWithAttachment(String toEmail,
                                        String body,
                                        String subject,
                                        String attachment) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("contatosmallmanager@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem
                = new FileSystemResource(new File(attachment));

        mimeMessageHelper.addAttachment(fileSystem.getFilename(),
                fileSystem);

        mailSender.send(mimeMessage);
        System.out.println("Mail Send...");

    }
    
    public void capturaEmailClient () {
    	
    }
    public void mensagemEmail(String toEmail,
                              String body,
                              String subject,com.br.smallmanager.apismallManager.dto.MensagemDto msg) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessage.setFrom("contatosmallmanager@gmail.com");
        mimeMessageHelper.setTo(toEmail);

        mimeMessageHelper.setSubject(subject);

        String body1 = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "\n"
                + "  <meta charset=\"utf-8\">\n"
                + "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n"
                + "  <title>Password Reset</title>\n"
                + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                + "  <style type=\"text/css\">\n"
                + "  /**\n"
                + "   * Google webfonts. Recommended to include the .woff version for cross-client compatibility.\n"
                + "   */\n"
                + "  @media screen {\n"
                + "    @font-face {\n"
                + "      font-family: 'Source Sans Pro';\n"
                + "      font-style: normal;\n"
                + "      font-weight: 400;\n"
                + "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\n"
                + "    }\n"
                + "\n"
                + "    @font-face {\n"
                + "      font-family: 'Source Sans Pro';\n"
                + "      font-style: normal;\n"
                + "      font-weight: 700;\n"
                + "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\n"
                + "    }\n"
                + "  }\n"
                + "\n"
                + "  /**\n"
                + "   * Avoid browser level font resizing.\n"
                + "   * 1. Windows Mobile\n"
                + "   * 2. iOS / OSX\n"
                + "   */\n"
                + "  body,\n"
                + "  table,\n"
                + "  td,\n"
                + "  a {\n"
                + "    -ms-text-size-adjust: 100%; /* 1 */\n"
                + "    -webkit-text-size-adjust: 100%; /* 2 */\n"
                + "  }\n"
                + "\n"
                + "  /**\n"
                + "   * Remove extra space added to tables and cells in Outlook.\n"
                + "   */\n"
                + "  table,\n"
                + "  td {\n"
                + "    mso-table-rspace: 0pt;\n"
                + "    mso-table-lspace: 0pt;\n"
                + "  }\n"
                + "\n"
                + "  /**\n"
                + "   * Better fluid images in Internet Explorer.\n"
                + "   */\n"
                + "  img {\n"
                + "    -ms-interpolation-mode: bicubic;\n"
                + "  }\n"
                + "\n"
                + "  /**\n"
                + "   * Remove blue links for iOS devices.\n"
                + "   */\n"
                + "  a[x-apple-data-detectors] {\n"
                + "    font-family: inherit !important;\n"
                + "    font-size: inherit !important;\n"
                + "    font-weight: inherit !important;\n"
                + "    line-height: inherit !important;\n"
                + "    color: inherit !important;\n"
                + "    text-decoration: none !important;\n"
                + "  }\n"
                + "\n"
                + "  /**\n"
                + "   * Fix centering issues in Android 4.4.\n"
                + "   */\n"
                + "  div[style*=\"margin: 16px 0;\"] {\n"
                + "    margin: 0 !important;\n"
                + "  }\n"
                + "\n"
                + "  body {\n"
                + "    width: 100% !important;\n"
                + "    height: 100% !important;\n"
                + "    padding: 0 !important;\n"
                + "    margin: 0 !important;\n"
                + "  }\n"
                + "\n"
                + "  /**\n"
                + "   * Collapse table borders to avoid space between cells.\n"
                + "   */\n"
                + "  table {\n"
                + "    border-collapse: collapse !important;\n"
                + "  }\n"
                + "\n"
                + "  a {\n"
                + "    color: #ffff;\n"
                + "  }\n"
                + "\n"
                + "  img {\n"
                + "    height: auto;\n"
                + "    line-height: 100%;\n"
                + "    text-decoration: none;\n"
                + "    border: 0;\n"
                + "    outline: none;\n"
                + "  }\n"
                + "  </style>\n"
                + "\n"
                + "</head>\n"
                + "<body style=\"background-color: #ffff;\">\n"
                + "\n"
                + "  <!-- start preheader -->\n"
                + "  <div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\n"
                + "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n"
                + "  </div>\n"
                + "  <!-- end preheader -->\n"
                + "\n"
                + "  <!-- start body -->\n"
                + "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                + "\n"
                + "    <!-- start logo -->\n"
                + "    <tr>\n"
                + "      <td align=\"center\" bgcolor=\"#fff\">\n"
                + "        <!--[if (gte mso 9)|(IE)]>\n"
                + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                + "        <tr>\n"
                + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                + "        <![endif]-->\n"
                + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                + "          <tr>\n"
                + "            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n"
                + "              <a href=\"https://sendgrid.com\" target=\"_blank\" style=\"display: inline-block;\">\n"
                + "                <img src="+msg.getLogo()+" alt=\"Logo\" border=\"0\" width=\"100\" style=\"display: block; width: 100px; max-width: 100px; min-width: 100px;\">\n"
                + "              </a>\n"
                + "            </td>\n"
                + "          </tr>\n"
                + "        </table>\n"
                + "        <!--[if (gte mso 9)|(IE)]>\n"
                + "        </td>\n"
                + "        </tr>\n"
                + "        </table>\n"
                + "        <![endif]-->\n"
                + "      </td>\n"
                + "    </tr>\n"
                + "    <!-- end logo -->\n"
                + "\n"
                + "    <!-- start hero -->\n"
                + "    <tr>\n"
                + "      <td align=\"center\" bgcolor=\"#fff\">\n"
                + "        <!--[if (gte mso 9)|(IE)]>\n"
                + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                + "        <tr>\n"
                + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                + "        <![endif]-->\n"
                + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                + "          <tr>\n"
                + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #aa00ff;\">\n"
                + "              <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">"+msg.getNome()+" enviou uma Mensagem</h1>\n"
                + "            </td>\n"
                + "          </tr>\n"
                + "        </table>\n"
                + "        <!--[if (gte mso 9)|(IE)]>\n"
                + "        </td>\n"
                + "        </tr>\n"
                + "        </table>\n"
                + "        <![endif]-->\n"
                + "      </td>\n"
                + "    </tr>\n"
                + "    <!-- end hero -->\n"
                + "\n"
                + "    <!-- start copy block -->\n"
                + "    <tr>\n"
                + "      <td align=\"center\" bgcolor=\"#fff\">\n"
                + "      \n"
                + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                + "\n"
                + "          <!-- start copy -->\n"
                + "          <tr>\n"
                + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n"
                + "              <p style=\"margin: 0;\">"+msg.getMensagem()+"</p>\n"
                + "            </td>\n"
                + "          </tr>\n"
                + "          <!-- end copy -->\n"
                + "\n"
                + "          <!-- start button -->\n"
                + "          <tr>\n"
                + "            <td align=\"left\" bgcolor=\"#ffffff\">\n"
                + "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                + "                <tr>\n"
                + "                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">\n"
                + "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n"
                + "                      <tr>\n"
                + "                         \n"
                + "                      </tr>\n"
                + "                    </table>\n"
                + "                  </td>\n"
                + "                </tr>\n"
                + "              </table>\n"
                + "            </td>\n"
                + "          </tr>\n"
                + "          <!-- end button -->\n"
                + "\n"
                + "          <!-- start copy -->\n"
                + "          <tr>\n"
                + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n"
                + "                          </td>\n"
                + "          </tr>\n"
                + "          <!-- end copy -->\n"
                + "\n"
                + "          <!-- start copy -->\n"
                + "          <tr>\n"
                + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">\n"
                + "              <p style=\"margin: 0;\"Animal-X</p>\n"
                + "            </td>\n"
                + "          </tr>\n"
                + "          <!-- end copy -->\n"
                + "\n"
                + "        </table>\n"
                + "        \n"
                + "      </td>\n"
                + "    </tr>\n"
                + "    <!-- end copy block -->\n"
                + "\n"
                + "   \n"
                + "    <!-- end footer -->\n"
                + "\n"
                + "  </table>\n"
                + "  <!-- end body -->\n"
                + "\n"
                + "</body>\n"
                + "</html>";

        mimeMessageHelper.setText(body1,true);
        mimeMessageHelper.setSubject(subject);
        mailSender.send(mimeMessage);

    }
    
    public void sendEmailWithAttachment(String toEmail,
            String body,
            String subject) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessage.setFrom("contatosmallmanager@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        
        mimeMessageHelper.setSubject(subject);
        
        String body1 = "<!DOCTYPE html>\r\n" + 
        		"<html>\r\n" + 
        		"<head>\r\n" + 
        		"\r\n" + 
        		"  <meta charset=\"utf-8\">\r\n" + 
        		"  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\r\n" + 
        		"  <title>Password Reset</title>\r\n" + 
        		"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
        		"  <style type=\"text/css\">\r\n" + 
        		"  /**\r\n" + 
        		"   * Google webfonts. Recommended to include the .woff version for cross-client compatibility.\r\n" + 
        		"   */\r\n" + 
        		"  @media screen {\r\n" + 
        		"    @font-face {\r\n" + 
        		"      font-family: 'Source Sans Pro';\r\n" + 
        		"      font-style: normal;\r\n" + 
        		"      font-weight: 400;\r\n" + 
        		"      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\r\n" + 
        		"    }\r\n" + 
        		"\r\n" + 
        		"    @font-face {\r\n" + 
        		"      font-family: 'Source Sans Pro';\r\n" + 
        		"      font-style: normal;\r\n" + 
        		"      font-weight: 700;\r\n" + 
        		"      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\r\n" + 
        		"    }\r\n" + 
        		"  }\r\n" + 
        		"\r\n" + 
        		"  /**\r\n" + 
        		"   * Avoid browser level font resizing.\r\n" + 
        		"   * 1. Windows Mobile\r\n" + 
        		"   * 2. iOS / OSX\r\n" + 
        		"   */\r\n" + 
        		"  body,\r\n" + 
        		"  table,\r\n" + 
        		"  td,\r\n" + 
        		"  a {\r\n" + 
        		"    -ms-text-size-adjust: 100%; /* 1 */\r\n" + 
        		"    -webkit-text-size-adjust: 100%; /* 2 */\r\n" + 
        		"  }\r\n" + 
        		"\r\n" + 
        		"  /**\r\n" + 
        		"   * Remove extra space added to tables and cells in Outlook.\r\n" + 
        		"   */\r\n" + 
        		"  table,\r\n" + 
        		"  td {\r\n" + 
        		"    mso-table-rspace: 0pt;\r\n" + 
        		"    mso-table-lspace: 0pt;\r\n" + 
        		"  }\r\n" + 
        		"\r\n" + 
        		"  /**\r\n" + 
        		"   * Better fluid images in Internet Explorer.\r\n" + 
        		"   */\r\n" + 
        		"  img {\r\n" + 
        		"    -ms-interpolation-mode: bicubic;\r\n" + 
        		"  }\r\n" + 
        		"\r\n" + 
        		"  /**\r\n" + 
        		"   * Remove blue links for iOS devices.\r\n" + 
        		"   */\r\n" + 
        		"  a[x-apple-data-detectors] {\r\n" + 
        		"    font-family: inherit !important;\r\n" + 
        		"    font-size: inherit !important;\r\n" + 
        		"    font-weight: inherit !important;\r\n" + 
        		"    line-height: inherit !important;\r\n" + 
        		"    color: inherit !important;\r\n" + 
        		"    text-decoration: none !important;\r\n" + 
        		"  }\r\n" + 
        		"\r\n" + 
        		"  /**\r\n" + 
        		"   * Fix centering issues in Android 4.4.\r\n" + 
        		"   */\r\n" + 
        		"  div[style*=\"margin: 16px 0;\"] {\r\n" + 
        		"    margin: 0 !important;\r\n" + 
        		"  }\r\n" + 
        		"\r\n" + 
        		"  body {\r\n" + 
        		"    width: 100% !important;\r\n" + 
        		"    height: 100% !important;\r\n" + 
        		"    padding: 0 !important;\r\n" + 
        		"    margin: 0 !important;\r\n" + 
        		"  }\r\n" + 
        		"\r\n" + 
        		"  /**\r\n" + 
        		"   * Collapse table borders to avoid space between cells.\r\n" + 
        		"   */\r\n" + 
        		"  table {\r\n" + 
        		"    border-collapse: collapse !important;\r\n" + 
        		"  }\r\n" + 
        		"\r\n" + 
        		"  a {\r\n" + 
        		"    color: #aa00ff;\r\n" + 
        		"  }\r\n" + 
        		"\r\n" + 
        		"  img {\r\n" + 
        		"    height: auto;\r\n" + 
        		"    line-height: 100%;\r\n" + 
        		"    text-decoration: none;\r\n" + 
        		"    border: 0;\r\n" + 
        		"    outline: none;\r\n" + 
        		"  }\r\n" + 
        		"  </style>\r\n" + 
        		"\r\n" + 
        		"</head>\r\n" + 
        		"<body style=\"background-color: #fff;\">\r\n" + 
        		"\r\n" + 
        		"  <!-- start preheader -->\r\n" + 
        		"  <div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\r\n" + 
        		"    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\r\n" + 
        		"  </div>\r\n" + 
        		"  <!-- end preheader -->\r\n" + 
        		"\r\n" + 
        		"  <!-- start body -->\r\n" + 
        		"  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + 
        		"\r\n" + 
        		"    <!-- start logo -->\r\n" + 
        		"    <tr>\r\n" + 
        		"      <td align=\"center\" bgcolor=\"#e9ecef\">\r\n" + 
        		"        <!--[if (gte mso 9)|(IE)]>\r\n" + 
        		"        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\r\n" + 
        		"        <tr>\r\n" + 
        		"        <td align=\"center\" valign=\"top\" width=\"600\">\r\n" + 
        		"        <![endif]-->\r\n" + 
        		"        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n" + 
        		"          <tr>\r\n" + 
        		"            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\r\n" + 
        		"              <a href=\"https://sendgrid.com\"  style=\"display: inline-block;\">\r\n" + 
        		"                <img src=\"https://small.s3.us-east-2.amazonaws.com/logo.svg\" alt=\"Logo\" border=\"0\" width=\"100\" style=\"display: block; width: 48px; max-width:270px; min-width:270px;\">\r\n" + 
        		"              </a> \r\n" + 
        		"            </td>\r\n" + 
        		"          </tr>\r\n" + 
        		"        </table>\r\n" + 
        		"        <!--[if (gte mso 9)|(IE)]>\r\n" + 
        		"        </td>\r\n" + 
        		"        </tr>\r\n" + 
        		"        </table>\r\n" + 
        		"        <![endif]-->\r\n" + 
        		"      </td>\r\n" + 
        		"    </tr>\r\n" + 
        		"    <!-- end logo -->\r\n" + 
        		"\r\n" + 
        		"    <!-- start hero -->\r\n" + 
        		"    <tr>\r\n" + 
        		"      <td align=\"center\" bgcolor=\"#e9ecef\">\r\n" + 
        		"        <!--[if (gte mso 9)|(IE)]>\r\n" + 
        		"        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\r\n" + 
        		"        <tr>\r\n" + 
        		"        <td align=\"center\" valign=\"top\" width=\"600\">\r\n" + 
        		"        <![endif]-->\r\n" + 
        		"        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n" + 
        		"          <tr>\r\n" + 
        		"            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #aa00ff;\">\r\n" + 
        		"              <h1 style=\"margin: 0; color=#ffffff ; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Redefina sua senha</h1>\r\n" + 
        		"            </td>\r\n" + 
        		"          </tr>\r\n" + 
        		"        </table>\r\n" + 
        		"        <!--[if (gte mso 9)|(IE)]>\r\n" + 
        		"        </td>\r\n" + 
        		"        </tr>\r\n" + 
        		"        </table>\r\n" + 
        		"        <![endif]-->\r\n" + 
        		"      </td>\r\n" + 
        		"    </tr>\r\n" + 
        		"    <!-- end hero -->\r\n" + 
        		"\r\n" + 
        		"    <!-- start copy block -->\r\n" + 
        		"    <tr>\r\n" + 
        		"      <td align=\"center\" bgcolor=\"#e9ecef\">\r\n" + 
        		"      \r\n" + 
        		"        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n" + 
        		"\r\n" + 
        		"          <!-- start copy -->\r\n" + 
        		"          <tr>\r\n" + 
        		"            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\r\n" + 
        		"              <p style=\"margin: 0;\">Toque no botão abaixo para redefinir a senha da sua conta de cliente. Se você não solicitou uma nova senha, você pode excluir este e-mail com segurança.</p>\r\n" + 
        		"            </td>\r\n" + 
        		"          </tr>\r\n" + 
        		"          <!-- end copy -->\r\n" + 
        		"\r\n" + 
        		"          <!-- start button -->\r\n" + 
        		"          <tr>\r\n" + 
        		"            <td align=\"left\" bgcolor=\"#ffffff\">\r\n" + 
        		"              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + 
        		"                <tr>\r\n" + 
        		"                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">\r\n" + 
        		"                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
        		"                      <tr>\r\n" + 
        		"                        <td align=\"center\" bgcolor=\"#aa00ff\" style=\"border-radius: 6px;\">\r\n" + 
        		"                          <a href="+body+" style=\"display: inline-block; padding: 16px 36px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; color:white; font-style: bold; text-decoration: none; border-radius: 6px;\">Redefinir minha senha</a>\r\n" + 
        		"                        </td>\r\n" + 
        		"                      </tr>\r\n" + 
        		"                    </table>\r\n" + 
        		"                  </td>\r\n" + 
        		"                </tr>\r\n" + 
        		"              </table>\r\n" + 
        		"            </td>\r\n" + 
        		"          </tr>\r\n" + 
        		"          <!-- end button -->\r\n" + 
        		"\r\n" + 
        		"          <!-- start copy -->\r\n" + 
        		"          <tr>\r\n" + 
        		"            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\r\n" + 
        		"              <p style=\"margin: 0;\">Se isso não funcionar, copie e cole o seguinte link no seu navegador:</p>\r\n" + 
        		"              <p style=\"margin: 0;\"><a href="+body+" target=\"_blank\">"+body+"</a></p>\r\n" + 
        		"            </td>\r\n" + 
        		"          </tr>\r\n" + 
        		"          <!-- end copy -->\r\n" + 
        		"\r\n" + 
        		"          <!-- start copy -->\r\n" + 
        		"          <tr>\r\n" + 
        		"            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">\r\n" + 
        		"              <p style=\"margin: 0;\"Animal-X</p>\r\n" + 
        		"            </td>\r\n" + 
        		"          </tr>\r\n" + 
        		"          <!-- end copy -->\r\n" + 
        		"\r\n" + 
        		"        </table>\r\n" + 
        		"        <!--[if (gte mso 9)|(IE)]>\r\n" + 
        		"        </td>\r\n" + 
        		"        </tr>\r\n" + 
        		"        </table>\r\n" + 
        		"        <![endif]-->\r\n" + 
        		"      </td>\r\n" + 
        		"    </tr>\r\n" + 
        		"    <!-- end copy block -->\r\n" + 
        		"\r\n" + 
        		"    <!-- start footer -->\r\n" + 
        		"    <tr>\r\n" + 
        		"      <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 24px;\">\r\n" + 
        		"        <!--[if (gte mso 9)|(IE)]>\r\n" + 
        		"        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\r\n" + 
        		"        <tr>\r\n" + 
        		"        <td align=\"center\" valign=\"top\" width=\"600\">\r\n" + 
        		"        <![endif]-->\r\n" + 
        		"        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n" + 
        		"\r\n" + 
        		"          <!-- start permission -->\r\n" + 
        		"           \r\n" + 
        		"          <!-- end permission -->\r\n" + 
        		"\r\n" + 
        		"          <!-- start unsubscribe -->\r\n" + 
        		"          <tr>\r\n" + 
        		"            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\r\n" + 
        		"              <p style=\"margin: 0;\">Se não foi você que solicitou a recuperação de senha ingnore este e-mail.</p>\r\n" + 
        		"               \r\n" + 
        		"            </td>\r\n" + 
        		"          </tr>\r\n" + 
        		"          <!-- end unsubscribe -->\r\n" + 
        		"\r\n" + 
        		"        </table>\r\n" + 
        		"         \r\n" + 
        		"      </td>\r\n" + 
        		"    </tr>\r\n" + 
        		"    <!-- end footer -->\r\n" + 
        		"\r\n" + 
        		"  </table>\r\n" + 
        		"  <!-- end body -->\r\n" + 
        		"\r\n" + 
        		"</body>\r\n" + 
        		"</html>";
         
        mimeMessageHelper.setText(body1,true); 
        mimeMessageHelper.setSubject(subject); 
        mailSender.send(mimeMessage);  
    }
    

}
