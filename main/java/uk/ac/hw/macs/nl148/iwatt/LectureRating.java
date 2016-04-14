package uk.ac.hw.macs.nl148.iwatt;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

/**
 * Author: Neio Lucas
 * File : About.java
 * Platform : Android Operating System
 * Date:  06/04/2016..
 * Description: This activity allows the user to rate his/her completeted courses.
 */

public class LectureRating extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    List<LocalCourse> courses;
    LectureRatingAdapter la;
    Button submit;
    Button back;
    Spinner dropdown;
    private static final String username = "studentheriotwatt@gmail.com";
    private static final String password = "no-reply";
    private Multipart _multipart;
    DBHelper dbHelper;
    RuntimeExceptionDao<LocalCourse, Object> localCoursesDao = null;
    ArrayList<LectureRatingData> lecdata = new ArrayList<>();
    ArrayList<LectureRatingData> questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.i);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_lecture_rating);


        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title10);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Simple tfb.ttf");
        toolbar.setTitle("");
        toolbar_title.setTypeface(tf);
        setSupportActionBar(toolbar);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        questions = new ArrayList<>();

        back = (Button) findViewById(R.id.rate_back);
        back.setOnClickListener(this);

        questions.add(new LectureRatingData("Level of effort you put into the course",0.0f));
        questions.add(new LectureRatingData("Level of skill/knowledge at start of course",0.0f));
        questions.add(new LectureRatingData("Level of skill/knowledge at end of course", 0.0f));
        questions.add(new LectureRatingData("Level of skill/knowledge required to complete the course",0.0f));
        questions.add(new LectureRatingData("Contribution of course to your skill/knowledge",0.0f));
        questions.add(new LectureRatingData("I found the course intellectually challenging and stimulating",0.0f));
        questions.add(new LectureRatingData("I learnt and understood the subject materials in this course",0.0f));
        questions.add(new LectureRatingData("Instructor had a genuine interest in individual students",0.0f));
        questions.add(new LectureRatingData("Instructor was adequately accessible to students during office hours or after class",0.0f));
        questions.add(new LectureRatingData("Instructor is enthusiastic about teaching this course",0.0f));




        la = new LectureRatingAdapter(this,R.layout.question_layout,questions);
        listView = (ListView) findViewById(R.id.form_list);

        //listView.setAdapter(adapter);
        listView.setAdapter(la);
        dbHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);

        localCoursesDao = dbHelper.getCourseExceptionDao();

         courses = localCoursesDao.queryForAll();

        ArrayList<String> coursenames = new ArrayList<>();

        coursenames.add("[Select a course]");
        for(LocalCourse c : courses)
        {
            coursenames.add(c.getCoursename());
        }

        dropdown = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, coursenames);
        dropdown.setAdapter(spinneradapter);




    }


    @Override
    public void onClick(View v) {

        if(v == submit) {

            if(!dropdown.getSelectedItem().toString().equals("[Select a course]")) {

                ArrayList<LectureRatingData> filteredArray = la.getResults();

                //using hashset to prevent duplicate data
                HashSet<LectureRatingData> set = new HashSet<LectureRatingData>();
                set.addAll(filteredArray);
                filteredArray.clear();
                filteredArray.addAll(set);

                //using forloop to get staff memeber
                String staff = "";
                float totalRating = 0.0f;
                for (LocalCourse c : courses) {
                    if (c.getCoursename().equals(dropdown.getSelectedItem().toString())) {
                        staff = c.getCoordinator();
                    }
                }

                //in future the staffs name would be dynmically entered
                String message = "Dear " + staff + "," + "\n\n" +
                        "This is a feedback form from one of your students attending " + dropdown.getSelectedItem().toString() +
                        ".Please note that each question is rated out of 5." + "\n\n\n";


                for (LectureRatingData lecd : filteredArray) {
                    message += lecd.getQuestion() + ".\nRate: " + lecd.getRating() + "\n\n";
                    totalRating += lecd.getRating();

                }

                //adding rating to message
                message += "Average Rating: " + averageRating(totalRating,questions.size());

                message += " \n\n Kind Regards, \n The iWatt Team";

                System.out.print(message);


                if (filteredArray.size() != questions.size()) {

                    Toast.makeText(this, "Please rate all questions", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("status", "all is done");
                    sendMail("mrneliolucas@gmail.com", "Answered Evaluation Form for " + dropdown.getSelectedItem().toString(), message);
                }
            }
            else
            {
                Toast.makeText(this, "Please select a course before submitting", Toast.LENGTH_SHORT).show();
            }


        }

        if(v == back)
        {
            finish();
        }

    }

    //caluclate average rating
    private String averageRating(float total , int number)
    {
        float avg = total/number;

        return avg + "";
    }

    //sends email to respective staff memeber with feedback results
    private void sendMail(String email, String subject, String messageBody )
    {
        Session session = createSessionObject();

        try {
            Message message = createMessage(email, subject, messageBody, session );
            new SendMailTask().execute(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public void addAttachment(String filename) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);

        _multipart.addBodyPart(messageBodyPart);
    }

    private Message createMessage(String email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("hw@hw.com", " No-Reply"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }

    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

     class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LectureRating.this, "Please wait", "Submitting form", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            thankYouMessage();

        }

         //displays a message after submission
         private void thankYouMessage()
         {
             AlertDialog.Builder builder = new AlertDialog.Builder(LectureRating.this);
             TextView tx = new TextView(LectureRating.this);
             builder.setTitle("Lecture Rating");
             builder.setMessage("\n" +
                     "\nThank you for your time.Your feed back is important to us.\n" +
                     "\n");
             builder.setView(tx);
             builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {


                     Intent i = new Intent(getBaseContext(),MainActivity.class);
                     finish();
                     startActivity(i);

                 }
             });

             AlertDialog build = builder.create();
             build.setCanceledOnTouchOutside(false);
             build.show();
         }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
