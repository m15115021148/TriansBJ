package com.sitemap.beijingtrain.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sitemap.beijingtrain.R;
import com.sitemap.beijingtrain.model.AllTrainModel;
import com.sitemap.beijingtrain.model.TemperatureModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @desc 适配器
 * Created by chenmeng on 2016/10/18.
 */
public class TrainListViewAdapter extends BaseAdapter{
    private Context mContext;//本类
    private List<TemperatureModel> mList;//数据
    private Holder holder;//帮助类
    private HashMap<TextView,String> map;// 数据集合(温度)

    public TrainListViewAdapter(Context context,List<TemperatureModel> list){
        this.mContext = context;
        this.mList = list;
        map = new HashMap<>();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.train_list_view_item,null);
            holder = new Holder();
            holder.id = (TextView) convertView.findViewById(R.id.train_id);
            holder.number = (TextView) convertView.findViewById(R.id.train_number);
            holder.bg = (LinearLayout) convertView.findViewById(R.id.train_bg);
            holder.tem1 = (TextView) convertView.findViewById(R.id.tem1);
            holder.tem2 = (TextView) convertView.findViewById(R.id.tem2);
            holder.tem3 = (TextView) convertView.findViewById(R.id.tem3);
            holder.tem4 = (TextView) convertView.findViewById(R.id.tem4);
            holder.tem5 = (TextView) convertView.findViewById(R.id.tem5);
            holder.tem6 = (TextView) convertView.findViewById(R.id.tem6);
            holder.tem7 = (TextView) convertView.findViewById(R.id.tem7);
            holder.tem8 = (TextView) convertView.findViewById(R.id.tem8);
            holder.tem9 = (TextView) convertView.findViewById(R.id.tem9);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        TemperatureModel model = mList.get(position);
        holder.id.setText(model.getCarriage_name());
        holder.number.setText(model.getCarriage_number());

        holder.tem1.setText(model.getTem1());
        holder.tem2.setText(model.getTem2());
        holder.tem3.setText(model.getTem3());
        holder.tem4.setText(model.getTem4());
        holder.tem5.setText(model.getTem5());
        holder.tem6.setText(model.getTem6());
        holder.tem7.setText(model.getTem7());
        holder.tem8.setText(model.getTem8());
        holder.tem9.setText(model.getTemp());

        map.put(holder.tem1, model.getTem1());
        map.put(holder.tem2,model.getTem2());
        map.put(holder.tem3,model.getTem3());
        map.put(holder.tem4,model.getTem4());
        map.put(holder.tem5,model.getTem5());
        map.put(holder.tem6,model.getTem6());
        map.put(holder.tem7,model.getTem7());
        map.put(holder.tem8,model.getTem8());
        map.put(holder.tem9,model.getTemp());



        if(model.getType().equals("3")){
            holder.bg.setBackground(mContext.getResources().getDrawable(R.drawable.train_item_bg2));
            holder.id.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.number.setTextColor(mContext.getResources().getColor(R.color.white));
            for (Map.Entry<TextView, String> entry: map.entrySet()) {
                if (isNumeric(entry.getValue())){
                    entry.getKey().setTextColor(mContext.getResources().getColor(R.color.black));
                    entry.getKey().setBackground(mContext.getResources().getDrawable(R.drawable.train_item_bg));
                }else{//背景黄色
                    entry.getKey().setTextColor(mContext.getResources().getColor(R.color.white));
                    entry.getKey().setBackground(mContext.getResources().getDrawable(R.drawable.train_item_bg1));
                }
            }
        }else if(model.getType().equals("2")){
            holder.bg.setBackground(mContext.getResources().getDrawable(R.drawable.train_item_bg));
            holder.id.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.number.setTextColor(mContext.getResources().getColor(R.color.black));

            for (Map.Entry<TextView, String> entry: map.entrySet()) {
                    if (isNumeric(entry.getValue())){
                        entry.getKey().setTextColor(mContext.getResources().getColor(R.color.black));
                        entry.getKey().setBackground(mContext.getResources().getDrawable(R.drawable.train_item_bg));
                    }else{
                        entry.getKey().setTextColor(mContext.getResources().getColor(R.color.white));
                        entry.getKey().setBackground(mContext.getResources().getDrawable(R.drawable.train_item_bg1));
                    }
            }
        }else {
            holder.bg.setBackground(mContext.getResources().getDrawable(R.drawable.train_item_bg));
            holder.id.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.number.setTextColor(mContext.getResources().getColor(R.color.black));
            for (Map.Entry<TextView, String> entry: map.entrySet()) {
                if (isNumeric(entry.getValue())){
                    entry.getKey().setTextColor(mContext.getResources().getColor(R.color.black));
                    entry.getKey().setBackground(mContext.getResources().getDrawable(R.drawable.train_item_bg));
                }else{
                    entry.getKey().setTextColor(mContext.getResources().getColor(R.color.white));
                    entry.getKey().setBackground(mContext.getResources().getDrawable(R.drawable.train_item_bg1));
                }
            }
        }

        return convertView;
    }

    /**
     * 判断是否为数字
     * @param str
     * @return
     */
    public boolean isNumeric(String str){
//        Pattern pattern = Pattern.compile("[0-9]*");
        Pattern p = Pattern.compile("^-?[0-9]*$|^-?([0-9]*)\\.(\\d*)$");
//        Matcher isNum = pattern.matcher(str);
        Matcher isFloat = p.matcher(str);
        if( !isFloat.matches()){
            return false;
        }
        return true;
    }


    private class Holder{
        TextView id;
        TextView number;
        LinearLayout bg;//左侧背景
        TextView tem1,tem2,tem3,tem4,tem5,tem6,tem7,tem8,tem9;
    }

}
