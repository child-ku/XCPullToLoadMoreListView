package com.xc.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RecordingAdapter extends BaseAdapter {
    private Context context;
    private List<Recording> recordings;
    private OnItemClickListener onItemClickListener;

    public RecordingAdapter(Context context, List<Recording> recordings) {
        this.context = context;
        this.recordings = recordings;
    }

    @Override
    public int getCount() {
        return recordings.size();
    }

    @Override
    public Object getItem(int position) {
        return recordings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.recording_item, parent, false);
            holder = new ViewHolder();
            holder.tvDuration = (TextView) convertView.findViewById(R.id.tv_duration);
            holder.tvRecordingTime = (TextView) convertView.findViewById(R.id.tv_recording_time);
            holder.tvFileName = (TextView) convertView.findViewById(R.id.tv_file_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 获取当前录音对象
        Recording recording = recordings.get(position);

        // 设置录音时长
        holder.tvDuration.setText(recording.getDurationString());

        // 设置录音时间
        holder.tvRecordingTime.setText(recording.getRecordingTimeString());

        // 设置文件名
        holder.tvFileName.setText(recording.getFileName());

        // 设置点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        return convertView;
    }

    // ViewHolder内部类，用于存储视图引用
    private static class ViewHolder {
        TextView tvDuration;
        TextView tvRecordingTime;
        TextView tvFileName;
    }

    // 设置列表项点击监听器
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // 列表项点击监听器接口
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}