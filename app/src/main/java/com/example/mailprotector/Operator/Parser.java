package com.example.mailprotector.Operator;
//
//import android.os.Parcel;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Properties;
//
//import javax.mail.Address;
//import javax.mail.BodyPart;
//import javax.mail.Flags;
//import javax.mail.Folder;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Multipart;
//import javax.mail.Part;
//import javax.mail.Session;
//import javax.mail.Store;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import javax.mail.internet.MimeUtility;
//import javax.mail.search.SearchTerm;
//import javax.mail.search.SubjectTerm;
//
///**
// * 使用POP3协议解析邮件工具类
// * @Slf4j
// */
//
//
public class Parser {
////.getLogger(Parser.class);
//    private final Logger log = LoggerFactory.getLogger(Parser.class);
//
//    public void main(String[] args) throws Exception{
//        resceive("702146903@qq.com", "gpvarzvonyjjbdad","邮件主题(包含图片和附件)");
//    }
//
///*
//*
//     * 获取邮箱信息
//     * @param emailAdress 需要解析的邮箱地址
//     * @param password    邮箱的授权密码
//     * @throws Exception
//*/
//
//    public void resceive(String emailAdress, String password,String projectName) throws Exception {
//
//        String port = "110";   // 端口号
//        String servicePath = "pop.qq.com";   // 服务器地址
//
//
//        // 准备连接服务器的会话信息
//        Properties props = new Properties();
//        props.setProperty("mail.store.protocol", "pop3");       // 使用pop3协议
//        props.setProperty("mail.pop3.port", port);              // 端口
//        props.setProperty("mail.pop3.host", servicePath);       // pop3服务器
//
//        // 创建Session实例对象
//        Session session = Session.getInstance(props);
//        Store store = session.getStore("pop3");
//        store.connect(emailAdress, password); //163邮箱程序登录属于第三方登录所以这里的密码是163给的授权密码而并非普通的登录密码
//
//
//        // 获得收件箱
//        Folder folder = store.getFolder("INBOX");
//        /*Folder.READ_ONLY：只读权限
//         Folder.READ_WRITE：可读可写（可以修改邮件的状态）*/
//
//
//        folder.open(Folder.READ_WRITE); //打开收件箱
//
////        // 由于POP3协议无法获知邮件的状态,所以getUnreadMessageCount得到的是收件箱的邮件总数
////        System.out.println("未读邮件数: " + folder.getUnreadMessageCount());
////        // 由于POP3协议无法获知邮件的状态,所以下面得到的结果始终都是为0
////        System.out.println("删除邮件数: " + folder.getDeletedMessageCount());
////        System.out.println("新邮件: " + folder.getNewMessageCount());
//
//        // 获得收件箱中的邮件总数
//        log.warn("邮件总数: {}", folder.getMessageCount());
//
//        // 得到收件箱中的所有邮件,并解析
//        //Message[] messages = folder.getMessages();
//
//        //筛选邮件
////        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd 01:00:00");
////        SearchTerm st=new AndTerm(new SubjectTerm("UpdateFromIESToCargo"),
////                new SentDateTerm(6,sf.parse(sf.format(new Date())))  );//6 代表大于等于这时间之后
//        SearchTerm st=new SubjectTerm(projectName);
//        Message[] messages = folder.search(st);
//
//        //解析邮件
//        parseMessage(messages);
//
//        //得到收件箱中的所有邮件并且删除邮件
////        deleteMessage(messages);
//
//        //释放资源
//        folder.close(true);
//        store.close();
//    }
//
///**
//     * 解析邮件
//     * @param messages 要解析的邮件列表
//   */
//
//    public void parseMessage(Message... messages) throws MessagingException, IOException {
//        if (messages == null || messages.length < 1)
//            throw new MessagingException("未找到要解析的邮件!");
//        // 解析所有邮件
//        for (int i = 0, count = messages.length; i < count; i++) {
//            MimeMessage msg = (MimeMessage) messages[i];
//            log.info("------------------解析第" + msg.getMessageNumber() + "封邮件-------------------- ");
//            log.warn("主题: {}" , getSubject(msg));
//            log.warn("发件人: {}" , getFrom(msg));
//            log.warn("收件人：{}" , getReceiveAddress(msg, null));
//            log.warn("发送时间：{}" , getSentDate(msg, null));
//            log.warn("是否已读：{}" , isSeen(msg));
//            log.warn("邮件大小：{}" , msg.getSize() * 1024 + "kb");
////            boolean isContainerAttachment = isContainAttachment(msg);
////            log.warn("是否包含附件：{}" ,isContainerAttachment);
////            if (isContainerAttachment) {
////                saveAttachment(msg, "d:\\log\\" + msg.getSubject() + "_" + i + "_"); //保存附件
////            }
//            StringBuffer content = new StringBuffer(30);
//            //解析邮件正文
//            getMailTextContent(msg, content);
//            log.warn("邮件正文：{}" , content);
//            log.info("------------------第" + msg.getMessageNumber() + "封邮件解析结束-------------------- ");
//            System.out.println();
//        }
//    }
//
//
///*
//*
//     * 删除邮件
//     * @param messages 要删除邮件列表
//*/
//
//
//    public void deleteMessage(Message... messages) throws MessagingException, IOException {
//        if (messages == null || messages.length < 1)
//            throw new MessagingException("未找到要解析的邮件!");
//        // 解析所有邮件
//        for (int i = 0, count = messages.length; i < count; i++) {
///**
//             *   邮件删除
//           */
//
//            Message message = messages[i];
//            String subject = message.getSubject();
//            // set the DELETE flag to true
//            message.setFlag(Flags.Flag.DELETED, true);
//            System.out.println("Marked DELETE for message: " + subject);
//        }
//    }
//
///**
//     * 获得邮件主题
//     * @param msg 邮件内容
//     * @return 解码后的邮件主题
//     */
//
//    public String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {
//        return MimeUtility.decodeText(msg.getSubject());
//    }
//    /*
//     * 获得邮件发件人
//     * @param msg 邮件内容
//     * @return 姓名 <Email地址>
//     * @throws MessagingException
//     * @throws UnsupportedEncodingException
//     */
//
//    public String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
//        String from = "";
//        Address[] froms = msg.getFrom();
//        if (froms.length < 1)
//            throw new MessagingException("没有发件人!");
//
//        InternetAddress address = (InternetAddress) froms[0];
//        String person = address.getPersonal();
//        if (person != null) {
//            person = MimeUtility.decodeText(person) + " ";
//        } else {
//            person = "";
//        }
//        from = person + "<" + address.getAddress() + ">";
//
//        return from;
//    }
//
//    /*
//     * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
//     * <p>Message.RecipientType.TO  收件人</p>
//     * <p>Message.RecipientType.CC  抄送</p>
//     * <p>Message.RecipientType.BCC 密送</p>
//     * @param msg  邮件内容
//     * @param type 收件人类型
//     * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ...
//     * @throws MessagingException
//     */
//
//    public String getReceiveAddress(MimeMessage msg, Message.RecipientType type) throws MessagingException {
//        StringBuffer receiveAddress = new StringBuffer();
//        Address[] addresss = null;
//        if (type == null) {
//            addresss = msg.getAllRecipients();
//        } else {
//            addresss = msg.getRecipients(type);
//        }
//
//        if (addresss == null || addresss.length < 1)
//            throw new MessagingException("没有收件人!");
//        for (Address address : addresss) {
//            InternetAddress internetAddress = (InternetAddress) address;
//            receiveAddress.append(internetAddress.toUnicodeString()).append(",");
//        }
//
//        receiveAddress.deleteCharAt(receiveAddress.length() - 1); //删除最后一个逗号
//
//        return receiveAddress.toString();
//    }
//
//    /*
//     * 获得邮件发送时间
//     * @param msg 邮件内容
//     * @return yyyy年mm月dd日 星期X HH:mm
//     * @throws MessagingException
//     */
//
//    public String getSentDate(MimeMessage msg, String pattern) throws MessagingException {
//        Date receivedDate = msg.getSentDate();
//        if (receivedDate == null)
//            return "";
//        if (pattern == null || "".equals(pattern))
//            pattern = "yyyy年MM月dd日 E HH:mm ";
//        return new SimpleDateFormat(pattern).format(receivedDate);
//    }
//
//    /*
//     * 判断邮件中是否包含附件
//     * @param part 邮件内容
//     * @return 邮件中存在附件返回true，不存在返回false
//     * @throws MessagingException
//     * @throws IOException
//    */
//
//    public boolean isContainAttachment(Part part) throws MessagingException, IOException {
//        boolean flag = false;
//        if (part.isMimeType("multipart/*")) {
//            MimeMultipart multipart = (MimeMultipart) part.getContent();
//            int partCount = multipart.getCount();
//            for (int i = 0; i < partCount; i++) {
//                BodyPart bodyPart = multipart.getBodyPart(i);
//                String disp = bodyPart.getDisposition();
//                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
//                    flag = true;
//                } else if (bodyPart.isMimeType("multipart/*")) {
//                    flag = isContainAttachment(bodyPart);
//                } else {
//                    String contentType = bodyPart.getContentType();
//                    if (contentType.indexOf("application") != -1) {
//                        flag = true;
//                    }
//
//                    if (contentType.indexOf("name") != -1) {
//                        flag = true;
//                    }
//                }
//
//                if (flag) break;
//            }
//        } else if (part.isMimeType("message/rfc822")) {
//            flag = isContainAttachment((Part) part.getContent());
//        }
//        return flag;
//    }
//
//   /*
//     * 判断邮件是否已读
//     * @param msg 邮件内容
//     * @return 如果邮件已读返回true, 否则返回false
//     * @throws MessagingException
//    */
//
//    public boolean isSeen(MimeMessage msg) throws MessagingException {
//        return msg.getFlags().contains(Flags.Flag.SEEN);
//    }
//
//    /*
//     * 获得邮件的优先级
//     * @param msg 邮件内容
//     * @return 1(High):紧急  3:普通(Normal)  5:低(Low)
//     * @throws MessagingException
//
//
//
//*
//     * 获得邮件文本内容
//     * @param part    邮件体
//     * @param content 存储邮件文本内容的字符串
//     * @throws MessagingException
//     * @throws IOException
//     */
//
//    public void getMailTextContent(Part part, StringBuffer content) throws MessagingException, IOException {
//        //如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
//        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
//        if (part.isMimeType("text/*") && !isContainTextAttach) {
//            content.append(part.getContent().toString());
//        } else if (part.isMimeType("message/rfc822")) {
//            getMailTextContent((Part) part.getContent(), content);
//        } else if (part.isMimeType("multipart/*")) {
//            Multipart multipart = (Multipart) part.getContent();
//            int partCount = multipart.getCount();
//            for (int i = 0; i < partCount; i++) {
//                BodyPart bodyPart = multipart.getBodyPart(i);
//                getMailTextContent(bodyPart, content);
//            }
//        }
//    }
//
//   /*
//     * 保存附件
//     *
//     * @param part    邮件中多个组合体中的其中一个组合体
//     * @param destDir 附件保存目录
//     * @throws UnsupportedEncodingException
//     * @throws MessagingException
//     * @throws FileNotFoundException
//     * @throws IOException
//     */
//
//    public void saveAttachment(Part part, String destDir) throws MessagingException, IOException {
//        if (part.isMimeType("multipart/*")) {
//            Multipart multipart = (Multipart) part.getContent();    //复杂体邮件
//            //复杂体邮件包含多个邮件体
//            int partCount = multipart.getCount();
//            for (int i = 0; i < partCount; i++) {
//                //获得复杂体邮件中其中一个邮件体
//                BodyPart bodyPart = multipart.getBodyPart(i);
//                //某一个邮件体也有可能是由多个邮件体组成的复杂体
//                String disp = bodyPart.getDisposition();
//                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
//                    InputStream is = bodyPart.getInputStream();
//                    saveFile(is, destDir, decodeText(bodyPart.getFileName()));
//                } else if (bodyPart.isMimeType("multipart/*")) {
//                    saveAttachment(bodyPart, destDir);
//                } else {
//                    String contentType = bodyPart.getContentType();
//                    if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {
//                        saveFile(bodyPart.getInputStream(), destDir, decodeText(bodyPart.getFileName()));
//                    }
//                }
//            }
//        } else if (part.isMimeType("message/rfc822")) {
//            saveAttachment((Part) part.getContent(), destDir);
//        }
//    }
//
//    /*
//     * 读取输入流中的数据保存至指定目录
//     * @param is       输入流
//     * @param fileName 文件名
//     * @param destDir  文件存储目录
//     * @throws FileNotFoundException
//     * @throws IOException
//     */
//
//    private void saveFile(InputStream is, String destDir, String fileName) throws FileNotFoundException, IOException {
//        BufferedInputStream bis = new BufferedInputStream(is);
//        BufferedOutputStream bos = new BufferedOutputStream(
//                new FileOutputStream(new File(destDir + fileName)));
//        int len = -1;
//        while ((len = bis.read()) != -1) {
//            bos.write(len);
//            bos.flush();
//        }
//        bos.close();
//        bis.close();
//    }
//
//    /*
//     * 文本解码
//     * @param encodeText 解码MimeUtility.encodeText(String text)方法编码后的文本
//     * @return 解码后的文本
//     * @throws UnsupportedEncodingException
//     */
//
//    public String decodeText(String encodeText) throws UnsupportedEncodingException {
//        if (encodeText == null || "".equals(encodeText)) {
//            return "";
//        } else {
//            return MimeUtility.decodeText(encodeText);
//        }
//    }
//
//
}
