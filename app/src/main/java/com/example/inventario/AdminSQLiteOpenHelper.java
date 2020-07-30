package com.example.inventario;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {



    public AdminSQLiteOpenHelper
            (@Nullable Context context){ super(context, "Inventario", null, 1);


    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE produto(" +
                "indicador VARCHAR (2)," +
                "codbar VARCHAR (21)," +
                "quantidade VARCHAR(20)," +
                "nomeproduto VARCHAR(20)" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void apagarRegistros(String tabela, SQLiteDatabase db){
        db.execSQL("DELETE FROM "+tabela);
    }






}
