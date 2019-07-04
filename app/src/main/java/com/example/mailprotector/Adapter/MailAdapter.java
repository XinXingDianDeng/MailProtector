package com.example.mailprotector.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mailprotector.ObjStructure.Mail;
import com.example.mailprotector.R;
import com.example.mailprotector.Layout.SwipeListLayout;

import java.util.List;
import java.util.Set;

public class MailAdapter extends BaseAdapter{

    private List<Mail> mData;//定义数据
    private LayoutInflater mInflater;
    /*
    定义构造器，在Activity创建对象Adapter的时候将数据data和Inflater传入自定义的Adapter中进行处理。
    */
    public MailAdapter(List<Mail> data, LayoutInflater inflater){
        mData = data;
        mInflater = inflater;
        System.out.println(data.size());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder = null;
        //获得ListView中的view
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.mail_item, null);
            holder = new ViewHolder();
            holder.imagePhoto = convertView.findViewById(R.id.mail_image);
            holder.imagePoint = convertView.findViewById(R.id.mail_point);
            holder.senderName = convertView.findViewById(R.id.mail_sender);
            holder.mailTheme = convertView.findViewById(R.id.mail_theme);
            holder.mailContent = convertView.findViewById(R.id.mail_content);
            convertView.setTag(holder);   //将Holder存储到convertView中
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //获得邮件对象
        Mail mail = mData.get(position);

        //获得自定义布局中每一个控件的对象。

        /*if(mail.getIsRead()){
            holder.imagePoint.setVisibility(View.INVISIBLE);
        }*/
        //将数据一一添加到自定义的布局中。
        //将数据一一添加到自定义的布局中。
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()
                    +"/MailProtector/"+mail.sender+".jpg");
            holder.imagePhoto.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.senderName.setText(mail.sender);
        holder.mailTheme.setText(mail.theme);
        if(mail.getContent().length()>20){
            holder.mailContent.setText(mail.getContent().substring(0,20)+"...");
        }else {
            holder.mailContent.setText(mail.getContent());
        }
        return convertView ;
    }
    static class ViewHolder{
        ImageView imagePhoto;
        ImageView imagePoint;
        TextView senderName;
        TextView mailTheme;
        TextView mailContent;
    }


}