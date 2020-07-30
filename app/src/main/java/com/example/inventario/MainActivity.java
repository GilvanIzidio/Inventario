package com.example.inventario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.getExternalStorageDirectory;

public class MainActivity extends AppCompatActivity {

         @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
             File diretorio = new File(Environment.getExternalStorageDirectory() + "/APPInventario");
             boolean isCreate = false;
             if (!diretorio.exists()) {
                 isCreate = diretorio.mkdir();
         }}


    public void entrar(View v){
        Intent in;
        in = new Intent(MainActivity.this, actDigitacao.class);
        startActivity(in);
    }

    public void importarInventario(View v){
        Intent in;
        in= new Intent(MainActivity.this, actImportacao.class);
        startActivity(in);
    }

    public void exportarInventario(View v){
             Intent in;
             in= new Intent(MainActivity.this, actExportacao.class);
             startActivity(in);
    }
}
