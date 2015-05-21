package com.tutorat.assocoma.QCM;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by Sidd8 on 23/07/2014.
 */
public class Reponses extends FragmentActivity
{
    private ArrayList<String> reponsesDonnees = new ArrayList<String>();
    private ArrayList<String> reponsesQuestions = new ArrayList<String>();
    private ArrayList<SpannableStringBuilder> correction = new ArrayList<SpannableStringBuilder>();
    private ArrayList<String> ennoncesQuestions = new ArrayList<String>();
    private ArrayList<String> itemsQuestions = new ArrayList<String>();
    private ArrayList<String> reponsesAttenduesAff = new ArrayList<String>();
    private ArrayList<String> reponsesDonneesAff = new ArrayList<String>();

    private ArrayList<String> affReponsesDonnees = new ArrayList<String>();
    private ArrayList<String> affReponsesAttendues = new ArrayList<String>();
    private ArrayList<Boolean> etatReponse = new ArrayList<Boolean>();

    private ArrayList<Models> models = new ArrayList<Models>();

    private String loginU;
    private String passU;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reponses);

        this.reponsesDonnees = getIntent().getStringArrayListExtra("reponsesDonnees");
        this.reponsesQuestions = getIntent().getStringArrayListExtra("reponsesQuestions");
        this.ennoncesQuestions = getIntent().getStringArrayListExtra("ennonces");
        this.itemsQuestions = getIntent().getStringArrayListExtra("items");
        this.reponsesDonneesAff = getIntent().getStringArrayListExtra("reponsesDonneesAff");
        this.reponsesAttenduesAff = getIntent().getStringArrayListExtra("reponsesAttenduesAff");

        this.loginU = getIntent().getStringExtra("loginU");
        this.passU = getIntent().getStringExtra("passU");
;
        int result = 0;

        for(int i = 0; i < reponsesDonnees.size(); i++)
        {
            if(reponsesDonnees.get(i).equals(reponsesQuestions.get(i)))
            {
                result += 1;
                etatReponse.add(true);
            }
            else
            {
                etatReponse.add(false);
            }
        }

        sauvegarderEvolution(result, reponsesDonnees.size());

        models.add(new Models("Votre score :", result+"", "", false));

        for(int i = 0; i < ennoncesQuestions.size(); i++)
        {
            String determineNbReponsesDonnees = "";
            String determineNbReponsesAttendues = "";

            if(reponsesDonnees.get(i).split("\\|").length <= 1)
            {
                determineNbReponsesDonnees = "Votre réponse : ";
            }
            else if(reponsesDonnees.get(i).split("\\|").length > 1)
            {
                determineNbReponsesDonnees = "Vos réponses : ";
            }
            else
            {
                determineNbReponsesDonnees = "Vous avez passé cette question";
            }

            if(reponsesQuestions.get(i).split("\\|").length <= 1)
            {
                determineNbReponsesAttendues = "Réponse attendue : ";
            }
            else
            {
                determineNbReponsesAttendues = "Réponses attendues : ";
            }

            this.affReponsesDonnees.add(determineNbReponsesDonnees + this.reponsesDonneesAff.get(i));
            this.affReponsesAttendues.add(determineNbReponsesAttendues + this.reponsesAttenduesAff.get(i));

            models.add(new Models(this.ennoncesQuestions.get(i), this.affReponsesDonnees.get(i), this.affReponsesAttendues.get(i), this.etatReponse.get(i)));
        }

        ListView listeMatieres = (ListView) findViewById(R.id.lv_reponses);
        ListAdapter listeReponsesAdapter = new ListAdapter(this, this.models, ennoncesQuestions.size());
        listeMatieres.setAdapter(listeReponsesAdapter);
    }

    public void sauvegarderEvolution(int resultat, int nbQuestions)
    {
        PrefUtils.saveEvolvingToPrefs(Reponses.this, resultat, nbQuestions);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Reponses.this, ChoixMat.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent;
        // Handle presses on the action bar items
        switch (item.getItemId())
        {
            case R.id.retour_matieres:
                intent = new Intent(Reponses.this, ChoixMat.class);
                intent.putExtra("loginU", this.loginU);
                intent.putExtra("passU", this.passU);
                startActivity(intent);
                return true;
            case R.id.score_evolution:
                intent = new Intent(Reponses.this, EvolutionScore.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}