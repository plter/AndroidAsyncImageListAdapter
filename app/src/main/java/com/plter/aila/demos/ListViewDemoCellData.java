package com.plter.aila.demos;

import org.json.JSONObject;

/**
 * Created by plter on 12/16/15.
 */
public class ListViewDemoCellData {

    private String icon;
    private String name;

    public ListViewDemoCellData(JSONObject jo) {
        this.icon = String.format("%s/%s", Config.BASE_URL, jo.optString("icon"));
        this.name = jo.optString("name");
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}
