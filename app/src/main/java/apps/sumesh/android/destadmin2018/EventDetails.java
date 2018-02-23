package apps.sumesh.android.destadmin2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details_layout);


        Intent i=getIntent();
        EventModel event=(EventModel)i.getSerializableExtra("EventObject");

        TextView eName,eDesc,eLoc,eDate,eCount,eTag;
        eName=(TextView)findViewById(R.id.event_name);
        eDesc=(TextView)findViewById(R.id.event_desc);
        eLoc=(TextView)findViewById(R.id.event_loc);
        eDate=(TextView)findViewById(R.id.event_date);
        eTag=(TextView)findViewById(R.id.event_tag);
        eCount=(TextView)findViewById(R.id.event_count) ;

        eName.setText(event.getName());
        eDesc.setText(event.getDescription());
        eLoc.setText(event.getLocation());
        eTag.setText(event.getTag());
        eDate.setText(event.getDate());
        eCount.setText(""+event.getCount());





    }

}
