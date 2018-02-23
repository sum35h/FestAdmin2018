package apps.sumesh.android.destadmin2018;


import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    public ArrayList<EventModel> dataSet;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView eventName;
            TextView eventDesc;
            TextView likes;
            TextView tag;
        private CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.eventName = (TextView) itemView.findViewById(R.id.EventName);
            this.eventDesc = (TextView) itemView.findViewById(R.id.EventDesc);
            this.likes = (TextView) itemView.findViewById(R.id.EventCount);
            this.tag=(TextView) itemView.findViewById(R.id.EventTag);
            cardView=(CardView)itemView.findViewById(R.id.card_view);

        }

    }

    public EventAdapter(ArrayList<EventModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card_layout, parent, false);

       // view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {


        TextView en=holder.eventName;
        TextView ed=holder.eventDesc;
        TextView c=holder.likes;
        TextView t=holder.tag;

        en.setText(dataSet.get(listPosition).getName());
        ed.setText(dataSet.get(listPosition).getDescription());
        t.setText(dataSet.get(listPosition).getTag());
      // c.setText(dataSet.get(listPosition).getCount());
        holder.cardView.setTag(listPosition);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Log.d("clicked","clicked :"+v.toString());
               int pos=(int)v.getTag();
               // Toast.makeText(v.getContext(),"clicked item :"+dataSet.get(i).getName(), Toast.LENGTH_SHORT).show();;
                Intent i = new Intent(v.getContext(),EventDetails.class);
                i.putExtra("EventObject",(EventModel)dataSet.get(pos));
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}