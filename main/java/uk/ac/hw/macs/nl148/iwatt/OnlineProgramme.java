package uk.ac.hw.macs.nl148.iwatt;

/**
 * Created by mrnel on 04/02/2016.
 */
public class OnlineProgramme {


    private String dept;
    private String fullTitle;
    private String progCode;
    private String progDesc;
    private String school;
    private String ucasCode;
    private String ucasTitle;
    private String length;


    public OnlineProgramme(String dept , String fullTitle , String progCode , String progDesc , String ucasCode , String ucasTitle , String length)
    {
        this.dept = dept;
        this.fullTitle = fullTitle;
        this.progCode = progCode;
        this.progDesc = progDesc;
        this.ucasCode = ucasCode;
        this.ucasTitle = ucasTitle;
        this.length = length;
    }

    public OnlineProgramme(){

    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
    }

    public String getProgDesc() {
        return progDesc;
    }

    public void setProgDesc(String progDesc) {
        this.progDesc = progDesc;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUcasCode() {
        return ucasCode;
    }

    public void setUcasCode(String ucasCode) {
        this.ucasCode = ucasCode;
    }

    public String getUcasTitle() {
        return ucasTitle;
    }

    public void setUcasTitle(String ucasTitle) {
        this.ucasTitle = ucasTitle;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
