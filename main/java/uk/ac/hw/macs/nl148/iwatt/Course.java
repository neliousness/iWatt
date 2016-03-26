package uk.ac.hw.macs.nl148.iwatt;

import java.io.Serializable;

/**
 * Created by mrnel on 28/02/2016.
 */
public class Course  implements Serializable {

    private int id;
    private String code;
    private String coursename;
    private String coordinator;
    private String aims;
    private String syllabus;
    private String mandatory;
    private int year;

    Course (){

    }

    public Course(String code, String coursename, String coordinator, String aims, String syllabus, String mandatory, int year) {
        this.code = code;
        this.coursename = coursename;
        this.coordinator = coordinator;
        this.aims = aims;
        this.syllabus = syllabus;
        this.mandatory = mandatory;
        this.year = year;
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

    public String getAims() {
        return aims;
    }

    public void setAims(String aims) {
        this.aims = aims;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
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
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", coursename='" + coursename + '\'' +
                ", coordinator='" + coordinator + '\'' +
                ", aims='" + aims + '\'' +
                ", syllabus='" + syllabus + '\'' +
                ", mandatory='" + mandatory + '\'' +
                ", year=" + year +
                '}';
    }
}


