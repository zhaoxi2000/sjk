package com.ijinshan.sjk.util;

import java.util.Random;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanTest {
    private static final Logger logger = LoggerFactory.getLogger(BeanTest.class);

    @Test
    public void test() {
        // App4Summary app = new App4Summary();
        // String description =
        // "ssssssssssssssssssssssssssssssssdddddddddddddddddddddddddddddddd999999999999999999999999999999999999999999999cccccccccccccccccccccc";
        // app.setDescription(description);
        //
        // description = "sss";
        // app.setDescription(description);
        //
        // description =
        // "<p>手机YY是什么：手机上最大的位置交友乐园</p><p>手机YY的口号：百步之内，必有Y友</p><p>手机YY介绍：手机YY每天看，帅哥美 女有照片，语音短信聊一聊，就在附近可见面。</p><p><strong>手机YY功能：</strong></p><p><strong>1、好友聊天 - </strong>实时显示已发出的文字的状态，支持发送表情、图片、照片、语音对讲、一对一IP电话。</p><p><strong>2、定位距离 - </strong>可以随时随地的发现附近正在使用YY的帅哥、靓妹，精准显示相距多远。</p><p><strong>3、K歌娱乐 - </strong>数千个由网友自创的娱乐频道，可尽情收听K歌、电台等丰富的娱乐节目。</p><p><strong>4、好友广播 -</strong> 高兴时、悲伤时，在广播里喊一句，和朋友们一起分享你的喜怒哀乐。</p><p><strong>5、频道语音 - </strong>使用手机，进入频道团队语音，一起和游戏公会成员征战天下。</p>  ";
        // app.setDescription(description);
        // System.out.println(app.getDescription());
        //
        // LocalTime localTime = new LocalTime("00:00");
        // System.out.println(localTime.toString());
        // System.out.println(localTime.getMillisOfDay());

        int count = 0;
        for (int i = 0; i < 10000; i++) {
            // System.out.println(Math.random());
            int r = new Random().nextInt(5);
            if(r==0){
                count ++;
            }
        }
        System.out.println(count);

    }
}
