// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CaptchaServletUtil.java

package kr.caincheon.church.common.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioSystem;

import nl.captcha.audio.Sample;
/*
 * 
 */
public final class CaptchaServletUtil
{

    public CaptchaServletUtil()
    {
    }

    public static void writeImage(HttpServletResponse response, BufferedImage bi)
    {
        response.setHeader("Cache-Control", "private,no-cache,no-store");
        response.setContentType("image/png");
        try
        {
            writeImage(((OutputStream) (response.getOutputStream())), bi);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeImage(OutputStream os, BufferedImage bi)
    {
        try
        {
            ImageIO.write(bi, "png", os);
            os.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeAudio(OutputStream os, Sample sample)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            AudioSystem.write(sample.getAudioInputStream(), javax.sound.sampled.AudioFileFormat.Type.WAVE, baos);
            os.write(baos.toByteArray());
            os.flush();
            os.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeAudio(HttpServletResponse response, Sample sample)
    {
        response.setHeader("Cache-Control", "private,no-cache,no-store");
        response.setContentType("audio/wave");
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            AudioSystem.write(sample.getAudioInputStream(), javax.sound.sampled.AudioFileFormat.Type.WAVE, baos);
            response.setContentLength(baos.size());
            OutputStream os = response.getOutputStream();
            os.write(baos.toByteArray());
            os.flush();
            os.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
