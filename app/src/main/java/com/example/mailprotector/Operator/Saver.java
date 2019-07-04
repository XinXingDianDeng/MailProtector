package com.example.mailprotector.Operator;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Xml;

import com.example.mailprotector.ObjStructure.Mail;
import com.example.mailprotector.ObjStructure.MailBox;
import com.example.mailprotector.ObjStructure.User;
import com.example.mailprotector.R;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Saver {
    public String parentPath;

    public Saver(String parentPath){
        this.parentPath=parentPath;
    }

    //等比例缩小图片
    public Bitmap scaleImage(Bitmap bitmap, float newWidth, float newHeight){
        if (bitmap == null){
            return null;
        }
        float scaleWidth = newWidth/bitmap.getWidth();
        float scaleHeight = newHeight/bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,true);
        if (bitmap != null & !bitmap.isRecycled()){
            bitmap.recycle();//销毁原图片
        }
        return newBitmap;
    }
    //保存图片
    public void saveImg(User me,Resources resources)throws Exception{

        Bitmap image = BitmapFactory.decodeResource(resources, R.mipmap.myavator);
        //System.out.println(image);
        File file = new File(this.parentPath,me.acct+".jpg");
        if(!file.exists()){
            // 保存图片
            FileOutputStream out=new FileOutputStream(file);
            //降低质量
            image.compress(Bitmap.CompressFormat.JPEG,30,out);
            for(MailBox tmpBox:me.getBox()){
                for (Mail tmp:tmpBox.getIns()){
                    int i=(int)(Math.random()*10)%10;
                    switch (i){
                        case 0:image=BitmapFactory.decodeResource(resources,R.mipmap.test0);break;
                        case 1:image=BitmapFactory.decodeResource(resources,R.mipmap.test1);break;
                        case 2:image=BitmapFactory.decodeResource(resources,R.mipmap.test2);break;
                        case 3:image=BitmapFactory.decodeResource(resources,R.mipmap.test3);break;
                        case 4:image=BitmapFactory.decodeResource(resources,R.mipmap.test4);break;
                        case 5:image=BitmapFactory.decodeResource(resources,R.mipmap.test5);break;
                        case 6:image=BitmapFactory.decodeResource(resources,R.mipmap.test6);break;
                        case 7:image=BitmapFactory.decodeResource(resources,R.mipmap.test7);break;
                        case 8:image=BitmapFactory.decodeResource(resources,R.mipmap.test8);break;
                        case 9:image=BitmapFactory.decodeResource(resources,R.mipmap.test9);break;
                        default:break;
                    }
                    file=new File(this.parentPath,tmp.sender+".jpg");
                    if(!file.exists()){
                        out =new FileOutputStream(file);
                        image=scaleImage(image,100,100);
                        image.compress(Bitmap.CompressFormat.JPEG,50,out);
                    }
                }
            }
            out.flush();
            out.close();
        }
    }
    //对象序列化
    //快速、文件小，但不能处理过于复杂的对象、生成文件对人不可读
    public void objSave(User me,FileOutputStream out)throws Exception{
        try {
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(me);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //自定义序列化
    //存写慢、文件大，可用于人工检验
    public void save(User me) throws Exception {

        File file=new File(this.parentPath,"/myMail.xml");
        FileOutputStream out = new FileOutputStream(file);
        //String[] allName={"ins","drafts","sents","garbages","spams"};
        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(out, "UTF-8");
        serializer.startDocument("UTF-8", true);
        serializer.startTag(null, "User");
        serializer.attribute(null,"acct",me.acct);
        serializer.startTag(null,"userPwd");serializer.text(me.getPwd());serializer.endTag(null,"userPwd");
        serializer.startTag(null,"userName");serializer.text(me.userName);serializer.endTag(null,"userName");

        serializer.startTag(null,"boxList");
        for (MailBox tmpbox : me.getBox()) {
            serializer.startTag(null,"MailBox");
            serializer.attribute(null,"addr",tmpbox.addr);
            serializer.startTag(null,"pwd");serializer.text(tmpbox.getPwd());serializer.endTag(null,"pwd");
            List<List<Mail>>all=new ArrayList<>();
            all.add(tmpbox.getIns());
            all.add(tmpbox.getDrafts());
            all.add(tmpbox.getSents());
            all.add(tmpbox.getGarbages());
            all.add(tmpbox.getSpams());
            for(int i=0;i<5;i++){
                //serializer.startTag(null,allName[i]);
                for(Mail tmp:all.get(i)){
                    serializer.startTag(null,"Mail");
                    serializer.attribute(null,"createDate",String.valueOf(tmp.createDate.getTime()));
                    serializer.startTag(null,"theme");serializer.text(tmp.theme);serializer.endTag(null,"theme");
                    serializer.startTag(null,"content");serializer.text(tmp.getContent());serializer.endTag(null,"content");
                    serializer.startTag(null,"sender");serializer.text(tmp.sender);serializer.endTag(null,"sender");
                    serializer.startTag(null,"receiver");serializer.text(tmp.receiver);serializer.endTag(null,"receiver");
                    serializer.startTag(null,"sendDate");serializer.text(String.valueOf(tmp.sendDate.getTime()));serializer.endTag(null,"sendDate");
                    serializer.startTag(null,"receiveDate");serializer.text(String.valueOf(tmp.receiveDate.getTime()));serializer.endTag(null,"receiveDate");
                    serializer.startTag(null,"flag");serializer.text(String.valueOf(tmp.flag));serializer.endTag(null,"flag");
                    serializer.endTag(null,"Mail");
                }//serializer.endTag(null,allName[i]);
            }serializer.endTag(null,"MailBox");
        }serializer.endTag(null,"boxList");

        serializer.startTag(null,"friendList");
        for(String tmp:me.getFriend()){
            serializer.startTag(null,"faddr");
            serializer.text(tmp);
            serializer.endTag(null,"faddr");
        }serializer.endTag(null,"friendList");

        serializer.startTag(null,"blackList");
        for(String tmp:me.getBlack()){
            serializer.startTag(null,"baddr");
            serializer.text(tmp);
            serializer.endTag(null,"baddr");
        }serializer.endTag(null,"blackList");

        serializer.endTag(null, "User");
        serializer.endDocument();
        out.flush();
        out.close();
    }
}
