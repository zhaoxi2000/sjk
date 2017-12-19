package com.ijinshan.sjk.po;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Tuple;
import redis.clients.util.SafeEncoder;



public class Vapp implements Comparable<Vapp> {
    private static final Logger logger = LoggerFactory.getLogger(Vapp.class);

    private byte[] element;
    private Double score;
    
    
    public byte[] getBinaryElement() {
        return element;
    }

    public void setElement(byte[] element) {
        this.element = element;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if (null != element) {
            for (final byte b : element) {
                result = prime * result + b;
            }
        }
        long temp;
        temp = Double.doubleToLongBits(score);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vapp other = (Vapp) obj;
        if (element == null) {
            if (other.element != null)
                return false;
        } else if (!Arrays.equals(element, other.element))
            return false;
        return true;
    }

    public int compareTo(Vapp other) {
        if (Arrays.equals(this.element, other.element))
            return 0;
        else
            return this.score < other.getScore() ? -1 : 1;
    }
    
    public Vapp(String element, Double score) {
        super();
        this.element = SafeEncoder.encode(element);
        this.score = score;
    }
    
    public String getElement() {
        if (null != element) {
            return SafeEncoder.encode(element);
        } else {
            return null;
        }
    }
    
    public String toString() {
        return '[' + Arrays.toString(element) + ',' + score + ']';
    }
}
