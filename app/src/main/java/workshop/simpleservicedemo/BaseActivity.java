package workshop.simpleservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.MAIN");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("LoginActivity", "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
                Toast toast = Toast.makeText(context, "หมดเวลา", Toast.LENGTH_LONG);
                toast.show();
            }
        };

        this.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onResume();
        this.unregisterReceiver(mReceiver);
    }


}
