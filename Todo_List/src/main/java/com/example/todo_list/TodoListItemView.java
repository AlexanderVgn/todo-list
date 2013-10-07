package com.example.todo_list;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class TodoListItemView extends TextView{

    private Paint marginPaint;

    private Paint linePaint;

    private int paperColor;

    private float margin;

    public TodoListItemView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TodoListItemView (Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    public TodoListItemView (Context context) {
        super(context);
        init();
    }

    private void init(){
        // Получить ссылку на таблицу ресурсов
        Resources myResources = getResources();

        // Создать кисти для рисования, которые используем в методе onDraw
        marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        marginPaint.setColor(myResourcesS.getColor(R.color.notepad_margin));
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(myResources.getColor(R.color.notepad_lines));

        // Получить цвет фона для листа и ширину кромки
        paperColor = myResources.getColor(R.color.notepad_paper);
        margin = myResources.getDimension(R.dimen.notepad_margin);
    }

    @Override
    public void onDraw (Canvas canvas) {
        // Фоновый цвет для листа
        canvas.drawColor(paperColor);

        // Нарисовать управляющие линии
        canvas.drawLine(0, 0, getMeasuredHeight(), 0, linePaint);
        canvas.drawLine(0, getMeasuredHeight(),
                getMeasuredWidth(), getMeasuredHeight(), linePaint);

        // Нарисовать кромку
        canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);

        // Переместить текст в сторону кромки
        canvas.save();
        canvas.translate(margin, 0);

        super.onDraw(canvas);
        canvas.restore();
    }

}
