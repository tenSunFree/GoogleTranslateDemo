package com.home.googletranslatedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TypingInputActivity extends AppCompatActivity {

    private LinearLayout closeLinearLayout;
    private EditText editText;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing_input);

        initializationViewAndRelatedSettings();
    }

    /** 初始化View, 以及相關點擊監聽 */
    private void initializationViewAndRelatedSettings() {
        editText =  findViewById(R.id.editText);
        editText.setText(getIntent().getExtras().getString("inputText"));
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {

                /** 如果輸入法在視窗上已經顯示 則隱藏, 反之則顯示 */
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
        closeLinearLayout =  findViewById(R.id.closeLinearLayout);
        closeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }

    /** 返回的時候, 回傳輸入的資料 */
    @Override
    public void onBackPressed() {
        intent = new Intent();
        intent.putExtra("inputText", editText.getText().toString());                                                            // 將inputText放入Bundle
        setResult(Activity.RESULT_OK, intent);	                                                    // 回傳RESULT_OK
        supportFinishAfterTransition();
    }
}
