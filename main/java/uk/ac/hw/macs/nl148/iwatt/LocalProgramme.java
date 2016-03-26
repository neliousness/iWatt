package uk.ac.hw.macs.nl148.iwatt;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mrnel on 04/02/2016.
 */

@DatabaseTable(tableName = "localprogramme")
public class LocalProgramme {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String progCode;

    @DatabaseField
    private String progDesc;

    @DatabaseField
    private int length;

    @DatabaseField
    private int year;

    public LocalProgramme(){

    }

    public LocalProgramme(String progCode, String progDesc, int length, int year) {
        super();
        this.progCode = progCode;
        this.progDesc = progDesc;
        this.length = length;
        this.year = year;
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

        LocalProgramme that = (LocalProgramme) o;

        if (id != that.id) return false;
        if (getLength() != that.getLength()) return false;
        if (getYear() != that.getYear()) return false;
        if (getProgCode() != null ? !getProgCode().equals(that.getProgCode()) : that.getProgCode() != null)
            return false;
        return !(getProgDesc() != null ? !getProgDesc().equals(that.getProgDesc()) : that.getProgDesc() != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (getProgCode() != null ? getProgCode().hashCode() : 0);
        result = 31 * result + (getProgDesc() != null ? getProgDesc().hashCode() : 0);
        result = 31 * result + getLength();
        result = 31 * result + getYear();
        return result;
    }

    @Override
    public String toString() {
        return "LocalProgramme{" +
                "id=" + id +
                ", progCode='" + progCode + '\'' +
                ", progDesc='" + progDesc + '\'' +
                ", length=" + length +
                ", year=" + year +
                '}';
    }
}