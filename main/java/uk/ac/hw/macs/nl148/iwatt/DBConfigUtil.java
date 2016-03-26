package uk.ac.hw.macs.nl148.iwatt;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by mrnel on 05/02/2016.
 */
public class DBConfigUtil extends OrmLiteConfigUtil {

    private  static final Class<?> [] classes = new Class[]{Student.class , LocalProgramme.class , LocalCourse.class};

    public static void main(String[] args) throws IOException, SQLException {
        writeConfigFile("ormlite_config.txt" ,classes);
    }
}
