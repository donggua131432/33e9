package com.e9cloud.core.util;

import it.sauronsoftware.jave.*;

import java.io.File;

/**
 * MP3 转 WAV
 */
public class Audio2Wav {

    public static void main(String[] args) throws EncoderException {

        File mp3 = new File("d:\\source.mp3");
        File wav = new File("d:\\target1.wav");

        audio2wav(mp3, wav);

        File wav1 = new File("d:\\source.wav");
        File wav2 = new File("d:\\target2.wav");

        audio2wav(wav1, wav2);

        Encoder encoder = new Encoder();
        MultimediaInfo info = encoder.getInfo(mp3);

    }

    public static void audio2wav(File source, File target) throws EncoderException {
        long time1 = System.currentTimeMillis();

        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_s16le");//设置输出编码格式
        audio.setBitRate(128000);//设置比特率 // 128 kb/s bitrate you should call setBitRate(new Integer(128000)).
        audio.setChannels(1);//设置双声道还是单声道 // 1 = mono, 2 = stereo (Mono 单声道 Stereo 立体)
        audio.setSamplingRate(8000);//设置采样率 //CD-like 44100 Hz sampling-rate, you should call setSamplingRate(new Integer(44100)).
        //audio.setVolume(500);//设置输出音量

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("wav");
        attrs.setAudioAttributes(audio);

        Encoder encoder = new Encoder();
        encoder.encode(source, target, attrs);

        long time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);
    }

}
