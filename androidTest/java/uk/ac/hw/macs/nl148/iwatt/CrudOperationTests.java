package uk.ac.hw.macs.nl148.iwatt;

import android.test.AndroidTestCase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mrnel on 12/04/2016.
 */
public class CrudOperationTests extends AndroidTestCase {

    DBHelperTest dbHelperTest;
    RuntimeExceptionDao<Student, Object> studentDao = null;
    RuntimeExceptionDao<LocalProgramme, Object> localProgrammesDao = null;
    RuntimeExceptionDao<LocalCourse, Object> courseDao = null;


    public void testInsertStudent()
    {



        dbHelperTest = OpenHelperManager.getHelper(mContext, DBHelperTest.class);
        studentDao = dbHelperTest.getStudentExceptionDao();
        List<Student> studentList = studentDao.queryForAll();

        //assert that therea are no entries in the table
        assertEquals(0, studentList.size());

        studentDao.create(new Student("Kilo", "Milo", "Dilo"));

        //assert that there is one entry in the table
        List<Student> studentList2 = studentDao.queryForAll();
        Student student = new Student("Kilo", "Milo", "Dilo");
        assertEquals(student, studentList2.get(0));


    }

    public void testInsertProgramme()
    {
        dbHelperTest = OpenHelperManager.getHelper(mContext, DBHelperTest.class);
        localProgrammesDao = dbHelperTest.getProgrammeExceptionDao();
        List<LocalProgramme> programmes = localProgrammesDao.queryForAll();

        //assert that their are no entries in the table
        assertEquals(0, programmes.size());


        localProgrammesDao.create(new LocalProgramme("test code", "test description", 3, 4));
        //assert that there is one entry in the table
        List<LocalProgramme> programmes2 = localProgrammesDao.queryForAll();
        assertEquals(1, programmes2.size());



    }

    public void testInsertCourse()
    {
        dbHelperTest = OpenHelperManager.getHelper(mContext, DBHelperTest.class);
        courseDao = dbHelperTest.getCourseExceptionDao();
        List<LocalCourse> courses = courseDao.queryForAll();

        assertEquals(0, courses.size());

        courseDao.create(new LocalCourse("ABC", 3, "Super Computing", "John Cena", "yes"));
        courseDao.create(new LocalCourse("DEF",3,"Advanced Super Computing", "Stuart Cena", "no"));
        courseDao.create(new LocalCourse("GHI",3,"Supreme Super Computing", "Micheal Cena", "yes"));
        courseDao.create(new LocalCourse("JKL", 3, "Divine Super Computing", "Rob Cena", "yes"));

        //assert that 4 courses have been inserted
        List<LocalCourse> courses2 = courseDao.queryForAll();
        assertEquals( 4, courses2.size());



        for(LocalCourse course : courses)
        {
            //assert that there is one course that is not mandatory
            assertEquals(course.getMandatory(), "no");


        }


    }
    public void testUpdateProgramme() {
        dbHelperTest = OpenHelperManager.getHelper(mContext, DBHelperTest.class);
        localProgrammesDao = dbHelperTest.getProgrammeExceptionDao();


        //storing programme name into a list
        List<LocalProgramme> programmes = localProgrammesDao.queryForAll();

        //creating a builder to update LocalProgramme table
        UpdateBuilder<LocalProgramme, Object> updateBuilder = localProgrammesDao.updateBuilder();
        for (LocalProgramme lcc : programmes) {

            try {
                //update table where progCode equals the value ( lcc.getProgCode())
                updateBuilder.where().eq("progCode", "test code");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                // update the value of of  year and progDesc where progCode equals the value ( lcc.getProgCode())
                updateBuilder.updateColumnValue("progDesc" /* column */, "updated description" /* value */);
                updateBuilder.updateColumnValue("year" /* column */,  "1" /* value */);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                // complete update transaction
                updateBuilder.update();
                Log.d("update course  status", lcc.toString());

            } catch (SQLException e) {
                e.printStackTrace();
            }




        }
        List<LocalProgramme> locall2 = localProgrammesDao.queryForAll();
        for(LocalProgramme p : locall2)
        {
            assertEquals("updated description",p.getProgDesc());
        }
    }

    public void testUpdateStudent()
    {
        dbHelperTest = OpenHelperManager.getHelper(mContext, DBHelperTest.class);
        studentDao = dbHelperTest.getStudentExceptionDao();


        //storing programme name into a list
        List<Student> students = studentDao.queryForAll();


        //creating a builder to update LocalProgramme table
        UpdateBuilder<Student, Object> updateBuilder = studentDao.updateBuilder();
        for (Student s : students) {

            try {
                //update table where progCode equals the value ( lcc.getProgCode())
                updateBuilder.where().eq("name", "Kilo");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                // update the value of of  year and progDesc where progCode equals the value ( lcc.getProgCode())
                updateBuilder.updateColumnValue("username" /* column */, "new Username" /* value */);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                // complete update transaction
                updateBuilder.update();
                Log.d("update course  status", s.toString());

            } catch (SQLException e) {
                e.printStackTrace();
            }




        }
        List<Student> students2 = studentDao.queryForAll();
        for(Student s : students2)
        {
            assertEquals("new Username",s.getUsername());
        }

    }

    public void testUpdateCourse()
    {
        dbHelperTest = OpenHelperManager.getHelper(mContext, DBHelperTest.class);
        courseDao = dbHelperTest.getCourseExceptionDao();


        //storing programme name into a list
        List<LocalCourse> courses = courseDao.queryForAll();


        //creating a builder to update LocalProgramme table
        UpdateBuilder<LocalCourse, Object> updateBuilder = courseDao.updateBuilder();
        for (LocalCourse c : courses) {

            try {
                //update table where progCode equals the value ( lcc.getProgCode())
                updateBuilder.where().eq("code", "ABC");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                // update the value of of  year and progDesc where progCode equals the value ( lcc.getProgCode())
                updateBuilder.updateColumnValue("coordinator" /* column */, "Ed Sheeran" /* value */);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                // complete update transaction
                updateBuilder.update();
                Log.d("update course  status", c.toString());

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        List<LocalCourse> courses2 = courseDao.queryForAll();



            assertEquals("Ed Sheeran",courses2.get(0).getCoordinator());


    }

    public void testQueryPrograme() {

        dbHelperTest = OpenHelperManager.getHelper(mContext, DBHelperTest.class);
        localProgrammesDao = dbHelperTest.getProgrammeExceptionDao();
        List<LocalProgramme> programme = localProgrammesDao.queryForAll();

        assertEquals(1,programme.size());
        assertEquals("test description",programme.get(0).getProgDesc());
    }

    public void testQueryStudent() {


        dbHelperTest = OpenHelperManager.getHelper(mContext, DBHelperTest.class);
        studentDao = dbHelperTest.getStudentExceptionDao();
        List<Student> student = studentDao.queryForAll();

        assertEquals(1,student.size());
        assertEquals("Kilo",student.get(0).getName());

    }

    public void testQueryAllCourses() {

        dbHelperTest = OpenHelperManager.getHelper(mContext, DBHelperTest.class);
        courseDao = dbHelperTest.getCourseExceptionDao();
        List<LocalCourse> courses = courseDao.queryForAll();

        assertEquals(4,courses.size());
        assertEquals("no",courses.get(1).getMandatory());


    }
}
