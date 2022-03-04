package com.example.final_project_covid_patient_finder;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomAdapter  extends ArrayAdapter<User> {
    private Activity context;
    private List<User> userList;
    public CustomAdapter( Activity context, List<User> userList) {
        super(context,R.layout.sample_layout,userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.sample_layout,null,true);
        User user=userList.get(position);
        TextView Name = view.findViewById(R.id.nametextviewid_sample_layoutid);
        TextView Nid = view.findViewById(R.id.nidtextviewid_sample_layoutid);
        TextView Mobile = view.findViewById(R.id.mobile_sample_layoutid);
        TextView Birthdate = view.findViewById(R.id.birthdateid_sample_layoutid);
        TextView Corona_status = view.findViewById(R.id.corona_ststustextviewid_sample_layoutid);
        TextView vaccinated = view.findViewById(R.id.vaccinated_ststustextviewid_sample_layoutid);
        TextView Health_status = view.findViewById(R.id.health_ststustextviewid_sample_layoutid);




        Name.setText("Name:"+user.getName());
        Nid.setText("Nid:"+user.getNid());
        Mobile.setText("Mobile:"+user.getMobile());
        Birthdate.setText("Birth Date:"+user.getBirthdate());
       // Corona_status.setText("Corona Status:"+user.getCoronaStatus());
      //  vaccinated.setText("Vaccinated:"+user.getVaccinated_Status());
        Health_status.setText("Health Status:"+user.getHealthStatus());

        String text1=user.getCoronaStatus();

        if( text1.equals("Positive")){
            Corona_status.setTextColor(Color.parseColor("#FF0000"));


        }
        if( text1.equals("Negative")){
            Corona_status.setTextColor(Color.parseColor("#008000"));


        }

        Corona_status.setText("Vaccinated:"+text1);




        String text2=user.getVaccinated_Status();

        if( text2.equals("NO")){
            vaccinated.setTextColor(Color.parseColor("#FF0000"));


        }
        if( text2.equals("YES")){
            vaccinated.setTextColor(Color.parseColor("#008000"));


        }

        vaccinated.setText("Vaccinated:"+text2);



        return view;
    }



}
