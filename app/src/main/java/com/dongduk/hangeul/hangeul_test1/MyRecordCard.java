package com.dongduk.hangeul.hangeul_test1;

/**
 * Created by yoon1 on 2017-09-17.
 */

public class MyRecordCard {
    String tvDateRecord;
    String tvWordRecord;
    String tvContentRecord01;
    String tvContentRecord02;
    String tvContentRecord03;
    String tvContentRecord04;


    public MyRecordCard(String tvDateRecord, String tvWordRecord, String tvContentRecord01, String tvContentRecord02, String tvContentRecord03, String tvContentRecord04) {
        this.tvDateRecord = tvDateRecord;
        this.tvWordRecord = tvWordRecord;
        this.tvContentRecord01 = tvContentRecord01;
        this.tvContentRecord02 = tvContentRecord02;
        this.tvContentRecord03 = tvContentRecord03;
        this.tvContentRecord04 = tvContentRecord04;
    }

    public String getTvDateRecord() {
        return tvDateRecord;
    }

    public void setTvDateRecord(String tvDateRecord) {
        this.tvDateRecord = tvDateRecord;
    }

    public String getTvWordRecord() {
        return tvWordRecord;
    }

    public void setTvWordRecord(String tvWordRecord) {
        this.tvWordRecord = tvWordRecord;
    }

    public String getTvContentRecord01() {
        return tvContentRecord01;
    }

    public void setTvContentRecord01(String tvContentRecord01) {
        this.tvContentRecord01 = tvContentRecord01;
    }

    public String getTvContentRecord02() {
        return tvContentRecord02;
    }

    public void setTvContentRecord02(String tvContentRecord02) {
        this.tvContentRecord02 = tvContentRecord02;
    }

    public String getTvContentRecord03() {
        return tvContentRecord03;
    }

    public void setTvContentRecord03(String tvContentRecord03) {
        this.tvContentRecord03 = tvContentRecord03;
    }

    public String getTvContentRecord04() {
        return tvContentRecord04;
    }

    public void setTvContentRecord04(String tvContentRecord04) {
        this.tvContentRecord04 = tvContentRecord04;
    }
}
