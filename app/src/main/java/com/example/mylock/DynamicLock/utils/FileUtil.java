package com.example.mylock.DynamicLock.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;

public class FileUtil {
	
	/**
	  * �ж��Ƿ���SD��
	  * @return �У�true û�У�false
	  */
	 public static boolean hasSDCard() {
			return Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState());
	 }
	 
	 /** 
	 * ��ȡsd��·�� ˫sd��ʱ����õ�������sd�� 
	 *  
	 * @return ����sd��·��
	 */  
	public static String getSDCardPath() {  
	    String cmd = "cat /proc/mounts";  
	    Runtime run = Runtime.getRuntime();// �����뵱ǰ Java Ӧ�ó�����ص�����ʱ����  
	    BufferedInputStream in = null;  
	    BufferedReader inBr = null;  
	    try {  
	        Process p = run.exec(cmd);// ������һ��������ִ������  
	        in = new BufferedInputStream(p.getInputStream());  
	        inBr = new BufferedReader(new InputStreamReader(in));  
	  
	        String lineStr;  
	        while ((lineStr = inBr.readLine()) != null) {  
	            // �������ִ�к��ڿ���̨�������Ϣ  

	            if (lineStr.contains("sdcard")  
	                    && lineStr.contains(".android_secure")) {  
	                String[] strArray = lineStr.split(" ");  
	                if (strArray != null && strArray.length >= 5) {  
	                    String result = strArray[1].replace("/.android_secure",  
	                            "");  
	                    return result;  
	                }  
	            }  
	            // ��������Ƿ�ִ��ʧ�ܡ�  
	            if (p.waitFor() != 0 && p.exitValue() == 1) {  
	                // p.exitValue()==0��ʾ����������1������������  
	            }
	        }  
	    } catch (Exception e) {  
	        // return Environment.getExternalStorageDirectory().getPath();
	    } finally {  
	        try {  
	            if (in != null) {  
	                in.close();  
	            }  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	        try {  
	            if (inBr != null) {  
	                inBr.close();  
	            }  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	    }  
	    return Environment.getExternalStorageDirectory().getPath();  
	}  
	
	/**
	 * ����ռ䷽��csize
	 * */  
   public static String filesize(String spath) {   
   	
	   File path = new File(spath); 
   	   StatFs statfs = new StatFs(path.getPath());  
       //��ȡblock��SIZE  
       long blocSize = statfs.getBlockSize();  
       //��ȡBLOCK����  
       long totalBlocks = statfs.getBlockCount();  
       //���е�Block������  
       long availaBlock = statfs.getAvailableBlocks();  
       
       //�����ܿռ��С�Ϳ��еĿռ��С  
       //String[] total = filesize(totalBlocks * blocSize);  
       String availale = filesize(availaBlock * blocSize);  
       return "";
   }  
   
   /**
	 * ����ռ䷽��csize
	 * */  
   public static String filesize(long size) {   
       String str = "";   
       if (size >= 1024) {   
           str = "KB";   
           size /= 1024;   
           if (size >= 1024) {   
               str = "MB";   
               size /= 1024;   
           }   
       }  
       DecimalFormat formatter = new DecimalFormat();   
       formatter.setGroupingSize(3);   
       return formatter.format(size)+str;   
   }  
   
   public static boolean Availfilesize(String spath) {   
	   boolean b = false;
	   File path = new File(spath); 
	   StatFs statfs = new StatFs(path.getPath());  
       //��ȡblock��SIZE  
       long blocSize = statfs.getBlockSize();  
       //��ȡBLOCK����  
       long totalBlocks = statfs.getBlockCount();  
       //���е�Block������  
       long availaBlock = statfs.getAvailableBlocks();  
       
       long size = availaBlock * blocSize;  
       if (size >= (1024*1024*1)) {   
    	   b = true;
       }else{
    	   b = false;
       }
       return b;   
   }  
   
	
	/**
	 * �����ļ�
	 * 
	 * @param sourceLocation Դ�ļ�
	 * @param targetLocation Ŀ���ļ�
	 * @throws IOException
	 *             Create at 2014-8-21 ����10:47:55
	 */
	public static void copyDirectory(File sourceLocation, File targetLocation)
			throws IOException {
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < sourceLocation.listFiles().length; i++) {

				copyDirectory(new File(sourceLocation, children[i]), new File(
						targetLocation, children[i]));
			}
		} else {

			InputStream in = new FileInputStream(sourceLocation);

			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}

	}

	/**
	 * ɾ��ָ���ļ����������ļ� param path �ļ�����������·��
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�

				File myFilePath = new File(path + "/" + tempList[i]);
				myFilePath.delete(); // ��ɾ�����ļ���

				flag = true;
			}
		}
		return flag;
	}

	/**
	 * ɾ��ָ��·�����ļ�
	 */
	public static void  deleteFile(String path)
	{
		if(path.trim().length()==0)
			return;
		try{
			File file = new File(path);
			if(getSDCardPath()!=null)
			{
				if (file.exists())
				{
			        file.delete();
				}
			}
		}catch(Exception e){}
    }
	
	 public static void save(String fileName, String content ,Context context) throws Exception {
		 
         // ����ҳ������Ķ����ı���Ϣ�����Ե��ļ���������.txt��׺����βʱ���Զ�����.txt��׺
         if (!fileName.endsWith(".txt")) {
             fileName = fileName + ".txt";
         }
          
         byte[] buf = fileName.getBytes("iso8859-1");
          
          
         fileName = new String(buf,"utf-8");
          
         // Context.MODE_PRIVATE��ΪĬ�ϲ���ģʽ��������ļ���˽�����ݣ�ֻ�ܱ�Ӧ�ñ�����ʣ��ڸ�ģʽ�£�д������ݻḲ��ԭ�ļ������ݣ���������д�������׷�ӵ�ԭ�ļ��С�����ʹ��Context.MODE_APPEND
         // Context.MODE_APPEND��ģʽ�����ļ��Ƿ���ڣ����ھ����ļ�׷�����ݣ�����ʹ������ļ���
         // Context.MODE_WORLD_READABLE��Context.MODE_WORLD_WRITEABLE������������Ӧ���Ƿ���Ȩ�޶�д���ļ���
         // MODE_WORLD_READABLE����ʾ��ǰ�ļ����Ա�����Ӧ�ö�ȡ��MODE_WORLD_WRITEABLE����ʾ��ǰ�ļ����Ա�����Ӧ��д�롣
         // ���ϣ���ļ�������Ӧ�ö���д�����Դ��룺
         // openFileOutput("output.txt", Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);

         FileOutputStream fos =  new FileOutputStream(fileName);
         fos.write(content.getBytes());
         fos.close();
     }

}
