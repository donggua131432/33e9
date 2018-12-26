package com.e9cloud.mongodb.domain;

import java.util.Date;

/**
 * Created by wangqing on 2016/11/30.
 */
public class DownLoadWrapper {
        private Integer id;
        private String conds;
        private String fileName;
        private String filePath;
        private Date createTime;
        private String source;
        private String userId;
        private String feeid;
        private String status;
        private Integer pageCount;

    public enum Business{

            REST("01","restRecord", RestRecord.class),
            SIP("02","sipRecord", SipRecord.class),
            MASK("03", "maskRecord",MaskRecord.class),
            CCENTER("04", "ccenterRecord",CcRecord.class),
            VOICE("05","voiceRecord",VoiceRecord.class),
            SIPPHONE_REST("06","sipphoneRecord",SipPhoneRecord.class),
            ECC("07","ivrRecord",IvrRecord.class),
            MASKB("08","maskBRecord",MaskBRecord.class),
            VOICEVERIFY("09","voiceVerifyRecord",VoiceVerifyRecord.class);
            private String code;
            private  Class entityClass;
            private String collection;
            // 构造方法
            Business(String code, String collection,Class entityClass) {
                this.code = code;
                this.collection = collection;
                this.entityClass = entityClass;
            }
            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public Class getEntityClass() {
                return entityClass;
            }

            public void setEntityClass(Class entityClass) {
                this.entityClass = entityClass;
            }


            public String getCollection() {
                return collection;
            }

            public void setCollection(String collection) {
                this.collection = collection;
            }

        }

        public enum Fields{
            REST("01","AppID|CallID|类型|主叫号码|被叫号码|开始时间|结束时间|通话状态|通话费用|录音费用",
                    "appid|callSid|abLine|aTelno|bTelno|qssj|jssj|connectSucc|fee|recordingFee"),
            SIP("02","SubID|主叫号码|被叫号码|开始时间|结束时间|通话状态|通话费用",
                    "subid|aTelno|bTelno|qssj|jssj|connectSucc|fee"),
            MASK("03", "AppID|CallID|呼叫类型|类型|城市|主叫号码|被叫号码|隐私号码|开始时间|结束时间|通话状态|通话费用|录音费用",
                    "appid|callSid|abLine|displayPoolCity|aTelno|bTelno|display|qssj|jssj|connectSucc|fee|recordingFee"),
            CCENTER("04", "省份|城市|SubID(呼叫中心ID)|呼叫类型|运营商|主叫号码|被叫号码|开始时间|结束时间|通话状态|通话费用|入中继ID|出中继ID",
                    "pname|cname|subid|abLine|operator|zj|bj|qssj|jssj|connectSucc|fee|incoming|outgoing"),
            VOICE("05","AppID|RequestID|模版ID|被叫号码|外显号码|DTMF码|开始时间|结束时间|通话状态|通话费用",
                    "appid|requestId|templateId|bj|display|dtmf|qssj|jssj|connectSucc|fee"),
            SIPPHONE("06","AppID|CallID|呼叫类型|类型|主叫号码|被叫号码|外显号码|开始时间|结束时间|通话状态|通话时长|通话费用|录音费用",
                    "appid|callSid|rcdType|abLine|zj|bj|display|qssj|jssj|connectSucc|thsc|fee|recordingFee"),
            ECC("07","AppID|CallID|呼叫类型|主叫号码|被叫号码|外显号码|开始时间|结束时间|通话状态|通话时长|通话费用|录音费用",
                             "appid|callSid|rcdType|zj|bj|display|qssj|jssj|connectSucc|thsc|fee|recordingFee"),
            MASKB("08","AppID|CallID|主叫号码|被叫号码|虚拟号码|接听状态|主叫发起时间|被叫接听时间|结束时间|通话时长|挂机方向|第三方错误码|通话费用",
                        "appid|callid|telA|telB|telX|connectSucc|calltime|starttime|jssj|thsc|releasedir|releaseCause|fee"),
            VOICEVERIFY("09","AppID|requestId|模版ID|被叫号码|外显号码|接通状态|呼叫时间|结束时间|通话时长|第三方错误码|通话费用",
                          "appid|callSid|templateId|bj|display|connectSucc|qssj|jssj|thsc|reason|fee");
            private String code;
            private String fieldName;
            private String fieldV;
            Fields(String code,String fieldName,String fieldV){
                this.code=code;
                this.fieldName = fieldName;
                this.fieldV = fieldV;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getFieldName() {
                return fieldName;
            }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
            }

            public String getFieldV() {
                return fieldV;
            }

            public void setFieldV(String fieldV) {
                this.fieldV = fieldV;
            }
        }

    public enum OrderBy{
        REST("01", "qssj", "desc"),
        SIP("02", "qssj", "desc"),
        MASK("03", "qssj", "desc"),
        CCENTER("04", "qssj", "desc"),
        VOICE("05", "qssj", "desc"),
        SIPPHONE("06", "qssj", "desc"),
        ECC("07", "qssj", "desc"),
        MASKB("08", "qssj", "desc"),
        VOICEVERIFY("09", "qssj", "desc");

        private String code;
        private String sidx;
        private String sord;

        OrderBy(String code, String sidx, String sord){
            this.code = code;
            this.sidx = sidx;
            this.sord = sord;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSidx() {
            return sidx;
        }

        public void setSidx(String sidx) {
            this.sidx = sidx;
        }

        public String getSord() {
            return sord;
        }

        public void setSord(String sord) {
            this.sord = sord;
        }
    }

    public String getConds() {
        return conds;
    }

    public void setConds(String conds) {
        this.conds = conds;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public OrderBy getOrderBy(String code) {
        for (OrderBy b : OrderBy.values()) {
            if (b.getCode().equals(code)) {
                return b;
            }
        }
        return null;
    }

    // 普通方法
    public static Business getBusiness(String code) {
        for (Business b : Business.values()) {
            if (b.getCode().equals(code)) {
                return b;
            }
        }
        return null;
    }
    // 普通方法
    public static Fields getFields(String code) {
        for (Fields b : Fields.values()) {
            if (b.getCode().equals(code)) {
                return b;
            }
        }
        return null;
    }
}
