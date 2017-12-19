package com.ijinshan.sjk.vo;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TopAppVo  implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String pkname;
    private Date lastUpdateTime;
    
    public TopAppVo() {
        super();
    }
    
    
    public TopAppVo(int id, String name, String pkname) {
        super();
        this.id = id;
        this.name = name;
        this.pkname = pkname;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "Name", nullable = false, length = 80)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "Pkname", nullable = false, length = 200)
    public String getPkname() {
        return pkname;
    }
    public void setPkname(String pkname) {
        this.pkname = pkname;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastUpdateTime", nullable = false, length = 19)
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }


    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    
}
