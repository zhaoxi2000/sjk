package com.ijinshan.sjk.ntxservice;

import java.util.List;

public interface ListCallback<T> {
    List<T> doIn(List<Integer> ids);
}
