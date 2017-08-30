package com.agricultural.domains;

/**
 * Created by Alexey on 01.03.2017.
 */
public enum Month {

    January(0,"Січень"),
    February(1,"Лютий"),
    March(2,"Березень"),
    April(3,"Квітень"),
    May(4,"Травень"),
    June(5,"Червень"),
    July(6,"Липень"),
    August(7,"Серпень"),
    September(8,"Вересень"),
    October(9,"Жовтень"),
    November(10,"Листопад"),
    December(11,"Грудень");

    private int index;
    private String name;


     Month( int index, String name) {
         this.index = index;
         this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
