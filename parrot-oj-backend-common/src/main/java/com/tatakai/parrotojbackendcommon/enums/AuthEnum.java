package com.tatakai.parrotojbackendcommon.enums;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum AuthEnum {
    ADMIN("管理员","admin"),USER("用户","user"),NOT_LOGIN("未登录","notLogin");
    private final String text;
    private final String value;

    AuthEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }


    public static AuthEnum getEnumByValue(String value) {
        if(ObjectUtils.isEmpty(value)){
            return null;
        }
        for (AuthEnum authEnum : AuthEnum.values()) {
            if(authEnum.value.equals(value)){
                return authEnum;
            }
        }
        return null;
    }
    public static List<String> getValues(){
        return  Arrays.stream(values()).map(it->it.value).collect(Collectors.toList());
    }
}
