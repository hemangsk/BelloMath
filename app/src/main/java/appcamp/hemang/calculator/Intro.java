package appcamp.hemang.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hemang on 22/06/16.
 */
public class Intro extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = new Intent(this, MainActivity.class);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean status  = sharedPreferences.getBoolean("Status", false);
        if(status){
            startActivity(intent);
            finish();
        }

        else {
            setContentView(R.layout.activity_intro);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("Status", true);
            editor.commit();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }



    }
}
