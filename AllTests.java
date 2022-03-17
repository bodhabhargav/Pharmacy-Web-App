package com.nttdata.casestudy.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ProductTestDAO.class, OrderTestDAO.class})
public class AllTests {

}
