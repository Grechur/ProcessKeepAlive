package com.grechur.processkeepalive;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.grechur.processkeepalive.doubleservice.GuardService;
import com.grechur.processkeepalive.doubleservice.ProcessService;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        startService(new Intent(this,ProcessService.class));
        startService(new Intent(this,GuardService.class));
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            startService(new Intent(this,JobWakeService.class));
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
