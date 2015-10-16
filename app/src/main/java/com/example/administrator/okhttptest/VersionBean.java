package com.example.administrator.okhttptest;

/**
 * Created by Administrator on 2015/10/16.
 */
public class VersionBean {

    /**
     * status : 1
     * message : 成功
     * data : {"ver":6,"url":"http://au.apk.umeng.com/uploads/apps/553b31bb67e58ef298004ee4/_umeng_%40_2_%40_63xxxxxxxxx.apk"}
     * extra :
     */

    private String status;
    private String message;
    private DataEntity data;
    private String extra;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public DataEntity getData() {
        return data;
    }

    public String getExtra() {
        return extra;
    }

    public static class DataEntity {
        /**
         * ver : 6
         * url : http://au.apk.umeng.com/uploads/apps/553b31bb67e58ef298004ee4/_umeng_%40_2_%40_63xxxxxxxxx.apk
         */

        private int ver;
        private String url;

        public void setVer(int ver) {
            this.ver = ver;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getVer() {
            return ver;
        }

        public String getUrl() {
            return url;
        }
    }
}
