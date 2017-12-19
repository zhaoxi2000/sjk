package com.ijinshan.sjk.service;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Boss {

    boolean canSubmit();

    void submit(Callable<Boolean> task, int index);
}
