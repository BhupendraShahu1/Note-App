package com.example.noteapp.AdopterClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.ModelClass.RoomModel;
import com.example.noteapp.R;
import com.example.noteapp.RoomDAta.DataBaseHelper;
import com.example.noteapp.Update2;
import com.example.noteapp.ViewModel.ViewModelClass;

import java.util.ArrayList;

public class AdapterClass2 extends RecyclerView.Adapter<AdapterClass2.itemHolder> {
    ArrayList<RoomModel> arrayList = new ArrayList<>();
    Context context;
    DataBaseHelper dataBaseHelper;


    public AdapterClass2(ArrayList<RoomModel> arrayList, Context context, DataBaseHelper dbHelper) {
        this.arrayList = arrayList;
        this.context = context;
        this.dataBaseHelper = dbHelper;
    }

    @NonNull
    @Override
    public AdapterClass2.itemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item2, parent, false);
        return new itemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass2.itemHolder holder, int position) {
        int position1 = position;
        RoomModel roomModel = this.arrayList.get(position);
        holder.setItem(roomModel);
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteItem(position1);
                return true;
            }
        });
//        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                deleteItem(position1);
//                return true;
//            }
//        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Update2.class);
                intent.putExtra("n", arrayList.get(position1).getTittle());
                intent.putExtra("c", arrayList.get(position1).getContent());
                intent.putExtra("t", arrayList.get(position1).getTime());
                intent.putExtra("id", arrayList.get(position1).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class itemHolder extends RecyclerView.ViewHolder {
        TextView tittle, Content, time;
        LinearLayout linearLayout;

        public itemHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.id_tittle);
            Content = itemView.findViewById(R.id.id_content);
            time = itemView.findViewById(R.id.id_time);
            linearLayout = itemView.findViewById(R.id.id_linearLayout);

        }

        public void setItem(RoomModel roomModel) {
            tittle.setText(roomModel.getTittle());
            Content.setText(roomModel.getContent());
            time.setText(roomModel.getTime());

        }
    }

    public void deleteItem(int itemPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("are you want to delete");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ViewModelClass viewModelClass = new ViewModelClass(((Activity) context).getApplication());
                viewModelClass.delete(new RoomModel(arrayList.get(itemPosition).getId(), arrayList.get(itemPosition).getTittle(), arrayList.get(itemPosition).getContent(), arrayList.get(itemPosition).getTime()));
                arrayList.remove(itemPosition);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.show();
    }
}
