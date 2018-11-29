package test.study.wzm.xiaomingweather.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import test.study.wzm.xiaomingweather.R;

public class ChooseAreaDialog extends Dialog {
    public ChooseAreaDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_choose_area);
    }
}
