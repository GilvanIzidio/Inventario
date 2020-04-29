package com.example.inventario;

public class produto {
    String indicador;
    String codbar;
    String nomeproduto;

    public produto(String indicador, String codbar, String nomeproduto){

        this.indicador = indicador;
        this.codbar=codbar;
        this.nomeproduto=nomeproduto;

    }
    public String getIndicador() {
        return indicador;
    }




    public void  setIndicador(String indicador){this.indicador=indicador;}

    public String getCodbar(){return codbar;}
    public void  setCodbar(String codbar){this.codbar=codbar;}

    public  String getNomeproduto(){return nomeproduto;}
    public  void  setNomeproduto(String codbar){this.codbar=codbar;}
}
