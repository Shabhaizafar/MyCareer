package com.droidevils.hired.Helper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.droidevils.hired.Helper.Adapter.AvailableServiceHelper;
import com.droidevils.hired.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ServiceAdapter extends ArrayAdapter<AvailableServiceHelper> implements Filterable {

    Context context;
    ArrayList<AvailableServiceHelper> serviceHelpers;
    ArrayList<AvailableServiceHelper> serviceHelpersAll;

    public ServiceAdapter(Context context, ArrayList<AvailableServiceHelper> serviceHelpers) {
        super(context, R.layout.service_card_design, serviceHelpers);
        this.context = context;
        this.serviceHelpers = serviceHelpers;
//        this.serviceHelpersAll = new ArrayList<>(serviceHelpers);
//        serviceHelpersAll = serviceHelpers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        AvailableServiceHelper serviceHelper = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.service_card_design, parent, false);
        }

        ImageView serviceImage = convertView.findViewById(R.id.service_image);
        TextView userName = convertView.findViewById(R.id.service_title);
        TextView serviceName = convertView.findViewById(R.id.service_sub_title);
        RatingBar serviceRating = convertView.findViewById(R.id.service_rating);
        TextView availability = convertView.findViewById(R.id.service_availability);
        ImageView availabilityIcon = convertView.findViewById(R.id.service_availability_icon);
        TextView timeFrom = convertView.findViewById(R.id.service_time_from);
        TextView timeTo = convertView.findViewById(R.id.service_time_to);
        LinearLayout workingDays = convertView.findViewById(R.id.service_working_days);
        TextView distance = convertView.findViewById(R.id.service_distance);

        convertView.setContentDescription(serviceHelper.getServiceId());
        serviceImage.setImageResource(serviceHelper.getServiceImage());
        userName.setText(serviceHelper.getUserName());
        userName.setContentDescription(serviceHelper.getUserId());
        serviceName.setText(serviceHelper.getServiceName());
        serviceName.setContentDescription(serviceHelper.getServiceId());
        serviceRating.setRating(serviceHelper.getServiceRating());
        availability.setText(serviceHelper.isAvailability() ? R.string.available : R.string.unavailable);
        availabilityIcon.setImageResource(serviceHelper.isAvailability() ? R.drawable.ic_tick_green_18dp : R.drawable.ic_cross_red_18dp);
        timeFrom.setText(serviceHelper.getTimeFrom());
        timeTo.setText(serviceHelper.getTimeTo());
        workingDays.setContentDescription(serviceHelper.getWorkingDays());
        for (int i = 0; i < 7; i++) {
            if (serviceHelper.getWorkingDays().charAt(i) == '0') {
                ImageView dayView = (ImageView) workingDays.getChildAt(i);
                dayView.setImageResource(R.drawable.ic_circle_nofill);
            }
        }
        distance.setText(serviceHelper.getDistance() == Double.MAX_VALUE ? "" : formatDistance(serviceHelper.getDistance()));
        return super.getView(position, convertView, parent);
//        return convertView;
    }

    public String formatDistance(double value){
        DecimalFormat df = new DecimalFormat("0.0");
        df.setRoundingMode(RoundingMode.UP);
        return String.format("%s Kms", df.format(value));
    }

    public void setOriginalList(ArrayList<AvailableServiceHelper> serviceHelpers) {
        this.serviceHelpersAll = new ArrayList<>(serviceHelpers);
    }

    //Filter


    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<AvailableServiceHelper> filteredServices = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredServices.addAll(serviceHelpersAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (AvailableServiceHelper serviceHelper : serviceHelpersAll)
                    if (serviceHelper.getServiceName().toLowerCase().contains(filterPattern) ||
                            serviceHelper.getServiceId().toLowerCase().startsWith(filterPattern) ||
                            serviceHelper.getUserName().toLowerCase().startsWith(filterPattern) ||
                            serviceHelper.getUserId().toLowerCase().startsWith(filterPattern))
                        filteredServices.add(serviceHelper);
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredServices;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            serviceHelpers.clear();
            serviceHelpers.addAll((ArrayList) results.values);
            sortByDistance();
            notifyDataSetChanged();
        }
    };

    private void sortByDistance() {
        Collections.sort(serviceHelpers, new Comparator<AvailableServiceHelper>() {
            @Override
            public int compare(AvailableServiceHelper o1, AvailableServiceHelper o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        });
    }

}
