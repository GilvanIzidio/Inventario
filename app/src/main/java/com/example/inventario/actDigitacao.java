package com.example.inventario;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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
import java.util.ArrayList;
import java.util.List;

public class actDigitacao extends AppCompatActivity {
    Button btnScan;
    EditText edtQtd;
    Button btnupdate;
    EditText edtResultado;
    List<produto> lista = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digitacao);
        btnScan = (Button) findViewById(R.id.btnScan);
        edtResultado = (EditText) findViewById(R.id.edtResultado);
        btnupdate = (Button)findViewById(R.id.btnupdate);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(actDigitacao.this);
        SQLiteDatabase db = admin.getWritableDatabase();


        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testeVariavel();


            }
        });

        final Activity activity = this;
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtResultado.length()>0){
                    retorno();
                    return;
                }
                else {

                }
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(integrator.QR_CODE_TYPES);
                integrator.setDesiredBarcodeFormats(integrator.ONE_D_CODE_TYPES);
                //integrator.setBeepEnabled(false);
                integrator.setPrompt("Lendo Codigo");
                integrator.setCameraId(0);
                integrator.initiateScan();
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
        String ResultadoNomeProduto = ((String) edtResultado.getText().toString());
        Cursor rs = (db.rawQuery("SELECT NOMEPRODUTO nome_produto  FROM PRODUTO  WHERE CODBAR='"+ResultadoNomeProduto+"'",null));
        String  resultado = "";
        if (rs.moveToFirst()) {
            resultado = rs.getString(rs.getColumnIndex("nome_produto"));
        } else {
            alert("Produto não encontrado");
        }
        TextView textNomeProduto = (TextView) findViewById(R.id.textNomeProduto);
        textNomeProduto.setText(resultado);

    }

     public void salvar(){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(actDigitacao.this);
            SQLiteDatabase db = admin.getWritableDatabase();

            String quantidade_estoque = ((String) edtQtd.getText().toString());
            String codigo_de_barras = ((String) edtResultado.getText().toString());

            String sql = "";
            sql = "UPDATE produto SET quantidade =' "+quantidade_estoque+"' WHERE codbar = '"+codigo_de_barras+"'";
            db.beginTransaction();
            SQLiteStatement sqLiteStatement = db.compileStatement(sql);
            sqLiteStatement.clearBindings();
         //   sqLiteStatement.bindString(1,quantidade_estoque);
         //   sqLiteStatement.bindString(2,String.valueOf(codigo_de_barras));
            sqLiteStatement.executeInsert();
            db.setTransactionSuccessful();
            db.endTransaction();
        }

        public void testeVariavel(){
            String res = edtQtd.getText().toString();

            if (res != null) {
                alert("Tem valor");
            } else {
                alert("Sem valor");
            }

        }

}
