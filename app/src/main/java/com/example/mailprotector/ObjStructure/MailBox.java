

/*
by Sry
 */
package com.example.mailprotector.ObjStructure;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailBox {
    //public Bitmap image;//邮箱图标
    public String addr;//邮箱地址
    private String pwd;//邮箱密码
    private List<Mail> ins;//收件列表
    private List<Mail> drafts;//草稿列表
    private List<Mail> sents;//已发送邮件列表
    private List<Mail> garbages;//已删除邮件列表
    private List<Mail> spams;//垃圾邮件列表

    public MailBox(){
        //this.image=null;
        this.addr="";
        this.pwd="";
        this.ins=new ArrayList<>();
        this.drafts=new ArrayList<>();
        this.sents=new ArrayList<>();
        this.garbages=new ArrayList<>();
        this.spams=new ArrayList<>();
    }
    public MailBox(String addr,String pwd){
        super();
        this.addr=addr;
        this.pwd=pwd;
    }
    public String show(){
        String allStr="";
        List<List<Mail>>all=new ArrayList<>();
        all.add(ins);all.add(drafts);all.add(sents);all.add(garbages);all.add(spams);
        for(List<Mail> tmpList:all){
            for(Mail tmp:tmpList){
                allStr+=tmp.show();
            }
        }
        return "<邮箱："+this.addr+
                ">\n邮箱密码："+this.pwd+
                "\n"+allStr+"\n";
    }
    //***************************************
    //获取图标
    /*public Bitmap getImage(){
        return this.image;
    }*/
    //修改图标
    /*public void setImage(Bitmap newImg){
        this.image=newImg;
    }*/
    //***************************************
    //获取密码
    public String getPwd(){
        return this.pwd;
    }
    //修改密码
    public void setPwd(String newPwd){
        this.pwd=newPwd;
    }
    //***************************************
    //获取收件列表
    public List<Mail> getIns(){
        return ins;
    }
    //获取收件列表第i元素
    public Mail getInsAt(int index){
        return ins.get(index);
    }
    //增加收件
    public void insAdd(Mail newIn){
        newIn.flag=0;
        this.ins.add(newIn);
    }
    //删除第i个收件
    public void insDelAt(int index){
        if(index>-1&&index<ins.size()){
            this.ins.remove(index);
        }
    }
    //删除所有收件
    public void insDelAll(){
        if(ins.size()>0){
            ins.clear();
        }
    }
    //***************************************
    //获取草稿列表
    public List<Mail> getDrafts(){
        return drafts;
    }
    //获取草稿列表第i元素
    public Mail getDraftsAt(int index){
        return drafts.get(index);
    }
    //增加草稿
    public void draftsAdd(Mail newDft){
        newDft.flag=1;
        this.drafts.add(newDft);
    }
    //删除第i个草稿
    public void draftsDelAt(int index){
        if(index>-1&&index<drafts.size()){
            this.drafts.remove(index);
        }
    }
    //删除所有草稿
    public void draftsDelAll(){
        if(drafts.size()>0){
            drafts.clear();
        }
    }
    //***************************************
    //获取已发送表
    public List<Mail> getSents(){
        return sents;
    }
    //获取已发送表第i元素
    public Mail getSentsAt(int index){
        return sents.get(index);
    }
    //增加已发送
    public void sentsAdd(Mail newSent){
        newSent.flag=2;
        this.sents.add(newSent);
    }
    //删除第i个已发送
    public void sentsDelAt(int index){
        if(index>-1&&index<sents.size()){
            this.sents.remove(index);
        }
    }
    //删除所有已发送
    public void sentsDelAll(){
        if(sents.size()>0){
            sents.clear();
        }
    }
    //***************************************
    //获取已删除表
    public List<Mail> getGarbages(){
        return garbages;
    }
    //获取已删除表第i元素
    public Mail getGarbagesAt(int index){
        return garbages.get(index);
    }
    //增加已删除
    public void garbagesAdd(Mail newGarbage){
        newGarbage.flag=3;
        this.garbages.add(newGarbage);
    }
    //删除第i个已删除
    public void garbagesDelAt(int index){
        if(index>-1&&index<garbages.size()){
            this.garbages.remove(index);
        }
    }
    //删除所有已删除
    public void garbagesDelAll(){
        if(garbages.size()>0){
            garbages.clear();
        }
    }
    //***************************************
    //获取垃圾邮件表
    public List<Mail> getSpams(){
        return spams;
    }
    //获取垃圾邮件表第i元素
    public Mail getSpamsAt(int index){
        return spams.get(index);
    }
    //增加垃圾邮件
    public void spamsAdd(Mail newSpam){
        newSpam.flag=4;
        this.spams.add(newSpam);
    }
    //删除第i个垃圾邮件
    public void spamsDelAt(int index){
        if(index>-1&&index<spams.size()){
        this.spams.remove(index);
        }
    }
    //删除所有垃圾邮件
    public void spamsDelAll(){
        if(spams.size()>0){
            spams.clear();
        }
    }
    //*********************************************
    //模拟python识别邮件的垃圾度
    public void classify(Mail mail){
        this.insAdd(mail);
        /*Random random=new Random();
        boolean isSpam=random.nextBoolean();
        if(!isSpam){
            this.insAdd(mail);
        }else {
            this.spamsAdd(mail);
        }*/
    }


}
