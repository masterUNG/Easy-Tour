package appewtc.masterung.easytour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by masterUNG on 3/12/16 AD.
 */
public class TourAdapter extends BaseAdapter{

    //Explicit
    private Context context;
    private String[] nameStrings, provinceStrings, timeUseStrings;

    public TourAdapter(Context context, String[] nameStrings, String[] provinceStrings, String[] timeUseStrings) {
        this.context = context;
        this.nameStrings = nameStrings;
        this.provinceStrings = provinceStrings;
        this.timeUseStrings = timeUseStrings;
    }   // Constructor

    @Override
    public int getCount() {
        return nameStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.tour_program_list, viewGroup, false);

        TextView nameTextView = (TextView) view1.findViewById(R.id.textView9);
        nameTextView.setText(nameStrings[i]);

        TextView provinceTextView = (TextView) view1.findViewById(R.id.textView10);
        provinceTextView.setText(provinceStrings[i]);

        TextView timeUseTextView = (TextView) view1.findViewById(R.id.textView8);
        timeUseTextView.setText(timeUseStrings[i]);

        return view1;
    }
}   // Main Class
