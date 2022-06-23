package com.ara.todayoutfit;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class TestDeveloper {

    public static void main(String[] args) throws IOException {

        File file = new File("/Users/ara/test.sql");

        if (!file.exists()) {
            file.createNewFile();
        }

        String sql = "";
        int maxNum = 1000000;
        for (int i = 0; i < maxNum; i++) {
            if ((i/maxNum)%10 == 0) {
                System.out.printf("%d%%\n", i/maxNum);
            }
            sql += String.format("insert into post(content, location, write_date) values ('날씨가 좋아요', '광진구', now());");
        }

        System.out.println(sql);

        FileWriter fw = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(sql);
    }

}
