package com.telebott.moviesmanage.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RequestData {
    @JSONField(jsonDirect = true)
    @ApiModelProperty(dataType = "json",name = "data",value = "包裹的json字段",example = "{ page:1, limit: 20, title:'123'}")
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
