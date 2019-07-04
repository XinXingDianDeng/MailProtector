package com.example.mailprotector.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import  android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mailprotector.ObjStructure.Mail;
import com.example.mailprotector.Operator.Sender;
import com.example.mailprotector.R;

import org.xml.sax.DTDHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewMail extends AppCompatActivity {

    private Toolbar toolbar;
    //private Sender sender=new Sender();
    private Mail newMail;
    private EditText receiverView;
    private EditText themeView;
    private EditText contentView;

//    private String receiver;
//    private String theme;
//    private String content;

    //发件人邮箱
    private String senderAddress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newmail_tosend);
        toolbar=findViewById(R.id.newmail_bar);
        setSupportActionBar(toolbar);
        senderAddress = toolbar.getSubtitle().toString();
        newMail = new Mail(senderAddress);
        //收件人输入框
        receiverView = findViewById(R.id.newmail_receiver);
        //主题输入框
        themeView = findViewById(R.id.newmail_theme);
        //正文输入框
        contentView = findViewById(R.id.newmail_content);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.ic_cancel);
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(NewMail.this,AlertDialog.THEME_HOLO_LIGHT)
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //TODO: 保存草稿
                                draft(receiverView.getText().toString(),themeView.getText().toString(),
                                        contentView.getText().toString());
                                //返回邮件
                                Intent intent = new Intent(NewMail.this,MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("draft",newMail);
                                intent.putExtra("bundle",bundle);
                                setResult(2,intent);
                                finish();
                            }
                        })
                        .setNegativeButton("取消并退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .create();

                TextView msg = new TextView(NewMail.this);
                msg.setText("是否保存草稿");
                msg.setPadding(10, 30, 10, 10);

                msg.setGravity(Gravity.CENTER);
                msg.setTextSize(24);
                alertDialog.setView(msg);

                alertDialog.show();

                //修改确定、取消按钮字大小
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(20);
                /*
                try{
                    Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                    mAlert.setAccessible(true);
                    Object mAlertController = mAlert.get(alertDialog);

//                    //获取标题并设置
//                    Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
//                    mTitle.setAccessible(true);
//                    TextView title = (TextView)mTitle.get(mAlertController);
//                    title.setTextSize(40);

                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }catch (NoSuchFieldException e){
                    e.printStackTrace();
                }
                */
            }
        });

        //receiverView失去焦点
        receiverView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // 失去焦点,判断邮箱是否正确
                    receiverView.clearFocus();
                    port(receiverView.getText().toString());
                }else if(hasFocus){
                    //获得焦点，收件人输入框字体设为黑色
                    receiverView.setTextColor(Color.BLACK);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Toast.makeText(this,"====",Toast.LENGTH_SHORT).show();
        getMenuInflater().inflate(R.menu.newmail_bar_send,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //点击事件
        int id = item.getItemId();
        if(R.id.newmail_send==id){
            draft(receiverView.getText().toString(),themeView.getText().toString(),
                    contentView.getText().toString());
            //TODO:发邮件

        }
        return super.onOptionsItemSelected(item);
    }

    //写邮件、起草
    public void draft(String receiver,String theme,String content){
        newMail.receiver=receiver;
        newMail.theme=theme;
        newMail.setContent(content);
    }
    //发送
    public void send(Mail myMail){
        //do send first

        //then add this mail to sents
        //this.sentsAdd(myMail);
    }

    //localed by 杨肇欣
    public boolean isAddr(String addr){
        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z_0-9.]+@(163.com|126.com|sina.com|sohu.com|yahoo.com.cn|qq.com|"
                + "sogou.com|21cn.com|tom.com|etang.com|eyou.com|gmail.com|yahoo.com|msn.com|yeah.net|googlemail.com|"
                + "263.net|mail.com|163.net|citiz.com|chinaren.com|etang.com|hongkong.com)");
        Matcher matcher = pattern.matcher(addr);
        return matcher.matches();
    }

    public String port(String receiver){
        if(!receiver.equals("")) {
            if (isAddr(receiver)) {
                //TODO:将receiver中的域名对应的端口号一字符串返回
                String s1 = receiver.substring(receiver.indexOf("@")+1);
                switch(s1) {
                    case "qq.com":
                    case "163.com":
                    case "126.com":
                    case "gmail.com":
                    case "sina.com":
                    case "googlemail.com":
                    case "163.net":
                        return "465";
                    case "sohu.com":
                    case "yeah.net":
                    case "263.net":
                    case "chinaren.com":
                    case "etang.com":
                    case "hongkong.com":
                        return "25";
                    case "yahoo.com":
                        return "995";
                    case "msn.com":
                    case "mail.com":
                        return "587";
                    default:
                        return "";
                }
            } else {
                //TODO:提示用户收件人邮箱格式不对
                //收件人字体变为红色
                receiverView.setTextColor(this.getResources().getColor(R.color.red));
                //System.out.println("邮箱格式错误！");
                return "";
            }
        }else{
            return "";
        }
    }

}
