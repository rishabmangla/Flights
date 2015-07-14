package com.rishab.mangla.flights.adater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.rishab.mangla.flights.R;
import com.rishab.mangla.flights.model.Flight;

public class CustomListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Flight> flightItems;

	public CustomListAdapter(Activity activity, List<Flight> flightItems) {
		this.activity = activity;
		this.flightItems = flightItems;
	}

	@Override
	public int getCount() {
		return flightItems.size();
	}

	@Override
	public Object getItem(int location) {
		return flightItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);

		TextView flight = (TextView) convertView.findViewById(R.id.flight);
		TextView flightClass = (TextView) convertView.findViewById(R.id.flight_class);
		TextView src = (TextView) convertView.findViewById(R.id.src);
		TextView dest = (TextView) convertView.findViewById(R.id.dest);
		TextView takeOff = (TextView) convertView.findViewById(R.id.takeOff);
		TextView landing = (TextView) convertView.findViewById(R.id.landing);
		TextView duration = (TextView) convertView.findViewById(R.id.duration);
		TextView price = (TextView) convertView.findViewById(R.id.price);


		// getting flight data for the row
		Flight m = flightItems.get(position);

		// flight
		flight.setText(m.getFlight());

		// flight class
        flightClass.setText(m.getFlightClass());

		// src
		src.setText(m.getSrc() + " - ");

		// dest
		dest.setText(m.getDest());

		// takeOff
        takeOff.setText(m.getTakeOffTime() + " - ");

		// landing
        landing.setText(m.getLandingime());

        //duration
        duration.setText(m.getDuration());
		
		// price
		price.setText("Rs. " + String.valueOf(m.getPrice()));

		return convertView;
	}

}