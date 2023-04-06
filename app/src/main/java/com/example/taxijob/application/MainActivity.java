package com.example.taxijob.application;

import android.annotation.SuppressLint;
import android.content.Intent;
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

    private String addressAdmin = "taxi.easy.ua@gmail.com";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main_layout);

        firstName =  findViewById(R.id.first_name);
        secondName =  findViewById(R.id.second_name);
        email =  findViewById(R.id.email);
        phoneNumber =  findViewById(R.id.phone_number);

        startBtn = findViewById(R.id.sendEmail);
        startBtn.setOnClickListener(view -> sendEmail());

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
        String emailText = "Город: " + city
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
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
//            Toast.makeText(this, "Повідомлення надіслано адміністратору. Очікуйте відповіді на електронну пошту або дзвінок.", Toast.LENGTH_LONG).show();
//      andrey      intent = new Intent(this, StartActivity.class);
//            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }



//    private void sendEmail(String subject, String emailText) {
//        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//
//        emailIntent.setType("plain/text");
//        // Кому
//        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { addressAdmin });
//        // Зачем
//        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
//        // О чём
//        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailText);
////        // С чем
////        emailIntent.putExtra(
////                android.content.Intent.EXTRA_STREAM,
////                Uri.parse("file://"
////                        + Environment.getExternalStorageDirectory()
////                        + "/Клипы/SOTY_ATHD.mp4"));
//
//        try {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            finish();
//            Log.i("TAG", "Finished sending email...");
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
//    }

}
