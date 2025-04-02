package com.simats.drscareapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameView, ageView, genderView, idView;
    Button viewButton, deleteButton;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView14);
        nameView = itemView.findViewById(R.id.textView70);
        ageView = itemView.findViewById(R.id.textView71);
        genderView = itemView.findViewById(R.id.textView72);
        idView = itemView.findViewById(R.id.textView74);
        viewButton = itemView.findViewById(R.id.button8);
//        deleteButton = itemView.findViewById(R.id.button11);
    }

    public void bind(Item item) {
        nameView.setText(item.getName());
        ageView.setText(String.valueOf(item.getAge()));
        genderView.setText(item.getGender());
        idView.setText(item.getId());
        imageView.setImageResource(item.getImage());

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle view button click
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog.Builder instance
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());

                // Set the dialog title and message
                builder.setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Delete the item or perform delete operation
                                dialog.dismiss(); // Close the dialog
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss(); // Close the dialog
                            }
                        });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}


