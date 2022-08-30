package com.example.aclass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_view_department extends AppCompatActivity  implements JsonResponse, AdapterView.OnItemClickListener {


    ListView lv1;
    String[] course_id,department_id,department_name,department_description,course_name,duration,description,val;
    public static String course_ids,lgs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_department);
        lv1=(ListView)findViewById(R.id.lvdepartments);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_department.this;
        String q = "/User_view_department?login_id="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
    }




    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("User_view_department")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    course_id=new String[ja1.length()];
                    department_id=new String[ja1.length()];

                    department_name=new String[ja1.length()];
                    department_description=new String[ja1.length()];
                    course_name=new String[ja1.length()];
                    duration=new String[ja1.length()];
                    description=new String[ja1.length()];

                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {

                        course_id[i]=ja1.getJSONObject(i).getString("course_id");
                        department_id[i]=ja1.getJSONObject(i).getString("department_id");

                        department_name[i]=ja1.getJSONObject(i).getString("department_name");
                        department_description[i]=ja1.getJSONObject(i).getString("department_description");
                        course_name[i]=ja1.getJSONObject(i).getString("course_name");
                        duration[i]=ja1.getJSONObject(i).getString("duration");
                        description[i]=ja1.getJSONObject(i).getString("description");

//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Department Name : "+department_name[i]+"\nDepartment Description : "+department_description[i]+"\nCourse Name : "+course_name[i]+"\nDuration : "+duration[i]+"\nDescription : "+description[i];


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

        course_ids=course_id[arg2];

        final CharSequence[] items = {"View Subjects","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_department.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Subjects"))
                {
                    startActivity(new Intent(getApplicationContext(),User_view_Subjects.class));
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
        Intent b=new Intent(getApplicationContext(),Users_home.class);
        startActivity(b);
    }




}
