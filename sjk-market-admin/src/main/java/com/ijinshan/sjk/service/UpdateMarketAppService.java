package com.ijinshan.sjk.service;

import java.io.File;

public interface UpdateMarketAppService {
    File download(String url);

    File decompress(File file);

    void importMarketAppsByFile();
}
