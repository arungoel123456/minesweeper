package com.example.arun.tictac;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;

public class TTTButton extends AppCompatButton {

    private int value=0;
    public int row;
    public int col;
    private Boolean flag=Boolean.FALSE;
    public Boolean set = Boolean.FALSE;

    public TTTButton(Context context)
    {
        super(context);
    }

    public void setContent(int value)
    {
        this.value=value;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue()
    {
        setText(value + " ");
        setEnabled(false);
        set=Boolean.TRUE;
    }

    public void setFlag()
    {
        if(flag==Boolean.FALSE)
        {
            flag=Boolean.TRUE;
            setText("F");
            setClickable(false);
        }
        else
        {
            flag=Boolean.FALSE;
            setText("");
            setClickable(true);
        }
        return;
    }

    public Boolean getFlag() {
        return flag;
    }
}
