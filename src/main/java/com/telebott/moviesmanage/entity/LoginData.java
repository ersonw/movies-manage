package com.telebott.moviesmanage.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(hidden = true)
    private String token;
    @ApiModelProperty(hidden = true)
    private String user;

    public SystemUser getUser() {
        return JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
    }
}
