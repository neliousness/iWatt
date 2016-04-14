package uk.ac.hw.macs.nl148.iwatt;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.test.AndroidTestCase;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mrnel on 28/03/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class Registration extends AndroidTestCase {


    DBHelperTest dbHelperTest;

    @Mock
    Context context;

    RuntimeExceptionDao<Student, Object> studentDao = null;


    RuntimeExceptionDao<LocalProgramme, Object> localProgrammesDao = null;


    RuntimeExceptionDao<LocalCourse, Object> courseDao = null;



    @Test
    public void registerStudent()
    {
        context.
        //dbHelperTest = OpenHelperManager.getHelper(getClass(), DBHelperTest.class);
        studentDao = dbHelperTest.getStudentExceptionDao();
        studentDao.create(new Student("Kilo", "Milo", "Dilo"));

        List<Student> studentList = studentDao.queryForAll();

        Student student = new Student("Kilo", "Milo", "Dilo");

        assertEquals(studentList.get(0), student);

    }



}
