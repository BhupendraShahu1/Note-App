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
import com.example.noteapp.RoomDAta.DBHelper;
import com.example.noteapp.UpdateDel;
import com.example.noteapp.ViewModel.ViewModelClass;

import java.util.ArrayList;

import soup.neumorphism.NeumorphCardView;

public class AdopterCl extends RecyclerView.Adapter<AdopterCl.itemHolder> {
    ArrayList<RoomModel> arrayList = new ArrayList<>();
    Context context;
    DBHelper dbHelper;
    SendData sendData;

    public AdopterCl(ArrayList<RoomModel> arrayList, Context context, DBHelper dbHelper) {
        this.arrayList = arrayList;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public AdopterCl.itemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new itemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdopterCl.itemHolder holder, int position) {
        int p = position;
        RoomModel roomModel = this.arrayList.get(position);
        holder.setItem(roomModel);

        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteItem(p);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDel.class);
                intent.putExtra("n", arrayList.get(p).getTittle());
                intent.putExtra("c", arrayList.get(p).getContent());
                intent.putExtra("t", arrayList.get(p).getTime());
                intent.putExtra("id", arrayList.get(p).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class itemHolder extends RecyclerView.ViewHolder {
        TextView name, about, time;
        NeumorphCardView cardView;
        ConstraintLayout constraintLayout;

        public itemHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            about = itemView.findViewById(R.id.itemAbout);
            time = itemView.findViewById(R.id.itemTime);
            constraintLayout = itemView.findViewById(R.id.cont);
        }

        public void setItem(RoomModel roomModel) {
            name.setText(roomModel.getTittle());
            about.setText(roomModel.getContent());
            time.setText(roomModel.getTime());

        }
    }

    public void deleteItem(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("are you want to delete");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                dbHelper = DBHelper.getDatabase(context);
//                sendData.send(pos);
////                viewModelClass.delete(new RoomModel(arrayList.get(pos).getId(), arrayList.get(pos).getTittle(), arrayList.get(pos).getContent(), arrayList.get(pos).getTime()));
////                repositoryClass = new RepositoryClass((Application) context);
////                repositoryClass.delete(new RoomModel(arrayList.get(pos).getId(), arrayList.get(pos).getTittle(), arrayList.get(pos).getContent(), arrayList.get(pos).getTime()));
                ViewModelClass viewModelClass = new ViewModelClass(((Activity) context).getApplication());
                viewModelClass.delete(new RoomModel(arrayList.get(pos).getId(), arrayList.get(pos).getTittle(), arrayList.get(pos).getContent(), arrayList.get(pos).getTime()));
//                dbHelper.getDAO().deleteText(new RoomModel(arrayList.get(pos).getId(), arrayList.get(pos).getTittle(), arrayList.get(pos).getContent(), arrayList.get(pos).getTime()));
                arrayList.remove(pos);
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

    public interface SendData {
        public void send(int p);
    }
}
