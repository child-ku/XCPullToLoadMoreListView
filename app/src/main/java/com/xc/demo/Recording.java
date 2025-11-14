package com.xc.demo;

import java.io.Serializable;
import java.util.Date;

public class Recording implements Serializable {
    private String fileName;
    private long duration;
    private Date recordingTime;
    private String filePath;

    public Recording(String fileName, long duration, Date recordingTime, String filePath) {
        this.fileName = fileName;
        this.duration = duration;
        this.recordingTime = recordingTime;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getRecordingTime() {
        return recordingTime;
    }

    public void setRecordingTime(Date recordingTime) {
        this.recordingTime = recordingTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // 将时长转换为分:秒格式
    public String getDurationString() {
        long minutes = duration / 60;
        long seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    // 将录制时间转换为字符串格式
    public String getRecordingTimeString() {
        return android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", recordingTime).toString();
    }
}