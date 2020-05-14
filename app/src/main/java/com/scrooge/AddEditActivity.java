package com.scrooge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.scrooge.Helper.DatabaseHelper;
import com.scrooge.Helper.DebtorContract.DebtorColumns;

import java.util.ArrayList;
import java.util.List;

public class AddEditActivity extends AppCompatActivity {
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    private EditText name,debt;
    private ImageView icon;
    private Long idToEdit;
    private String nameToEdit;
    private double debtToEdit;
    private List<Long> createdDebtors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        name = findViewById(R.id.nameEditTextId);
        debt =  findViewById(R.id.debtEditTextId);
        icon = findViewById(R.id.imageViewId);
        if(getIntent().hasExtra("DEBTOR_TO_EDIT_ID")) {
            idToEdit = getIntent().getLongExtra("DEBTOR_TO_EDIT_ID",0);
            nameToEdit = getIntent().getStringExtra("DEBTOR_TO_EDIT_NAME");
            debtToEdit = getIntent().getDoubleExtra("DEBTOR_TO_EDIT_DEBT",0);
            name.setText(nameToEdit);
            debt.setText(String.valueOf(debtToEdit));
        }

        System.out.println(nameToEdit);
        System.out.println(debtToEdit);
        findViewById(R.id.backToListId).setOnClickListener(v -> {
            Intent displayList = new Intent(this, DeptorsActivity.class);
            startActivity(displayList);
        });
        findViewById(R.id.symulationGoBtnId).setOnClickListener(v -> {
            if(getIntent().hasExtra("DEBTOR_TO_EDIT_ID")) {
                Intent intent = new Intent(this, SimulateActivity.class);
                intent.putExtra("DEBTOR_TO_SIMULATE_ID", idToEdit.longValue());
                intent.putExtra("DEBTOR_TO_SIMULATE_NAME", name.getText().toString());
                intent.putExtra("DEBTOR_TO_SIMULATE_DEBT", Double.valueOf(debt.getText().toString()));
                finish();
                startActivity(intent);
            }else {
                Toast.makeText(this, "You must select Debtor from list!",(int)2000).show();
            }
        });

        findViewById(R.id.backChangesId).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Add the buttons
            builder.setPositiveButton("Confirm back changes", (dialog, id) -> {
                if(validate() && (getIntent().hasExtra("DEBTOR_TO_EDIT_ID"))) {
                    updateDebtor(idToEdit.longValue(),getIntent().getStringExtra("DEBTOR_TO_EDIT_NAME"),getIntent().getDoubleExtra("DEBTOR_TO_EDIT_DEBT",0));
                    Toast.makeText(this, "Update undid!",(int)2000).show();
                }else if(validate()){
                    for(Long l : createdDebtors) {
                        removeDebtor(l);
                    }
                    Toast.makeText(this, "Debtor creation undid!",(int)2000).show();
                }
                finish();
                startActivity(getIntent());
            });
            builder.setNegativeButton("Cancel back changes", (dialog, id) -> {

            });

            AlertDialog dialog = builder.create();
            dialog.show();

        });

        findViewById(R.id.saveBtnId).setOnClickListener(v -> {
            if(validate() && (getIntent().hasExtra("DEBTOR_TO_EDIT_ID"))) {
                updateDebtor(idToEdit.longValue(),name.getText().toString(),Double.valueOf(debt.getText().toString()));
                Toast.makeText(this, "Updated Successfully!",(int)2000).show();

            }else if(validate()){
                addDebtor(name.getText().toString(),Double.valueOf(debt.getText().toString()));
                Toast.makeText(this, "Debtor created successfully!",(int)2000).show();
            }
        });

    }
    public void addDebtor(String name,double debt){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DebtorColumns.COLUMN_NAME_NAME,name);
        values.put(DebtorColumns.COLUMN_NAME_DEBT, debt);

        long newRowId = db.insert(DebtorColumns.TABLE_NAME, null, values);
        createdDebtors.add(newRowId);

    }
    public void updateDebtor(Long id,String name,double debt){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DebtorColumns.COLUMN_NAME_NAME, name);
        values.put(DebtorColumns.COLUMN_NAME_DEBT, debt);

        String selection = DebtorColumns._ID + " LIKE " +id;
        String[] selectionArgs = { "MyOldTitle" };

        int count = db.update(
                DebtorColumns.TABLE_NAME,
                values,
                 selection,
                null);
    }
    public void removeDebtor(Long id){
        // Define 'where' part of query.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = DebtorColumns._ID + " LIKE " +id;

        int deletedRows = db.delete( DebtorColumns.TABLE_NAME, selection, null);
    }

        public boolean validate(){
        String nameToValidate = name.getText().toString().trim();
        double debtToValidate = 0;
       if(debt != null && debt.getText()!=null && debt.getText().length()>0) {
           debtToValidate = Double.parseDouble(debt.getText().toString().trim());
       }
       else {
           debt.setError("Debt field can't be empty and must be number format");
           return false;
       }

        if(nameToValidate.isEmpty()) {
            name.setError("Name field must not be empty!");
            return false;
        }else if(nameToValidate.length()>24){
                name.setError("The name will not contain more than 24 characters");
                return false;
            }
        return true;
        }

}
