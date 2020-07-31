package org.cwm3.mgrsystem.common.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: chengweiming
 * @time: 2020/7/31 17:39
 */
public class StatusCode {
    public static final int REQUEST_CODE_SUCCESS = 200;
    public static final int REQUEST_CODE_CLOSE = 401;
    public static final int REQUEST_CODE_BAD = 400;
    public static final int REQUEST_CODE_UNKNOWN = 404;
    public static final int REQUEST_CODE_TIMEOUT = 408;
    public static final int REQUEST_CODE_ERROR = 500;
    public static final int REQUEST_CODE_OPERATION_FAILURE = 507;
    public static final int ERROR_CODE_40000 = 40000;
    public static final int ERROR_CODE_40001 = 40001;
    public static final int ERROR_CODE_40002 = 40002;
    public static final int ERROR_CODE_40003 = 40003;
    public static final int ERROR_CODE_40004 = 40004;
    public static final int ERROR_CODE_40005 = 40005;
    public static final int ERROR_CODE_40006 = 40006;
    public static final int ERROR_CODE_40007 = 40007;
    public static final int ERROR_CODE_40008 = 40008;
    public static final int ERROR_CODE_40009 = 40009;
    public static final int ERROR_CODE_40010 = 40010;
    public static final int ERROR_CODE_40011 = 40011;
    public static final Map<Integer, String> REQUEST_MSG_MAP = new HashMap();

    public StatusCode() {
    }

    static {
        REQUEST_MSG_MAP.put(200, "请求成功");
        REQUEST_MSG_MAP.put(401, "接口已关闭");
        REQUEST_MSG_MAP.put(400, "语义/参数错误");
        REQUEST_MSG_MAP.put(404, "未知接口");
        REQUEST_MSG_MAP.put(408, "请求超时");
        REQUEST_MSG_MAP.put(500, "系统错误");
        REQUEST_MSG_MAP.put(507, "操作失败");
        REQUEST_MSG_MAP.put((int) '鱀', "会话过期或无效");
//        REQUEST_MSG_MAP.put('鱁', "操作数据库失败");
//        REQUEST_MSG_MAP.put('鱂', "下载文件失败");
//        REQUEST_MSG_MAP.put('鱃', "处理数据异常");
//        REQUEST_MSG_MAP.put('鱄', "上传文件服务器失败");
//        REQUEST_MSG_MAP.put('鱅', "数据检验不合法");
//        REQUEST_MSG_MAP.put('鱆', "离线数据下载处理发生异常");
//        REQUEST_MSG_MAP.put('鱇', "未知异常");
//        REQUEST_MSG_MAP.put('鱈', "会话当前应用为空,请联系管理员");
//        REQUEST_MSG_MAP.put('鱉', "访问接口不能为空,请联系管理员");
//        REQUEST_MSG_MAP.put('鱊', "请求URL未在应用中登记,请联系管理员登记");
//        REQUEST_MSG_MAP.put('鱋', "无权访问,请联系管理员授权");
    }
}