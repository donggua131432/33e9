package com.e9cloud.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static String getFileContent(String file) {
        byte[] b = new byte[28];
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(b, 0, 28);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        int i = 0;
        return bytestoH(b);
    }

    public static String bytestoH(byte[] b) {
        if (b == null || b.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            int x = b[i] & 0xFF;
            String hv = Integer.toHexString(x);
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static FileType getType(String file) {
        String s = getFileContent(file);
        if (s == null || s.length() == 0) {
            return null;
        }
        s = s.toUpperCase();
        for (FileType fileType : FileType.values()) {
            if (s.startsWith(fileType.getValue())) {
                return fileType;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // System.out.println(5 ^ (1 << 1));
        File dir = new File("F:/KwDownload/song/");
        for (File f : dir.listFiles()) {
            System.out.println(f.getAbsolutePath());
            System.out.println(getType(f.getAbsolutePath()));
        }
    }
}
