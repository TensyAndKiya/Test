package com.clei.Y2021.M04.D13;

import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 从视频中截取图片
 *
 * @author KIyA
 * @backStory 手动暂停播放视频到指定位置来截图太麻烦了
 * @date 2021-04-13
 */
public class GetFrameImgFromVideo {

    /**
     * 视频存放路径
     */
    private final static String VIDEO_PATH = "D:/Temp/郭顶 - 水星记.mkv";

    /**
     * 目标截图存放路径
     */
    private final static String TARGET_IMG_PATH = "D:/Picture/screenshot";

    /**
     * 截图前缀名称
     */
    private final static String IMG_NAME_PREFIX = "Mary";

    /**
     * 截图文件后缀
     */
    private final static String IMG_NAME_SUFFIX = "jpg";

    /**
     * 一秒的微秒数
     */
    private final static long ONE_SECOND_MICROSECOND = 1_000_000L;

    public static void main(String[] args) throws Exception {
        getImg(50, 20, 1);
    }

    /**
     * 提取第startSecond秒开始的imgNum帧图片
     *
     * @param startSecond 开始秒数
     * @param imgNum      要截取的图片数
     * @param grabMethod  截取方法 1按时间 2按帧数
     * @throws Exception 各种异常
     */
    private static void getImg(int startSecond, int imgNum, int grabMethod) throws Exception {
        if (startSecond < 0 || imgNum < 1) {
            throw new RuntimeException("参数错误");
        }
        // 开始
        FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault(VIDEO_PATH);
        grabber.start();
        // 看下元数据
        grabber.getMetadata().forEach((k, v) -> PrintUtil.log("K : {}, V : {}", k, v));
        grabber.getAudioMetadata().forEach((k, v) -> PrintUtil.log("K : {}, V : {}", k, v));
        grabber.getVideoMetadata().forEach((k, v) -> PrintUtil.log("K : {}, V : {}", k, v));
        // 看下时间
        long timeLength = grabber.getLengthInTime();
        PrintUtil.log("视频时长： {} 微秒", timeLength);
        PrintUtil.log("视频时长： {} 毫秒", timeLength / 1000);
        PrintUtil.log("视频时长： {} 秒", timeLength / ONE_SECOND_MICROSECOND);
        long startMicroSecond = (startSecond - 1) * ONE_SECOND_MICROSECOND;
        // 选择截取方式
        if (1 == grabMethod) {
            grabByTime(grabber, startMicroSecond, imgNum);
        } else {
            grabByFrame(grabber, startMicroSecond, imgNum);
        }
        // 结束
        grabber.stop();

        PrintUtil.log("END.");
    }

    /**
     * 根据时间来设置timestamp截取对应帧的图片
     *
     * @param grabber          截取器
     * @param startMicroSecond 开始微秒数
     * @param imgNum           要截取的图片数
     * @throws Exception 各种异常
     */
    private static void grabByTime(FFmpegFrameGrabber grabber, long startMicroSecond, int imgNum) throws Exception {
        long timeLength = grabber.getLengthInTime();
        int frameNumber = grabber.getLengthInVideoFrames();
        long unit = new BigDecimal(timeLength).divide(new BigDecimal(frameNumber), 64, RoundingMode.HALF_UP).longValue();
        for (int i = 0; i < imgNum && startMicroSecond < timeLength; i++) {
            long timestamp = startMicroSecond + i * unit;
            grabber.setVideoTimestamp(timestamp);
            // 截图
            grabFrame(grabber, i);
        }
    }

    /**
     * 根据时间来设置frameNumber截取对应帧的图片
     *
     * @param grabber          截取器
     * @param startMicroSecond 开始微秒数
     * @param imgNum           要截取的图片数
     * @throws Exception 各种异常
     */
    private static void grabByFrame(FFmpegFrameGrabber grabber, long startMicroSecond, int imgNum) throws Exception {
        long timeLength = grabber.getLengthInTime();
        int frameNumber = grabber.getLengthInVideoFrames();
        BigDecimal ratio = new BigDecimal(startMicroSecond).divide(new BigDecimal(timeLength), 64, RoundingMode.HALF_UP);
        int startFrame = new BigDecimal(frameNumber).multiply(ratio).intValue();
        int endFrame = startFrame + imgNum;
        endFrame = Math.min(frameNumber, endFrame);
        for (int i = startFrame, j = 0; i < endFrame; i++, j++) {
            grabber.setVideoFrameNumber(startFrame);
            // 截图
            grabFrame(grabber, j);
        }
    }

    /**
     * 截图
     *
     * @param grabber 截取器
     * @param i       图片序数
     * @throws Exception 各种异常
     */
    private static void grabFrame(FFmpegFrameGrabber grabber, int i) throws Exception {
        Frame frame = grabber.grabFrame();
        // 旋转
        String rotate = grabber.getVideoMetadata("rotate");
        if (StringUtil.isNotBlank(rotate)) {
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            opencv_core.IplImage source = converter.convert(frame);
            opencv_core.IplImage img = opencv_core.IplImage.create(source.height(), source.width(), source.depth(), source.nChannels());
            opencv_core.cvTranspose(source, img);
            opencv_core.cvFlip(img, img, Integer.parseInt(rotate));
            frame = converter.convert(img);
        }
        // 生成图片
        if (null != frame && null != frame.image) {
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage bufferedImage = converter.getBufferedImage(frame);
            String targetFilePath = TARGET_IMG_PATH + File.separator + IMG_NAME_PREFIX + '_' + i + '.' + IMG_NAME_SUFFIX;
            File file = new File(targetFilePath);
            ImageIO.write(bufferedImage, IMG_NAME_SUFFIX, file);
        }
    }
}
