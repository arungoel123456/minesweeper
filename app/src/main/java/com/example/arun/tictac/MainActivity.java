package com.example.arun.tictac;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout rootLayout;
    public static final String TAG = "MainActivity.class";
    public int SIZE = 10;
    public Boolean STATUS;
    public ArrayList<LinearLayout> rows = new ArrayList<>();
    TTTButton board[][] = new TTTButton[SIZE][SIZE];



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.reset) {
            setup();
        }
        if(id== R.id.three)
        {
            SIZE=3;
            setup();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String name = intent.getStringExtra(Main2Activity.NAME_KEY);
        TextView textView = findViewById(R.id.show_name);
        textView.setText(name + " is playing");

        setup();

    }

    public void setup() {
        rootLayout = findViewById(R.id.rootLayout);
        rootLayout.removeAllViews();
        STATUS = Boolean.FALSE;
        rows.clear();
        for (int i = 0; i < SIZE; i++) {
            LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            l.setLayoutParams(layoutParams);
            rootLayout.addView(l);
            rows.add(l);
        }

        for (int i = 0; i < SIZE; i++) {

            LinearLayout row = rows.get(i);
            for (int j = 0; j < SIZE; j++) {
                TTTButton b = new TTTButton(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                layoutParams.setMargins(1, 1, 1, 1); //left, top, right, bottom
                b.setLayoutParams(layoutParams);
                row.addView(b);
                board[i][j] = b;

                // setting button styling
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setStroke(1 , Color.WHITE);
                drawable.setColor(Color.BLUE);
                b.setBackgroundDrawable(drawable);

                //b.setBackgroundColor(Color.RED);
                b.row=i;
                b.col=j;
                b.setOnClickListener(this);
                b.setOnLongClickListener(new View.OnLongClickListener(){
                    @Override
                    public boolean onLongClick(View view) {
                        TTTButton curr=(TTTButton)view;
                        curr.setFlag();
                        return true;
                    }
                });
            }
        }
        for(int i=0;i<SIZE;i++)
        {
            board[i][i].setContent(-1);
        }
        for(int i=0;i<SIZE;i++)
        {
            for(int j=0;j<SIZE;j++)
            {
                if(j==i)
                {
                    continue;
                }
                int num=0;
                if(j-1>=0 && board[i][j-1].getValue()== -1  )
                {
                    num++;
                }
                if(j+1<SIZE && board[i][j+1].getValue()== -1  )
                {
                    num++;
                }
                if(i-1>=0 && board[i-1][j].getValue()== -1  )
                {
                    num++;
                }
                if(i+1<SIZE && board[i+1][j].getValue()== -1  )
                {
                    num++;
                }
                if(i-1>=0 && j-1>=0 && board[i-1][j-1].getValue() ==-1)
                {
                    num++;
                }
                if(i+1<SIZE && j-1>=0 && board[i+1][j-1].getValue() ==-1)
                {
                    num++;
                }
                if(i+1<SIZE && j+1<SIZE && board[i+1][j+1].getValue() ==-1)
                {
                    num++;
                }
                if(i-1>=0 && j+1<SIZE && board[i-1][j+1].getValue() ==-1)
                {
                    num++;
                }
                board[i][j].setContent(num);


            }
        }

    }


    public void setBoard( int row, int col, Boolean check[][])
    {
        Log.d(TAG,"row: " + row + " col: " + col );
        if(row<0 || row>=SIZE ||col<0 || col>=SIZE)
        {
            return ;
        }
        if(check[row][col]==Boolean.TRUE)
        {
            return ;
        }

//        TTTButton b=(TTTButton)view;
        TTTButton b=board[row][col];
        int value=b.getValue();
        if(b.getFlag() == Boolean.TRUE)
        {
            check[row][col]=Boolean.TRUE;
            return;
        }
        if(value==-1)
        {
            return;
        }
        if(value!=0)
        {
            b.setValue();
            return;
        }
        b.setValue();
        check[row][col]=Boolean.TRUE;
        if(row-1>=0) {
            setBoard( row-1, col, check);
        }
        if(row+1<SIZE) {
            setBoard( row+1, col, check);
        }
        if(col-1>=0) {
            setBoard( row, col-1, check);
        }
        if(col+1<SIZE) {
            setBoard( row, col+1, check);
        }
        if(row+1<SIZE && col+1 < SIZE) {
            setBoard( row+1, col+1,check);
        }
        if(row-1>=0 && col+1<SIZE) {
            setBoard( row-1, col+1 , check);
        }
        if(row+1<SIZE && col-1>=0) {
            setBoard( row+1, col-1 , check);
        }
        if(row-1>=0 && col-1>=0) {
            setBoard(row-1, col-1 , check);
        }
        return ;
    }

    public void checkStatus()
    {
        for(int i=0;i<SIZE;i++)
        {
            for(int j=0;j<SIZE;j++)
            {
                if(board[i][j].getValue()==-1)
                {
                    continue;
                }
                if(board[i][j].set==Boolean.FALSE)
                {
                    return;
                }
                if(board[i][j].getFlag()==Boolean.TRUE)
                {
                    return;
                }
            }
        }
        STATUS=Boolean.TRUE;
    }

    @Override
    public void onClick(View view) {
        if(STATUS==Boolean.FALSE) {
            TTTButton b = (TTTButton) view;
            int value = b.getValue();
            if (value == -1) {
                Toast.makeText(this, "You Loss", Toast.LENGTH_LONG).show();
                for (int i = 0; i < SIZE; i++) {
                    board[i][i].setText("-1");
                }
                STATUS = Boolean.TRUE;
                return ;
            }

            else if (value != 0) {
                b.setValue();
            }

            else {
                Boolean check[][] = new Boolean[SIZE][SIZE];
                setBoard(b.row, b.col, check);
            }
            checkStatus();

            if (STATUS == Boolean.TRUE) {
                Toast.makeText(this, "You WON", Toast.LENGTH_LONG).show();
                for (int i = 0; i < SIZE; i++) {
                    board[i][i].setEnabled(false);
                }
            }
        }
    }
}