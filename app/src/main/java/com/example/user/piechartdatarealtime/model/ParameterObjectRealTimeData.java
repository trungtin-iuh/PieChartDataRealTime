package com.example.user.piechartdatarealtime.model;

import java.util.ArrayList;
import java.util.List;

/**
 * author : thanhuy.nguyen
 * Created by developer on 3/9/2016.
 */
public class ParameterObjectRealTimeData {
    public static final transient String TYPE_ANY_DATA_NAME = "any-data";

    private String property; // for get retrieve data real time
    private String type; // for set data real time
    private String hostname;
    private List<AddressParameterRealTimeData> addresses;

    public ParameterObjectRealTimeData(){
        this.addresses = new ArrayList<AddressParameterRealTimeData>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setAddresses(List<AddressParameterRealTimeData> addresses) {
        this.addresses = addresses;
    }

    public String getProperty() {
        return property;
    }

    public String getHostname() {
        return hostname;
    }

    public List<AddressParameterRealTimeData> getAddresses() {
        return addresses;
    }

    public boolean isExistedAddresse(String address){
        for(AddressParameterRealTimeData addressParameterRealTimeData : addresses){
            if(addressParameterRealTimeData.getAddress().equals(address)){
                return true;
            }
        }
        return false;
    }

}
