package com.example.inventario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.DialogInterface;

//import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.*;

public class actImportacao extends AppCompatActivity {

    Button btnImportar;
    List<produto> listaprodutos = new ArrayList<>();
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importacao);

        btnImportar = findViewById(R.id.btnImportar);
        btnDelete = findViewById(R.id.btnDelete);

        pedirPermissao();

        btnImportar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                importarTXT();
            }
        });

        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                limparTabela("produto");
            }
        });

    }

    public void importarTXT(){
        limparTabela("produto");
        File pasta = new File(getExternalStorageDirectory() + "/APPInventario");
        String arquivoproduto = pasta.toString() + "/" + "COLETOR.txt";

        boolean isCreate = false;
        if(!pasta.exists()) {
            Toast.makeText(this, "Pasta APPInventario não existe", Toast.LENGTH_SHORT).show();
        }else {
            String separador;
            String[] arranjo;
            try{
                FileReader fileReader = new FileReader(arquivoproduto);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((separador = bufferedReader.readLine()) != null){

                    arranjo = separador.split("[|]");

                    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(actImportacao.this,"Inventario",null,1);
                    SQLiteDatabase db = admin.getWritableDatabase();
                    ContentValues registro = new ContentValues();

                    registro.put("indicador",   arranjo[0]);
                    registro.put("codbar",      arranjo[1]);
                    registro.put("nomeproduto", arranjo[2]);

                    listaprodutos.add(
                            new produto(
                                    arranjo[0],
                                    arranjo[1],
                                    arranjo[2]
                            )
                    );



                    db.insert("produto",null,registro);
                    db.close();
                    Toast.makeText(actImportacao.this,"Importado com Sucesso",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e) { }
        } }

    public void pedirPermissao(){
        if(ContextCompat.checkSelfPermission(actImportacao.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(actImportacao.this, new  String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);

        }
    }
    public void limparTabela(String tabela){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(actImportacao.this,"Inventario",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        admin.apagarRegistros(tabela,db);
        Toast.makeText(actImportacao.this,"Foi Limpado os registros de "+tabela,Toast.LENGTH_SHORT).show();
     }



    }


