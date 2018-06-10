package com.example.android.aroma.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.aroma.Ingredients;
import com.example.android.aroma.R;
import com.example.android.aroma.Steps;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends ArrayAdapter {

    List list = new ArrayList<>();
    public StepsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    public void add(@Nullable Steps object) {
        super.add(object);
        list.add(object);

    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return super.getPosition(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row=convertView;
        StepsHolder stepsHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.list_item_step,parent,false);
            stepsHolder=new StepsHolder();
            stepsHolder.txtStep=row.findViewById(R.id.step_row);
            stepsHolder.txtStepNumb=row.findViewById(R.id.step_number);

            row.setTag(stepsHolder);
        }
        else
        {
            stepsHolder=(StepsHolder) row.getTag();
        }
        Steps steps = (Steps) this.getItem(position);
        stepsHolder.txtStep.setText(steps.getStep());
        stepsHolder.txtStepNumb.setText(steps.getStep_number());
        return row;
    }
    static class StepsHolder {
        TextView txtStep,txtStepNumb;

    }
}
