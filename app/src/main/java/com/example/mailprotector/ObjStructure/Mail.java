
/*
by Sry
 */

package com.example.mailprotector.ObjStructure;
import java.io.Serializable;
import java.util.Date;

public class Mail implements Serializable {
    public String theme;//邮件主题
    private String content;//邮件内容
    public String sender;//发件人
    public String receiver;//收件人
    public Date createDate;//创建邮件时间
    public Date sendDate;//发送邮件时间
    public Date receiveDate;//收到邮件时间
    public int flag;//归属集

    public Mail(String sender){
        this.theme="";
        this.content="";
        this.createDate=null;
        this.sendDate=null;
        this.receiveDate=null;
        this.sender=sender;
        this.receiver="";
        this.flag=-1;
    }
    //获取文本、正文内容
    public String getContent(){
        return this.content;
    }
    //修改文本内容
    public void setContent(String newCtt){
        this.content=newCtt;
    }
    //主题获取和修改将在邮件所属的邮箱中进行
    //注意，创建、发送、收取时间不在mial内部赋值
    //而是由邮件所属邮箱操作
    public String show(){
        String type=null;
        switch (this.flag){
            case 0:type="<收件箱邮件>";break;
            case 1:type="<草稿箱邮件>";break;
            case 2:type="<已发送邮件>";break;
            case 3:type="<已删除邮件>";break;
            case 4:type="<垃圾邮件>";break;
            default:type="<邮件>";break;
        }
        return type+
                "\n创建时间："+this.createDate.toString()+
                "\n主题："+this.theme+
                "\n正文：\n"+this.content+
                "\n发件人："+this.sender+
                "\n发送时间："+this.sendDate.toString()+
                "\n收件人："+this.receiver+
                "\n接收时间："+this.receiveDate.toString()+"\n";
    }
}
