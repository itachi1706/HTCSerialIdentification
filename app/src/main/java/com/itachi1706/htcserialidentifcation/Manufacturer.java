package com.itachi1706.htcserialidentifcation;

/**
 * Created by Kenneth on 20/4/2015
 * for HTCSerialIdentification in package com.itachi1706.htcserialidentifcation
 */
public enum Manufacturer {
    HT("HT", "Hsinchu, Taiwan"),
    SH("SH", "Shanghai, China"),
    FA("FA", "Taiwan (FA)"),
    UNKNOWN("UNKNOWN", "Unknown Location");

    private String code, location;

    Manufacturer(String code, String location){
        this.code = code;
        this.location = location;
    }

    public static Manufacturer fromCode(String code){
        for (Manufacturer m : Manufacturer.values()){
            if (m.getCode().equals(code))
                return m;
        }
        return UNKNOWN;
    }

    public String getCode() {
        return code;
    }

    public String getLocation() {
        return location;
    }
}
