package com.dongduk.hangeul.hangeul_test1;

import java.io.Serializable;

/**
 * Created by jiyoungwon on 2017. 9. 17..
 */

public class Word  implements Serializable{
//    private long wid;
//    private String word;
//    private String wordDesc;

    private int id;
    private String title;
    private String ex;
    private String content;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



//
//    public long getWid() {
//        return wid;
//    }
//
//    public void setWid(long wid) {
//        this.wid = wid;
//    }
//
//    public String getWord() {
//        return word;
//    }
//
//    public void setWord(String word) {
//        this.word = word;
//    }
//
//    public String getWordDesc() {
//        return wordDesc;
//    }
//
//    public void setWordDesc(String wordDesc) {
//        this.wordDesc = wordDesc;
//    }
}
