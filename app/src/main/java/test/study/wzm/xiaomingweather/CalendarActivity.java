package test.study.wzm.xiaomingweather;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.DateTimePicker;
import test.study.wzm.xiaomingweather.Adapter.CalendarEventAdapter;
import test.study.wzm.xiaomingweather.DB.Event;

public class CalendarActivity extends AppCompatActivity {


//

    int github;
    CalendarDay currentDate;
    MaterialCalendarView calendarView;
    DateTimePicker picker;
    TextView selectTime;
    String selectedTime_year_month_day;
    String selectedTime_HourMin;
    private List<Event> eventList = new ArrayList<>();
    RecyclerView recyclerView;
    CalendarEventAdapter eventAdapter;
    String ed_content;
    AlertDialog.Builder builder;
    View add_event_view;
    AlertDialog dialog;
    EditText event_content;
    Button ok;
    Button cancel;
    CalendarDay currentCalendarDay;
    TextView span_year;
    TextView span_month_day_week;
    String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六",};
    Calendar calendar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
//        LitePal.getDatabase();
        //initEventList();
        initRecyclerView();
        initToolBar();
        initCalendar();
        initDialog();
        initPicker();
        searchTodayEvent(currentDate);
    }

    private void initCalendar() {
        calendarView = findViewById(R.id.calendar_view);
        Date date = new Date(System.currentTimeMillis());
        currentCalendarDay = CalendarDay.from(date);
        calendarView.setSelectedDate(currentCalendarDay);
        currentDate = currentCalendarDay;

        //初始化当天的时间,并判断今天 是否少于10号,因为十号以下的是 01 02 03 04 05 06 07 08 09 显示,所以我们拼接字符串要在前面加 0
        judgeDayLessThanTenSize();
        //设置日历显示的开始天,月显示还是周显示,最晚的日期MinimumDate 和 最早的日期
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .setMinimumDate(CalendarDay.from(currentCalendarDay.getYear() - 2, currentCalendarDay.getMonth(), 1))  //当年的前一年为日历的开始时间
                .setMaximumDate(CalendarDay.from(currentCalendarDay.getYear() + 2, currentCalendarDay.getMonth(), 31)) //当年的后一年为日历的结束时间
                .commit();


        calendarView.setAllowClickDaysOutsideCurrentMonth(false);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                currentDate = date;
                currentCalendarDay = date;

                /*  这段代码是点击日期,然后跳出增加日历安排对话框
                selectedTime_year_month_day = date.getYear() + "年" + date.getMonth() + "月" + date.getDay();
                span_year.setText(currentCalendarDay.getYear() + "");//直接设置getYear会报错,加上""就不会报错了,why?虽然getYear返回的是int类型
                span_month_day_week.setText(currentCalendarDay.getMonth() + 1 + "月" + currentCalendarDay.getDay() + " " + weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                if (dialog == null) {
                    dialog = builder.show();
                } else {
                    dialog.show();
                }
                picker.show();
                */
                //初始化选择的时间
                judgeDayLessThanTenSize();
                searchTodayEvent(date);

            }
        });
        //设置周的文本
        calendarView.setWeekDayLabels(new String[]{"日", "一", "二", "三", "四", "五", "六"});
        //设置年月的title
        calendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                StringBuffer buffer = new StringBuffer();
                int yearOne = day.getYear();
                int monthOne = day.getMonth() + 1;
                buffer.append(yearOne).append("年").append(monthOne).append("月");
                return buffer;
            }
        });
        calendar = Calendar.getInstance();
        calendar.set(currentCalendarDay.getYear(), currentCalendarDay.getMonth(), currentCalendarDay.getDay());
