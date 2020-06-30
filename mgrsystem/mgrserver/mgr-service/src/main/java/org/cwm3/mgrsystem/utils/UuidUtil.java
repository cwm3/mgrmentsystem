package org.cwm3.mgrsystem.utils;

import java.util.UUID;

/**
 * @description:
 * @author: chengweiming
 * @time: 2020/6/29 11:56
 */
public class UuidUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    public static void main(String[] args) {
        System.out.println("格式前的UUID ： " + UUID.randomUUID().toString());
        System.out.println("格式化后的UUID ：" + getUUID());
    }
}
