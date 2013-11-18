package com.wavecounter;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {

    private static final String LOG_TAG = "wavecounter main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     *  User clicked record button.
     *  start collecting all sensor information
     */
    public void recordButton(View view){

        Log.i(LOG_TAG, "hit record");
        Intent intent = new Intent(this, SensorActivity.class);
        //intent.putExtra(SUBMITTED_REPORT_MESSAGE, submittedReport);
        //if(tookPhoto)
        //    intent.putExtra(SUBMITTED_REPORT_PHOTO, imageUri.getPath());
        startActivity(intent);

    }

}
