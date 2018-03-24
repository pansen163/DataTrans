package com.lhzt.datatrans;


import com.lhzt.datatrans.utils.TransUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

/**
 * Created by pansen on 2017/10/17.
 */
@SpringBootApplication
public class DataTransMain implements CommandLineRunner {

  public static String pathName = "/tmp/aaa";

  private static Logger logger = LoggerFactory.getLogger(DataTransMain.class);

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(DataTransMain.class);
    app.run(args);
  }

  @Override
  public void run(String... strings) throws IOException {
    File dirFile = new File(pathName);
    if (!dirFile.exists()) {
      System.out.println("do not exit");
      return;
    }

    if (!dirFile.isDirectory()) {
      if (dirFile.isFile()) {
        System.out.println(dirFile.getCanonicalFile());
      }
      return;
    }

    String[] fileList = dirFile.list();
    for (int i = 0; i < fileList.length; i++) {
      String string = fileList[i];
      File souce = new File(dirFile.getPath(), string);
      TransUtil.dataTrans(souce, 1400000);//每4200000新建文件
      //TransUtil.dataTrans(souce, 3);//每4200000新建文件
    }
    System.out.println("结束");
  }
}
