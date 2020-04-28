package com.dicycat.kroy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        FireStationTest.class,
        FireTruckTest.class,
        FortressTest.class,
        GooseTest.class,
        EntityTest.class,
        GameObjectTest.class,
        PipeTest.class,
        UFOTest.class
})

public class AllTests {
}