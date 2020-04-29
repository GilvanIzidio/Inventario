package com.example.inventario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static java.sql.Types.INTEGER;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory,int version){
      super(context,"Inventario",null,1);
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
public void selectcodbar(String varResult, SQLiteDatabase db){
        db.execSQL("SELECT P.NOMEPRODUTO  FROM PRODUTO P WHERE P.CODBAR="+varResult);
}





}
