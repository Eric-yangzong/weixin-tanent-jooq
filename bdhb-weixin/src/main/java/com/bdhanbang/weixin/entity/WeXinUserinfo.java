package com.bdhanbang.weixin.entity;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @ClassName: WeXinUserinfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author yangxz
 * @date 2018年8月4日 下午12:39:32
 * 
 */
public class WeXinUserinfo implements Serializable {


    private static final long serialVersionUID = 2006772118;

    private UUID     id;
    private String   openId;
    private String   nickName;
    private Integer  gender;
    private String   language;
    private String   city;
    private String   province;
    private String   country;
    private String   avatarUrl;
    private JsonNode watermark;
    private JsonNode jsonb;

    public WeXinUserinfo() {}

    public WeXinUserinfo(WeXinUserinfo value) {
        this.id = value.id;
        this.openId = value.openId;
        this.nickName = value.nickName;
        this.gender = value.gender;
        this.language = value.language;
        this.city = value.city;
        this.province = value.province;
        this.country = value.country;
        this.avatarUrl = value.avatarUrl;
        this.watermark = value.watermark;
        this.jsonb = value.jsonb;
    }

    public WeXinUserinfo(
        UUID     id,
        String   openId,
        String   nickName,
        Integer  gender,
        String   language,
        String   city,
        String   province,
        String   country,
        String   avatarUrl,
        JsonNode watermark,
        JsonNode jsonb
    ) {
        this.id = id;
        this.openId = openId;
        this.nickName = nickName;
        this.gender = gender;
        this.language = language;
        this.city = city;
        this.province = province;
        this.country = country;
        this.avatarUrl = avatarUrl;
        this.watermark = watermark;
        this.jsonb = jsonb;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public JsonNode getWatermark() {
        return this.watermark;
    }

    public void setWatermark(JsonNode watermark) {
        this.watermark = watermark;
    }

    public JsonNode getJsonb() {
        return this.jsonb;
    }

    public void setJsonb(JsonNode jsonb) {
        this.jsonb = jsonb;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("WeXinUserinfo (");

        sb.append(id);
        sb.append(", ").append(openId);
        sb.append(", ").append(nickName);
        sb.append(", ").append(gender);
        sb.append(", ").append(language);
        sb.append(", ").append(city);
        sb.append(", ").append(province);
        sb.append(", ").append(country);
        sb.append(", ").append(avatarUrl);
        sb.append(", ").append(watermark);
        sb.append(", ").append(jsonb);

        sb.append(")");
        return sb.toString();
    }

}
