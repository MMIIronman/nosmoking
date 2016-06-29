package jp.co.mmi_sc.nosmoking;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.pdfview.PDFView;

import java.util.Locale;

public class ManualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PDFView pdfView = (PDFView) findViewById(R.id.pdfview);
        Locale locale = Locale.getDefault();
        if (Locale.JAPAN.equals(locale)) {
            pdfView.fromAsset("manual_ja.pdf")
                    .defaultPage(1)
                    .showMinimap(false)
                    .enableSwipe(true)
                    .load();
        } else {
            pdfView.fromAsset("manual_en.pdf")
                    .defaultPage(1)
                    .showMinimap(false)
                    .enableSwipe(true)
                    .load();
        }
    }

}
