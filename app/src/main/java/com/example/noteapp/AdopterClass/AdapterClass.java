package com.example.noteapp.AdopterClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.ModelClass.RoomModel;
import com.example.noteapp.R;
import com.example.noteapp.RoomDAta.DataBaseHelper;
import com.example.noteapp.UpdateDel;
import com.example.noteapp.ViewModel.ViewModelClass;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.itemHolder> {
    ArrayList<RoomModel> arrayList = new ArrayList<>();
    Context context;
    DataBaseHelper dataBaseHelper;


    public AdapterClass(ArrayList<RoomModel> arrayList, Context context, DataBaseHelper dbHelper) {
        this.arrayList = arrayList;
        this.context = context;
        this.dataBaseHelper = dbHelper;
    }
    @NonNull
    @Override
    public AdapterClass.itemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new itemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.itemHolder holder, int position) {
        int position1 = position;
        RoomModel roomModel = this.arrayList.get(position);
        holder.setItem(roomModel);
        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteItem(position1);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDel.class);
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
        ConstraintLayout constraintLayout;

        public itemHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.item_tittle);
            Content = itemView.findViewById(R.id.item_content);
            time = itemView.findViewById(R.id.item_time);
            constraintLayout = itemView.findViewById(R.id.item_constraint_layout);

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
