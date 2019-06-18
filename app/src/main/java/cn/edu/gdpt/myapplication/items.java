package cn.edu.gdpt.myapplication;

public class items {
    String layear,lamonth,laday;

    public items(String layear, String lamonth, String laday) {
        this.layear = layear;
        this.lamonth = lamonth;
        this.laday = laday;
    }

    public items() {
    }

    public String getLayear() {
        return layear;
    }

    public void setLayear(String layear) {
        this.layear = layear;
    }

    public String getLamonth() {
        return lamonth;
    }

    public void setLamonth(String lamonth) {
        this.lamonth = lamonth;
    }

    public String getLaday() {
        return laday;
    }

    public void setLaday(String laday) {
        this.laday = laday;
    }
}
