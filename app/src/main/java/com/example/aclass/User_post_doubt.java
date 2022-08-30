package com.example.aclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_post_doubt extends AppCompatActivity  implements JsonResponse {

    Button b1;
    EditText e1;
    ListView l1;
    public static String complaints;
    public static String[] doubt_id,student_id,doubt,answer,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post_doubt);
        e1=(EditText)findViewById(R.id.etcmp);
        l1=(ListView)findViewById(R.id.lvcmp);
        b1=(Button)findViewById(R.id.btcmp);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                complaints=e1.getText().toString();
                if(complaints.equalsIgnoreCase(""))
                {
                    e1.setError("No value for doubts");
                    e1.setFocusable(true);
                }
                else{
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) User_post_doubt.this;
                    String q = "/User_post_doubt?loginid="+Login.logid+"&complaints="+complaints+"&subject_id="+User_view_Subjects.subject_ids;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }
            }
        });
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_post_doubt.this;
        String q = "/User_view_post_doubt?subject_id="+User_view_Subjects.subject_ids+"&loginid="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
    }


    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String method=jo.getString("method");
            if(method.equalsIgnoreCase("User_post_doubt")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    Toast.makeText(getApplicationContext(), " SENT", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),User_post_doubt.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong!Try Again.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Users_home.class));
                }
            }
            if(method.equalsIgnoreCase("User_view_post_doubt")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    doubt_id=new String[ja1.length()];
                    student_id=new String[ja1.length()];
                    doubt=new String[ja1.length()];
                    answer=new String[ja1.length()];
                    value=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        doubt_id[i]=ja1.getJSONObject(i).getString("doubt_id");
                        student_id[i]=ja1.getJSONObject(i).getString("student_id");
                        doubt[i]=ja1.getJSONObject(i).getString("doubt");
                        answer[i]=ja1.getJSONObject(i).getString("answer");
                        value[i]="Doubt:  "+doubt[i]+"\nAnswer : "+answer[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
                    l1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));
                }

                else

                {
                    Toast.makeText(getApplicationContext(), "No Data!!", Toast.LENGTH_LONG).show();

                }
            }

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(), Users_home.class);
        startActivity(b);
    }

}
