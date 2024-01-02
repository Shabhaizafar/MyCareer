package com.droidevils.hired.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.droidevils.hired.Helper.Adapter.RegisterModel;
import com.droidevils.hired.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button edtLogin;
    EditText edtEmail;
    EditText edtPassword;
    TextView newAcc;
    TextView tvfp;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://jobcareer-63ca2-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("Register");

        edtLogin = findViewById(R.id.btn_login);
        edtEmail = findViewById(R.id.edt_email2);
        edtPassword = findViewById(R.id.edt_password2);
        newAcc = findViewById(R.id.new_acc);
        tvfp = findViewById(R.id.tv_fp);

        tvfp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = (LayoutInflater) getLayoutInflater();
                View tvFp = layoutInflater.inflate(R.layout.raw_fp, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                AlertDialog alertDialog = builder.create();
                alertDialog.setView(tvFp);
                alertDialog.show();
                EditText edtFPEmail = tvFp.findViewById(R.id.edt_fp);
                Button btnCP = tvFp.findViewById(R.id.btn_submit);

                btnCP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String strFPEmail = edtFPEmail.getText().toString();
                        if (strFPEmail.equals("")) {
                            Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                        } else {
                            if (alertDialog.isShowing()) {
                                alertDialog.dismiss();
                            }
                            firebaseAuth.sendPasswordResetEmail(strFPEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "We have sent a password reset link to your email!!!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Please enter valid email!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        edtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = edtEmail.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if (strEmail.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Enter Email id", Toast.LENGTH_SHORT).show();
                }
                else if (!strEmail.matches(emailPattern))
                {
                    Toast.makeText(LoginActivity.this, "Enter valid Email id", Toast.LENGTH_SHORT).show();
                }
                else if (strPassword.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                        firebaseAuth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    if(firebaseUser.isEmailVerified())
                                    {
                                        String strUID = firebaseAuth.getUid();
                                        databaseReference.child(strUID).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                RegisterModel registerModel = snapshot.getValue(RegisterModel.class);
                                                String loginEmail = registerModel.getUser_email();
                                                String loginName = registerModel.getUser_firstName();
                                                String loginMobilenumber = registerModel.getUser_mobileNumber();
                                                String loginRole = registerModel.getUser_Role();
                                                SharedPreferences sharedPreferences = getSharedPreferences("My_Career", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("KEY_USERID",strUID);
                                                editor.putString("KEY_PREF_EMAIL", loginEmail);
                                                editor.putString("KEY_PREF_USERNAME", loginName);
                                                editor.putString("KEY_PREF_MOBILENUMBER", loginMobilenumber);
                                                editor.putString("KEY_USERROLE",loginRole);
                                                editor.commit();

                                                if (loginRole.equals("Employer"))
                                                {
                                                    Toast.makeText(LoginActivity.this,"Welcome "+loginName,Toast.LENGTH_SHORT).show();
                                                    edtLogin.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            String txt_email=edtEmail.getText().toString();
                                                            String txt_password=edtPassword.getText().toString();
                                                            loginUser(txt_email,txt_password);
                                                            startActivity(new Intent(LoginActivity.this,Choose.class));
                                                            finish();
                                                        }
                                                    });
                                                }
                                                else if (loginRole.equals("Job Seeker"))
                                                {
                                                    Toast.makeText(LoginActivity.this,"Welcome "+loginName,Toast.LENGTH_SHORT).show();
                                                    edtLogin.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            String txt_email=edtEmail.getText().toString();
                                                            String txt_password=edtPassword.getText().toString();
                                                            loginUser(txt_email,txt_password);
                                                            startActivity(new Intent(LoginActivity.this,Choose1.class));
                                                            finish();
                                                        }
                                                    });
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                            }
                                        });
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this,"Your email is not verified yet!!!",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Wrong Credentials!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });//end of firebase auth
                    }
                }
        });

        newAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void loginUser(String txt_email, String txt_password) {
        firebaseAuth.signInWithEmailAndPassword(txt_email, txt_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Logged in Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
