package test.study.wzm.xiaomingweather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import test.study.wzm.xiaomingweather.DB.Event;
import test.study.wzm.xiaomingweather.CalendarActivity;
import test.study.wzm.xiaomingweather.R;

public class CalendarEventAdapter extends RecyclerView.Adapter<CalendarEventAdapter.ViewHolder> {

    private List<Event> eventList;
    Context mContext;
    public CalendarEventAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_event_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Event selectedEvent = eventList.get(position);
                CalendarActivity activity = (CalendarActivity)mContext;
                activity.delectSelectedEvent(selectedEvent.getEvent_content());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.today_time.setText(event.getToday_time());
        holder.event_time.setText(event.getEvent_time());
        holder.event_content.setText(event.getEvent_content());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView today_time;
        TextView event_time;
        TextView event_content;
        ImageView delete_item;
        public ViewHolder(View view) {
            super(view);
            today_time = view.findViewById(R.id.today_time);
            event_time = view.findViewById(R.id.event_time);
            event_content = view.findViewById(R.id.event_content);
            delete_item = view.findViewById(R.id.delete_item);
        }
    }
}
