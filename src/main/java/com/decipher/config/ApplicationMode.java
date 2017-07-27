package com.decipher.config;

/**
 * Created by abhishek on 9/9/16.
 */
public enum ApplicationMode {
    DEVELOPMENT(0), UAT(1),PRODUCTION(2);

    private int mode;

    private ApplicationMode(int mode){
        this.mode = mode;
    }
}