//        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
    }


    private void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("日程");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_action_name);
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        eventAdapter = new CalendarEventAdapter(eventList, CalendarActivity.this);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setFocusableInTouchMode(false);
    }

    private void initPicker() {
        picker = new DateTimePicker(this, DateTimePicker.HOUR_24);//24小时值
        picker.setDateRangeStart(2017, 1, 1);//日期起点
        picker.setDateRangeEnd(2020, 1, 1);//日期终点
        picker.setTimeRangeStart(0, 0);//时间范围起点
        picker.setTimeRangeEnd(23, 59);//时间范围终点
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                //year:年，month:月，day:日，hour:时，minute:分
//                Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day + " "
//                        + hour + ":" + minute, Toast.LENGTH_LONG).show();

                    //picker 自己的日期格式就是 YYYY:mm:dd
                    selectedTime_year_month_day = year + "年" + month + "月" + day;

//                Toast.makeText(getApplicationContext(), selectedTime_year_month_day, Toast.LENGTH_LONG).show();

                selectedTime_HourMin = hour + ":" + minute;
                span_year.setText(year + "                       " + hour + ":" + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
//                calendar1.setFirstDayOfWeek(Calendar.MONDAY);
                span_month_day_week.setText(month + "月" + day + " " + weekDays[calendar1.get(Calendar.DAY_OF_WEEK) - 1]);
                ;
            }
        });
    }

    private void initEventList() {
        for (int i = 0; i < 5; i++) {
            Event a = new Event();
            a.setEvent_time("上午");
            a.setToday_time("20181122");
            a.setEvent_content("上課上課上課上課上課上課上課上課");
            eventList.add(a);
        }
    }

    private void showCurrentTime() {
        Date date2 = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
        String format = sdf.format(date2);

        CalendarDay current = calendarView.getCurrentDate();
        int year = current.getYear();
        int month = current.getMonth();
        int day = current.getDay();
        Toast.makeText(this, format + "奇怪了？这里的时间和日历的不一样  " + year + "年" + month + "月" + day + "日", Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取点击后的日期数
     */

    public void getTime(View view) {
        if (currentDate != null) {
            int year = currentDate.getYear();
            int month = currentDate.getMonth() + 1; //月份跟系统一样是从0开始的，实际获取时要加1  } }
            int day = currentDate.getDay();
            Toast.makeText(this, currentDate.toString() + "你选中的是：" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "请选择时间", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "you clicked back", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.add_event:
                span_year.setText(currentCalendarDay.getYear() + "");//直接设置getYear会报错,加上""就不会报错了,why?虽然getYear返回的是int类型
                span_month_day_week.setText(currentCalendarDay.getMonth() + 1 + "月" + currentCalendarDay.getDay() + " " + weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                if (dialog == null) {
                    dialog = builder.show();
                } else {
                    dialog.show();
                }
//                picker.show();
                break;
            case R.id.delete_All:
                delectAllOfToday(currentDate);
                break;
            case R.id.today_event:
                showToday();
                searchTodayEvent(currentDate);
                break;
        }
        return true;
    }

    private void initDialog() {
//        Toast.makeText(this, "you clicked add event", Toast.LENGTH_SHORT).show();
        builder = new AlertDialog.Builder(this);
        add_event_view = getLayoutInflater().inflate(R.layout.add_event, null);
        builder.setView(add_event_view);
        selectTime = add_event_view.findViewById(R.id.select_time);
        event_content = add_event_view.findViewById(R.id.add_event_content);
        ok = add_event_view.findViewById(R.id.btn_ok);
        cancel = add_event_view.findViewById(R.id.btn_cancel);
        span_year = add_event_view.findViewById(R.id.year);
        span_month_day_week = add_event_view.findViewById(R.id.month_day_week);
        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ed_content = event_content.getText().toString().trim();
                if (ed_content.equals("")) {
                    Toast.makeText(CalendarActivity.this, "输入的内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Event event = new Event();
                event.setEvent_content(ed_content);
                event.setToday_time(selectedTime_year_month_day);
                event.setEvent_time(selectedTime_HourMin);
                event.save();
//                eventList.add(event);
                searchTodayEvent(currentCalendarDay);
                event_content.setText("");
                dialog.dismiss();
            }
        });
        //
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 查找今天的所有计划安排,显示在recyclerview中
     *
     * @param date
     */
    private void searchTodayEvent(CalendarDay date) {
        Date currentDate = date.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd");
        String targetTime = sdf.format(currentDate);
        List<Event> events = DataSupport.where("today_time = ?", targetTime).find(Event.class);
        eventList.clear();
        eventList.addAll(events);

        for (Event event : eventList) {
            Log.d("searchTodayEvent", event.getToday_time() + "--" + event.getEvent_time() + "--" + event.getEvent_content());
        }
        eventAdapter.notifyDataSetChanged();
    }

    /**
     * 删除选择的计划
     *
     * @param target_content 通过item的内容,去数据库查找删除
     */
    public void delectSelectedEvent(String target_content) {
        Toast.makeText(CalendarActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
        DataSupport.deleteAll(Event.class, "event_content = ?", target_content);
        searchTodayEvent(currentDate);
        eventAdapter.notifyDataSetChanged();

    }

    /**
     * 删除今天的所有计划安排
     *
     * @param date 根据传入的某一天删除某一天的计划
     */
    private void delectAllOfToday(CalendarDay date) {
        Toast.makeText(CalendarActivity.this, "删除当天所有日程安排", Toast.LENGTH_SHORT).show();
        Date currentDate = date.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd");
        String targetTime = sdf.format(currentDate);
        DataSupport.deleteAll(Event.class, "today_time = ?", targetTime);
        eventList.clear();
        eventAdapter.notifyDataSetChanged();
    }

    /*
        点击toorbar上的日历meun回到今天的日期
     */
    private void showToday() {
        Date date = new Date(System.currentTimeMillis());
        currentCalendarDay = CalendarDay.from(date);
        calendarView.setSelectedDate(currentCalendarDay);
        currentDate = currentCalendarDay;
    }

    /**
     *     并判断今天 是否少于10号,因为十号以下的是 01 02 03 04 05 06 07 08 09 显示,所以我们拼接字符串要在前面加 0
     *     再设置今天的时间
     */
    private void judgeDayLessThanTenSize() {
        if (currentCalendarDay.getDay() < 10) {
            selectedTime_year_month_day = currentCalendarDay.getYear() + "年" + (currentCalendarDay.getMonth() + 1) + "月0" + currentCalendarDay.getDay();
        } else {
            selectedTime_year_month_day = currentCalendarDay.getYear() + "年" + (currentCalendarDay.getMonth() + 1) + "月" + currentCalendarDay.getDay();
        }
        Toast.makeText(getApplicationContext(), selectedTime_year_month_day, Toast.LENGTH_LONG).show();

    }
}
