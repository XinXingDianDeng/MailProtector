
/*
by Sry
 */
package com.example.mailprotector.ObjStructure;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String acct;//账号
    //public Bitmap avatar;//用户头像
    public String userName;//用户名
    private String pwd;//密码
    private List<MailBox> boxList;//用户邮箱列表
    private List<String> friendList;//通讯录
    private List<String> blackList;//黑名单

    //无参构造
    public User(){
        this.acct ="";
        //this.avatar=null;
        this.userName="";
        this.pwd="";
        this.boxList=new ArrayList<>();
        this.friendList=new ArrayList<>();
        this.blackList=new ArrayList<>();
    }
    //带账号密码构造
    public User(String account,String pwd){
        super();
        this.acct =account;
        this.pwd=pwd;
    }
    public String show(){
        String allStr="";
        for(MailBox tmpBox:boxList){allStr+=tmpBox.show();}
        allStr+="<通讯录>\n";
        for(String tmp:friendList){allStr+=tmp+"\n";}
        allStr+="<黑名单>\n";
        for(String tmp:blackList){allStr+=tmp+"\n";}
        return "<用户："+this.acct+
                ">\n昵称："+this.userName+
                "\n密码："+this.pwd+
                "\n"+allStr+"\n";
    }
    //账号公有可直接获取，不可修改
    //头像公有可直接获取，可修改
    /*public void setAvatar(Bitmap newAvt){
        this.avatar=newAvt;
    }*/
    //获取密码
    public String getPwd(){
        return this.pwd;
    }
    //修改密码
    public void setPwd(String newPwd){
        this.pwd=newPwd;
    }
    //******************************************
    //获取用户邮箱
    public List<MailBox> getBox(){
        return this.boxList;
    }
    //获取用户第i个邮箱
    public MailBox getBoxAt(int index){
        return this.boxList.get(index);
    }
    //增加用户邮箱
    public void boxAdd(MailBox newBox){
        this.boxList.add(newBox);
    }
    //删除用户第i个邮箱
    public void boxDelAt(int index){
        if(index>-1&&index<boxList.size()){
            this.boxList.remove(index);
        }
    }
    //删除所有邮箱
    public void boxDelAll(){
        if(boxList.size()>0){
            boxList.clear();
        }
    }
    //将用户选中邮箱移至第i位
    public void boxTo(int index){

    }
    //*********************************************
    //获取通讯录
    public List<String> getFriend(){
        return this.friendList;
    }
    //获取通讯录第i个
    public String getFriendAt(int index){
        return this.friendList.get(index);
    }
    //添加好友
    public void friendAdd(String newFri){
        this.friendList.add(newFri);
    }
    //删除第i个好友
    public void friendDelAt(int index){
        if(index>-1&&index<friendList.size()){
            this.friendList.remove(index);
        }
    }
    //删除所有好友
    public void friendDelAll(){
        if(friendList.size()>0){
            friendList.clear();
        }
    }

    //*********************************************
    // 获取黑名单
    public List<String> getBlack(){
        return this.blackList;
    }
    //获取黑名单第i个
    public String getBlackAt(int index){
        return this.blackList.get(index);
    }
    //添加黑户
    public void blackAdd(String newBla){
        this.blackList.add(newBla);
    }
    //删除第i个黑户
    public void blackDelAt(int index){
        if(index>-1&&index<blackList.size()){
            this.blackList.remove(index);
        }
    }
    //删除所有黑户
    public void blackDelAll(){
        if(blackList.size()>0){
            blackList.clear();
        }
    }
    //*********************************************
    //注册
    public void register(){
    }
    //登录、退出
    public void login(){
    }

}
