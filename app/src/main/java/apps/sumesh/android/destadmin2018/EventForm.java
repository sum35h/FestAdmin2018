package apps.sumesh.android.destadmin2018;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class EventForm extends AppCompatActivity {


    private DocumentReference mDocRef ;

        private EditText eventName, inputDesc, inputLocation,txtDate, txtTime;
        private TextInputLayout inputLayoutName,    inputLayoutDesc, inputLayoutLocation;
        private Button btnCreateEvent,btnDatePicker, btnTimePicker;
        private Spinner categorySpinner;
    FirebaseFirestore db;
    private int mYear, mMonth, mDay, mHour, mMinute;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_event_form);
            FirebaseApp.initializeApp(this);
               mDocRef= FirebaseFirestore.getInstance().collection("Events").document();
             db = FirebaseFirestore.getInstance();
            inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
            inputLayoutDesc = (TextInputLayout) findViewById(R.id.input_layout_desc);
            inputLayoutLocation = (TextInputLayout) findViewById(R.id.input_layout_location);
            eventName = (EditText) findViewById(R.id.event_name);
            inputDesc = (EditText) findViewById(R.id.input_desc);
            inputLocation = (EditText) findViewById(R.id.input_location);
            btnCreateEvent = (Button) findViewById(R.id.btn_create_event);
            categorySpinner=(Spinner)findViewById(R.id.category_spinner);

            final String[] categories = new String[] { "Music", "Dance", "Gaming" ,"Tech"};

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, categories);

            categorySpinner.setAdapter(adapter);

            categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    Log.v("item", "pos:"+parent.getItemAtPosition(position)+" :"+categories[position]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            });


            eventName.addTextChangedListener(new MyTextWatcher(eventName));
            inputDesc.addTextChangedListener(new MyTextWatcher(inputDesc));
            inputLocation.addTextChangedListener(new MyTextWatcher(inputLocation));


            btnDatePicker=(Button)findViewById(R.id.btn_date);
            btnTimePicker=(Button)findViewById(R.id.btn_time);
            txtDate=(EditText)findViewById(R.id.in_date);
            txtTime=(EditText)findViewById(R.id.in_time);

            btnDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(EventForm.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });

            btnTimePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(EventForm.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    txtTime.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            });







            btnCreateEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitForm();
                }
            });
        }

        /**
         * Validating form
         */
        private void submitForm() {
            if (!validate(eventName)) {
                return;
            }
            if (!validate(inputLocation)) {
                return;
            }
            if (!validate(inputDesc)) {
                return;
            }

             String en=eventName.getText().toString();
            String de=inputDesc.getText().toString();

            String tg=categorySpinner.getSelectedItem().toString();
            String  dt=txtDate.getText().toString();

            String loc=inputLocation.getText().toString();
            String tm=txtTime.getText().toString();
            EventModel event=new EventModel(en,de,tg,dt,loc,tm);
            db.collection("Events").document(en).set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override public void onSuccess(Void aVoid) {
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EventForm.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

            Toast.makeText(getApplicationContext(), "Event Created!"+categorySpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        }

        private boolean validate(TextView tv) {
            TextInputLayout textInputLayout=null;
            switch (tv.getId()) {
                case R.id.event_name:
                    textInputLayout=inputLayoutName;
                    break;
                case R.id.input_location:
                    textInputLayout=inputLayoutLocation;
                    break;
                case R.id.input_desc:
                    textInputLayout=inputLayoutDesc;
                    break;
            }

            if (tv.getText().toString().trim().isEmpty()) {

                textInputLayout.setError("Field cannot be Empty!");
                requestFocus(eventName);
                return false;
            } else {
                textInputLayout.setErrorEnabled(false);
            }

            return true;
        }





        private void requestFocus(View view) {
            if (view.requestFocus()) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }

        private class MyTextWatcher implements TextWatcher {

            private View view;

            private MyTextWatcher(View view) {
                this.view = view;
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                switch (view.getId()) {
                    case R.id.event_name:
                        validate(eventName);
                        break;
                    case R.id.input_location:
                        validate(inputLocation);
                        break;
                    case R.id.input_desc:
                        validate(inputDesc);
                        break;
                }
            }
        }
    
}
