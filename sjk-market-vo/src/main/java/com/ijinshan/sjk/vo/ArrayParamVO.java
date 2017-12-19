package com.ijinshan.sjk.vo;
/**
 * <pre>
 * @author Du WangXi
 * Create on 2013-1-16 下午4:41:01
 * </pre>
 */
public class ArrayParamVO {

    
    public BigGamePack[] bigGamePacks;

    public ArrayParamVO() {
        super();
    }

    public ArrayParamVO(BigGamePack[] bigGamePacks) {
        super();
        this.bigGamePacks = bigGamePacks;
    }

    public BigGamePack[] getBigGamePacks() {
        return bigGamePacks;
    }

    public void setBigGamePacks(BigGamePack[] bigGamePacks) {
        this.bigGamePacks = bigGamePacks;
    }
    
    
}
