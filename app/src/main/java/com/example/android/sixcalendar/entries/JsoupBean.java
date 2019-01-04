package com.example.android.sixcalendar.entries;

import java.util.List;

/**
 * Created by jackie on 2019/1/3.
 */

public class JsoupBean {
    //logo
    private String logoUrl;
    private String logoName;
    private String logoImg;
    //最上面左边的导航栏
    private List<String> nv1_NameList;
    private List<String> nv1_UrlList;
    //最上面右边的导航栏
    private List<String> nv1_1_NameList;
    private List<String> nv1_1_UrlList;
    //导航栏2
    private List<String> nv2_NameList;
    private List<String> nv2_UrlList;
    //广告栏
    private List<String> advert_Img_List;
    private List<String> advert_Url_List;
    //banner
    private List<String> banner_ContentList;
    private List<String> banner_UrlList;
    private List<String> banner_ImgList;
    //内容1   banner旁边的
    private List<String> content1_UrlList;
    private List<String> content1_ContentList;
    //排行榜标题
    private List<String> rank_UrlList;
    private List<String> rank_ContentList;

    public List<String> getRank_UrlList() {
        return rank_UrlList;
    }

    public void setRank_UrlList(List<String> rank_UrlList) {
        this.rank_UrlList = rank_UrlList;
    }

    public List<String> getRank_ContentList() {
        return rank_ContentList;
    }

    public void setRank_ContentList(List<String> rank_ContentList) {
        this.rank_ContentList = rank_ContentList;
    }

    public List<String> getContent1_UrlList() {
        return content1_UrlList;
    }

    public void setContent1_UrlList(List<String> content1_UrlList) {
        this.content1_UrlList = content1_UrlList;
    }

    public List<String> getContent1_ContentList() {
        return content1_ContentList;
    }

    public void setContent1_ContentList(List<String> content1_ContentList) {
        this.content1_ContentList = content1_ContentList;
    }

    public List<String> getAdvert_Img_List() {
        return advert_Img_List;
    }

    public void setAdvert_Img_List(List<String> advert_Img_List) {
        this.advert_Img_List = advert_Img_List;
    }

    public List<String> getBanner_ContentList() {
        return banner_ContentList;
    }

    public void setBanner_ContentList(List<String> banner_ContentList) {
        this.banner_ContentList = banner_ContentList;
    }

    public List<String> getBanner_UrlList() {
        return banner_UrlList;
    }

    public void setBanner_UrlList(List<String> banner_UrlList) {
        this.banner_UrlList = banner_UrlList;
    }

    public List<String> getBanner_ImgList() {
        return banner_ImgList;
    }

    public void setBanner_ImgList(List<String> banner_ImgList) {
        this.banner_ImgList = banner_ImgList;
    }

    public List<String> getAdvert_Name_List() {
        return advert_Img_List;
    }

    public void setAdvert_Name_List(List<String> advert_Img_List) {
        this.advert_Img_List = advert_Img_List;
    }

    public List<String> getAdvert_Url_List() {
        return advert_Url_List;
    }

    public void setAdvert_Url_List(List<String> advert_Url_List) {
        this.advert_Url_List = advert_Url_List;
    }

    public List<String> getNv2_NameList() {
        return nv2_NameList;
    }

    public void setNv2_NameList(List<String> nv2_NameList) {
        this.nv2_NameList = nv2_NameList;
    }

    public List<String> getNv2_UrlList() {
        return nv2_UrlList;
    }

    public void setNv2_UrlList(List<String> nv2_UrlList) {
        this.nv2_UrlList = nv2_UrlList;
    }

    public List<String> getNv1_1_NameList() {
        return nv1_1_NameList;
    }

    public void setNv1_1_NameList(List<String> nv1_1_NameList) {
        this.nv1_1_NameList = nv1_1_NameList;
    }

    public List<String> getNv1_1_UrlList() {
        return nv1_1_UrlList;
    }

    public void setNv1_1_UrlList(List<String> nv1_1_UrlList) {
        this.nv1_1_UrlList = nv1_1_UrlList;
    }

    public List<String> getNv1_NameList() {
        return nv1_NameList;
    }

    public void setNv1_NameList(List<String> nv1_NameList) {
        this.nv1_NameList = nv1_NameList;
    }

    public List<String> getNv1_UrlList() {
        return nv1_UrlList;
    }

    public void setNv1_UrlList(List<String> nv1_UrlList) {
        this.nv1_UrlList = nv1_UrlList;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    @Override
    public String toString() {
        return "JsoupBean{" +
                "logoUrl='" + logoUrl + '\'' +
                ", logoName='" + logoName + '\'' +
                ", logoImg='" + logoImg + '\'' +
                ", nv1_NameList=" + nv1_NameList +
                ", nv1_UrlList=" + nv1_UrlList +
                ", nv1_1_NameList=" + nv1_1_NameList +
                ", nv1_1_UrlList=" + nv1_1_UrlList +
                ", nv2_NameList=" + nv2_NameList +
                ", nv2_UrlList=" + nv2_UrlList +
                ", advert_Img_List=" + advert_Img_List +
                ", advert_Url_List=" + advert_Url_List +
                ", banner_ContentList=" + banner_ContentList +
                ", banner_UrlList=" + banner_UrlList +
                ", banner_ImgList=" + banner_ImgList +
                ", content1_UrlList=" + content1_UrlList +
                ", content1_ContentList=" + content1_ContentList +
                ", rank_UrlList=" + rank_UrlList +
                ", rank_ContentList=" + rank_ContentList +
                '}';
    }
}
