package org.cwm3.mgrsystem.common.entity;

import java.io.Serializable;

/**
 * @description:
 * @author: chengweiming
 * @time: 2020/6/24 17:33
 */
public class AjaxResult implements Serializable {
    private static final long serialVersionUID = 5990701580664511688L;
    private boolean success;
    private String ackCode;
    private String message;
    private String detailMsg;
    private Object data;

    public AjaxResult() {
        this.success = true;
    }

    public AjaxResult(boolean success) {
        this.success = true;
        this.success = success;
    }

    public AjaxResult(boolean success, String message) {
        this(success);
        this.message = message;
    }

    public AjaxResult(Object data) {
        this(true);
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAckCode() {
        return this.ackCode;
    }

    public void setAckCode(String ackCode) {
        this.ackCode = ackCode;
        if ("".equals(ackCode)) {
            this.setSuccess(false);
        }

    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        if (success) {
            this.ackCode = ReturnConstant.ACK_SUCCESS;
        } else {
            this.ackCode = ReturnConstant.ACK_FAILURE;
        }

        this.success = success;
    }

    public String getDetailMsg() {
        return this.detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setRestFullMsg(String msg) {
        this.message = msg;
    }
}
