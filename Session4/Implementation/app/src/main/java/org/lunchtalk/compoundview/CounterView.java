package org.lunchtalk.compoundview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ingo on 08.01.2016.
 */
public class CounterView extends LinearLayout {

    private static final int DEFAULT_INITIAL_COUNT = 0;
    private static final int DEFAULT_COUNTER_STEP_SIZE = 1;

    private TextView counterTextView;
    private Button decrementButton;
    private Button incrementButton;

    private int count;
    private int counterStep;

    public CounterView(final Context context) {
        this(context, null);
    }

    public CounterView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CounterView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeAttributes(attrs);
        initializeViews();
        initializeListeners();
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public int getCounterStepSize() {
        return counterStep;
    }

    public void setCounterStepSize(final int counterStep) {
        this.counterStep = counterStep;
    }

    public void incrementCounter() {
        updateCounter(counterStep);
    }

    public void decrementCounter() {
        updateCounter(-counterStep);
    }

    private void updateCounter(int counterStep) {
        count += counterStep;
        showCountInTextView();
    }

    private void showCountInTextView() {
        counterTextView.setText(String.valueOf(count));
    }

    private void initializeAttributes(final AttributeSet attributes) {
        final TypedArray styledAttributes = getContext().obtainStyledAttributes(attributes, R.styleable.CounterView);
        count = styledAttributes.getInteger(R.styleable.CounterView_initialCount, DEFAULT_INITIAL_COUNT);
        counterStep = styledAttributes.getInteger(R.styleable.CounterView_countingStepSize, DEFAULT_COUNTER_STEP_SIZE);
        styledAttributes.recycle();
    }

    private void initializeViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.counter_view, this, true);
        decrementButton = (Button) rootView.findViewById(R.id.decrementButton);
        incrementButton = (Button) rootView.findViewById(R.id.incrementButton);
        counterTextView = (TextView) rootView.findViewById(R.id.counterTextView);
        showCountInTextView();
    }

    private void initializeListeners() {
        incrementButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                incrementCounter();
            }
        });
        decrementButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                decrementCounter();
            }
        });
    }

}
