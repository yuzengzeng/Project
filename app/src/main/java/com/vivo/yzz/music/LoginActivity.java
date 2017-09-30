package com.vivo.yzz.music;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {




    // UI references.
    private AutoCompleteTextView nameView;
    private EditText mPasswordView;
    private ProgressBar loginProgressBar;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameView=(AutoCompleteTextView)findViewById(R.id.name);
        mPasswordView=(EditText)findViewById(R.id.password);
        loginProgressBar = (ProgressBar)findViewById(R.id.login_progress);

        loginButton = (Button)findViewById(R.id.login);


    }

    public void login(View view){
        String name=nameView.getText().toString();
        String password=mPasswordView.getText().toString();
        if (name.equals("")||name.equals(" ")||name==null){
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
        }else if (password.equals("")||password.equals(" ")||password==null){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        }else {
            loginProgressBar.setVisibility(View.VISIBLE);
            //发送数据
            loginButton.setClickable(false);
            loginButton.setText("登录中，请稍后。。。。");
        }
    }

}

