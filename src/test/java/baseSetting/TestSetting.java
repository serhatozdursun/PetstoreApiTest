package baseSetting;

import org.junit.After;
import org.junit.Before;

public class TestSetting {

    @Before
    public void doSomeThingBeforeTest(){
        System.out.println("do something before test");
    }

    @After
    public void doSomeThingAfterTest(){
        System.out.println("do something after test");
    }
}
