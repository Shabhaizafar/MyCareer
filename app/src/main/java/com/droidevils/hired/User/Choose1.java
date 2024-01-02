package com.droidevils.hired.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.droidevils.hired.R;

public class Choose1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose1);
//        Button employee = findViewById(R.id.button10);
        Button employee = findViewById(R.id.button8);
        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Choose1.this,DashboardActivity1.class));
                finish();
                Toast.makeText(Choose1.this, "Successful!!", Toast.LENGTH_SHORT).show();
            }
        });

//        employer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Choose1.this,EmployerActivity.class));
//                finish();
//                Toast.makeText(Choose1.this, "Successful!!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}

