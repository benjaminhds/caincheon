package kr.caincheon.church.common.base;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import kr.caincheon.church.common.CAGiguInfoUtil;
import kr.caincheon.church.dao.TempleDao;

/**
 * QR코드 생성하는 유틸리티의 Wrapper 임.
 * @author benjamin
 */
public class CommonGetSptringBean  {

	private final Logger _logger = Logger.getLogger(getClass());
	
	public final static String MVC_XML = "mvc-context.xml";
	
	private static ApplicationContext mAppCtx = null;
	
	/*
	 * Private Constructor
	 */
	private CommonGetSptringBean() {
	}
	
	/*
	 * Singleton-pattern
	 */
	private static class Singleton {
		private static CommonGetSptringBean INSTANCE = new CommonGetSptringBean();
		private static CommonGetSptringBean get() {
			if(INSTANCE==null)
				INSTANCE = new CommonGetSptringBean();
			return INSTANCE;
		}
	}
	
	/*
	 * 
	 */
	public static CommonGetSptringBean getInstance() {
		return Singleton.get();
	}
	
	/*
	 * 
	 */
	public synchronized Object getBean(Class clazz) {
		// on the running tomcat server, on the web service
		return getBean(MVC_XML, clazz);
	}
	
	/* */
	public synchronized Object getBean(String springBeanName) {
		return this.getBean(MVC_XML, springBeanName);
	}
	
	/* */
	public synchronized Object getBean(String springConfigXmlFile, String springBeanName) {
		
		mAppCtx = getApplicationContext(springConfigXmlFile);
		if(mAppCtx == null) 
			new java.io.IOException("class path resource ["+springConfigXmlFile+"] cannot be opened because it does not exist");
		
		return mAppCtx.getBean(springBeanName);
	}
	
	/* */
	public synchronized Object getBean(String springConfigXmlFile, Class clazz) {

		Object rtObj = null;
		
		mAppCtx = getApplicationContext(springConfigXmlFile);
		if(mAppCtx == null) 
			new java.io.IOException("class path resource ["+springConfigXmlFile+"] cannot be opened because it does not exist");
		
		
		String springBeanName = clazz.getSimpleName();
		       springBeanName = springBeanName.substring(0,1).toLowerCase() + springBeanName.substring(1);
		
		try {
			rtObj = mAppCtx.getBean(springBeanName, clazz);
			if(rtObj == null) {
//				GenericXmlApplicationContext gctx = null;
//				gctx = new org.springframework.context.support.GenericXmlApplicationContext();
//				gctx.load(springConfigXmlFile);
				
				
				
				String[] ss = mAppCtx.getBeanDefinitionNames();
				String compBean = "";
				for(int i=0 ; i<ss.length; i++) {
					compBean = mAppCtx.getBean(ss[i]).getClass().getName();
					
					System.out.println(i + "] "+ ss[i] + ":" 
							 + compBean + " ===> is found ? " 
							 + (compBean.equalsIgnoreCase(springBeanName))
							 + rtObj );
					if(compBean.equalsIgnoreCase(springBeanName)) {
						rtObj = mAppCtx.getBean(springBeanName);
						break;
					}
//					System.out.println(i + "] "+ ss[i] + ":" 
//										 + gctx.getBeanDefinition(ss[i]).getClass().getName() + " ===> is found ? " 
//										 + (gctx.getBeanDefinition(ss[i]).getClass().getName().equalsIgnoreCase(springBeanName))
//										 + rtObj );
//					if(gctx.getBeanDefinition(ss[i]).getClass().getName().equalsIgnoreCase(springBeanName)) {
//						rtObj = gctx.getBean(springBeanName);
//						break;
//					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtObj;
		
	}
	
	/**
	 * xml 파일을 찾아서 ApplicationContext로 변환하여 리턴한다.
	 * @param springConfigXmlFile
	 * @return ApplicationContext
	 */
	private synchronized ApplicationContext getApplicationContext(String springConfigXmlFile) {
		if(mAppCtx == null) {
			if( !springConfigXmlFile.contains("classpath:") || !springConfigXmlFile.contains("file:") ) {
				String tmpXml = "", tmpPath = this.getClass().getResource("/").getPath();
				int idx = springConfigXmlFile.lastIndexOf("/");
				if(idx > -1) {
					tmpXml  = springConfigXmlFile.substring( idx+1 );
					tmpPath = springConfigXmlFile.substring( 0, idx+1 );
				} else {
					tmpXml  = springConfigXmlFile;
					tmpPath = tmpPath.replaceAll("classes/", "");
				}
				
//				System.out.println( " >>>0 " + this.getClass().getResource("/").getPath() );
//				System.out.println( " >>>1 " + tmpPath );
//				System.out.println( " >>>2 " + tmpXml );
//				System.out.println( " >>>3 " + System.getProperty("user.dir") );
				
				File f = findInSubDirectories( tmpPath , tmpXml );
				springConfigXmlFile = "file:" + f.getAbsolutePath();
			}
			
			try {
				// String springConfigFile = "classpath:../mvc-config.xml" or "file:경로";
				mAppCtx = new ClassPathXmlApplicationContext(springConfigXmlFile);
			} catch (Exception e) {
				mAppCtx = new GenericXmlApplicationContext(springConfigXmlFile);
			}
		}
		return mAppCtx;
	}
	
	/** 
	 * 특정 폴더(baseDir) 이하에서 지정한 파일(searchFileNm)을 찾아서 File 객체를 리턴한다.
	 * @param baseDir - It is a root directory, search basic dir
	 * @param searchFileNm - search a file name in the baseDir
	 * @return File - Found file
	 */
	private File findInSubDirectories(String baseDir, String searchFileNm) {
		File rtFile = null;
		File dir = new File(baseDir);
		File[] fileList = dir.listFiles();
		try {
			for (int i = 0; i < fileList.length; i++) {
				File file = fileList[i];
				if (file.isFile() && file.getName().equals(searchFileNm)) {//대소문자 구분한 파일명 찾기
//					System.out.println("\t 파일 이름 = " + file.getName() + ":: " + file.getAbsolutePath());
					return file;
					
				} else if (file.isDirectory()) {
//					System.out.println("디렉토리 이름 = " + file.getName());
					// 서브디렉토리가 존재하면 재귀호출
					rtFile = findInSubDirectories(file.getCanonicalPath().toString(), searchFileNm);
					if(rtFile!=null) {
						break;
					}
				}
			}
		} catch (IOException e) {
		}
		
		return rtFile;
	}

	
	public static void main(String[] args) {
		/*
		 * user.dir=C:\WorkspaceEclipse\WehuddlingWeb
		 */
		System.out.println(System.getProperties());
		System.out.println(System.getProperty("user.dir"));
		
//		CommonGetSptringBean.getInstance().findInSubDirectories(System.getProperty("user.dir")+"/src", MVC_XML);
//		System.exit(0);
		
		String path = "src/main/webapps/" ;
		
		Class clz = TempleDao.class;
		clz = CAGiguInfoUtil.class;
		
		Object foundBean = null;
//		foundBean = CommonGetSptringBean.getInstance().getBean(path+"WEB-INF/"+MVC_XML , clz);
//		foundBean = CommonGetSptringBean.getInstance().getBean("classpath:../"+MVC_XML , clz);
//		foundBean = CommonGetSptringBean.getInstance().getBean("../"+MVC_XML , clz);
		foundBean = CommonGetSptringBean.getInstance().getBean(clz);
		
		System.out.println( foundBean );
		System.out.println( foundBean.getClass() );
		
		
		
		
	}
	
}
