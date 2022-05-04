package com.telebott.moviesmanage.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RequestData {
//    @ApiModelProperty(name = "接收包裹的DATA字段",notes = "{data: { page:1, limit: 20, title:'123'}}")
    @JsonProperty(value = "data", required = false)
    private String data;
    @ApiModelProperty(hidden = true)
    private String user;

    public JSONObject getData() {
        return JSONObject.parseObject(data);
    }

    public SystemUser getUser() {
        JSONObject jsonObject = JSONObject.parseObject(user);
        return JSONObject.toJavaObject(jsonObject, SystemUser.class);
    }
}
