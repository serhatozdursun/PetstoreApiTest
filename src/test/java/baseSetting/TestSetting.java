package baseSetting;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestSetting {

    @BeforeEach
    public void doSomeThingBeforeTest(){
        System.out.println("do something before test");
    }

    @AfterEach
    public void doSomeThingAfterTest(){
        System.out.println("do something after test");
    }
}
