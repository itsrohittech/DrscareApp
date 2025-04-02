package com.simats.drscareapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final Context context;
    private List<Item> items;

    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = items.get(position);

        holder.textView70.setText(item.getName());
        holder.textView71.setText(String.valueOf(item.getAge()));
        holder.textView72.setText(item.getGender());
        holder.textView74.setText(item.getId());
        holder.imageView.setImageResource(item.getImage());
        String id = item.getId();

        holder.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, java_patient_data.class);
                intent.putExtra("patient_id", id);
                intent.putExtra("patient_name", holder.textView70.getText().toString());
                intent.putExtra("patient_gender", holder.textView72.getText().toString());
                intent.putExtra("patient_age", holder.textView71.getText().toString());
                context.startActivity(intent);
            }
        });

//        holder.button11.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Create an AlertDialog.Builder instance
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                // Set the dialog title and message
//                builder.setTitle("Confirm Delete")
//                        .setMessage("Are you sure you want to delete this item?")
//                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // Perform the delete operation here
//                                deleteItem(position); // Delete item from RecyclerView and database
//                                dialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                // Create and show the AlertDialog
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
    }

    private void deleteItem(int position) {
        if (position < 0 || position >= items.size()) {
            return; // Check for valid position
        }

        Item itemToDelete = items.get(position);
        String patientId = itemToDelete.getId(); // Assuming getId() returns the patient ID
        items.remove(position);
        notifyItemRemoved(position);

        // Perform the database deletion
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(IPv4Connection.getBaseUrl() + "delete_patient.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    String postData = "patient_id=" + URLEncoder.encode(patientId, "UTF-8");

                    try (OutputStream outputStream = connection.getOutputStream()) {
                        outputStream.write(postData.getBytes());
                        outputStream.flush();
                    }

                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Process the response if needed

                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // Update the adapter on successful deletion
                setItems(items);
            }
        }.execute();
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged(); // Notify adapter of data change
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView70, textView71, textView72, textView74;
        Button button8, button11;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView14);
            textView70 = itemView.findViewById(R.id.textView70);
            textView71 = itemView.findViewById(R.id.textView71);
            textView72 = itemView.findViewById(R.id.textView72);
            textView74 = itemView.findViewById(R.id.textView74);
            button8 = itemView.findViewById(R.id.button8);
//            button11 = itemView.findViewById(R.id.button11);
        }
    }
}
