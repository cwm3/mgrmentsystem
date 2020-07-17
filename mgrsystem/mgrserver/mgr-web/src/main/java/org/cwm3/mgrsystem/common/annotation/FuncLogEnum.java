package org.cwm3.mgrsystem.common.annotation;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
* @描述: 日志类型枚举
* @作者: 代鹏刚
* @日期: 2020/7/9 16:54
**/
@Getter
public enum FuncLogEnum {

    OPERATE("0","operate", "普通操作"),
    DATA_RESOURCE("1","data", "数据资源操作"),
    SYS_FUNCTION("1","system", "系统功能操作"),
    POWER("1","power", "权限管理操作"),
    OTHER("1","other", "其他管理操作"),
    ;

    FuncLogEnum() {
    }

    FuncLogEnum(String type, String code, String description) {
        this.type = type;
        this.code = code;
        this.description = description;
    }

    private String type;
    private String code;
    private String description;

    public static List<FuncLogEnum> list(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        List<FuncLogEnum> list = new ArrayList<>();
        for (FuncLogEnum element : FuncLogEnum.values()) {
            if (element.getType().equals(type)) {
                list.add(element);
            }
        }
        return list;
    }

    public static FuncLogEnum find(String code) {
        if (code == null) {
            return null;
        }
        for (FuncLogEnum element : FuncLogEnum.values()) {
            if (element.getCode().equals(code)) {
                return element;
            }
        }
        return null;
    }
}


