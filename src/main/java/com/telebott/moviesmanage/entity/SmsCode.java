package com.telebott.moviesmanage.entity;

import com.telebott.moviesmanage.util.SmsBaoUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Setter
@Getter
@ToString(includeFieldNames = true)
public class SmsCode {
    private String id;
    private String phone;
    private String code;
    public SmsCode(){
        UUID uuid = UUID.randomUUID();
        id = uuid.toString().replaceAll("-","");
        code = SmsBaoUtil.getSmsCode();
    }
}
