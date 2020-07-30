package com.example.inventario;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;

public class actExportacao extends AppCompatActivity {
    Button btnexporta;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportar);
        btnexporta = (Button) findViewById(R.id.btnexporta);
        textView = (TextView) findViewById(R.id.textView);

        Permissao();

        btnexporta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExportarTXT();
            }
        });
    }

    private void ExportarTXT() {
        File diretorio = new File(Environment.getExternalStorageDirectory() + "/APPInventario");
        String arquivo = diretorio.toString() + "/" + "COLETOR APP.txt";

        boolean isCreate = false;
        if (!diretorio.exists()) {
            isCreate = diretorio.mkdir();
        }
        try {
            FileWriter fileWriter = new FileWriter(arquivo);
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(actExportacao.this);
            SQLiteDatabase db = admin.getWritableDatabase();

            Cursor fila = (db.rawQuery("select CODBAR CODIGO_BAR,QUANTIDADE QT_D from produto where QT_D is not null",null));
            if (fila !=null && fila.getCount() !=0){
                fila.moveToFirst();
                do{
                    fileWriter.append(fila.getString(0));
                    fileWriter.append(";");
                    fileWriter.append(fila.getString(1));
                    fileWriter.append("\n");
                }while (fila.moveToNext());
                Toast.makeText(this, "Exportado com Sucesso", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Não tem Registros", Toast.LENGTH_SHORT).show();
            }
            db.close();
            fileWriter.close();
        }catch (Exception e){}
        }



    public void Permissao(){
        if(ContextCompat.checkSelfPermission(actExportacao.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(actExportacao.this, new  String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
    } }}