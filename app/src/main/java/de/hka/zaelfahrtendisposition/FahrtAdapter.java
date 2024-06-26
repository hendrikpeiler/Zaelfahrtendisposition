package de.hka.zaelfahrtendisposition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FahrtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<Fahrt> fahrtList;

    public FahrtAdapter(List<Fahrt> fahrtList) {
        this.fahrtList = fahrtList;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fahrt_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fahrt_item, parent, false);
            return new FahrtViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_HEADER) {
            // No binding necessary for header
        } else {
            FahrtViewHolder fahrtViewHolder = (FahrtViewHolder) holder;
            Fahrt fahrt = fahrtList.get(position - 1); // Adjust for header
            fahrtViewHolder.tagesgruppe.setText(fahrt.getTagesgruppe());
            fahrtViewHolder.linie.setText(fahrt.getLinie());
            fahrtViewHolder.richtung.setText(String.valueOf(fahrt.getRichtung()));
            fahrtViewHolder.abfahrtszeit.setText(fahrt.getAbfahrtszeit());
            fahrtViewHolder.starthaltestelle.setText(fahrt.getStarthaltestelle());
            fahrtViewHolder.geplant.setText(String.valueOf(fahrt.getGeplant()));
            fahrtViewHolder.erhoben.setText(String.valueOf(fahrt.getErhoben()));
            fahrtViewHolder.guetepruefungOk.setText(String.valueOf(fahrt.getGuetepruefungOk()));

            // Setze die Prüfquote
            fahrtViewHolder.pruefquote.setText(String.valueOf(fahrt.getPruefQuote()) + "%");
        }
    }

    @Override
    public int getItemCount() {
        return fahrtList.size() + 1; // Add one for the header
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class FahrtViewHolder extends RecyclerView.ViewHolder {
        TextView tagesgruppe, linie, richtung, abfahrtszeit, starthaltestelle, geplant, erhoben, guetepruefungOk, pruefquote;

        public FahrtViewHolder(@NonNull View itemView) {
            super(itemView);
            tagesgruppe = itemView.findViewById(R.id.tagesgruppe);
            linie = itemView.findViewById(R.id.linie);
            richtung = itemView.findViewById(R.id.richtung);
            abfahrtszeit = itemView.findViewById(R.id.abfahrtszeit);
            starthaltestelle = itemView.findViewById(R.id.starthaltestelle);
            geplant = itemView.findViewById(R.id.geplant);
            erhoben = itemView.findViewById(R.id.erhoben);
            guetepruefungOk = itemView.findViewById(R.id.guetepruefungOk);
            pruefquote = itemView.findViewById(R.id.pruefquote);
        }
    }
}
