package com.xc.demo;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordingManager {
    private static final String TAG = "RecordingManager";
    private static final String RECORDING_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Recordings";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss");

    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;
    private boolean isPaused = false;
    private long startTime = 0;
    private long pausedTime = 0;
    private String currentFilePath;

    public RecordingManager() {
        // 创建录音目录
        File dir = new File(RECORDING_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // 开始录音
    public boolean startRecording() {
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            // 创建录音文件
            String fileName = "RECORDING_" + DATE_FORMAT.format(new Date()) + ".mp4";
            currentFilePath = RECORDING_DIR + "/" + fileName;
            mediaRecorder.setOutputFile(currentFilePath);

            mediaRecorder.prepare();
            mediaRecorder.start();

            isRecording = true;
            isPaused = false;
            startTime = System.currentTimeMillis();
            pausedTime = 0;

            Log.d(TAG, "开始录音: " + currentFilePath);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "录音失败: " + e.getMessage());
            release();
            return false;
        }
    }

    // 暂停录音
    public void pauseRecording() {
        if (isRecording && !isPaused && mediaRecorder != null) {
            try {
                mediaRecorder.pause();
                isPaused = true;
                pausedTime += System.currentTimeMillis() - startTime;
                Log.d(TAG, "暂停录音");
            } catch (IllegalStateException e) {
                Log.e(TAG, "暂停录音失败: " + e.getMessage());
            }
        }
    }

    // 恢复录音
    public void resumeRecording() {
        if (isRecording && isPaused && mediaRecorder != null) {
            try {
                mediaRecorder.resume();
                isPaused = false;
                startTime = System.currentTimeMillis();
                Log.d(TAG, "恢复录音");
            } catch (IllegalStateException e) {
                Log.e(TAG, "恢复录音失败: " + e.getMessage());
            }
        }
    }

    // 停止录音
    public Recording stopRecording() {
        if (isRecording && mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                isRecording = false;
                isPaused = false;

                long duration = (System.currentTimeMillis() - startTime + pausedTime) / 1000;
                Date recordingTime = new Date();
                String fileName = new File(currentFilePath).getName();

                Log.d(TAG, "停止录音，时长: " + duration + "秒");

                Recording recording = new Recording(fileName, duration, recordingTime, currentFilePath);
                release();
                return recording;
            } catch (IllegalStateException e) {
                Log.e(TAG, "停止录音失败: " + e.getMessage());
                release();
                return null;
            }
        }
        return null;
    }

    // 释放资源
    private void release() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
        isRecording = false;
        isPaused = false;
        startTime = 0;
        pausedTime = 0;
        currentFilePath = null;
    }

    // 获取当前录音状态
    public boolean isRecording() {
        return isRecording;
    }

    // 获取当前录音是否暂停
    public boolean isPaused() {
        return isPaused;
    }

    // 获取录音时长（秒）
    public long getCurrentDuration() {
        if (isRecording) {
            if (isPaused) {
                return pausedTime / 1000;
            } else {
                return (System.currentTimeMillis() - startTime + pausedTime) / 1000;
            }
        }
        return 0;
    }

    // 获取录音目录
    public static String getRecordingDir() {
        return RECORDING_DIR;
    }
}