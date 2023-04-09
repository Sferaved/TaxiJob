package com.example.taxijob.application;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.taxijob.R;
import com.example.taxijob.about.AboutActivity;
import com.example.taxijob.start.StartActivity;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private ArrayAdapter<String> adapter;
    private String[] arrayCities;
    private EditText firstName;
    private EditText secondName;
    private EditText email;
    private EditText phoneNumber;
    private String city = "Київ";
    private Button startBtn;
    private boolean valid;

    private String addressAdmin = "taxi.easy.ua@gmail.com";
    private final int NOTIFICATION_ID = 127;
    private final String TAG = "TAG";
    private EmailValidator emailValidator;
    private PhoneValidator phoneValidator;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main_layout);

        firstName =  findViewById(R.id.first_name);
        secondName =  findViewById(R.id.second_name);
        email =  findViewById(R.id.email);
        phoneNumber =  findViewById(R.id.phone_number);

        emailValidator = new EmailValidator();
        phoneValidator = new PhoneValidator();

        startBtn = findViewById(R.id.sendEmail);
        startBtn.setOnClickListener(view -> {
                    if(isValid()){
                        sendEmail();
                    }
                }
                );

        Spinner spinnerCities = findViewById(R.id.list_cities);
        arrayCities = cities();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayCities);
        spinnerCities.setAdapter(adapter);
        spinnerCities.setPrompt("Title");
        spinnerCities.setSelection(0);
        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(MainActivity.this, getString(R.string.selected)+ " город: " + arrayCities[position], Toast.LENGTH_SHORT).show();
                city = arrayCities[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.about:
                intent = new Intent(this, AboutActivity.class);
                break;
        }
        startActivity(intent);
        return false;
    }

    private String[] cities () {
        return new String[]{
                "Київ",
                "Вінниця",
                "Дніпро",
                "Донецьк",
                "Житомир",
                "Запоріжжя",
                "Івано-Франківськ",
                "Кропивницький",
                "Луганськ",
                "Луцьк",
                "Львів",
                "Миколаїв",
                "Одеса",
                "Полтава",
                "Рівне",
                "Суми",
                "Тернопіль",
                "Ужгород",
                "Харків",
                "Херсон",
                "Хмельницький",
                "Черкаси",
                "Чернігів",
                "Чернівці"};
    }

    protected void sendEmail() {
        Log.i("Send email", "");

        String subject = "Лист від водія-кандидата";
        String emailText = "Прошу розглянути мою кандидатуру для роботи водієм у Вашій службі таксі." +
                "\nГород: " + city
                + "\nІм\'я:  " + firstName.getText().toString()
                + "\nПрізвище: " + secondName.getText().toString()
                + "\nEmail: " + email.getText().toString()
                + "\nТелефон: " + phoneNumber.getText().toString();


        String[] TO = {addressAdmin};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailText);

        try {
            startActivity(Intent.createChooser(emailIntent, "Надислати лист..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showNotification();
    }

    public void showNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_foreground))
                .setTicker("New notification")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Повідомлення надіслано адміністратору.")
                .setContentText("Очікуйте відповіді на електронну пошту або дзвінок.");

        Notification notification = builder.build();

        long[] vibrate = {1500,1000, 1500, 1000};
        notification.vibrate = vibrate;
        notification.flags = notification.flags | Notification.FLAG_INSISTENT;
        manager.notify(NOTIFICATION_ID, notification);

    }

    private boolean isValid() {
        StringBuilder messageError = new StringBuilder("Введіть ");
        valid = true;
        if (firstName.getText().toString().equals("")) {
            valid = false;
            messageError.append("ім'я");
        }
         if (secondName.getText().toString().equals("")){
             valid = false;
             messageError.append(" прізвище");
         }
         if (email.getText().toString().equals("")) {
             valid = false;
             messageError.append(" електронну пошту");
         } else {
             if(!emailValidator.validate(email.getText().toString())){
                 valid = false;
                 Toast.makeText(this, "Перевірте формат написання електронної пошти: " + email.getText().toString(), Toast.LENGTH_SHORT).show();
             };
         }
         
         if (phoneNumber.getText().toString().equals("")) {
             valid = false;
             messageError.append(" номер телефону");
         } else {
             if(!phoneValidator.validate(phoneNumber.getText().toString())){
                 valid = false;
                 Toast.makeText(this, "Перевірте формат вводу номера телефона: " + phoneNumber.getText().toString(), Toast.LENGTH_SHORT).show();
             }
         };

         if(!messageError.toString().equals("Введіть ")) {
             Toast.makeText(this, messageError, Toast.LENGTH_SHORT).show();
         }

         return valid;
    }

}
