package com.example.inventario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    static String codbar = "tituloaaaaa";

    public AdminSQLiteOpenHelper
            (@Nullable Context context){ super(context, "Inventario", null, 1);

             //String TITULO = "titulo";
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE produto(" +
                "indicador TEXT (2)," +
                "codbar TEXT (21)," +
                "quantidade INTEGER," +
                "nomeproduto TEXT (20)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

public void apagarRegistros(String tabela, SQLiteDatabase db){
        db.execSQL("DELETE FROM "+tabela);
}



    /*public void pegaNomeProduto(String resultNomeProduto, SQLiteDatabase db){
        db.execSQL("SELECT P.NOMEPRODUTO  FROM PRODUTO P WHERE P.CODBAR="+edtResultado);

        TextView textNomeProduto = (TextView)findViewById(R.id.textNomeProduto);
        textNomeProduto.setText(resultNomeProduto);}*/


}
