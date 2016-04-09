package uk.ac.hw.macs.nl148.iwatt;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Author: Neio Lucas
 * File : DBConfigUtil.java
 * Platform : Android Operating System
 * Date: 05/02/2016
 * Description: This class is independently executed from the application. It is used to create the
 * relatiobal objects
 */

public class DBConfigUtil extends OrmLiteConfigUtil {

    private  static final Class<?> [] classes = new Class[]{Student.class , LocalProgramme.class , LocalCourse.class};

    public static void main(String[] args) throws IOException, SQLException {
        writeConfigFile("ormlite_config.txt" ,classes);
    }
}
