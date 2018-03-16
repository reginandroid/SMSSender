package app.com.regiko.smssender;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ковтун on 27.02.2018.
 */

public class RecieverAdapter extends ArrayAdapter<Reciever> {
    Context mContext;
    ArrayList<Reciever> recievers = new ArrayList<>();
    Reciever items;
    boolean[] isChecked;
    ArrayList<String> phones = new ArrayList<>();
    SparseBooleanArray mCheckStates;
    public RecieverAdapter(Context context, int textViewResourceId,
                          ArrayList<Reciever> items1) {
        super(context, textViewResourceId, items1);
        recievers = items1;
        mContext =context;
        mCheckStates = new SparseBooleanArray(items1.size());


    }
    @Override
    public int getCount() {
        return recievers.size();
    }
    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.item_reciever, null);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {

            mViewHolder = (MyViewHolder) convertView.getTag();
        }


//        mViewHolder.cb
//                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView,
//                                                 boolean ischecked) {
//                        if (buttonView.isChecked())
//                            isChecked[position] = true;
//                        else
//                            isChecked[position] = false;
//                    }
//                });

        Reciever contacts = recievers.get(position);
        if (contacts != null)
             {mViewHolder.cb.setTag(position);

                 mViewHolder.cb.setChecked(mCheckStates.get(position, false));

                mViewHolder.name.setText(contacts.getName());
                mViewHolder.phone.setText(contacts.getPhone());
            }
        mViewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(isChecked){
            phones.add(recievers.get(position).getPhone());
        }
        else{
            phones.remove(recievers.get(position).getPhone());
        }
    }
});
        return convertView;
    }
    public boolean isChecked(int position) {
        return mCheckStates.get(position, true);
    }

    public void setChecked(int position, boolean isChecked) {
        mCheckStates.put(position, isChecked);

    }
//
//    public void toggle(int position) {
//        setChecked(position, !isChecked(position));
//
//    }
//
//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        mCheckStates.put((Integer) compoundButton.getTag(), b);
//    }

    private class MyViewHolder {
        TextView name, phone;
        CheckBox cb;

        private MyViewHolder(View item) {
            name = (TextView) item.findViewById(R.id.name);
            phone = (TextView) item.findViewById(R.id.phone);
            cb = (CheckBox) item.findViewById(R.id.checkBox);

        }
    }

}
