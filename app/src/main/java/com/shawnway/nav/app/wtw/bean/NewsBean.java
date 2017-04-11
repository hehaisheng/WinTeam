package com.shawnway.nav.app.wtw.bean;

import java.io.Serializable;


/**
 * 最新消息bean
 */
public class NewsBean implements Serializable {

    public String id;
    public String author;//作者
    public Integer createBy;
    public long createTime;
    public int newsCategoryId;
    public String newsContent;
    public String newsPic;
    public String newsTitle;
    public Integer publishBy;
    public Long publishTime;
    public String shortDesc;
    public int showOrder;
    public int status;
    public Integer updateBy;
    public long updateTime;
}
