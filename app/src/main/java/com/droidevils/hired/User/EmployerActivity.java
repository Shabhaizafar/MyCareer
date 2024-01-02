package com.droidevils.hired.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.droidevils.hired.Helper.Adapter.EmployerInfo;
import com.droidevils.hired.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EmployerActivity extends AppCompatActivity {
    public EditText editname;
    public EditText editcname;
    public EditText editphone;
    public EditText editcity;
    public EditText editcountry;
    public EditText editvac;
    public Button enter;
    private FirebaseFirestore db;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    FirebaseAuth firebaseAuth;
    public EmployerActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);
        editname = findViewById(R.id.editText2);
        editcname = findViewById(R.id.editText4);
        editphone = findViewById(R.id.editText7);
        editcity = findViewById(R.id.editText8);
        editcountry = findViewById(R.id.editText9);
        editvac=findViewById(R.id.editText10);
        enter =findViewById(R.id.button4);
        db = FirebaseFirestore.getInstance();

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth = FirebaseAuth.getInstance();
                rootNode = FirebaseDatabase.getInstance("https://mycareers-2d37a-default-rtdb.asia-southeast1.firebasedatabase.app/");
                reference = rootNode.getReference("employer");


                String name=editname.getText().toString();
                String cname=editcname.getText().toString();
                String phone=editphone.getText().toString();
                String city=editcity.getText().toString();
                String country=editcountry.getText().toString();
                String vac=editvac.getText().toString();
                Map<String, Object> user=new HashMap<>();
                user.put("name",name);
                user.put("company name",cname);
                user.put("phone",phone);
                user.put("city",city);
                user.put("country",country);
                user.put("vacancy",vac);

                EmployerInfo eminfo = new EmployerInfo(name,cname,phone,city,country, vac);

                reference.child(phone).setValue(eminfo);

                db.collection("employers").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(EmployerActivity.this, "Details added successfully!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EmployerActivity.this, "Error "+e, Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(EmployerActivity.this,DashboardActivity.class));
                finish();
            }
        });
    }

    public void enter(View view) {
    }

    public void setEditdocumentid(String id) {
    }

    public String getEditdocumentid() {
        return null;
    }
}
