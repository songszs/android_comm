package com.zs.test.retrofit;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-6-2 下午4:02
 * @description: mytest
 */
public class BookInfo {

    private String id;
    private String alt;
    private String title;
    private String summary;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
