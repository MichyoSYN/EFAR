package com.example.efar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        // Delay 2 seconds to run() and jump to another page.
        new Handler().postDelayed(new Runnable() {
        	
        	public void run() {
        		Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
        		startActivity(intent);
        		WelcomeActivity.this.finish();
        	}
        }, 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
