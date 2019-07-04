package com.example.mailprotector.Operator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mailprotector.ObjStructure.Mail;
import com.example.mailprotector.ObjStructure.MailBox;
import com.example.mailprotector.ObjStructure.User;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Loader {
    public String parentPath;
    public Loader(String parentPath){
        this.parentPath=parentPath;
    }
    //加载图片
    public List<Bitmap> loadImg(User me){
        List<Bitmap> images=new ArrayList<>();
        String path = this.parentPath+"/"+me.acct+".jpg";
        System.out.println(path);
        images.add(BitmapFactory.decodeFile(path));
        for(MailBox tmpBox:me.getBox()){
            for(Mail tmp:tmpBox.getIns()){
                path=this.parentPath+"/"+tmp.sender+".jpg";
                images.add(BitmapFactory.decodeFile(path));
            }
        }
        return images;
    }
    //对象反序列，快速、但不能处理复杂对象
    public User objLoad(InputStream xml)throws Exception{
        try {
            ObjectInputStream objIn = new ObjectInputStream(xml);
            return  (User) objIn.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new User();
    }
    //自定义反序列，较慢、用于人工检验
    public User load(InputStream xml)throws Exception {

        User me = null;
        MailBox myBox=null;
        Mail myMail=null;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(xml, "UTF-8");

        // 获得事件的类型
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(XmlPullParser.START_DOCUMENT==eventType){
                me = new User();
            }else if(XmlPullParser.START_TAG==eventType){
                switch (parser.getName()){
                    case "User":me.acct=parser.getAttributeValue(0);break;
                    case "userPwd":me.setPwd(parser.nextText());break;
                    case "userName":me.userName=parser.nextText();break;
                    case "MailBox":
                        myBox=new MailBox();
                        myBox.addr=parser.getAttributeValue(0);break;
                    case "pwd":
                        myBox.setPwd(parser.nextText());break;
                    case "Mail":
                        myMail=new Mail(myBox.addr);
                        myMail.createDate = new Date(Long.parseLong(parser.getAttributeValue(0)));break;
                    case "theme":myMail.theme=parser.nextText();break;
                    case "content":myMail.setContent(parser.nextText());break;
                    case "sender":myMail.sender=parser.nextText();break;
                    case "receiver":myMail.receiver=parser.nextText();break;
                    case "sendDate"://textView.setText(parser.nextText()+"testInt"+i+myMail.sender+myBox.addr+myBox.getPwd()+myMail.createDate.toString());
                        myMail.sendDate=new Date(Long.parseLong(parser.nextText()));break;
                    case "receiveDate":myMail.receiveDate=new Date(Long.parseLong(parser.nextText()));break;
                    case "flag":
                        myMail.flag= Integer.parseInt(parser.nextText());
                        switch (myMail.flag){
                            case 0:myBox.insAdd(myMail);break;
                            case 1:myBox.draftsAdd(myMail);break;
                            case 2:myBox.sentsAdd(myMail);break;
                            case 3:myBox.garbagesAdd(myMail);break;
                            case 4:myBox.spamsAdd(myMail);break;
                            default:break;
                        }break;
                    case "faddr":me.friendAdd(parser.nextText());break;
                    case "baddr":me.blackAdd(parser.nextText());break;
                    default:break;
                }
            }else if(XmlPullParser.END_TAG==eventType){
                if("MailBox".equals(parser.getName())){
                    me.boxAdd(myBox);
                }
            }
            eventType = parser.next();
        }
        return me;
    }
}
