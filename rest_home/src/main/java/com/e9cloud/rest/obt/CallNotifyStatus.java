package com.e9cloud.rest.obt;

import java.util.Date;

/**
 * Created by wzj on 2017/5/12.
 */
public class CallNotifyStatus {

    private String action ;//请求类型  CallInvite(呼叫发起)  CallEstablish(呼叫建立) Hangup(呼叫挂机)  IncomeHangup(专号通回呼挂机)

    private String callSid;//呼叫的唯一标示	32位字符串

    private boolean status;

    private Date createDateTime;//入库时间

    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCallSid() {
        return callSid;
    }

    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
}
