package com.ww.colormvvm.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ww.colormvvm.model.Color;

/**
 * Created by wangwang on 2018/3/22.
 */
@Entity(tableName = "colors")
public class ColorEntity implements Color {
    private int B;
    private int C;
    private int G;
    private int K;
    private int M;
    private int R;
    private int Y;
    private String jpname;
    private String hex;
    @PrimaryKey
    @NonNull
    private String id;
    private String enname;

    public ColorEntity() {
    }

    public ColorEntity(int b, int c, int g, int k, int m, int r, int y, String jpname, String hex, String id, String enname) {
        B = b;
        C = c;
        G = g;
        K = k;
        M = m;
        R = r;
        Y = y;
        this.jpname = jpname;
        this.hex = hex;
        this.id = id;
        this.enname = enname;
    }
public ColorEntity(Color color){
    B = color.getB();
    C = color.getC();
    G = color.getG();
    K = color.getK();
    M = color.getM();
    R = color.getR();
    Y = color.getY();
    this.jpname = color.getJpname();
    this.hex = color.getHex();
    this.id = color.getId();
    this.enname = color.getEnname();
}
    @Override
    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    @Override
    public int getC() {
        return C;
    }

    public void setC(int c) {
        C = c;
    }

    @Override
    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    @Override
    public int getK() {
        return K;
    }

    public void setK(int k) {
        K = k;
    }

    @Override
    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    @Override
    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    @Override
    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    @Override
    public String getJpname() {
        return jpname;
    }

    public void setJpname(String jpname) {
        this.jpname = jpname;
    }

    @Override
    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }
}
