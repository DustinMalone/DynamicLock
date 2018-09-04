package com.example.mylock.DynamicLock.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class BitmapUtil {
	public static Bitmap readBitmapAutoSize(String filePath) {   
		if(filePath==null||filePath.trim().length()==0)
			return null;
		FileInputStream fs = null;  
		BufferedInputStream bs = null;  
		try {  
		    fs = new FileInputStream(filePath);  
		    bs = new BufferedInputStream(fs);  
		    BitmapFactory.Options options = setBitmapOption(filePath);  
		    return BitmapFactory.decodeStream(bs, null, options);  
		} catch (Exception e) {  
		    e.printStackTrace();  
		} finally {  
		    try {  
		        bs.close();  
		        fs.close();  
		    } catch (Exception e) {  
		        e.printStackTrace();  
		    }  
		}  
		return null;  
	}  
	
	private static BitmapFactory.Options setBitmapOption(String file) {  
        BitmapFactory.Options opt = new BitmapFactory.Options();  
        opt.inJustDecodeBounds = true;            
        //����ֻ�ǽ���ͼƬ�ı߾࣬�˲���Ŀ���Ƕ���ͼƬ��ʵ�ʿ�Ⱥ͸߶�  
        BitmapFactory.decodeFile(file, opt);  
  
        int outWidth = opt.outWidth; //���ͼƬ��ʵ�ʸߺͿ�  
        int outHeight = opt.outHeight;  
        int sampleSize = (int) (outHeight / (float) 200);
        if (sampleSize <= 0)
			sampleSize = 1;
        opt.inSampleSize = sampleSize;
        opt.inDither = false;  
        opt.inPreferredConfig = Bitmap.Config.RGB_565;      
        opt.inJustDecodeBounds = false;//���ѱ�־��ԭ  
        return opt;  
    }
	
	public static String bitmapToBase64(Bitmap bitmap) {  
	    String result = null;  
	    ByteArrayOutputStream baos = null;  
	    try {
	        if (bitmap != null) {  
	            baos = new ByteArrayOutputStream();  
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
	            baos.flush();  
	            baos.close();  
	            byte[] bitmapBytes = baos.toByteArray();  
	            result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);  
	        }  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        try {  
	            if (baos != null) {  
	                baos.flush();  
	                baos.close();  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    return result;  
	}  
	public static Bitmap base64ToBitmap(String base64Data) {  
	    byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);  
	    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);  
	}
}
