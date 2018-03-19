package com.example.user.piechartdatarealtime.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 3/9/2016.
 */
public class RealTimeStreamingData {
    public static final transient String REALTIME_STREAMING_DATA = "realtime-streaming-data";
    public static final transient String DATA_TYPE_DEVICE = "device";

    private String sessionId;
    private Integer updateTime;
    private Integer keepAliveTime;
    private String timezone;
    private Long timestamp;
    private String responseMessage;

    private List<ParameterObjectRealTimeData> objects;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public void setKeepAliveTime(Integer keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setObjects(List<ParameterObjectRealTimeData> objects) {
        this.objects = objects;
    }

    public RealTimeStreamingData(){
        this.sessionId = "";
//        this.keepAliveTime = 300;
        this.updateTime = 5;
        objects = new ArrayList<ParameterObjectRealTimeData>();
    }

    public String getSessionId() {
        return sessionId;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public Integer getKeepAliveTime() {
        return keepAliveTime;
    }

    public String getTimezone() {
        return timezone;
    }

    public List<ParameterObjectRealTimeData> getObjects() {
        return objects;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ParameterObjectRealTimeData getParameterByHostName(String hostName){
        for(ParameterObjectRealTimeData parameterObjectRealTimeData : objects){
            if(parameterObjectRealTimeData.getHostname().equals(hostName)){
                return parameterObjectRealTimeData;
            }
        }
        return null;
    }

    public AddressParameterRealTimeData getAddressByHostnameAndAddress(String hostName, String address){
        for(ParameterObjectRealTimeData parameterObjectRealTimeData : objects){
            boolean isExistedHostname = parameterObjectRealTimeData
                    .getHostname().equals(hostName);
            if(isExistedHostname){
                List<AddressParameterRealTimeData> addresses =
                        parameterObjectRealTimeData.getAddresses();
                for(AddressParameterRealTimeData addressParameter : addresses){
                    boolean isExistedAddress = addressParameter
                            .getAddress().equals(address);
                    if(isExistedAddress){
                        return addressParameter;
                    }
                }
            }
        }
        return null;
    }

}
