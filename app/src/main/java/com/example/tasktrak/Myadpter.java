package com.example.tasktrak;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class Myadpter extends RecyclerView.Adapter<Myadpter.Myview> {
   @NonNull
   ArrayList<model> models;
   Context context;
   Dbhelper dbhelper;
   Myadpter adpter;
   public static int count=0;
    public static int mincount=0;
   public  String titlename="";
   int maxcount;

   public Myadpter(@NonNull ArrayList<model> models,Context context) {
      this.models = models;
      this.context=context;
   }

   @Override
   public Myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v= LayoutInflater.from(context).inflate(R.layout.simplerow,null);
      return new Myview(v);
   }

   @Override
   public void onBindViewHolder(@NonNull Myview holder, int position) {
      holder.tv.setText(models.get(position).getTitle());
      holder.progressBar.setProgress(models.get(position).getMincount());
      holder.progressBar.setMax(models.get(position).getMaxcount());
      holder.number.setText(models.get(position).getMincount()+"/"+models.get(position).getMaxcount());
      holder.rv.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             count=models.get(position).getMincount();
             titlename=models.get(position).getTitle();
             maxcount=models.get(position).getMaxcount();
             titlename=models.get(position).getTitle();
            View view1=LayoutInflater.from(context).inflate(R.layout.buttons,null);
          AlertDialog.Builder alertbuilder=new AlertDialog.Builder(context);
            FloatingActionButton add,delete,remove;
            add=(FloatingActionButton)view1.findViewById(R.id.plus);
            remove=(FloatingActionButton)view1.findViewById(R.id.minus);
            delete=(FloatingActionButton)view1.findViewById(R.id.delete);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                   if(count<maxcount)
                   {
                       count=count+1;
                       holder.number.setText(count+"/"+maxcount);
                       holder.progressBar.setProgress(count);
                       holder.progressBar.setMax(maxcount);
                       dbhelper =new Dbhelper(context);
                       boolean i=dbhelper.update(titlename,count);

                   }
                   else {
                       Toast.makeText(context,"Task successfully completed ",Toast.LENGTH_SHORT).show();

                   }
                }
            });
             remove.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if(count>0)
                     {
                         count=count-1;
                         holder.number.setText(count+"/"+maxcount);
                         holder.progressBar.setProgress(count);
                         holder.progressBar.setMax(maxcount);
                         dbhelper =new Dbhelper(context);
                         boolean i=dbhelper.update(titlename,count);
                        //notifyDataSetChanged();

                     }
                     else {
                         Toast.makeText(context,"No more task",Toast.LENGTH_SHORT).show();
                     }
                 }
             });
             delete.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     dbhelper =new Dbhelper(context);
                     Boolean i=dbhelper.delete(titlename);
                     Toast.makeText(context,"Task deleted sucessfully",Toast.LENGTH_SHORT).show();
                     models.remove(position);
                     notifyItemRemoved(position);
                 }
             });
            alertbuilder.setView(view1);
            AlertDialog alertDialog=alertbuilder.create();
            alertDialog.show();

         }
      });

   }

   @Override
   public int getItemCount() {
      return models.size();
   }
   class  Myview extends RecyclerView.ViewHolder
   {
      TextView tv;
      TextView number;
      ProgressBar progressBar;
      RelativeLayout rv;

      public Myview(@NonNull View itemView) {
         super(itemView);
        tv=(TextView)itemView.findViewById(R.id.taskname);
        number=(TextView)itemView.findViewById(R.id.taskcomplated);
        progressBar=(ProgressBar)itemView.findViewById(R.id.progressBar);
        rv=(RelativeLayout)itemView.findViewById(R.id.holder);
      }
   }

}
