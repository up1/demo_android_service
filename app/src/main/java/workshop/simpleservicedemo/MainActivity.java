package workshop.simpleservicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import workshop.simpleservicedemo.service.CountdownService;
import workshop.simpleservicedemo.service.CountdownServiceBinder;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getName();

    private ServiceConnection serviceConnection;
    private CountdownService countdownService;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.serviceIntent = new Intent(this, CountdownService.class);
    }

    public void startTimeService(View view) {
        this.startAndBindCountdownService();
        if(countdownService != null) {
            countdownService.startCountdown();
        }
    }

    public void nextPage(View view) {
        if(countdownService != null) {
            countdownService.reset();
        }
        Intent intent = new Intent(this, SomeActivity.class);
        startActivity(intent);
    }

    public void stopTimeService(View view) {
        if(countdownService != null) {
            countdownService.reset();
            countdownService.startCountdown();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.startAndBindCountdownService();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause() called");
        if(countdownService != null) {
            Log.d(TAG, "onPause() called 2");
            countdownService.reset();
        }
        if(serviceConnection != null) {
            Log.d(TAG, "onPause() called 3");
            unbindService(serviceConnection);
            countdownService = null;
            serviceConnection = null;
        }

        super.onPause();
    }

    private void startAndBindCountdownService() {
        if(this.countdownService == null) {
            startService(serviceIntent);
            initServiceConnection();
            bindService(serviceIntent, serviceConnection, 0);
        }
    }

    private void initServiceConnection() {
        Log.d(TAG, "initServiceConnection()");
        this.serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className,
                                           IBinder service) {
                CountdownServiceBinder binder = (CountdownServiceBinder) service;
                MainActivity.this.onServiceConnected(binder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                MainActivity.this.onServiceDisconnected();
            }
        };
    }

    private void onServiceConnected(CountdownServiceBinder binder) {
        Log.d(TAG, "onServiceConnected(CountdownServiceBinder)");
        this.countdownService = binder.getCountdownService();
    }

    private void onServiceDisconnected() {
        Log.d(TAG, "onServiceDisconnected()");
        this.countdownService = null;
        this.serviceConnection = null;
    }
}
