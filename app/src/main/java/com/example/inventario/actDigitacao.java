package com.example.inventario;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View.OnClickListener;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class actDigitacao extends AppCompatActivity {
    Button btnScan;
    EditText edtQtd;
    Button btnprocura;
    EditText edtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digitacao);
        btnScan = (Button) findViewById(R.id.btnScan);
        btnprocura=(Button) findViewById(R.id.btnprocura);
        edtResultado=(EditText)findViewById(R.id.edtResultado);
        final Activity activity = this;

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(integrator.QR_CODE_TYPES);
                integrator.setDesiredBarcodeFormats(integrator.ONE_D_CODE_TYPES);
                //integrator.setBeepEnabled(false);
                integrator.setPrompt("Lendo Codigo");
                integrator.setCameraId(0);
                integrator.initiateScan();

            }
        });

        btnprocura.setOnClickListener(new OnClickListener() {
           String varResult = edtResultado.getText().toString();
            @Override
            public void onClick(View view) {
                select_produto(varResult);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                alert(result.getContents());
            } else {
                alert("Leitura Cancelada");
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

       // Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
        EditText edtResultado =(EditText)findViewById(R.id.edtResultado);
        edtResultado.setText(result.getContents());


    }
    private void alert(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    public void select_produto(String varResult) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(actDigitacao.this,"Inventario",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        admin.selectcodbar(varResult,db);
        Toast.makeText(actDigitacao.this,"O registro é "+varResult,Toast.LENGTH_LONG).show();
    }



}