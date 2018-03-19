package com.example.user.piechartdatarealtime.model;

import android.text.TextUtils;

/**
 * author : thanhuy.nguyen
 * Created by developer on 3/9/2016.
 */
public class AddressParameterRealTimeData {
    public static final int LIMIT_RECORD_DATA_CACHE = 25;
    public static final String MESSAGE_REQUEST_FAIL = "message-request-fail";

    private Long timestamp;
    private String address;
    private String dataType;
    private Integer length;
    private String value;
    private String changeOfValue;
    private String responseCode;
    private String responseMessage;
    private String status;

//    private LimitedStorageData<RealTimeDataCache> cacheValue;

    public AddressParameterRealTimeData(){

    }

//    public LimitedStorageData<RealTimeDataCache> getCacheValue() {
//        return cacheValue;
//    }
//
//    public void setCacheValue(LimitedStorageData<RealTimeDataCache> cacheValue) {
//        this.cacheValue = cacheValue;
//    }

    public boolean isRequestSuccess(){
        return status == null ? true : false;
    }

    public void setChangeOfValue(String changeOfValue) {
        this.changeOfValue = changeOfValue;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAddress() {
        return address;
    }

    public String getDataType() {
        return dataType;
    }

    public Integer getLength() {
        return length;
    }

    public String getValue() {
        return value;
    }

    public String getChangeOfValue() {
        return changeOfValue;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public ResponseCodeMessage getResponseCodeMessage(){
        ResponseCodeMessage responseCodeMessage = null;
        if(!TextUtils.isEmpty(responseCode)){
            responseCodeMessage = new ResponseCodeMessage();
            if(TextUtils.equals(responseCode, "00")){
                responseCodeMessage.mIsSuccess = true;
                responseCodeMessage.mMessage = "Successful";
            } else if(TextUtils.equals(responseCode, "01")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Serial Number is not found";
            } else if(TextUtils.equals(responseCode, "02")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "FCC/NCC is not correct";
            } else if(TextUtils.equals(responseCode, "03")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Function Code is not supported";
            } else if(TextUtils.equals(responseCode, "04")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Device Identify is not found";
            } else if(TextUtils.equals(responseCode, "05")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Writing data to database failed";
            } else if(TextUtils.equals(responseCode, "06")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Unknown error";
            } else if(TextUtils.equals(responseCode, "07")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Cycle Code is invalid";
            } else if(TextUtils.equals(responseCode, "08")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Memmory Area is not allowed writing";
            } else if(TextUtils.equals(responseCode, "09")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Memmory Area is not allowed reading";
            } else if(TextUtils.equals(responseCode, "0A")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Device has beed locked";
            } else if(TextUtils.equals(responseCode, "0B")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Device has been deattached";
            } else if(TextUtils.equals(responseCode, "0C")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Realtime time is invalid";
            } else if(TextUtils.equals(responseCode, "0D")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "The number of realtime transaction is invalid";
            } else if(TextUtils.equals(responseCode, "0E")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Device is unattached";
            } else if(TextUtils.equals(responseCode, "0F")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "FCC is invalid";
            }  else if(TextUtils.equals(responseCode, "10")){
                responseCodeMessage.mIsSuccess = false;
                responseCodeMessage.mMessage = "Time out";
            }
        }
        return responseCodeMessage;
    }

    public class ResponseCodeMessage{
        private String mMessage;
        private boolean mIsSuccess;

        public String getMessage() {
            return mMessage;
        }

        public void setMessage(String message) {
            mMessage = message;
        }

        public boolean isSuccess() {
            return mIsSuccess;
        }

        public void setSuccess(boolean success) {
            mIsSuccess = success;
        }
    }
}
