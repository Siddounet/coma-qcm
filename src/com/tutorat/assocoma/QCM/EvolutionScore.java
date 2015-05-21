package com.tutorat.assocoma.QCM;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.androidplot.xy.*;

import java.util.Arrays;

/**
 * Created by Sidd8 on 24/10/2014.
 */
public class EvolutionScore extends FragmentActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evolution_scores);

        XYPlot scoreEvolution = (XYPlot) findViewById(R.id.mySimpleXYPlot);
        String savedScore = PrefUtils.getFromPrefs(EvolutionScore.this, "evolution","");
        if(savedScore.length() > 0)
        {
            String[] splitedScores = savedScore.split("\\|");
            Number[] evolution = new Number[splitedScores.length];
            for(int i = 0; i < splitedScores.length; i++)
            {
                evolution[i] = Integer.parseInt(splitedScores[i]);
            }

            XYSeries evolutionLine = new SimpleXYSeries(
                    Arrays.asList(evolution),          // SimpleXYSeries takes a List so turn our array into a List
                    SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                    "Evolution (%)");                             // Set the display title of the series

            LineAndPointFormatter evolutionLineFormat = new LineAndPointFormatter();
            evolutionLineFormat.setPointLabelFormatter(new PointLabelFormatter());
            evolutionLineFormat.configure(getApplicationContext(),R.xml.line_point_formatter_with_plf1);
            scoreEvolution.addSeries(evolutionLine, evolutionLineFormat);

            scoreEvolution.setTicksPerRangeLabel(3);
            scoreEvolution.getGraphWidget().setDomainLabelOrientation(-45);
            scoreEvolution.setRangeBoundaries(0,100, BoundaryMode.FIXED);
        }
    }
}