package com.tutorat.assocoma.QCM;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Sidd8 on 13/10/2014.
 */
public class ListAdapter extends ArrayAdapter<Models>
{
    private final Context context;

    private ArrayList<Models> models = new ArrayList<Models>();
    private int nbQuestions;

    public ListAdapter(Context context, ArrayList<Models> models, int nbQuestions)
    {
        super(context,R.layout.reponse_items, models);

        this.context = context;
        this.models = models;
        this.nbQuestions = nbQuestions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.reponse_items, parent, false);

        TextView enonce = (TextView) rowView.findViewById(R.id.tv_reponse_enonce);
        TextView reponseDonnee = (TextView) rowView.findViewById(R.id.tv_reponse_donnee);
        TextView reponseAttendue = (TextView) rowView.findViewById(R.id.tv_reponse_attendue);
        TextView etatReponse = (TextView) rowView.findViewById(R.id.tv_color_response);

        enonce.setText(models.get(position).getEnonce());
        reponseDonnee.setText(models.get(position).getReponseDonnee());
        reponseAttendue.setText(models.get(position).getReponseAttendue());
        if(position == 0)
        {
            if(Integer.parseInt(models.get(position).getReponseDonnee()) > (nbQuestions/2))
                reponseDonnee.setTextColor(Color.GREEN);
            else if(Integer.parseInt(models.get(position).getReponseDonnee()) < (nbQuestions/2))
                reponseDonnee.setTextColor(Color.RED);
            else
                reponseDonnee.setTextColor(Color.BLACK);

            reponseDonnee.setText(models.get(position).getReponseDonnee()+"/"+this.nbQuestions);
        }
        else
        {
            if(models.get(position).getEtatReponse())
                etatReponse.setBackgroundResource(R.drawable.green_gradient);
            else
                etatReponse.setBackgroundResource(R.drawable.red_gradient);

        }
        return rowView;
    }
}
