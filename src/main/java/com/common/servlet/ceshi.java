package com.common.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.common.utils.CutImgUtils;
 
public class ceshi {
    public static void main(String[] args) throws IOException {
        CutImgUtils.scale("C:/Users/t/Desktop/login2.jpg","C:/Users/t/Desktop/yasuo.jpg", 180, 240, true);//等比例缩放  输出缩放图片
 
        File newfile=new File("C:/Users/t/Desktop/yasuo.jpg");   
        BufferedImage bufferedimage=ImageIO.read(newfile);
        int width = bufferedimage.getWidth();
        int height = bufferedimage.getHeight();
        //目标将图片裁剪成 宽240，高160
        if (width > 240) {
                                                            /*开始x坐标              开始y坐标             结束x坐标                     结束y坐标*/
            bufferedimage=CutImgUtils.cropImage(bufferedimage,(int) ((width - 240) / 2),0,(int) (width - (width-240) / 2),(int) (height)
                    );
            if (height > 160) {
                bufferedimage=CutImgUtils.cropImage(bufferedimage,0,(int) ((height - 160) / 2),240,(int) (height - (height - 160) / 2)
                        );
            }
        }else{
            if (height > 160) {
                bufferedimage=CutImgUtils.cropImage(bufferedimage,0,(int) ((height - 160) / 2),(int) (width),(int) (height - (height - 160) / 2)
                        );
            }
        }
        ImageIO.write(bufferedimage, "jpg", new File("C:/Users/t/Desktop/caijian.jpg"));    //输出裁剪图片
    }
}