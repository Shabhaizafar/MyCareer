package com.droidevils.hired.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.droidevils.hired.R;

public class Choose extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
//        Button employee = findViewById(R.id.button8);
        Button employer = findViewById(R.id.button7);
//        employee.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Choose.this,Employee.class));
//                finish();
//                Toast.makeText(Choose.this, "Successful!!", Toast.LENGTH_SHORT).show();
//            }
//        });

        employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Choose.this,DashboardActivity.class));
                finish();
                Toast.makeText(Choose.this, "Successful!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
