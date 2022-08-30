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

public class User_view_Subjects extends AppCompatActivity  implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv1;
    String[] subject_id,course_id,subject_name,total_hours,subject_description,val;
    public static String subject_ids,lgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_subjects);
        lv1=(ListView)findViewById(R.id.lvsub);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_Subjects.this;
        String q = "/User_view_Subjects?course_ids="+User_view_department.course_ids;
        q=q.replace(" ","%20");
        JR.execute(q);
    }




    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("User_view_Subjects")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    course_id=new String[ja1.length()];
                    subject_id=new String[ja1.length()];

                    subject_name=new String[ja1.length()];
                    total_hours=new String[ja1.length()];
                    subject_description=new String[ja1.length()];

                    val=new String[ja1.length()];


                    for(int i = 0;i<ja1.length();i++)
                    {

                        course_id[i]=ja1.getJSONObject(i).getString("course_id");
                        subject_id[i]=ja1.getJSONObject(i).getString("subject_id");

                        subject_name[i]=ja1.getJSONObject(i).getString("subject_name");
                        total_hours[i]=ja1.getJSONObject(i).getString("total_hours");
                        subject_description[i]=ja1.getJSONObject(i).getString("subject_description");

//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Subject Name : "+subject_name[i]+"\nTotal Hours : "+total_hours[i]+"\nDescription : "+subject_description[i];


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

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

        subject_ids=subject_id[arg2];

        final CharSequence[] items = {"View Time Table","View Notes","Add Doubt","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_Subjects.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Time Table"))
                {
                    startActivity(new Intent(getApplicationContext(),User_view_time_table.class));
                }
                else  if (items[item].equals("View Notes"))
                {
                    startActivity(new Intent(getApplicationContext(),User_view_post.class));
                }

                else  if (items[item].equals("Add Doubt"))
                {
                    startActivity(new Intent(getApplicationContext(),User_post_doubt.class));
                }



                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(i, GALLERY_CODE);
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),User_view_department.class);
        startActivity(b);
    }




}
