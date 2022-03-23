package com.telebott.moviesmanage.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RequestData {
    @JsonProperty(value = "data", required = false)
    private String data;
    private String user;
    public SystemUser getUser() {
        JSONObject jsonObject = JSONObject.parseObject(user);
        return JSONObject.toJavaObject(jsonObject, SystemUser.class);
    }
}
