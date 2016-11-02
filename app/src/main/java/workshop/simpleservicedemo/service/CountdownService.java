package workshop.simpleservicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by somkiat on 11/2/2016 AD.
 */

public class CountdownService extends Service {

    private static final String TAG = CountdownService.class.getName();
    private CountdownServiceBinder countdownServiceBinder;
    private CountDownTimer countdownTimer;
    private static final int COUNTDOWN_TICK_INTERVALL = 300;
    private long remainingMilliseconds;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return this.countdownServiceBinder;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate() called");
        super.onCreate();
        this.countdownServiceBinder = new CountdownServiceBinder(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() called with: intent = [" + intent + "], flags = [" + flags + "], startId = [" + startId + "]");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
    }

    public void startCountdown() {
        Log.d(TAG, "startCountdown() called");
        this.countdownTimer = new CountDownTimer(5000,
                COUNTDOWN_TICK_INTERVALL) {
            public void onTick(long millisUntilFinished) {
                CountdownService.this.onCountdownTimerTick(millisUntilFinished);
            }

            public void onFinish() {
                CountdownService.this.onCountdownTimerFinish();
            }
        }.start();
    }

    private void onCountdownTimerTick(long remainingMilliseconds) {
        Log.v(TAG, "countdown tick - remainingMilliseconds: "
                + remainingMilliseconds);
        this.remainingMilliseconds = remainingMilliseconds;
    }

    private void onCountdownTimerFinish() {
        Log.d(TAG, "countdown finished");
        this.remainingMilliseconds = 0;

        Intent intent = new Intent("android.intent.action.MAIN");
        this.sendBroadcast(intent);

    }

    public void reset() {
        this.remainingMilliseconds = 5000;
        countdownTimer.cancel();
    }
}
