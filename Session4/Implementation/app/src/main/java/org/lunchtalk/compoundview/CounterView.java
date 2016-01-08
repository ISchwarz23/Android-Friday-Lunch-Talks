package org.lunchtalk.compoundview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ingo on 08.01.2016.
 */
public class CounterView extends LinearLayout {

    private TextView counterTextView;
    private Button decrementButton;
    private Button incrementButton;

    public CounterView(final Context context) {
        this(context, null);
    }

    public CounterView(final Context context, final AttributeSet attrs) {
        this(context, null, 0);
    }

    public CounterView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews();
    }

    private void initializeViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.counter_view, this, true);
        counterTextView = (TextView) rootView.findViewById(R.id.counterTextView);
        decrementButton = (Button) rootView.findViewById(R.id.decrementButton);
        incrementButton = (Button) rootView.findViewById(R.id.incrementButton);
    }

}
