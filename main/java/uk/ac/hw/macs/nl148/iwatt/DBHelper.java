package uk.ac.hw.macs.nl148.iwatt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;



/**
 * Author: Neio Lucas
 * File : DBHelper.java
 * Platform : Android Operating System
 * Date: 05/02/2016.
 * Description: This class manages the database operations that create the database and manage CRUD
 * operstions.
 */


public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final  String DATABASE_NAME = "student_hw_db.db";
    private static final  int DATABASE_VERSION = 2;

    private Dao<Student, Object> studentDao = null;
    private Dao<LocalProgramme, Object> programmeDao = null;
    private Dao<LocalCourse, Object> courseDao = null;

    private RuntimeExceptionDao<Student , Object> runtimeExceptionStudentDao = null;
    private RuntimeExceptionDao<LocalProgramme , Object> runtimeExceptionProgrammeDao = null;
    private RuntimeExceptionDao<LocalCourse , Object> runtimeExceptionCourseDao = null;

    public DBHelper(Context context)
    {
        super(context , DATABASE_NAME ,null , DATABASE_VERSION, R.raw.ormlite_config);
    }

    //creates tables
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource,LocalProgramme.class);
            TableUtils.createTable(connectionSource, Student.class);
            TableUtils.createTable(connectionSource, LocalCourse.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //drops tables if they exist
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,Student.class,true);
            TableUtils.dropTable(connectionSource, LocalProgramme.class, true);
            TableUtils.dropTable(connectionSource, LocalCourse.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Studet data access object
    public Dao<Student , Object> getStudentDao() throws SQLException {
        if(studentDao == null){
            studentDao = getDao(Student.class);
        }
        return studentDao;
    }

    //LocalProgramme data access object
    public Dao<LocalProgramme , Object> getProgrammeDao() throws SQLException {
        if(programmeDao == null){


            programmeDao = getDao(LocalProgramme.class);
        }
        return programmeDao;
    }

    //LocalCourse data access object
    public Dao<LocalCourse , Object> getCourseDao() throws SQLException {
        if(courseDao == null){


            courseDao = getDao(LocalCourse.class);
        }
        return courseDao;
    }

    //runtime exceptions method for Student DAO
    public RuntimeExceptionDao<Student, Object> getStudentExceptionDao(){
        if(runtimeExceptionStudentDao == null)
        {
            runtimeExceptionStudentDao = getRuntimeExceptionDao(Student.class);
        }
        return  runtimeExceptionStudentDao;
    }

    //runtime exceptions method for LocalProgramme DAO
    public RuntimeExceptionDao<LocalProgramme, Object> getProgrammeExceptionDao(){
        if(runtimeExceptionProgrammeDao == null)
        {
            runtimeExceptionProgrammeDao = getRuntimeExceptionDao(LocalProgramme.class);
        }
        return  runtimeExceptionProgrammeDao;
    }

    //runtime exceptions method for LocalCourse DAO
    public RuntimeExceptionDao<LocalCourse, Object> getCourseExceptionDao(){
        if(runtimeExceptionCourseDao == null)
        {
            runtimeExceptionCourseDao = getRuntimeExceptionDao(LocalCourse.class);
        }
        return  runtimeExceptionCourseDao;
    }
}
