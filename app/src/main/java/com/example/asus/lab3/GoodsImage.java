package com.example.asus.lab3;

public class GoodsImage {
    public GoodsImage(){

    }
    static int getImage(String string){
        switch (string){
            case "Enchated Forest":
                return R.mipmap.enchatedforest;
            case "Arla Milk":
                return R.mipmap.arla;
            case "Devondale Milk":
                return R.mipmap.devondale;
            case "Kindle Oasis":
                return R.mipmap.kindle;
            case "waitrose 早餐麦片":
                return R.mipmap.waitrose;
            case "Mcvitie's 饼干":
                return R.mipmap.mcvitie;
            case "Ferrero Rocher":
                return R.mipmap.ferrero;
            case "Maltesers":
                return R.mipmap.maltesers;
            case "Lindt":
                return R.mipmap.lindt;
            case "Borggreve":
                return R.mipmap.borggreve;
            default:
                return R.mipmap.enchatedforest;
        }
    }
}
