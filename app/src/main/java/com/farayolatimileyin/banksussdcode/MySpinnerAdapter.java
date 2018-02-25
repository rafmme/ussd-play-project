package com.farayolatimileyin.banksussdcode;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by FARAYOLA-FBTS on 25/02/2018.
 */

public class MySpinnerAdapter extends ArrayAdapter<String> {

    public MySpinnerAdapter(Context context, int resourceId){
        super(context,resourceId);
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}
