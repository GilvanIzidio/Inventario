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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import java.lang.String;

public class actDigitacao extends AppCompatActivity {
    Button btnScan;
    EditText edtQtd;
    Button btnprocura;
    EditText edtResultado;
    String textNomeProduto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digitacao);
        btnScan = (Button) findViewById(R.id.btnScan);
        edtResultado = (EditText) findViewById(R.id.edtResultado);
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
        carrega_dados();

    }


    private void alert(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public Cursor carrega_dados() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(actDigitacao.this);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor cursor;
        String[] campos =  {AdminSQLiteOpenHelper.codbar};
        cursor = db.rawQuery("SELECT P.NOMEPRODUTO  FROM PRODUTO P WHERE P.CODBAR= 7909189139576", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }
    public class consulta extends Activity{
        private ListView listView;
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_digitacao);
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getBaseContext());
            Cursor cursor = carrega_dados();
            listView = (ListView)findViewById(R.id.listView);
            String[] nomeCampos = new String[] {AdminSQLiteOpenHelper.codbar};
            SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),R.layout.activity_digitacao,cursor,nomeCampos,null,0);
            listView.setAdapter(adaptador);

        }
    }



  /*  public void retorno (){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(actDigitacao.this,"Inventario",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        db.rawQuery("SELECT P.NOMEPRODUTO  FROM PRODUTO P WHERE P.CODBAR= ?",new String[] {"7909189139576"});
        String resultNomeProduto = db.toString();
        TextView textNomeProduto = (TextView)findViewById(R.id.textNomeProduto);
        textNomeProduto.setText(resultNomeProduto);*/



}