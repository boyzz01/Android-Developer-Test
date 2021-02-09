package com.ardy.androiddevelopertest.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager
{
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;


    int PRIVATE_MODE = 0;





    public PrefManager(String name, Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(name, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setLogin (boolean login)
    {
        editor.putBoolean("data", login);
        editor.commit();
    }

    public boolean isLogin()
    {
     return pref.getBoolean("data",false);
    }




}
