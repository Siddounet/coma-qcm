package com.example.QCM;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sidd8 on 30/01/14.
 */
public class ChoixMat extends Activity
{
    protected Button b_Histo;
    protected Button b_Embryo;
    protected Button b_BioCell;
    protected Button b_BioStat;
    protected Button b_Anat;
    protected Button b_EtudeMed;
    protected Button b_MedEmbryo;
    protected Button b_MedPhysio;
    protected Button b_MedOndoto;
    protected Button b_Odonto;
    protected Button b_Pharma;

    private Connexion myco = new Connexion();

    //private ConnexionBDD connection;
    private ConnexionBDD maco;

    protected String quests = "";

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choixqcm);

        this.b_Histo = (Button) findViewById(R.id.b_Histologie);
        this.b_Embryo = (Button) findViewById(R.id.b_Embryologie);
        this.b_BioCell = (Button) findViewById(R.id.b_BiologieCellulaire);
        this.b_BioStat = (Button) findViewById(R.id.b_Biostatistiques);
        this.b_Anat = (Button) findViewById(R.id.b_Anatomie);
        this.b_EtudeMed = (Button) findViewById(R.id.b_EtudeDuMedicament);
        this.b_MedEmbryo = (Button) findViewById(R.id.b_MedEmbryologie);
        this.b_MedPhysio = (Button) findViewById(R.id.b_MedPhysiologie);
        this.b_MedOndoto = (Button) findViewById(R.id.b_MedOdonto);
        this.b_Odonto = (Button) findViewById(R.id.b_Odonto);
        this.b_Pharma = (Button) findViewById(R.id.b_Pharma);

        this.myco = new Connexion();
    }

    public String getQuests()
    {
        return this.quests;
    }

    public ChoixMat()
    {
        this.quests = "";
    }

    public void chooseMat(View v)
    {
        /*ConnexionBDD*/
        this.maco = new ConnexionBDD();
        if (v == this.b_Histo)
        {
            Log.i("tagClick", "Histologie");
            this.quests = maco.getData("2","Histologie");
        }
        //else if (this.equals(this.b_Embryo))
        else if (v == this.b_Embryo)
            this.quests = maco.getData("2","Embryologie");
        //else if(this.equals(this.b_BioCell))
        else if (v == this.b_BioCell)
            this.quests = maco.getData("2","Biologie cellulaire");
        else if (v == this.b_BioStat)
        //else if(this.equals(this.b_BioStat))
            this.quests = maco.getData("4","Biostatistiques");
        else if (v == this.b_Anat)
        //else if(this.equals(this.b_Anat))
            this.quests = maco.getData("5","Anatomie");
        else if (v == this.b_EtudeMed)
        //else if(this.equals(this.b_EtudeMed))
            this.quests = maco.getData("6","&Eacute;tude du médicament");
        else if (v == this.b_MedEmbryo)
        //else if(this.equals(this.b_MedEmbryo))
            this.quests = maco.getData("méd","Embryologie");
        else if (v == this.b_MedPhysio)
        //else if(this.equals(this.b_MedPhysio))
            this.quests = maco.getData("méd","Physiologie");
        else if (v == this.b_MedOndoto)
        //else if(this.equals(this.b_MedOndoto))
            this.quests = maco.getData("méd/odonto","Spé médecine & odontologie");
        else if (v == this.b_Odonto)
        //else if(this.equals(this.b_Odonto))
            this.quests = maco.getData("odonto","Spé odontologie");
        else if (v == this.b_Pharma)
        //else if(this.equals(this.b_Pharma))
            this.quests = maco.getData("pharma","Spé pharmacie");
        else
            Log.i("tagErrClick","Bouton non existant?!");

        if(this.quests.length() > 0)
        {
            Log.i("tagQuestSize",this.quests.length()+"");
            Intent intent = new Intent(ChoixMat.this, Questions.class);
            intent.putExtra("itemsenn",this.quests);
            startActivity(intent);
        }
        else
        {
            Log.i("tagQuests","Quests length = 0 !");
        }
    }
}