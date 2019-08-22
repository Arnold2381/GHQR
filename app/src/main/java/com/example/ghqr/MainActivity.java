package com.example.ghqr;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import java.util.Random;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity<myRef, mListView> extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView zxing;
    String text;
    String[] split;
    String uid;
    String name;
    String gender;
    String f;
    private EditText bus;
    int a;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference().child("Buses");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bus=(EditText) findViewById(R.id.editText4);

    }

    public void scan(View view) {

        zxing = new ZXingScannerView(getApplicationContext());
        setContentView(zxing);
        zxing.setResultHandler(this);
        zxing.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();

        zxing.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        text = result.getText();
        int t = 1;
        if (t == 1) {
            send(text);
            t = 2;

            calla();
            setContentView(R.layout.activity_main);

            zxing.resumeCameraPreview(this);
        }


        zxing.resumeCameraPreview(this);

    }

    public void send(String text) {
        split = text.replace("=", " ").split(" ");
        uid = split[6].replace("\"", "");
        name = split[8].replace("\"", "") + " " + split[9].replace("\"", "");
        gender = split[11].replace("\"", "");
        Toast.makeText(getApplicationContext(), uid + " " + name + " " + gender, Toast.LENGTH_LONG).show();


    }

    public void calla() {
        Random random = new Random();
        a = random.nextInt();
        String aa = Integer.toString(a);
        f=bus.getText().toString();
        if (gender.equals("M")) {
            mRef.child(f).child("Passengers").child("Male").child(uid).child("Name").setValue(name);

            mRef.child(f).child("Passengers").child("Male").child(uid).child("Gender").setValue(gender);

        } else {
            mRef.child(f).child("Passengers").child("Female").child(uid).child("Name").setValue(name);
            //mRef.child("User").child("Female").child(aa).child("UID").setValue(uid);
            mRef.child(f).child("Passengers").child("Female").child(uid).child("Gender").setValue(gender);

        }
    }
}

