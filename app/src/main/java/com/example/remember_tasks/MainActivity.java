package com.example.remember_tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomSheetDialog mBottomSheetDialog;
    private View bottomSheet;
    private EditText myTaskEditTxt;
    private DatabaseReference mDatabaseReference ,taskDB;
    public  static final  String TaskDBName ="MyTasks";


    /* ___________________________  RecyclerView ____________________________ */
    private RecyclerView taskRecyclerView ;
    private ArrayList<Tasks> taskList ;
    private LinearLayoutManager linearLayoutManager;
    private TaskAdapter taskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomSheet =findViewById(R.id.frameLayout_bottomSheet);
        mBottomSheetDialog= new BottomSheetDialog(this);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        taskDB= mDatabaseReference.child(TaskDBName);

        taskList = new ArrayList<>();



        taskRecyclerView = findViewById(R.id.savedTasks_recyclerView);
        linearLayoutManager =new LinearLayoutManager(getApplicationContext());
        taskAdapter        = new TaskAdapter(getApplicationContext(),taskList);

        taskRecyclerView.setLayoutManager(linearLayoutManager);
        taskRecyclerView.setHasFixedSize(true);
        taskRecyclerView.setItemAnimator(new DefaultItemAnimator());
        taskRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        taskRecyclerView.setAdapter(taskAdapter);



        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayBottomSheet();


                /*__________________________Snackbar _______________________________*/
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });
    }

    private void displayBottomSheet() {
        final View bottomSheetLayout = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog , null);
        Button cancelBtn= bottomSheetLayout.findViewById(R.id.cancel_btn);
        Button addTaskBtn=bottomSheetLayout.findViewById(R.id.addTask_btn);
        myTaskEditTxt =bottomSheetLayout.findViewById(R.id.myTask_editText);


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();

            }
        });

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String myTaskStr = myTaskEditTxt.getText().toString();

                mDatabaseReference.child(TaskDBName).push().child("Content").setValue(myTaskStr);

                Toast.makeText(MainActivity.this, "my Task is : " +myTaskStr, Toast.LENGTH_SHORT).show();

                mBottomSheetDialog.dismiss();
            }
        });

        mBottomSheetDialog.setContentView(bottomSheetLayout);
        mBottomSheetDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();


        taskDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    Tasks taskContent = taskSnapshot.getValue(Tasks.class);

                    taskList.add(taskContent);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
