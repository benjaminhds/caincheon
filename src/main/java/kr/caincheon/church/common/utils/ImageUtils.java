package kr.caincheon.church.common.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 
 */
public class ImageUtils {
	
	// Thumbnail Path Build
	private static String getThumbnailPath(String sourceImageFullPath) {
		int idx = sourceImageFullPath.lastIndexOf("/");
		if(idx==-1) {
			idx = sourceImageFullPath.lastIndexOf("\\");
		}
		String thumbPath = sourceImageFullPath.substring(0, idx+1) + "thumbnail" + java.io.File.separator;
		File path = new java.io.File(thumbPath);
		if( !path.exists() ) {
			path.mkdirs();
		}
		return thumbPath + sourceImageFullPath.substring(idx+1);
	}
	
	// make a Thumbnail file
	public static boolean createThumbnail(String sourceImageFullPath) {
		boolean rtBool = false;
        try {
        	String srcImg = sourceImageFullPath;//"E:"+File.separator+"TestImageThumbnail.jpg";
        	String thmImg = getThumbnailPath(sourceImageFullPath);//"E:"+File.separator+"thumbnail/TestImageThumbnail.jpg";
        	String extNm = sourceImageFullPath.substring(sourceImageFullPath.lastIndexOf(".")+1);  // 확장자명 : jpg, jpeg, gif, png, etc.....
            //Thumbnail width size
            int thumbnail_width = 200;
            //Thumbnail Height size
            int thumbnail_height = 0;
            //Original File Absolute Path
            File origin_file_name = new File(srcImg);
            //Thumbnail File Absolute Path
            File thumb_file_name = new File(thmImg);
            // resize
            // x : y = x2(200) : y2
            // y2 = y * x2(200) / x
            Image img = new ImageIcon(srcImg).getImage();
            thumbnail_height = img.getHeight(null) * thumbnail_width / img.getWidth(null);
            
            BufferedImage buffer_original_image = ImageIO.read(origin_file_name);
            BufferedImage buffer_thumbnail_image = new BufferedImage(thumbnail_width, thumbnail_height, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic = buffer_thumbnail_image.createGraphics();
            graphic.drawImage(buffer_original_image, 0, 0, thumbnail_width, thumbnail_height, null);
            //ImageIO.write(buffer_thumbnail_image, "jpg", thumb_file_name);
            ImageIO.write(buffer_thumbnail_image, extNm, thumb_file_name);
            rtBool = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtBool;
    }
	
	/**
	 * 지정한 원본 파일과 자동생성된 썸네일 파일을 함께 지운다.
	 * @return
	 */
	public static boolean deleteFileWithThumbnail(String fullPath, String imgFileName) {
		boolean rtBool = false;
		// Original Image File delete
		java.io.File delfile = new java.io.File(fullPath + imgFileName);
    	if(delfile.exists()) {
    		delfile.delete();
    		// Thumbnail delete
    		delfile = new java.io.File(fullPath + "thumbnail" + java.io.File.separator + imgFileName);
    		delfile.delete();
    		rtBool = true;
    	}
		return rtBool;
	}
}
