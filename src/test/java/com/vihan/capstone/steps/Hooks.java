package com.vihan.capstone.steps;

import com.vihan.capstone.core.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends BaseTest {
    @Before
    public void before() {
        start();
    }

    @After
    public void after() {
        stop();
    }
}