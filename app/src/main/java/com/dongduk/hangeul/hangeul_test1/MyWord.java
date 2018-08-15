package com.dongduk.hangeul.hangeul_test1;

/**
 * Created by jiyoungwon on 2017. 9. 10..
 */

public class MyWord {

    private String date;
    private String word;
    private String desc1;
    private String desc2;

    public MyWord(String date, String word, String desc1, String desc2) {
        this.date = date;
        this.word = word;
        this.desc1 = desc1;
        this.desc2 = desc2;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }


}
