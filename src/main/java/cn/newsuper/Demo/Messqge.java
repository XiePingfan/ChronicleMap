package cn.newsuper.Demo;

import java.math.BigInteger;

/**
 * Created by Administrator on 2018/3/8.
 */
public class Messqge {
    public BigInteger HOUR_ID;
    public String CITY_NAME;
    public String SEN_NAME;
    public String AREA_NAME;
    public String IMSI;
    public  BigInteger TRAFFIC_ALL_HOURS;
    public BigInteger getHOUR_ID() {
        return HOUR_ID;
    }

    public void setHOUR_ID(BigInteger HOUR_ID) {
        this.HOUR_ID = HOUR_ID;
    }

    public String getCITY_NAME() {
        return CITY_NAME;
    }

    public void setCITY_NAME(String CITY_NAME) {
        this.CITY_NAME = CITY_NAME;
    }

    public String getSEN_NAME() {
        return SEN_NAME;
    }

    public void setSEN_NAME(String SEN_NAME) {
        this.SEN_NAME = SEN_NAME;
    }

    public String getAREA_NAME() {
        return AREA_NAME;
    }

    public void setAREA_NAME(String AREA_NAME) {
        this.AREA_NAME = AREA_NAME;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public BigInteger getTRAFFIC_ALL_HOURS() {
        return TRAFFIC_ALL_HOURS;
    }

    public void setTRAFFIC_ALL_HOURS(BigInteger TRAFFIC_ALL_HOURS) {
        this.TRAFFIC_ALL_HOURS = TRAFFIC_ALL_HOURS;
    }



}
