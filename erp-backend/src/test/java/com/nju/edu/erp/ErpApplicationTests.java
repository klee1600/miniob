package com.nju.edu.erp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

@SpringBootTest
class ErpApplicationTests {

    @Test
    void contextLoads() {
    }

    /*public static void main(String[] args) {
        //"127.0.0.1:8000",
*//*        String s = "211.162.81.";
        for (int i = 1; i <= 254; i++) {
            System.out.println("\"" + s + i + "\",");
        }*//*
        ArrayList<String> devVal = new ArrayList<>();
        String s = "211.162.81.";
        for (int i = 1; i <= 254; i++) {
            devVal.add(s + i);
        }
        for (String s1 : devVal) {
            System.out.println(s1);
        }
    }*/

    public static void main(String[] args) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = (now.get(Calendar.MONTH) + 1);
        int day = now.get(Calendar.DATE);
        System.out.println(day);
        System.out.println(month);
        System.out.println(year);


        System.out.println("+++++++++++++++++++++");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2022);
        c.set(Calendar.MONTH, 7-1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(c.getTime());
    }

}
