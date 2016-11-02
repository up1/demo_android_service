package workshop.simpleservicedemo.service;

import android.os.Binder;

public class CountdownServiceBinder extends Binder {


    private CountdownService countdownService;

    public CountdownService getCountdownService() {
        return countdownService;
    }

    public void setCountdownService(CountdownService countdownService) {
        this.countdownService = countdownService;
    }

    public CountdownServiceBinder(CountdownService countdownService) {

        this.countdownService = countdownService;
    }
}
