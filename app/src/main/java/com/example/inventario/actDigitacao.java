package com.example.inventario;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;

public class actDigitacao extends AppCompatActivity {
    Button btnScan;
    EditText edtQtd;
    Button btnprocura;
    EditText edtResultado;
    String textNomeProduto;
    Button button777;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digitacao);
        btnScan = (Button) findViewById(R.id.btnScan);
        edtResultado = (EditText) findViewById(R.id.edtResultado);
        button777 = (Button)findViewById(R.id.button777);
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

        button777.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retorno();
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

        EditText edtResultado = (EditText) findViewById(R.id.edtResultado);
        edtResultado.setText(result.getContents());
        retorno();

    }

    private void alert(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }




    public void retorno(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(actDigitacao.this);
        SQLiteDatabase db = admin.getWritableDatabase();



      //  Cursor rs = (db.rawQuery("SELECT NOMEPRODUTO nome_produto  FROM PRODUTO  WHERE CODBAR= "+edtResultado",null));
        Cursor rs = (db.rawQuery("SELECT NOMEPRODUTO nome_produto  FROM PRODUTO  WHERE CODBAR='"+edtResultado+"'",null));
        String  resultado = "";
        if (rs.moveToFirst()) {
            resultado = rs.getString(rs.getColumnIndex("nome_produto"));
        } else {
            alert("Produto não encontrado.");
        }
        TextView textNomeProduto = (TextView) findViewById(R.id.textNomeProduto);
        textNomeProduto.setText(resultado);


    }


}