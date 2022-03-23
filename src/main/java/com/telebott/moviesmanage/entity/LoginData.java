package com.telebott.moviesmanage.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class LoginData {
    private String username;
    private String password;
    private String token;
    private String user;

    public SystemUser getUser() {
        return JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
    }
}
