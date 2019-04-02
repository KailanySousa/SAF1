package br.senai.sp.conversores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

public class Imagem {

    public static Bitmap arrayToBitmap(byte[] imagemArray){

        Bitmap bm = BitmapFactory.decodeByteArray(imagemArray, 0 , imagemArray.length);

        return bm;
    }

    public static  byte[] bitmapToArray(Drawable drawable){

        //pega um drawable do image view e transforma em um bitmap drawable, pra dps pegar o bitmap
        Bitmap bm = ((BitmapDrawable)drawable).getBitmap();

        Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bm,300,300, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmapReduzido.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);

        byte[] fotoArray = byteArrayOutputStream.toByteArray(); //serializando a foto, transformando em byte array

        return fotoArray;
    }
}
