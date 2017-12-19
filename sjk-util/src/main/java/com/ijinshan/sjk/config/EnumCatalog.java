package com.ijinshan.sjk.config;

public enum EnumCatalog {
    SOFT((short) 1), //
    GAME((short) 2), //
    BIGGAME((short) 100), //
    ;
    private short catalog;

    EnumCatalog(short catalog) {
        this.catalog = catalog;
    }

    public static EnumCatalog get(short catalog) {
        switch (catalog) {
            case 1: {
                return SOFT;
            }
            case 2: {
                return GAME;
            }
            case 100: {
                return BIGGAME;
            }
            default: {
                break;
            }
        }
        throw new IllegalArgumentException();
    }

    public short getCatalog() {
        return catalog;
    }

    public void setCatalog(short catalog) {
        this.catalog = catalog;
    }

}
