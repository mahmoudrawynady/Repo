package com.example.mahmoudrawy.repo.Views;

/**
 * Created by mahmoud rawy™ 01221240053 on 09/01/2018.
 */
/*
this is a class for list item of the dialog that will appear to user to show html url
 */

public class URLItem {
    private String itemLabel;
    private int itemIcon;

    public URLItem(String itemLabel, int itemIcon) {
        this.itemLabel = itemLabel;
        this.itemIcon = itemIcon;
    }

    public void setItemIcon(int itemIcon) {
        this.itemIcon = itemIcon;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public int getItemIcon() {
        return itemIcon;
    }

    public String getItemLabel() {
        return itemLabel;
    }
}
