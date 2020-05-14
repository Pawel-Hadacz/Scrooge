package com.scrooge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.widget.TextView;


import com.scrooge.Helper.OnDebtorClick;
import com.scrooge.Helper.DebtorsRVAdapter;
import com.scrooge.Model.Debtor;

import com.scrooge.Helper.DatabaseHelper;
import com.scrooge.Helper.DebtorContract.DebtorColumns;

import java.util.ArrayList;
import java.util.List;


public class DeptorsActivity extends AppCompatActivity  implements OnDebtorClick {


    DatabaseHelper dbHelper = new DatabaseHelper(this);
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView sum;
    private double debtSum = 0;
    private List<Debtor> debtorsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deptors);
        debtorsList = loadDataFromDB();
        recyclerView =  findViewById(R.id.deptorsRVid);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(debtorsList!=null) {
            mAdapter = new DebtorsRVAdapter(debtorsList,this);
            recyclerView.setAdapter(mAdapter);
        }

        sum = findViewById(R.id.textViewDebtSumId);
        sum.setText("All debts: " + debtSum);

        findViewById(R.id.addDeptorBtnId).setOnClickListener(v -> {
            Intent displayList = new Intent(this,AddEditActivity.class);
            finish();
            startActivity(displayList);
        });

    }

    public List loadDataFromDB(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] schema = {
                BaseColumns._ID,
                DebtorColumns.COLUMN_NAME_NAME ,
                DebtorColumns.COLUMN_NAME_DEBT
        };

        Cursor cursor = db.query(
                DebtorColumns.TABLE_NAME,   // The table to query
                schema,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        debtSum = 0;
        List<Debtor> deptorsList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Debtor debtor = new Debtor();
            debtor.setId(cursor.getLong(
                    cursor.getColumnIndexOrThrow(DebtorColumns._ID)));
            debtor.setName(cursor.getString(cursor.getColumnIndexOrThrow(DebtorColumns.COLUMN_NAME_NAME)));
            debtor.setDebt(cursor.getDouble(cursor.getColumnIndexOrThrow(DebtorColumns.COLUMN_NAME_DEBT)));
            debtSum = debtSum + debtor.getDebt();
            deptorsList.add(debtor);
        }
        cursor.close();
        System.out.println(deptorsList);
        return deptorsList;
    }
    @Override
    public void onDebtClick(int position) {
        Intent intent = new Intent(this,AddEditActivity.class);
        intent.putExtra("DEBTOR_TO_EDIT_ID",debtorsList.get(position).getId());
        intent.putExtra("DEBTOR_TO_EDIT_NAME",debtorsList.get(position).getName());
        intent.putExtra("DEBTOR_TO_EDIT_DEBT",debtorsList.get(position).getDebt());
        finish();
        startActivity(intent);

    }

    @Override
    public void onDebtLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton("Delete Debtor", (dialog, id) -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String selection = DebtorColumns._ID + " LIKE " + debtorsList.get(position).getId();
            db.delete(DebtorColumns.TABLE_NAME, selection, null);
            finish();
            startActivity(getIntent());
        });
        builder.setNegativeButton("Leave Debtor", (dialog, id) -> {
            finish();
            startActivity(getIntent());
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
