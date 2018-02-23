package apps.sumesh.android.destadmin2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EventsFeedFragment extends Fragment{
    public static EventsFeedFragment newInstance() {
        EventsFeedFragment fragment = new EventsFeedFragment();
        return fragment;
    }


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<EventModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
     private FloatingActionButton fab;
 //   @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        final ArrayList<EventModel> eventsList = new ArrayList<EventModel>();
//        FirebaseFirestore db=FirebaseFirestore.getInstance();
//        db.collection("Events")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d("tg", document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d("tg", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_eeeee, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
         fab=(FloatingActionButton)v.findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i=new Intent(getActivity(),EventForm.class);
                 startActivity(i);
             }
         });

       // layoutManager = new LinearLayoutManager(getContext());
        layoutManager=new GridLayoutManager(getContext(),2);
       recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        data = new ArrayList<EventModel>();

        data.add(new EventModel(
                "Instrumental",
                "wdwdasd sdad asdas dsd lakdj aldjalkdadadwdwdwdwdasdsad sdasd a" ,
                "music"

        ));
        data.add(new EventModel(
                "Dance",
                "wdwdeewdwdwds sdsdssds  sds dsdsds dsddwd",
                "dance"

        ));
       final ArrayList<EventModel> eventsList = new ArrayList<EventModel>();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Events").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot value, FirebaseFirestoreException e) {
                        if(e==null)
                        {
                           eventsList.clear();
                            for(DocumentSnapshot doc :value){

                                EventModel event = doc.toObject(EventModel.class);
                               eventsList.add(event);
                                Log.e("events", "event read" );
                            }
                            adapter = new EventAdapter(eventsList);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });


       // adapter = new EventAdapter(eventsList);
       // recyclerView.setAdapter(adapter);
        return  v;
    }



}