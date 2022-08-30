package com.example.aclass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_view_time_table extends AppCompatActivity implements JsonResponse {

    ListView lv1;
    String[] first_name,last_name,date,starting_hour,ending_hour,teacher_availability_status,val;
    public static String subject_ids,lgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_time_table);
        lv1=(ListView)findViewById(R.id.lvtime);
//        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_time_table.this;
        String q = "/User_view_time_table?subject_ids="+User_view_Subjects.subject_ids;
        q=q.replace(" ","%20");
        JR.execute(q);
    }




    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("User_view_time_table")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    first_name=new String[ja1.length()];
                    last_name=new String[ja1.length()];
                    date=new String[ja1.length()];
                    starting_hour=new String[ja1.length()];
                    ending_hour=new String[ja1.length()];
                    teacher_availability_status=new String[ja1.length()];

                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {

                        first_name[i]=ja1.getJSONObject(i).getString("first_name");
                        last_name[i]=ja1.getJSONObject(i).getString("last_name");
                        date[i]=ja1.getJSONObject(i).getString("date");
                        starting_hour[i]=ja1.getJSONObject(i).getString("starting_hour");
                        ending_hour[i]=ja1.getJSONObject(i).getString("ending_hour");
                        teacher_availability_status[i]=ja1.getJSONObject(i).getString("teacher_availability_status");

//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Teacher Name : "+first_name[i]+ " "+last_name[i] +"\nAvailability Statuss : "+teacher_availability_status[i]+"\nTime : "+starting_hour[i] +" - "+ending_hour[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
                    lv1.setAdapter(ar);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }

//            if(method.equalsIgnoreCase("user_book"))
//            {
//                String status=jo.getString("status");
//                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
//                if(status.equalsIgnoreCase("success"))
//                {
//                    Toast.makeText(getApplicationContext(),"Booking Successfully!", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),"Booking Failed", Toast.LENGTH_LONG).show();
//                }
//            }

        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }
//
//    @Override
//    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//        // TODO Auto-generated method stub
//
//        subject_ids=subject_id[arg2];
//
//        final CharSequence[] items = {"View Time Table","Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_Subjects.this);
//        // builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener()
//        {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (items[item].equals("View Time Table"))
//                {
//                    startActivity(new Intent(getApplicationContext(),User_view_time_table.class));
//                }
//
//
//
//                else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//
//        });
//        builder.show();
////	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        //startActivityForResult(i, GALLERY_CODE);
//    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),User_view_Subjects.class);
        startActivity(b);
    }




}
