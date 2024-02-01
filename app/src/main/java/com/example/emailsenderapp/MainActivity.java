package com.example.emailsenderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.emailsenderapp.databinding.ActivityMainBinding;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        mainBinding= DataBindingUtil.setContentView (this,R.layout.activity_main);
        mainBinding.Btn1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                try {
                     String useremail=mainBinding.email.getText ().toString ();
                     String subject=mainBinding.subject.getText ().toString ();
                      String message=mainBinding.message.getText ().toString ();
                    String stringSenderEmail = "gymmanagementsystem18@gmail.com";

                    String stringPasswordSenderEmail = "yokz zpbu reaw xgbv";

                    String stringHost = "smtp.gmail.com";

                    Properties properties = System.getProperties ();

                    properties.put ("mail.smtp.host", stringHost);
                    properties.put ("mail.smtp.port", "465");
                    properties.put ("mail.smtp.ssl.enable", "true");
                    properties.put ("mail.smtp.auth", "true");

                    javax.mail.Session session = Session.getInstance (properties, new Authenticator () {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication (stringSenderEmail, stringPasswordSenderEmail);
                        }
                    });

                    MimeMessage mimeMessage = new MimeMessage (session);
                    mimeMessage.addRecipient (Message.RecipientType.TO, new InternetAddress (useremail));

                    mimeMessage.setSubject ("Subject:"+subject);
                    mimeMessage.setText (message);
                    Toast.makeText (MainActivity.this, "Done", Toast.LENGTH_SHORT).show ();
                    Thread thread = new Thread (new Runnable () {
                        @Override
                        public void run() {
                            try {
                                Transport.send (mimeMessage);
                            } catch (MessagingException e) {
                                e.printStackTrace ();
                            }
                        }
                    });
                    thread.start ();

                } catch (AddressException e) {
                    e.printStackTrace ();
                } catch (MessagingException e) {
                    e.printStackTrace ();
                }

            }
        });

    }
}