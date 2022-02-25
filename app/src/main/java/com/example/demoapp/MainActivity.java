package com.example.demoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;
import com.clevertap.android.sdk.inbox.CTInboxMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CTInboxListener,DisplayUnitListener {
    Button demo,demo2,demo3,demo4,demo5,demo6,test;
    CardView c;
    TextView title,mssg;
    RecyclerView rv;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);


        if (clevertapDefaultInstance != null) {
            //Set the Notification Inbox Listener
            clevertapDefaultInstance.setCTNotificationInboxListener(this);
            //Initialize the inbox and wait for callbacks on overridden methods
            clevertapDefaultInstance.initializeInbox();//comment if u want to put on on click cuz otherwise it will open everytime u launch app
        }

        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);




        demo=findViewById(R.id.b1);//create user
        demo2=findViewById(R.id.b2);//map user with info
        demo3=findViewById(R.id.b3);//app notifications
        demo4=findViewById(R.id.b4);//in app notification
        demo5=findViewById(R.id.b5);//app inbox
        demo6=findViewById(R.id.b6);
        c=findViewById(R.id.c1);
        title=findViewById(R.id.native_display_title);
        mssg=findViewById(R.id.native_display_message);
        test=findViewById(R.id.test);
        rv=findViewById(R.id.rv);


        //List<String> name=new ArrayList<>();
        //name.add("Jhon");
        ArrayList<CTInboxMessage> allMessages = clevertapDefaultInstance.getAllInboxMessages();
        Log.d("Rushdan","inbox"+clevertapDefaultInstance.getAllInboxMessages());

        rv.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter=new MyAdapter(this,allMessages);
        rv.setAdapter(adapter);



        //here

        CleverTapAPI.createNotificationChannel(getApplicationContext(),"1999","Channel1999","ChannelDescription", NotificationManager.IMPORTANCE_MAX,true);
        //here


        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", "Rushdan Bijapure");    // String
                //profileUpdate.put("Identity", 11111);      // String or number
                profileUpdate.put("Email", "rushdan@clevertap.com"); // Email address of the user
                profileUpdate.put("Phone", "8779097295");   // Phone (with the country code, starting with +)
                profileUpdate.put("Gender", "M");             // Can be either M or F
                profileUpdate.put("DOB", new Date());         // Date of Birth. Set the Date object to the appropriate value first
// optional fields. controls whether the user will be sent email, push etc.
                profileUpdate.put("MSG-email", false);        // Disable email notifications
                profileUpdate.put("MSG-push", true);          // Enable push notifications
                profileUpdate.put("MSG-sms", false);          // Disable SMS notifications
                profileUpdate.put("MSG-whatsapp", true);      // Enable WhatsApp notifications
                ArrayList<String> stuff = new ArrayList<String>();
                stuff.add("bag");
                stuff.add("shoes");
                profileUpdate.put("MyStuff", stuff);                        //ArrayList of Strings
                String[] otherStuff = {"Jeans","Perfume"};
                profileUpdate.put("MyStuff", otherStuff);                   //String Array



                clevertapDefaultInstance.onUserLogin(profileUpdate);
                clevertapDefaultInstance.pushEvent("Product viewed");
            }
        });

        demo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // event with properties
                HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
                prodViewedAction.put("Product Name", "Casio Chronograph Watch");
                prodViewedAction.put("Category", "Mens Accessories");
                prodViewedAction.put("Price", 59.99);
                prodViewedAction.put("Date", new java.util.Date());

                clevertapDefaultInstance.pushEvent("Product viewed", prodViewedAction);

/**
 * Data types
 * The value of a property can be of type Date (java.util.Date), an Integer, a Long, a Double,
 * a Float, a Character, a String, or a Boolean.
 *
 * Date object
 * When a property value is of type Date, the date and time are both recorded to the second.
 * This can be later used for targeting scenarios.
 * For e.g. if you are recording the time of the flight as an event property,
 * you can send a message to the user just before their flight takes off.
 */
            }
        });

        demo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clevertapDefaultInstance.pushEvent("Rushdan");

            }
        });


        demo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clevertapDefaultInstance.pushEvent("custominapp");

            }
        });

        demo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clevertapDefaultInstance.pushEvent("Inboxxxx");
                inboxDidInitialize();
                //inboxMessagesDidUpdate();

            }
        });

        demo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clevertapDefaultInstance.pushEvent("NativeDsiplayRushdan");
//                onDisplayUnitsLoaded();
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clevertapDefaultInstance.initializeInbox();
//                CTInboxMessage.
            }
        });






    }


    @Override
    public void inboxDidInitialize() {

        CleverTapAPI ct = CleverTapAPI.getDefaultInstance(getApplicationContext());
        ct.showAppInbox();//Opens Activity with default style configs

    }

    @Override
    public void inboxMessagesDidUpdate() {




    }


    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        //public CleverTapDisplayUnit getDisplayUnitForId();
        for (int i=0;i<units.size();i++) {
            CleverTapDisplayUnit unit = units.get(i);
            System.out.println(unit);
            prepareDisplayView(unit);
        }

    }


    private void prepareDisplayView(CleverTapDisplayUnit unit) {

        for(CleverTapDisplayUnitContent i:unit.getContents()) {
            title.setText(i.getTitle());
            mssg.setText(i.getMessage());

        }

        //Notification Viewed Event
        CleverTapAPI.getDefaultInstance(this).pushDisplayUnitViewedEventForID(unit.getUnitID());

        //Notification Clicked Event
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CleverTapAPI.getDefaultInstance(getApplicationContext()).pushDisplayUnitClickedEventForID(unit.getUnitID());

            }
        });
    }
}