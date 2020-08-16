package com.tdkj.dto;

/**
 * Created by len on 2017-12-29.
 */
public class ErrorMsgDTO implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        private Integer return_ret;
        private String return_msg;
    public Integer getReturn_ret() {
        return return_ret;
    }
    public void setReturn_ret(Integer return_ret) {
        this.return_ret = return_ret;
    }
    public String getReturn_msg() {
        return return_msg;
    }
    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }
}
