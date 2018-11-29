package test.study.wzm.xiaomingweather.DB;

import org.litepal.crud.DataSupport;

public class Event extends DataSupport{
    private String today_time;
    private String event_time;
    private String event_content;

    public String getToday_time() {
        return today_time;
    }

    public void setToday_time(String today_time) {
        this.today_time = today_time;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_content() {
        return event_content;
    }

    public void setEvent_content(String event_content) {
        this.event_content = event_content;
    }
}
