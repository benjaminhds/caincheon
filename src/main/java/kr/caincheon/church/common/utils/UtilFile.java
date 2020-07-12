// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UtilFile.java

package kr.caincheon.church.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UtilFile
{
    class FileName
    {

        public void separate(String fileFullPathName)
            throws Exception
        {
            try
            {
                PATH = fileFullPathName.substring(0, fileFullPathName.lastIndexOf(File.separator));
                FILE_NAME = fileFullPathName.substring(fileFullPathName.lastIndexOf(File.separator) + File.separator.length());
            }
            catch(StringIndexOutOfBoundsException e)
            {
                try
                {
                    PATH = fileFullPathName.substring(0, fileFullPathName.lastIndexOf("/"));
                    FILE_NAME = fileFullPathName.substring(fileFullPathName.lastIndexOf("/") + 1);
                }
                catch(Exception e1)
                {
                    throw e1;
                }
            }
        }

        public String PATH;
        public String FILE_NAME;
        final UtilFile this$0;

        public FileName()
        {
            this$0 = UtilFile.this;
        }
    }


    public UtilFile()
    {
    }

    public static boolean copy(String sourceFullPathName, String targetFullPathName)
        throws Exception
    {
		// source info check
		if( !new File(sourceFullPathName).exists() ) {
			throw new Exception("The source file is not exist.["+sourceFullPathName+"]");
		}
		FileName source = new UtilFile().new FileName();
		source.separate(sourceFullPathName);
		
		// target info check
		FileName target = new UtilFile().new FileName();
		target.separate(targetFullPathName);
		if(target.FILE_NAME.indexOf(".") == -1) {
			// targetFullPathName is path
			new File(targetFullPathName).mkdirs();
			
			// full name combination
			targetFullPathName = targetFullPathName + File.separator + source.FILE_NAME;
			
		} else {
			new File(target.PATH).mkdirs();
		}
		
		/* 복사시작 : 스트림, 채널 선언 */
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		FileChannel fcin = null;
		FileChannel fcout = null;
		try {

			// 스트림 생성
			inputStream = new FileInputStream(new File(sourceFullPathName));
			outputStream = new FileOutputStream(new File(targetFullPathName));

			// 채널 생성
			fcin = inputStream.getChannel();
			fcout = outputStream.getChannel();

			// 채널을 통한 스트림 전송
			long size = fcin.size();
			fcin.transferTo(0, size, fcout);
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			// 자원 해제
			try { fcout.close(); } catch (IOException ioe) {}
			try { fcin.close();  } catch (IOException ioe) {}

			try { outputStream.close(); } catch (IOException ioe) {}
			try { inputStream.close();  } catch (IOException ioe) {}
		}
	
		// file size compare
		return new File(sourceFullPathName).length() == new File(targetFullPathName).length() ? true : false;
    }

    public static String move(String sourceFullPathName, String targetFullPathName)
        throws Exception
    {
        FileName info = (new UtilFile()). new FileName();
        info.separate(targetFullPathName);
        return move(sourceFullPathName, info.PATH, info.FILE_NAME);
    }

    public static String move(String sourceFullPathName, String targetDir, String targetFileName)
        throws Exception
    {
    	// make a target dir
        File dir = new File(targetDir);
        if(!dir.exists()) dir.mkdirs();
        String targetFullPathName = targetDir;
        
        // make a target FullName
        if(targetFileName == null || targetFileName.trim().length() == 0) {
            FileName info = (new UtilFile()). new FileName();
            info.separate(sourceFullPathName);
            targetFullPathName += File.separator + info.FILE_NAME;
        } else {
        	targetFullPathName += File.separator + targetFileName;
        }
        
        // move
        File srcFile = new File(sourceFullPathName);
        if(srcFile.renameTo(new File(targetFullPathName)))
            return targetFullPathName;
        else
            return null;
    }
    
    /**
     * 파일을 읽은 후 byte[] 로 리턴
     * @param fileNm
     * @return byte[]
     * @throws IOException
     */
    public static byte[] readContents(String fileNm) throws IOException {
    	Path p = Paths.get(fileNm);
    	return Files.readAllBytes(p);
    }
    

    /**
     * 파일을 읽은 후 byte[] 로 리턴
     * @param fileNm
     * @return byte[]
     * @throws IOException
     */
    public static String readAllLines(String fileNm) throws IOException {
    	String body = "";
    	
    	int i = 0 ;
    	Path p = Paths.get(fileNm);
    	List<String> lines = Files.readAllLines(p);
    	for(String line : lines) {
    		body += (i > 0 ? "\n":"") + line;
    		i++;
    	}
    	/* 다른방식으로 읽기
    	BufferedReader br = Files.newBufferedReader(p, Charset.forName("UTF-8"));
    	String line = null;
    	while( (line=br.readLine()) != null ) {
    		body += line;
    	}
    	br.close();*/
    	
    	return body;
    }
    
//    public static void main(String[] args)  throws IOException {
//    	String fileNm = "C:/WorkspaceEclipse/caincheon/src/main/webapp/community/html/email_form_6.html";
//		String c = UtilFile.readAllLines(fileNm);
//		System.out.println(c.replace("$NAME","한동수"));
//	}
    
}
