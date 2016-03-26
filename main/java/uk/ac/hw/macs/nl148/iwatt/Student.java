package uk.ac.hw.macs.nl148.iwatt;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mrnel on 04/02/2016.
 */
@DatabaseTable(tableName = "student")
public class Student {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String surname;

    @DatabaseField
    private String username;

    public Student(){

    }

    public Student( String name, String surname, String username) {
        super();
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (getName() != null ? !getName().equals(student.getName()) : student.getName() != null)
            return false;
        if (getSurname() != null ? !getSurname().equals(student.getSurname()) : student.getSurname() != null)
            return false;
        return !(getUsername() != null ? !getUsername().equals(student.getUsername()) : student.getUsername() != null);

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
