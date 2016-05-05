package uk.ac.hw.macs.nl148.iwatt;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

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
 * File : Report.java
 * Platform : Android Operating System
 * Date: 17/04/2016
 * Description: This fragment displays the Report feature seen in the navigation menu
 */
public class Report extends Fragment implements View.OnClickListener {


    TextView name;
    TextView email;
    TextView report;
    Button exit;
    Button submit;
    Spinner dropdown;
    String message = "";
    private static final String username = "studentheriotwatt@gmail.com";
    private static final String password = "no-reply";
    private Multipart _multipart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_report, container,false);

        name = (TextView) view.findViewById(R.id.report_name);
        email = (TextView) view.findViewById(R.id.report_email);
        report = (TextView) view.findViewById(R.id.report_message);
        dropdown = (Spinner) view.findViewById(R.id.report_spinner);
        submit = (Button) view.findViewById(R.id.report_submit);
        exit = (Button) view.findViewById(R.id.report_exit);


        DBHelper dbHelper = OpenHelperManager.getHelper(getActivity().getApplicationContext(), DBHelper.class);
        RuntimeExceptionDao<Student, Object> studentDao = dbHelper.getStudentExceptionDao();
        List<Student> student = studentDao.queryForAll();

        name.setText("Name :" +student.get(0).getName());
        email.setText("Email :" + student.get(0).getEmail());
        email.setEms(11);
        name.setEms(6);

        submit.setOnClickListener(this);
        exit.setOnClickListener(this);

        ArrayList<String> subject = new ArrayList<>();

        subject.add("[Select a Subject]");
        subject.add("Bug Report");
        subject.add("Complaint");
        subject.add("Suggestion");
        subject.add("Compliment");


        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, subject);

        dropdown.setAdapter(spinneradapter);
        OpenHelperManager.releaseHelper();

        return view;
    }

    @Override
    public void onClick(View v) {

        if(submit == v)
        {
            //check if email is valid
            if(!email.getText().toString().contains("@") || !email.getText().toString().contains(".") )
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                TextView tx = new TextView(getActivity());
                builder.setTitle("Report");
                builder.setMessage("\n" +
                        "\nEmail address is invalid.\n" +
                        "\n\n" +"Please go to Personal Settings to change it.");
                builder.setView(tx);
                builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        AlertDialog build = builder.create();
                        build.cancel();

                    }
                });

                AlertDialog build = builder.create();
                build.setCanceledOnTouchOutside(false);
                build.show();
            }
            else
            {
                //check if subject is selected
                if(dropdown.getSelectedItem().toString().contentEquals("[Select a Subject]"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    TextView tx = new TextView(getActivity());
                    builder.setTitle("Report");
                    builder.setMessage("\n" +
                            "\nPlease select a subject before you submit.\n" +
                            "\n");
                    builder.setView(tx);
                    builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            AlertDialog build = builder.create();

                            build.cancel();
                        }
                    });

                    AlertDialog build = builder.create();
                    build.setCanceledOnTouchOutside(false);
                    build.show();
                }
                else{
                    //check if message box is empty
                    if(report.getText().toString().equals("") || report.getText().toString().equals(null) )
                    {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        TextView tx = new TextView(getActivity());
                        builder.setTitle("Report");
                        builder.setMessage("\n" +
                                "\nPlease write your "+dropdown.getSelectedItem().toString().toLowerCase()+" before you submit.\n" +
                                "\n");
                        builder.setView(tx);
                        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                AlertDialog build = builder.create();
                                build.cancel();

                            }
                        });

                        AlertDialog build = builder.create();
                        build.setCanceledOnTouchOutside(false);
                        build.show();
                    }
                    else
                    {

                        int maximum = 9999999;
                        int minimum = 1000000;
                        Random rn = new Random();
                        int range = maximum - minimum + 1;
                        int randomNum = rn.nextInt(range) + minimum;
                        String ticket = "#RP-" + randomNum;

                        message = report.getText().toString();

                        message += "\n\n"+"Ticket ID :" + ticket +"\n"
                                +"Type :" + dropdown.getSelectedItem().toString() + "\n"
                                + "Sender " + name.getText() + "\n"
                                + "Sender " + email.getText();

                        sendMail("mrneliolucas@gmail.com", dropdown.getSelectedItem().toString() +" :"+ticket,message);
                    }

                }
            }

        }

        if(exit == v)
        {
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();


        }

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
        message.setFrom(new InternetAddress("hw@hw.com", "iWatt Report"));
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
            progressDialog = ProgressDialog.show(getActivity(), "Please wait", "Submitting form", true, false);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            TextView tx = new TextView(getActivity());
            builder.setTitle("Report");
            builder.setMessage("\n" +
                    "\nThank you.We will get back to you as soon as possible.\n" +
                    "\n");
            builder.setView(tx);
            builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                   // Intent i = new Intent(getActivity(), MainActivity.class);
                    //getActivity().finish();
                    //startActivity(i);

                    getActivity().getFragmentManager().beginTransaction().remove(Report.this).commit();
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
}
