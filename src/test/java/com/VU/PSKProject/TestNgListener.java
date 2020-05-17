package com.VU.PSKProject;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNgListener implements ITestListener, IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod arg, ITestResult arg1) {
        System.out.println("Running test: " + arg.getTestMethod().getMethodName() + "...");
        System.out.println("Test description: \"" + arg1.getMethod().getDescription() + "\"");
    }
}
