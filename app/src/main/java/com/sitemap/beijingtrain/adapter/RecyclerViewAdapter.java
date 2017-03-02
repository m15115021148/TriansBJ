package com.sitemap.beijingtrain.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sitemap.beijingtrain.R;
import com.sitemap.beijingtrain.model.TimeModel;

import java.util.List;

/**
 * 横向滑动模块
 */
public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.RCLViewHolder> {
    private List<TimeModel> headDataList;
    private Context context;
    private OnItemClickLitener itemClickLitener;

    // Provide a reference to the type of views that you are using
    // (custom viewholder)
    public class RCLViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public TextView src;

        public RCLViewHolder(ViewGroup v) {
            super(v);

        }
    }

    public void setOnItemClickLitener(OnItemClickLitener itemClickLitener) {
        this.itemClickLitener = itemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(Context context, List<TimeModel> headDataList) {
        this.headDataList = headDataList;
        this.context = context;
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return headDataList.size();
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RCLViewHolder holder, final int position) {
        if (headDataList.get(position).getTime().length()>0){
            String text=headDataList.get(position).getTime().substring(10);
            holder.src.setText(text);
            holder.mTextView.setText(headDataList.get(position).getTime().substring(0,10));
        }else {
            holder.src.setText("");
            holder.mTextView.setText("");
        }
        holder.src.setTextColor(Color.parseColor("#ffffff"));
        if (headDataList.get(position).getType()==1){
            holder.src.setTextColor(Color.parseColor("#04f931"));//绿色
        }


    }

    // Create new views (invoked by the layout manager)
    @Override
    public RCLViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        // create a new view
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_view_item, parent, false);
        TextView imageView = (TextView) v.findViewById(R.id.image_item);
        TextView tv = (TextView) v.findViewById(R.id.text_item);
        RCLViewHolder vh = new RCLViewHolder(v);
        vh.mTextView = tv;
        vh.src = imageView;

        return vh;
    }

}
