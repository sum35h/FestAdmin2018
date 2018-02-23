package apps.sumesh.android.destadmin2018;

import java.io.Serializable;

public class EventModel implements Serializable {

    String name;
    String description;
    String tag;
    String date;
    String location;
    String time;

    int count;
     public EventModel()
     {

     }
    public EventModel(String name, String desc, String tag) {
        this.name = name;
        this.description=desc;
        this.tag=tag;
        count=0;
    }

    public EventModel(String name, String description, String tag, String date, String location, String time) {
        this.name = name;
        this.description = description;
        this.tag = tag;
        this.date = date;
        this.location = location;
        this.time = time;
        count=0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}