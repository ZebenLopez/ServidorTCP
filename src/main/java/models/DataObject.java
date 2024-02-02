package models;

public class DataObject {
    private String windowsVersion;
    private String userName;
    private String percentage1;
    private String percentage2;
    private String disk1;
    private String diskType1;
    private String diskFormat1;
    private String disk1TotalSpace;
    private String disk1UsedSpace;
    private String disk1UsedPercentage;
    private String disk2;
    private String diskType2;
    private String diskFormat2;
    private String disk2TotalSpace;
    private String disk2UsedSpace;
    private String disk2UsedPercentage;
    private String usb;

    // getters and setters

    public DataObject(String windowsVersion, String userName, String percentage1, String percentage2, String disk1,
                      String diskType1, String diskFormat1, String disk1TotalSpace, String disk1UsedSpace,
                      String disk1UsedPercentage, String disk2, String diskType2, String diskFormat2,
                      String disk2TotalSpace, String disk2UsedSpace, String disk2UsedPercentage, String usb) {
        this.windowsVersion = windowsVersion;
        this.userName = userName;
        this.percentage1 = percentage1;
        this.percentage2 = percentage2;
        this.disk1 = disk1;
        this.diskType1 = diskType1;
        this.diskFormat1 = diskFormat1;
        this.disk1TotalSpace = disk1TotalSpace;
        this.disk1UsedSpace = disk1UsedSpace;
        this.disk1UsedPercentage = disk1UsedPercentage;
        this.disk2 = disk2;
        this.diskType2 = diskType2;
        this.diskFormat2 = diskFormat2;
        this.disk2TotalSpace = disk2TotalSpace;
        this.disk2UsedSpace = disk2UsedSpace;
        this.disk2UsedPercentage = disk2UsedPercentage;
        this.usb = usb;
    }

    public String getWindowsVersion() {
        return windowsVersion;
    }

    public void setWindowsVersion(String windowsVersion) {
        this.windowsVersion = windowsVersion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPercentage1() {
        return percentage1;
    }

    public void setPercentage1(String percentage1) {
        this.percentage1 = percentage1;
    }

    public String getPercentage2() {
        return percentage2;
    }

    public void setPercentage2(String percentage2) {
        this.percentage2 = percentage2;
    }

    public String getDisk1() {
        return disk1;
    }

    public void setDisk1(String disk1) {
        this.disk1 = disk1;
    }

    public String getDiskType1() {
        return diskType1;
    }

    public void setDiskType1(String diskType1) {
        this.diskType1 = diskType1;
    }

    public String getDiskFormat1() {
        return diskFormat1;
    }

    public void setDiskFormat1(String diskFormat1) {
        this.diskFormat1 = diskFormat1;
    }

    public String getDisk1TotalSpace() {
        return disk1TotalSpace;
    }

    public void setDisk1TotalSpace(String disk1TotalSpace) {
        this.disk1TotalSpace = disk1TotalSpace;
    }

    public String getDisk1UsedSpace() {
        return disk1UsedSpace;
    }

    public void setDisk1UsedSpace(String disk1UsedSpace) {
        this.disk1UsedSpace = disk1UsedSpace;
    }

    public String getDisk1UsedPercentage() {
        return disk1UsedPercentage;
    }

    public void setDisk1UsedPercentage(String disk1UsedPercentage) {
        this.disk1UsedPercentage = disk1UsedPercentage;
    }

    public String getDisk2() {
        return disk2;
    }

    public void setDisk2(String disk2) {
        this.disk2 = disk2;
    }

    public String getDiskType2() {
        return diskType2;
    }

    public void setDiskType2(String diskType2) {
        this.diskType2 = diskType2;
    }

    public String getDiskFormat2() {
        return diskFormat2;
    }

    public void setDiskFormat2(String diskFormat2) {
        this.diskFormat2 = diskFormat2;
    }

    public String getDisk2TotalSpace() {
        return disk2TotalSpace;
    }

    public void setDisk2TotalSpace(String disk2TotalSpace) {
        this.disk2TotalSpace = disk2TotalSpace;
    }

    public String getDisk2UsedSpace() {
        return disk2UsedSpace;
    }

    public void setDisk2UsedSpace(String disk2UsedSpace) {
        this.disk2UsedSpace = disk2UsedSpace;
    }

    public String getDisk2UsedPercentage() {
        return disk2UsedPercentage;
    }

    public void setDisk2UsedPercentage(String disk2UsedPercentage) {
        this.disk2UsedPercentage = disk2UsedPercentage;
    }

    public String getUsb() {
        return usb;
    }

    public void setUsb(String usb) {
        this.usb = usb;
    }
}