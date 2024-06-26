package de.hka.zaelfahrtendisposition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FahrtAdapter extends RecyclerView.Adapter<FahrtAdapter.FahrtViewHolder> {

    private List<Fahrt> fahrtList;

    public FahrtAdapter(List<Fahrt> fahrtList) {
        this.fahrtList = fahrtList != null ? fahrtList : new ArrayList<>();
    }

    @NonNull
    @Override
    public FahrtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fahrt_item, parent, false);
        return new FahrtViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FahrtViewHolder holder, int position) {
        Fahrt fahrt = fahrtList.get(position);
        holder.tagesgruppeTextView.setText(fahrt.getTagesgruppe());
        holder.linieTextView.setText(fahrt.getLinie());
        holder.richtungTextView.setText(String.valueOf(fahrt.getRichtung()));
        holder.abfahrtszeitTextView.setText(fahrt.getAbfahrtszeit());
        holder.starthaltestelleTextView.setText(fahrt.getStarthaltestelle());
        holder.geplantTextView.setText(String.valueOf(fahrt.getGeplant()));
        holder.erhobenTextView.setText(String.valueOf(fahrt.getErhoben()));
        holder.guetepruefungOkTextView.setText(String.valueOf(fahrt.getGuetepruefungOk()));
        holder.pruefQuoteTextView.setText(String.valueOf(fahrt.getPruefQuote()));
    }

    @Override
    public int getItemCount() {
        return fahrtList.size();
    }

    public static class FahrtViewHolder extends RecyclerView.ViewHolder {
        public TextView tagesgruppeTextView;
        public TextView linieTextView;
        public TextView richtungTextView;
        public TextView abfahrtszeitTextView;
        public TextView starthaltestelleTextView;
        public TextView geplantTextView;
        public TextView erhobenTextView;
        public TextView guetepruefungOkTextView;
        public TextView pruefQuoteTextView;

        public FahrtViewHolder(View view) {
            super(view);
            tagesgruppeTextView = view.findViewById(R.id.tagesgruppe);
            linieTextView = view.findViewById(R.id.linie);
            richtungTextView = view.findViewById(R.id.richtung);
            abfahrtszeitTextView = view.findViewById(R.id.abfahrtszeit);
            starthaltestelleTextView = view.findViewById(R.id.starthaltestelle);
            geplantTextView = view.findViewById(R.id.geplant);
            erhobenTextView = view.findViewById(R.id.erhoben);
            guetepruefungOkTextView = view.findViewById(R.id.guetepruefungOk);
            pruefQuoteTextView = view.findViewById(R.id.pruefquote);
        }
    }
}
