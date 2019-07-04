package com.example.mailprotector.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mailprotector.Adapter.MailAdapter;
import com.example.mailprotector.ObjStructure.Mail;
import com.example.mailprotector.ObjStructure.MailBox;
import com.example.mailprotector.ObjStructure.User;
import com.example.mailprotector.Operator.Loader;
import com.example.mailprotector.Operator.Saver;
import com.example.mailprotector.Operator.Sender;
import com.example.mailprotector.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Object msgMe=new Object();
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_icon);
        //如果要在手机上创建一个文件夹，然后往里面存储一些文件，
        // 首先得考虑用户有没有sdcard，如果有就在sdcard上创建一个指定的文件夹，
        // 如果没有则在你的工程所在的目录“/data/data/你的包名”下创建文件夹。

        final File Dir=new File(Environment.getExternalStorageDirectory(),"MailProtector");
        if(!Dir.exists()){Dir.mkdirs();}

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.getHeaderView(0).setBackgroundDrawable(getResources().getDrawable(R.drawable.back));
        navigationView.setNavigationItemSelectedListener(this);

        NavigationMenuView nmv  = (NavigationMenuView) navigationView.getChildAt(0);
        nmv.setVerticalScrollBarEnabled(false);

        final BottomNavigationView bnv=(BottomNavigationView)findViewById(R.id.bottom_nav);


      /*  if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/

        initStatusBar();

        listView = findViewById(R.id.list_items);
        LayoutInflater inflater =getLayoutInflater();


        File file=new File(Dir.getPath(),"myMail.xml");
        Saver saver=new Saver(Dir.getPath());
        Loader loader=new Loader(Dir.getPath());

        if(!file.exists()){
            //保存数据
            try {
                //初始化
                User me=meInit();
                saver.save(me);
                //Toast.makeText(MainActivity.this,"文本保存成功！"+Dir.getPath(),Toast.LENGTH_SHORT).show();
                saver.saveImg(me,getBaseContext().getResources());
                FileInputStream fis=new FileInputStream(file);;
                //User newMe = objLoad(fis);
                User newMe = loader.load(fis);
                List<Bitmap>images=loader.loadImg(newMe);//主页面列表视图
                listView.setAdapter(new MailAdapter(newMe.getBoxAt(0).getIns(),inflater));
                Toast.makeText(MainActivity.this,"读取成功！",Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            //加载数据
            try {
                FileInputStream fis=new FileInputStream(file);;
                //User newMe = objLoad(fis);
                User newMe = loader.load(fis);
                List<Bitmap>images=loader.loadImg(newMe);//主页面列表视图
                listView.setAdapter(new MailAdapter(newMe.getBoxAt(0).getIns(),inflater));
                Toast.makeText(MainActivity.this,"读取成功！",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(MainActivity.this,"没有邮件哦~",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果返回键按下
            //此处写退向后台的处理
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Toast.makeText(this,"00000==",Toast.LENGTH_SHORT).show();
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(R.id.action_write==id){
            Intent intent=new Intent(MainActivity.this,NewMail.class);
            startActivityForResult(intent,1);
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 2){
            Bundle bundle = data.getBundleExtra("bundle");
            Mail draft = (Mail) bundle.getSerializable("draft");
            //System.out.println(draft.getContent());
            //TODO:将draft保存

        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_receiver || id == R.id.nav_sender || id == R.id.nav_draft
        || id == R.id.nav_spam || id == R.id.nav_delete){
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private User meInit(){
        String contenTmp = "";
        User me = new User();
        //me.avatar= BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.myavator);
        me.acct="iamwhatiam";
        me.setPwd("wdnmd_lajidaimahuiwoqingchun");
        me.userName="wssb";
        //Toast.makeText(MainActivity.this,"用户待办...",Toast.LENGTH_SHORT).show();
        for(int i=0;i<50;i++){
            me.friendAdd(i+11 + "@mial.com");
            me.blackAdd(i+61+ "@mial.com");
        }
        for (int i = 0; i < 3; i++) {
            MailBox box = new MailBox();
            box.addr = i+1+ "@mail.com";
            box.setPwd("pwd"+i+1);
            /*switch (i){
                case 0:box.image=BitmapFactory.decodeResource(getBaseContext().getResources(),R.mipmap.test0);break;
                case 1:box.image=BitmapFactory.decodeResource(getBaseContext().getResources(),R.mipmap.test1);break;
                case 2:box.image=BitmapFactory.decodeResource(getBaseContext().getResources(),R.mipmap.test2);break;
                default:break;
            }*/

            me.boxAdd(box);
            //Toast.makeText(MainActivity.this,"邮箱待办...",Toast.LENGTH_SHORT).show();
            for (int j = 0; j < 30; j++) {
                Mail mail = new Mail(j*10+i+11+"@mail.com");
                mail.theme = "theme"+j+1;
                int id = -1;
                switch (j%10){
                    case 0:id=R.string.c0;break;case 1:id=R.string.c1;break;
                    case 2:id=R.string.c2;break;case 3:id=R.string.c3;break;
                    case 4:id=R.string.c4;break;case 5:id=R.string.c5;break;
                    case 6:id=R.string.c6;break;case 7:id=R.string.c7;break;
                    case 8:id=R.string.c8;break;case 9:id=R.string.c9;break;
                    /*case 10:id=R.string.e0;break;case 11:id=R.string.e1;break;
                    case 12:id=R.string.e2;break;case 13:id=R.string.e3;break;
                    case 14:id=R.string.e4;break;case 15:id=R.string.e5;break;
                    case 16:id=R.string.e6;break;case 17:id=R.string.e7;break;
                    case 18:id=R.string.e8;break;case 19:id=R.string.e9;break;*/
                    default:break;
                }
                contenTmp=getBaseContext().getResources().getString(id);
                mail.setContent(contenTmp);
                mail.receiver = box.addr;
                mail.createDate = new Date(System.currentTimeMillis());
                mail.sendDate = new Date(System.currentTimeMillis());
                mail.receiveDate = new Date(System.currentTimeMillis());
                //Toast.makeText(MainActivity.this,"邮件待办...",Toast.LENGTH_SHORT).show();
                box.classify(mail);
                //Toast.makeText(MainActivity.this,"邮件完成！",Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(MainActivity.this,"邮箱完成！",Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this,"用户完成！",Toast.LENGTH_SHORT).show();
        }return me;
    }

    //读取一个文件所有内容
    private String myRead(String path) {
        int len=0;
        StringBuffer str=new StringBuffer("");
        File file=new File(path);
        try {
            FileInputStream is=new FileInputStream(file);
            InputStreamReader isr= new InputStreamReader(is);
            BufferedReader in= new BufferedReader(isr);
            String line=null;
            while( (line=in.readLine())!=null ){
                if(len != 0) {// 处理换行符的问题
                    str.append("\r\n"+line);
                }else{
                    str.append(line);
                }
                len++;
            }
            in.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }



    private void initStatusBar() {
        Window win = getWindow();
        {
            //KITKAT也能满足，只是SYSTEM_UI_FLAG_LIGHT_STATUS_BAR（状态栏字体颜色反转）只有在6.0才有效
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
                // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加
                win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                // 部分机型的statusbar会有半透明的黑色背景
                win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                win.setStatusBarColor(Color.TRANSPARENT);// SDK21
            }
        }
    }
}
