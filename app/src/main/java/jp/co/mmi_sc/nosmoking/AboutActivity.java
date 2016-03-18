package jp.co.mmi_sc.nosmoking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {
    private int debug_count;

    private final static int DEBUG_COUNT = 20;
    private final static String DEBUG_MODE_INPUT = "MMIironman";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        debug_count = 0;

    }

    @Override
    protected void onResume() {
        super.onResume();
        debug_count = 0;
        setLinkText();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        debug_count++;
        if (debug_count > DEBUG_COUNT) {
            debug_count = 0;
            // デバッグカウントを超えたら、入力画面を表示
            final MyConfig config = new MyConfig(this);
            final EditText editView = new EditText(this);

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Password?")
                            //setViewにてビューを設定します。
                    .setView(editView)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //文字列比較
                            String inputText = editView.getText().toString();
                            if (DEBUG_MODE_INPUT.equals(inputText)) {
                                config.changeDebugMode();
                                setLinkText();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .show();
        }

        return super.onTouchEvent(event);
    }

    void setLinkText() {
        MyConfig config = new MyConfig(this);
        Resources res = getResources();

        String strCount = new String();
        if (config.getDebugMode() == true) {
            strCount = "Debug Mode Now!!!";
        } else {
            strCount = res.getString(R.string.homepage);
        }
        TextView textView = (TextView) findViewById(R.id.linkTextView);
        textView.setText(strCount);
    }
}
