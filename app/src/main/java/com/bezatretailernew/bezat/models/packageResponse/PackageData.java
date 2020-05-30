package com.bezatretailernew.bezat.models.packageResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageData {

    @SerializedName("package_name")
    @Expose
    private String package_name;

    @SerializedName("package_name_ar")
    @Expose
    private String package_name_ar;

    @SerializedName("no_of_raffles")
    @Expose
    private String no_of_raffles;

    @SerializedName("pkg_img")
    @Expose
    private String pkg_img;

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_name_ar() {
        return package_name_ar;
    }

    public void setPackage_name_ar(String package_name_ar) {
        this.package_name_ar = package_name_ar;
    }

    public String getNo_of_raffles() {
        return no_of_raffles;
    }

    public void setNo_of_raffles(String no_of_raffles) {
        this.no_of_raffles = no_of_raffles;
    }

    public String getPkg_img() {
        return pkg_img;
    }

    public void setPkg_img(String pkg_img) {
        this.pkg_img = pkg_img;
    }
}
