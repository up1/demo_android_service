package workshop.simpleservicedemo;

import android.content.Intent;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeoutException;

import workshop.simpleservicedemo.service.CountdownService;
import workshop.simpleservicedemo.service.CountdownServiceBinder;

import static junit.framework.Assert.assertNotNull;

public class CountdownServiceTest {

    @Rule
    public final ServiceTestRule serviceTestRule = new ServiceTestRule();

    @Test
    public void shouldBoundCountdownService() throws TimeoutException {
        Intent seriveIntent = new Intent(InstrumentationRegistry.getTargetContext(), CountdownService.class);

        IBinder binder = serviceTestRule.bindService(seriveIntent);

        CountdownService countdownService = ((CountdownServiceBinder) binder).getCountdownService();
        assertNotNull(countdownService);
    }

}
