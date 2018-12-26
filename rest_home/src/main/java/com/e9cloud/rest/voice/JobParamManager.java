package com.e9cloud.rest.voice;


public abstract class JobParamManager {


    private static final String VOICENOTIF_JOBNAME = "voicenotify_jobname_";


    protected JobParamManager() {
    }

    public static String generateJobName(String key) {
        return VOICENOTIF_JOBNAME + key;
    }




}