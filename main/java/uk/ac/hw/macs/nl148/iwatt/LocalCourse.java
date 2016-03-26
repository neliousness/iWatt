package uk.ac.hw.macs.nl148.iwatt;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mrnel on 14/03/2016.
 */
@DatabaseTable(tableName = "localcourse")
public class LocalCourse {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String code;

    @DatabaseField
    private String coursename;

    @DatabaseField
    private String coordinator;


    @DatabaseField
    private String mandatory;

    @DatabaseField
    private int year;

    LocalCourse(){

    }

    public LocalCourse(String code, int year,  String coursename, String coordinator, String mandatory) {
        super();
        this.code = code;
        this.year = year;
        this.coursename = coursename;
        this.coordinator = coordinator;
        this.mandatory = mandatory;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }


    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalCourse that = (LocalCourse) o;

        if (id != that.id) return false;
        if (getYear() != that.getYear()) return false;
        if (getCode() != null ? !getCode().equals(that.getCode()) : that.getCode() != null)
            return false;
        if (getCoursename() != null ? !getCoursename().equals(that.getCoursename()) : that.getCoursename() != null)
            return false;
        if (getCoordinator() != null ? !getCoordinator().equals(that.getCoordinator()) : that.getCoordinator() != null)
            return false;
        return !(getMandatory() != null ? !getMandatory().equals(that.getMandatory()) : that.getMandatory() != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getCoursename() != null ? getCoursename().hashCode() : 0);
        result = 31 * result + (getCoordinator() != null ? getCoordinator().hashCode() : 0);
        result = 31 * result + (getMandatory() != null ? getMandatory().hashCode() : 0);
        result = 31 * result + getYear();
        return result;
    }

    @Override
    public String toString() {
        return "LocalCourse{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", coursename='" + coursename + '\'' +
                ", coordinator='" + coordinator + '\'' +
                ", mandatory='" + mandatory + '\'' +
                ", year=" + year +
                '}';
    }
}
