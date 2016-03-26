package uk.ac.hw.macs.nl148.iwatt;

import java.io.Serializable;

/**
 * Created by mrnel on 19/02/2016.
 */
public class TimeTableData implements Serializable {

    private String code;
    private String dow;
    private String title;
    private String room1;
    private String room2;
    private String semester;
    private String type;
    private String starttime;
    private String endtime;
    private String staff;
    private String year;

TimeTableData()
{

}

    public TimeTableData(String code, String dow, String title, String room1, String room2, String semester, String type, String starttime, String endtime, String staff, String year) {
        this.code = code;
        this.dow = dow;
        this.title = title;
        this.room1 = room1;
        this.room2 = room2;
        this.semester = semester;
        this.type = type;
        this.starttime = starttime;
        this.endtime = endtime;
        this.staff = staff;
        this.year = year;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDow() {
        return dow;
    }

    public void setDow(String dow) {
        this.dow = dow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoom1() {
        return room1;
    }

    public void setRoom1(String room1) {
        this.room1 = room1;
    }

    public String getRoom2() {
        return room2;
    }

    public void setRoom2(String room2) {
        this.room2 = room2;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "TimeTableData{" +
                "code='" + code + '\'' +
                ", dow='" + dow + '\'' +
                ", title='" + title + '\'' +
                ", room1='" + room1 + '\'' +
                ", room2='" + room2 + '\'' +
                ", semester='" + semester + '\'' +
                ", type='" + type + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", staff='" + staff + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
