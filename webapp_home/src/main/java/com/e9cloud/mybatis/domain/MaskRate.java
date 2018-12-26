package com.e9cloud.mybatis.domain;

import java.math.BigDecimal;
import java.util.Date;

public class MaskRate {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.feeid
     *
     * @mbggenerated
     */
    private String feeid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.start_date
     *
     * @mbggenerated
     */

    private Date startDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.end_date
     *
     * @mbggenerated
     */
    private Date endDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.forever
     *
     * @mbggenerated
     */
    private Boolean forever;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.fee_mode
     *
     * @mbggenerated
     */
    private String feeMode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.maskA
     *
     * @mbggenerated
     */
    private BigDecimal maska;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.maskA_discount
     *
     * @mbggenerated
     */
    private Integer maskaDiscount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.maskB
     *
     * @mbggenerated
     */
    private BigDecimal maskb;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.maskB_discount
     *
     * @mbggenerated
     */
    private Integer maskbDiscount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.recording
     *
     * @mbggenerated
     */
    private BigDecimal recording;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.recording_discount
     *
     * @mbggenerated
     */
    private Integer recordingDiscount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.callback
     *
     * @mbggenerated
     */
    private BigDecimal callback;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.callback_discount
     *
     * @mbggenerated
     */
    private Integer callbackDiscount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.recording_callback
     *
     * @mbggenerated
     */
    private BigDecimal recordingCallback;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.recording_callback_discount
     *
     * @mbggenerated
     */
    private Integer recordingCallbackDiscount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.rent
     *
     * @mbggenerated
     */
    private BigDecimal rent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rms_mask_rate.addtime
     *
     * @mbggenerated
     */
    private Date addtime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.feeid
     *
     * @return the value of rms_mask_rate.feeid
     *
     * @mbggenerated
     */
    public String getFeeid() {
        return feeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.feeid
     *
     * @param feeid the value for rms_mask_rate.feeid
     *
     * @mbggenerated
     */
    public void setFeeid(String feeid) {
        this.feeid = feeid == null ? null : feeid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.start_date
     *
     * @return the value of rms_mask_rate.start_date
     *
     * @mbggenerated
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.start_date
     *
     * @param startDate the value for rms_mask_rate.start_date
     *
     * @mbggenerated
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.end_date
     *
     * @return the value of rms_mask_rate.end_date
     *
     * @mbggenerated
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.end_date
     *
     * @param endDate the value for rms_mask_rate.end_date
     *
     * @mbggenerated
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.forever
     *
     * @return the value of rms_mask_rate.forever
     *
     * @mbggenerated
     */
    public Boolean getForever() {
        return forever;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.forever
     *
     * @param forever the value for rms_mask_rate.forever
     *
     * @mbggenerated
     */
    public void setForever(Boolean forever) {
        this.forever = forever;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.fee_mode
     *
     * @return the value of rms_mask_rate.fee_mode
     *
     * @mbggenerated
     */
    public String getFeeMode() {
        return feeMode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.fee_mode
     *
     * @param feeMode the value for rms_mask_rate.fee_mode
     *
     * @mbggenerated
     */
    public void setFeeMode(String feeMode) {
        this.feeMode = feeMode == null ? null : feeMode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.maskA
     *
     * @return the value of rms_mask_rate.maskA
     *
     * @mbggenerated
     */
    public BigDecimal getMaska() {
        return maska;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.maskA
     *
     * @param maska the value for rms_mask_rate.maskA
     *
     * @mbggenerated
     */
    public void setMaska(BigDecimal maska) {
        this.maska = maska;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.maskA_discount
     *
     * @return the value of rms_mask_rate.maskA_discount
     *
     * @mbggenerated
     */
    public Integer getMaskaDiscount() {
        return maskaDiscount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.maskA_discount
     *
     * @param maskaDiscount the value for rms_mask_rate.maskA_discount
     *
     * @mbggenerated
     */
    public void setMaskaDiscount(Integer maskaDiscount) {
        this.maskaDiscount = maskaDiscount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.maskB
     *
     * @return the value of rms_mask_rate.maskB
     *
     * @mbggenerated
     */
    public BigDecimal getMaskb() {
        return maskb;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.maskB
     *
     * @param maskb the value for rms_mask_rate.maskB
     *
     * @mbggenerated
     */
    public void setMaskb(BigDecimal maskb) {
        this.maskb = maskb;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.maskB_discount
     *
     * @return the value of rms_mask_rate.maskB_discount
     *
     * @mbggenerated
     */
    public Integer getMaskbDiscount() {
        return maskbDiscount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.maskB_discount
     *
     * @param maskbDiscount the value for rms_mask_rate.maskB_discount
     *
     * @mbggenerated
     */
    public void setMaskbDiscount(Integer maskbDiscount) {
        this.maskbDiscount = maskbDiscount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.recording
     *
     * @return the value of rms_mask_rate.recording
     *
     * @mbggenerated
     */
    public BigDecimal getRecording() {
        return recording;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.recording
     *
     * @param recording the value for rms_mask_rate.recording
     *
     * @mbggenerated
     */
    public void setRecording(BigDecimal recording) {
        this.recording = recording;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.recording_discount
     *
     * @return the value of rms_mask_rate.recording_discount
     *
     * @mbggenerated
     */
    public Integer getRecordingDiscount() {
        return recordingDiscount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.recording_discount
     *
     * @param recordingDiscount the value for rms_mask_rate.recording_discount
     *
     * @mbggenerated
     */
    public void setRecordingDiscount(Integer recordingDiscount) {
        this.recordingDiscount = recordingDiscount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.callback
     *
     * @return the value of rms_mask_rate.callback
     *
     * @mbggenerated
     */
    public BigDecimal getCallback() {
        return callback;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.callback
     *
     * @param callback the value for rms_mask_rate.callback
     *
     * @mbggenerated
     */
    public void setCallback(BigDecimal callback) {
        this.callback = callback;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.callback_discount
     *
     * @return the value of rms_mask_rate.callback_discount
     *
     * @mbggenerated
     */
    public Integer getCallbackDiscount() {
        return callbackDiscount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.callback_discount
     *
     * @param callbackDiscount the value for rms_mask_rate.callback_discount
     *
     * @mbggenerated
     */
    public void setCallbackDiscount(Integer callbackDiscount) {
        this.callbackDiscount = callbackDiscount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.recording_callback
     *
     * @return the value of rms_mask_rate.recording_callback
     *
     * @mbggenerated
     */
    public BigDecimal getRecordingCallback() {
        return recordingCallback;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.recording_callback
     *
     * @param recordingCallback the value for rms_mask_rate.recording_callback
     *
     * @mbggenerated
     */
    public void setRecordingCallback(BigDecimal recordingCallback) {
        this.recordingCallback = recordingCallback;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.recording_callback_discount
     *
     * @return the value of rms_mask_rate.recording_callback_discount
     *
     * @mbggenerated
     */
    public Integer getRecordingCallbackDiscount() {
        return recordingCallbackDiscount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.recording_callback_discount
     *
     * @param recordingCallbackDiscount the value for rms_mask_rate.recording_callback_discount
     *
     * @mbggenerated
     */
    public void setRecordingCallbackDiscount(Integer recordingCallbackDiscount) {
        this.recordingCallbackDiscount = recordingCallbackDiscount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.rent
     *
     * @return the value of rms_mask_rate.rent
     *
     * @mbggenerated
     */
    public BigDecimal getRent() {
        return rent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.rent
     *
     * @param rent the value for rms_mask_rate.rent
     *
     * @mbggenerated
     */
    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rms_mask_rate.addtime
     *
     * @return the value of rms_mask_rate.addtime
     *
     * @mbggenerated
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rms_mask_rate.addtime
     *
     * @param addtime the value for rms_mask_rate.addtime
     *
     * @mbggenerated
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}