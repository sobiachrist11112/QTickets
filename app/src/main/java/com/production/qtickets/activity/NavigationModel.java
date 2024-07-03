package com.production.qtickets.activity;


public class NavigationModel {
    //in this model all the items of the navigation list items based on the user status
    public NavigationModel(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String icon;

}
