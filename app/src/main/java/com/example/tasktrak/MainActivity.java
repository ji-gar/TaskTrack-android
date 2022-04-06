package com.example.tasktrak;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
RecyclerView tasklist;
int Intialvalue,finalvalue;
String taskname;
String emptycheck1,emptycheck2;
String empty="";
Dbhelper dbhelper;
    Myadpter myadpter;
List<model> mylist=new ArrayList<model>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tasklist=(RecyclerView)findViewById(R.id.tasklist);
        myadpter=new Myadpter((ArrayList<model>) mylist,this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
        tasklist.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration=new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL);
        tasklist.addItemDecoration(decoration);
        tasklist.setAdapter(myadpter);
        if(myadpter.getItemCount()==0)
        {
            Toast.makeText(MainActivity.this,"Click on Plus icon to add task",Toast.LENGTH_LONG).show();
        }
        fetch();

    }

    public void addtask(View v)
    {
     View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.addtask,null,false);
        AlertDialog.Builder alertbuilder=new AlertDialog.Builder(MainActivity.this);
        EditText iv,fv,tn;
        Button savetask;
        iv=(EditText)view.findViewById(R.id.Intialvalue);
        fv=(EditText)view.findViewById(R.id.finalvalue);
        tn=(EditText)view.findViewById(R.id.Taskname);
        savetask=(Button)view.findViewById(R.id.button);
        savetask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelper=new Dbhelper(MainActivity.this);
                taskname=tn.getText().toString();
                emptycheck1=iv.getText().toString();
                emptycheck2=fv.getText().toString();
                if(taskname.equals(empty)&&emptycheck1.equals(empty)&&emptycheck2.equals(empty))
                {
                    Toast.makeText(MainActivity.this,"Please Enter the details",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intialvalue=Integer.parseInt(emptycheck1);
                    finalvalue=Integer.parseInt(emptycheck2);
                    if(Intialvalue<=finalvalue)
                    {
                        dbhelper.addData(taskname, Intialvalue, finalvalue);
                        model m2 = new model(taskname, Intialvalue, finalvalue);
                        mylist.add(m2);
                        Toast.makeText(MainActivity.this, "new task added", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Intialvalue must be smaller then final value", Toast.LENGTH_LONG).show();
                    }

                }

                }

        });
        alertbuilder.setView(view);
        AlertDialog alertDialog=alertbuilder.create();
        alertDialog.show();
    }
    public void fetch()
    {
        Cursor cursor=new Dbhelper(MainActivity.this).fetch();
        while (cursor.moveToNext())
        {
            model m1 = new model(cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
            mylist.add(m1);
            myadpter.notifyDataSetChanged();
        }

    }


}