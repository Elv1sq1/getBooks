package com.tsu.zqy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName book
 * @Author Elv1s
 * @Date 2018/12/30 10:06
 * @Description:
 */
public class book {
    public static void main(String[] args) throws IOException {
        //通过目录获取各个章节的连接
        Map<String, String> links = getLinks();
        //遍历map 下载各个章节到本地
       for (String title : links.keySet() ) {
           if (!title.isEmpty()){
               String  url = links.get(title);
               String content = getContent(url);
               File file = new File("D:\\小说\\'"+title +"'.txt");
               // 使用IO流写入小说
               FileWriter fw = null;
               BufferedWriter bw = null;
               try {
                   fw = new FileWriter(file);
                   bw = new BufferedWriter(fw);
                   bw.write(content);
                   bw.flush();
               }catch (Exception e){
                   e.printStackTrace();
               }finally {
                   if(bw != null){
                       bw.close();
                   }
                   if(fw != null){
                       fw.close();
                   }
               }
           }
       }
    }

    /**
     * 根据小说目录获取小说各个章节的连接
     * @return
     * @throws IOException
     */
    public static Map<String,String> getLinks() throws IOException {
        //List<String> links = new ArrayList<>();
        Map<String,String> map = new LinkedHashMap<>();
        Document document = Jsoup.connect("http://www.17k.com/list/493239.html").get();
        Elements list = document.getElementsByClass("Main List");
        Elements as = list.get(0).getElementsByTag("a");

        for (Element a : as) {
            //各个章节的连接
            String href = a.attr("abs:href");
            //章节的标题
            String name = a.getElementsByTag("span").text();

            map.put(name, href);

            //System.out.println(href);
           // links.add(href);
        }

        //System.out.println(links);
        for (String i : map.keySet() ) {
            System.out.println(i+":"+ map.get(i));
        }
        //System.out.println(map);
        return map;

    }

    public static String getContent(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        String p = document.getElementsByClass("p").text();

        return p;
    }

}
