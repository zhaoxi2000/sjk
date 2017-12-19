package com.kingsoft.sjk.game.vo;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameInfo implements Serializable {
    private static final long serialVersionUID = 745078090870268255L;

    private static final Logger logger = LoggerFactory.getLogger(GameInfo.class);

    private int id;
    private String catalogName;
    private String folder;
    private String gameName;
    private String gameVersion;
    private String androidVersion;
    private String language;
    private String gameWebPage;
    private String gameDesc;

    public GameInfo() {
        super();
    }

    public GameInfo(int id, String catalogName, String folder, String gameName, String gameVersion,
            String androidVersion, String language, String gameWebPage, String gameDesc, String gameNote, String logo,
            String sceern, String pageUrl) {
        super();
        this.id = id;
        this.catalogName = catalogName;
        this.folder = folder;
        this.gameName = gameName;
        this.gameVersion = gameVersion;
        this.androidVersion = androidVersion;
        this.language = language;
        this.gameWebPage = gameWebPage;
        this.gameDesc = gameDesc;
        this.gameNote = gameNote;
        this.logo = logo;
        this.sceern = sceern;
        this.pageUrl = pageUrl;
    }

    private String gameNote;
    private String logo;
    private String sceern;
    private String pageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGameWebPage() {
        return gameWebPage;
    }

    public void setGameWebPage(String gameWebPage) {
        this.gameWebPage = gameWebPage;
    }

    public String getGameDesc() {
        return gameDesc;
    }

    public void setGameDesc(String gameDesc) {
        this.gameDesc = gameDesc;
    }

    public String getGameNote() {
        return gameNote;
    }

    public void setGameNote(String gameNote) {
        this.gameNote = gameNote;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSceern() {
        return sceern;
    }

    public void setSceern(String sceern) {
        this.sceern = sceern;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((androidVersion == null) ? 0 : androidVersion.hashCode());
        result = prime * result + ((catalogName == null) ? 0 : catalogName.hashCode());
        result = prime * result + ((folder == null) ? 0 : folder.hashCode());
        result = prime * result + ((gameDesc == null) ? 0 : gameDesc.hashCode());
        result = prime * result + ((gameName == null) ? 0 : gameName.hashCode());
        result = prime * result + ((gameNote == null) ? 0 : gameNote.hashCode());
        result = prime * result + ((gameVersion == null) ? 0 : gameVersion.hashCode());
        result = prime * result + ((gameWebPage == null) ? 0 : gameWebPage.hashCode());
        result = prime * result + id;
        result = prime * result + ((language == null) ? 0 : language.hashCode());
        result = prime * result + ((logo == null) ? 0 : logo.hashCode());
        result = prime * result + ((pageUrl == null) ? 0 : pageUrl.hashCode());
        result = prime * result + ((sceern == null) ? 0 : sceern.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GameInfo other = (GameInfo) obj;
        if (androidVersion == null) {
            if (other.androidVersion != null)
                return false;
        } else if (!androidVersion.equals(other.androidVersion))
            return false;
        if (catalogName == null) {
            if (other.catalogName != null)
                return false;
        } else if (!catalogName.equals(other.catalogName))
            return false;
        if (folder == null) {
            if (other.folder != null)
                return false;
        } else if (!folder.equals(other.folder))
            return false;
        if (gameDesc == null) {
            if (other.gameDesc != null)
                return false;
        } else if (!gameDesc.equals(other.gameDesc))
            return false;
        if (gameName == null) {
            if (other.gameName != null)
                return false;
        } else if (!gameName.equals(other.gameName))
            return false;
        if (gameNote == null) {
            if (other.gameNote != null)
                return false;
        } else if (!gameNote.equals(other.gameNote))
            return false;
        if (gameVersion == null) {
            if (other.gameVersion != null)
                return false;
        } else if (!gameVersion.equals(other.gameVersion))
            return false;
        if (gameWebPage == null) {
            if (other.gameWebPage != null)
                return false;
        } else if (!gameWebPage.equals(other.gameWebPage))
            return false;
        if (id != other.id)
            return false;
        if (language == null) {
            if (other.language != null)
                return false;
        } else if (!language.equals(other.language))
            return false;
        if (logo == null) {
            if (other.logo != null)
                return false;
        } else if (!logo.equals(other.logo))
            return false;
        if (pageUrl == null) {
            if (other.pageUrl != null)
                return false;
        } else if (!pageUrl.equals(other.pageUrl))
            return false;
        if (sceern == null) {
            if (other.sceern != null)
                return false;
        } else if (!sceern.equals(other.sceern))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "GameInfo [id=" + id + ", catalogName=" + catalogName + ", folder=" + folder + ", gameName=" + gameName
                + ", gameVersion=" + gameVersion + ", androidVersion=" + androidVersion + ", language=" + language
                + ", gameWebPage=" + gameWebPage + ", gameDesc=" + gameDesc + ", gameNote=" + gameNote + ", logo="
                + logo + ", sceern=" + sceern + ", pageUrl=" + pageUrl + "]";
    }

}
