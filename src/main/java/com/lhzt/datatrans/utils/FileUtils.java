package com.lhzt.datatrans.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * Created by pansen on 2017/10/18.
 */
public class FileUtils {

  private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

  /**
   * 追加文件：使用FileWriter
   */
  public static void fileAppender(File file, String content) {
    FileWriter writer = null;
    try {
      // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
      writer = new FileWriter(file, true);
      writer.write(content);
      writer.write(System.getProperty("line.separator"));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 追加文件：使用FileWriter
   */
  public static void fileAppender(File file, List<String> contents) {
    FileWriter writer = null;
    try {
      // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
      writer = new FileWriter(file, true);
      for (String content : contents) {
        writer.write(content);
        writer.write(System.getProperty("line.separator"));
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  public static void findStringInFile(String path, String findStr) throws IOException {
    File file = new File(path);
    InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");//考虑到编码格式
    BufferedReader bufferedReader = new BufferedReader(read);
    String line = null;
    while ((line = bufferedReader.readLine()) != null) {
      if (line.startsWith("#")) {
        continue;
      }
      //指定字符串判断处
      if (line.contains(findStr)) {
        System.out.println(line);
      }
    }
  }

  public static List<String> readLastNLines(File file, long numRead) {
    // 定义结果集
    List<String> result = new java.util.ArrayList<String>();
    //行数统计
    long count = 0;

    // 排除不可读状态
    if (!file.exists() || file.isDirectory() || !file.canRead()) {
      return null;
    }

    // 使用随机读取
    RandomAccessFile fileRead = null;
    try {
      //使用读模式
      fileRead = new RandomAccessFile(file, "r");
      //读取文件长度
      long length = fileRead.length();
      //如果是0，代表是空文件，直接返回空结果
      if (length == 0L) {
        return result;
      } else {
        //初始化游标
        long pos = length - 1;
        while (pos > 0) {
          pos--;
          //开始读取
          fileRead.seek(pos);
          //如果读取到\n代表是读取到一行
          if (fileRead.readByte() == '\n') {
            //使用readLine获取当前行
            String line = fileRead.readLine();
            //保存结果
            result.add(line);

            //打印当前行
            logger.info("read line:{}", line);

            //行数统计，如果到达了numRead指定的行数，就跳出循环
            count++;
            if (count == numRead) {
              break;
            }
          }
        }
        if (pos == 0) {
          fileRead.seek(0);
          result.add(fileRead.readLine());
        }
      }
    } catch (IOException e) {
      logger.error("readLastNLines error", e);
    } finally {
      if (fileRead != null) {
        try {
          //关闭资源
          fileRead.close();
        } catch (Exception e) {
          logger.error("readLastNLines error", e);
        }
      }
    }

    return result;
  }


}
