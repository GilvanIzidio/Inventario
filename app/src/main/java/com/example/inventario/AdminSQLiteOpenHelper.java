package com.example.inventario;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {



    public AdminSQLiteOpenHelper
            (@Nullable Context context){ super(context, "Inventario", null, 2);


    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE produto(" +
                "indicador TEXT (2)," +
                "codbar TEXT (21)," +
                "quantidade TEXT(20)," +
                "nomeproduto TEXT (20)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void apagarRegistros(String tabela, SQLiteDatabase db){
        db.execSQL("DELETE FROM "+tabela);
    }






}
