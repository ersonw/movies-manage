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
    @JsonProperty(value = "identifier", required = false)
    private String identifier;
    private String data;
    @JsonProperty(value = "user", required = false)
    private String user;
    public Users getUser() {
        JSONObject jsonObject = JSONObject.parseObject(user);
        return JSONObject.toJavaObject(jsonObject, Users.class);
    }
}
