package com.example.mylock.DynamicLock.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpTools {

    public static String GetContent(final String u) {
        byte[] bs = null;
        try {
            URL url = new URL(u);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10 * 1000);
            conn.setRequestMethod("GET");
            InputStream inStream = conn.getInputStream();
            bs = readInputStream(inStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = null;
        if (bs != null) {
            result = new String(bs);
        }
        return result;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        return outStream.toByteArray();
    }

    public static String GetContentByPost(String url, String da)
            throws Exception {
        byte[] entitydata = da.getBytes();
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5 * 10000);
        conn.setDoOutput(true);
        //application/x-www-form-urlencoded
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length",
                String.valueOf(entitydata.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(entitydata);
        outStream.flush();
        outStream.close();
        int code = conn.getResponseCode();
        System.out.println("==========" + code);
        InputStream inStream = conn.getInputStream();
        String sCurrentLine;
        String sTotalString = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inStream));
        while ((sCurrentLine = reader.readLine()) != null) {
            sTotalString += sCurrentLine;
        }
        return sTotalString.trim().length() == 0 ? null : sTotalString.trim();
    }

    public static int validStatusCode(String url) {
        // try{
        // HttpURLConnection.setFollowRedirects(false);
        // URL validatedURL = new URL(url);
        // HttpURLConnection con = (HttpURLConnection)
        // validatedURL.openConnection();
        // con.setRequestMethod("HEAD");
        // int responseCode = con.getResponseCode();
        // System.out.println(responseCode);
        // if (responseCode == 404 || responseCode == 405
        // || responseCode == 504) {
        // return false;
        // }
        // return true;
        //
        // }catch(Exception e) {
        // return false;
        // }

        try {
            // HttpURLConnection uConnection = (HttpURLConnection) (new
            // URL(url)).openConnection();
            // uConnection.connect();
            // int responseCode = uConnection.getResponseCode();
            // if (responseCode == 404 || responseCode == 405
            // || responseCode == 504) {
            // return false;
            // }
            // return true;
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setReadTimeout(10 * 1000);
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);
            if (responseCode == 404 || responseCode == 405
                    || responseCode == 504) {
                return 0;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}