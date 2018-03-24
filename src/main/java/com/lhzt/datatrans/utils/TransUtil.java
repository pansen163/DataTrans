package com.lhzt.datatrans.utils;

import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pansen on 2018/1/24.
 */
public class TransUtil {

  public static Map<String, String> map = new HashMap<String, String>() {
    {
      put("AUDCAD", "36");
      put("AUDCHF", "23");
      put("AUDJPY", "20");
      put("AUDNZD", "43");
      put("AUDUSD", "19");
      //put("CADCHF", "");
      put("CADJPY", "28");
      //put("CHFJPY", "");
      put("EURAUD", "25");
      put("EURCAD", "25");
      put("EURCHF", "28");
      put("EURGBP", "15");
      put("EURJPY", "16");
      put("EURNZD", "53");
      put("EURUSD", "12");
      put("GBPAUD", "34");
      put("GBPCAD", "38");
      put("GBPCHF", "36");
      put("GBPJPY", "30");
      put("GBPNZD", "58");
      put("GBPUSD", "21");
      // put("NZDCAD", "");
      put("NZDJPY", "34");
      put("NZDUSD", "23");
      put("USDCAD", "23");
      put("USDCHF", "24");
      put("USDJPY", "12");
      put("XAUUSD", "116");
    }
  };

  public static void dataTrans(File source, int inciseFile) throws IOException {
    LineIterator it = org.apache.commons.io.FileUtils.lineIterator(source,"utf-16LE");
    int count = 0;
    String dianCha = map.get(source.getName().substring(0, 6));
    if (dianCha == null) {
      System.out.println(source.getName() + " trans error");
      return;
    }
    List<String> temp = new java.util.ArrayList();

    List<String> standardTitle=new java.util.ArrayList();
    try {
      while (it.hasNext()) {
        String line = it.nextLine();

        if (line != null && line.length() > 0) {
          if (count == 0) {
            standardTitle.add(line);
          } else if (count == 1) {
            line = line + ";成交量;成交量;点差";
            standardTitle.add(line);
          } else if (count > 1) {
            line = line + ";100;100;" + dianCha;
          }

          temp.add(line);
          if (count != 0 && count % 10000 == 0) {
            System.out.println("count:" + count + " line:" + line);
            File trans = new File("/tmp/bbb/" + (int) count / inciseFile + source.getName());
            if (!trans.exists()) {
              trans.createNewFile();
              if((int) count / inciseFile>0){
                org.apache.commons.io.FileUtils.writeLines(trans,"utf-16LE",standardTitle,true);
              }
            }
            //FileUtils.fileAppender(trans, temp);
            org.apache.commons.io.FileUtils.writeLines(trans,"utf-16LE",temp,true);
            temp.clear();
          }

          count++;
        }
      }
      if (temp.size() > 0) {
        File trans = new File("/tmp/bbb/" + (int) count / inciseFile + source.getName());
        if (!trans.exists()) {
          trans.createNewFile();
          if((int) count / inciseFile>0){
            org.apache.commons.io.FileUtils.writeLines(trans,"utf-16LE",standardTitle,true);
          }
        }
        //FileUtils.fileAppender(trans, temp);
        org.apache.commons.io.FileUtils.writeLines(trans,"utf-16LE",temp,true);
        temp.clear();
      }
    } finally {
      LineIterator.closeQuietly(it);
    }
  }


  public static void dataTransWhole(File source, int inciseFile) throws IOException {
    List<String>
        lines =
        org.apache.commons.io.FileUtils.readLines(source, "unicode");
    int count = 0;
    int fileNameIncre = 0;
    String dianCha = map.get(source.getName().substring(0, 6));
    if (dianCha == null) {
      System.out.println(source.getName() + " trans error");
      return;
    }
    List<String> temp = new java.util.ArrayList();

    for (String line : lines) {
      if (line != null && line.length() > 0) {
        if (count == 0) {
          line = line + ";交易量;交易量;点差";
        } else {
          line = line + ";100;100;" + dianCha;
        }
        temp.add(line);
        if (count != 0 && count % inciseFile == 0) {
          File trans = new File("/tmp/bbb/" + fileNameIncre + source.getName());
          if (!trans.exists()) {
            trans.createNewFile();
          }
          FileUtils.fileAppender(trans, temp);
          fileNameIncre++;
          temp.clear();
        }
        count++;
      }
    }
    if (temp.size() > 0) {
      File trans = new File("/tmp/bbb/" + (fileNameIncre + 1) + source.getName());
      if (!trans.exists()) {
        trans.createNewFile();
      }
      FileUtils.fileAppender(trans, temp);
    }

  }

//  public static String ToDBC(String input) {
//
//    char c[] = input.toCharArray();
//    for (int i = 0; i < c.length; i++) {
//      if (c[i] == '\u3000') {
//        c[i] = ' ';
//      } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
//        c[i] = (char) (c[i] - 65248);
//
//      }
//    }
//    String returnString = new String(c);
//
//    return returnString;
//  }
//
//
//  public static void main(String[] args) {
//    String
//        line =
//        "2 0 1 3 . 0 3 . 1 3   1 5 : 3 2 ; 1 5 8 7 . 2 7 0 ; 1 5 8 7 . 4 8 0 ; 1 5 8 6 . 9 7 0 ; 1 5 8 7 . 4 6 0 ;100;100;116";
//
//    System.out.println(line.replace(" ",""));
//  }


}
