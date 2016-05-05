package cheng.com.android.cunghoangdao.ultils.htmltextview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Welcome on 4/29/2016.
 */
public class PatchedTextView extends TextView {
    public PatchedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public PatchedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public PatchedTextView(Context context) {
        super(context);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }catch (ArrayIndexOutOfBoundsException e){
            setText(getText().toString());
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
    @Override
    public void setGravity(int gravity){
        try{
            super.setGravity(gravity);
        }catch (ArrayIndexOutOfBoundsException e){
            setText(getText().toString());
            super.setGravity(gravity);
        }
    }
    @Override
    public void setText(CharSequence text, BufferType type) {
        try{
            super.setText(text, type);
        }catch (ArrayIndexOutOfBoundsException e){
            setText(text.toString());
        }
    }
}
