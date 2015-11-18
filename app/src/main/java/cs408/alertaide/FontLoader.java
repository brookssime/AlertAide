package cs408.alertaide;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Locale;

/**
 * Created by brookssime on 11/17/15.
 */
public enum FontLoader {

    BEBAS("bebas");



    private String name;
    private Typeface typeFace;


    private FontLoader(String name) {
        this.name = name;

        typeFace=null;
    }

    public static Typeface getTypeFace(Context context,String name){
        try {
            FontLoader item=FontLoader.valueOf(name.toUpperCase(Locale.getDefault()));
            if(item.typeFace==null){
                item.typeFace=Typeface.createFromAsset(context.getAssets(), "fonts/"+item.name+".ttf");
            }
            return item.typeFace;
        } catch (Exception e) {
            return null;
        }
    }
    public static Typeface getTypeFace(Context context,int id){
        FontLoader myArray[]= FontLoader.values();
        if(!(id<myArray.length)){
            return null;
        }
        try {
            if(myArray[id].typeFace==null){
                myArray[id].typeFace=Typeface.createFromAsset(context.getAssets(), "fonts/"+myArray[id].name+".ttf");
            }
            return myArray[id].typeFace;
        }catch (Exception e) {
            return null;
        }

    }

    public static Typeface getTypeFaceByName(Context context, String name){
        for(FontLoader item: FontLoader.values()){
            if(name.equalsIgnoreCase(item.name)){
                if(item.typeFace==null){
                    try{
                        item.typeFace=Typeface.createFromAsset(context.getAssets(), "fonts/"+item.name+".ttf");
                    }catch (Exception e) {
                        return null;
                    }
                }
                return item.typeFace;
            }
        }
        return null;
    }

    public static void loadAllFonts(Context context){
        for(FontLoader item: FontLoader.values()){
            if(item.typeFace==null){
                try{
                    item.typeFace=Typeface.createFromAsset(context.getAssets(), "fonts/"+item.name+".ttf");
                }catch (Exception e) {
                    item.typeFace=null;
                }
            }
        }
    }
}