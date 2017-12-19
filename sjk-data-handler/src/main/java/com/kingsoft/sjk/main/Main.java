package com.kingsoft.sjk.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kingsoft.sjk.game.service.AppDaoService;
import com.kingsoft.sjk.game.vo.GameInfo;
import com.kingsoft.sjk.util.ExcelUtils;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        logger.debug("Begin process  info ...");
        long start = System.currentTimeMillis();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("./context.xml");
        AppDaoService appDaoService = ctx.getBean("appDaoService", AppDaoService.class);

        String fileName = "D:/svn/trunk/sjk/sjk-data-handler/src/main/resources/newContent2.xls";

        try {
            String[][] arr = ExcelUtils.getData(new File(fileName));

            List<GameInfo> list = new ArrayList<GameInfo>();
            GameInfo gameinfo = null;
            for (String[] obj : arr) {
                gameinfo = new GameInfo();
                gameinfo.setId(Integer.valueOf(obj[0]));
                gameinfo.setCatalogName(obj[1]);
                gameinfo.setFolder(obj[2]);
                gameinfo.setGameName(obj[3]);
                gameinfo.setGameVersion(obj[4]);
                gameinfo.setAndroidVersion(obj[5]);
                gameinfo.setLanguage(obj[6]);
                gameinfo.setGameWebPage(obj[7]);
                gameinfo.setGameDesc(obj[8]);
                gameinfo.setGameNote(obj[9]);
                gameinfo.setLogo(obj[10]);
                gameinfo.setSceern(obj[11]);
                gameinfo.setPageUrl(obj[12]);
                logger.debug("catalog:{},folder:{}", obj[1], obj[2]);
                list.add(gameinfo);
            }

            StringBuilder sb = new StringBuilder();
            for (GameInfo o : list) {
                int id = appDaoService.saveAppAndGenerateInfo(o);
                sb.append(id).append(File.separator).append(o.getFolder()).append("\r\n");
            }
            File file = new File("/data/apps/static-web/sjk/app/market/result.txt");

            FileUtils.writeStringToFile(file, sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        logger.debug("end.cost times:{}s", (end - start) / 1000);
    }
}
