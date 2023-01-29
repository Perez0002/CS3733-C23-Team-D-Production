package edu.wpi.teamname;

public class locationName{
    private String longName;
    private String shortName;
    private String locationType;
    public locationName(String longName, String shortName,String nodeType){
        this.longName= longName;
        this.shortName = shortName;
        this.locationType=nodeType;
    }
    public locationName() {
        this.longName = "";
        this.shortName = "";
        this.locationType = "";
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }
}

