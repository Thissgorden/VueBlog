package com.GDLearn.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Gacha implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private int typ;

    private String itemname;

    private int rare;

    private int islimit;

    private String icon;
}
