package com.example.QCM;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Bastien Coutable
 * Date: 06/02/14
 * Time: 09:47
 */
public class Questions extends Activity
{
    private Button valider;
    private TextView ennonce;
    private CheckBox item1;
    private CheckBox item2;
    private CheckBox item3;
    private CheckBox item4;
    private CheckBox item5;
    private String[] itParsed;
    private String[] enParsed;
    private String[] item;
    private int nbAppel;
    private String index;

    private ChoixMat mat;
    private String[] matParsed = null;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.presqcm);

        Bundle b = getIntent().getExtras();
        this.index = b.getString("itemsenn");

        this.valider = (Button)findViewById(R.id.b_Valider);
        this.ennonce = (TextView)findViewById(R.id.tv_Ennonce);
        this.item1 = (CheckBox)findViewById(R.id.cb_Item1);
        this.item2 = (CheckBox)findViewById(R.id.cb_Item2);
        this.item3 = (CheckBox)findViewById(R.id.cb_Item3);
        this.item4 = (CheckBox)findViewById(R.id.cb_Item4);
        this.item5 = (CheckBox)findViewById(R.id.cb_Item5);

        this.itParsed = new String[5];
        //this.enParsed = new String[200];
        this.item = new String[100];

        this.nbAppel = 0;

        this.mat = new ChoixMat();
        launchQuestions();
    }

    public void parseMat()
    {
        if(this.index == null)
        {
            Log.i("tagmatquests","quests NULL");
        }
        this.matParsed = this.index.split("#%_%#");

        Log.i("tagMatParsed",matParsed.length+"");

        for(int i = 0; i < matParsed.length; i++)
        {
            Log.i("tagStringMatParsed",matParsed[i]);
        }
    }

    public void parseEnnonce()
    {
        int j = 0;
        for(int i = 0; i < matParsed.length; i++)
        {
            this.enParsed[j] = matParsed[i];
            j++;
            i++;
        }

        Log.i("tagSizeEnParsed",enParsed.length+"");

        for(int k = 0; k < enParsed.length; k++)
        {
            if(enParsed[k] == ""/* || enParsed[k].equals(null)*/)
            {
                Log.i("ErrStringEnParsed","EnParsed est null");
                break;
            }

            Log.i("tagStringEnParsed",""+k);
            Log.i("tagStringEnParsed",""+this.enParsed[k]);
        }
    }

    public int parseItems(int ind)
    {
        String[] pars = null;
        Log.i("!!!!!!!!!!!tagMatParsed", matParsed[ind]);
        pars = this.matParsed[ind].split("\\|-\\|");
        Log.i("tagMatParsed","indice " + ind + " valeur " + matParsed[ind]);
        Log.i("tagParsSize","Size " + pars.length);
        int tmp = 0;

        for(int k = 0; k < pars.length; k++)
        {
            if(pars[k] == "")
            {
                break;
            }
            Log.i("tagPars[k] ", pars[k]);
            this.itParsed[k] = pars[k];
            tmp++;
        }
        return tmp;
    }

    public void afficQCM(int nbitem, int id)
    {
        this.ennonce.setText(this.enParsed[id]+"\n");
        Log.i("tagEnnonce : ", enParsed[id]);

        this.item1.setChecked(false);
        this.item2.setChecked(false);
        this.item3.setChecked(false);
        this.item4.setChecked(false);
        this.item5.setChecked(false);

        switch(nbitem)
        {
            case 1:
                this.item1.setText("A- "+Html.fromHtml(itParsed[0])+"\n");
                this.item1.setVisibility(View.VISIBLE);
                break;
            case 2:
                this.item1.setText("A- "+Html.fromHtml(itParsed[0])+"\n");
                this.item1.setVisibility(View.VISIBLE);
                this.item2.setText("B- "+Html.fromHtml(itParsed[1])+"\n");
                this.item2.setVisibility(View.VISIBLE);
                break;
            case 3:
                this.item1.setText("A- "+Html.fromHtml(itParsed[0])+"\n");
                this.item1.setVisibility(View.VISIBLE);
                this.item2.setText("B- "+Html.fromHtml(itParsed[1])+"\n");
                this.item2.setVisibility(View.VISIBLE);
                this.item3.setText("C- "+Html.fromHtml(itParsed[2])+"\n");
                this.item3.setVisibility(View.VISIBLE);
                break;
            case 4:
                this.item1.setText("A- "+ Html.fromHtml(itParsed[0])+"\n");
                this.item1.setVisibility(View.VISIBLE);
                this.item2.setText("B- "+Html.fromHtml(itParsed[1])+"\n");
                this.item2.setVisibility(View.VISIBLE);
                this.item3.setText("C- "+Html.fromHtml(itParsed[2])+"\n");
                this.item3.setVisibility(View.VISIBLE);
                this.item4.setText("D- "+Html.fromHtml(itParsed[3])+"\n");
                this.item4.setVisibility(View.VISIBLE);
                break;
            case 5:
                this.item1.setText("A- "+Html.fromHtml(itParsed[0])+"\n");
                this.item1.setVisibility(View.VISIBLE);
                this.item2.setText("B- "+Html.fromHtml(itParsed[1])+"\n");
                this.item2.setVisibility(View.VISIBLE);
                this.item3.setText("C- "+Html.fromHtml(itParsed[2])+"\n");
                this.item3.setVisibility(View.VISIBLE);
                this.item4.setText("D- "+Html.fromHtml(itParsed[3])+"\n");
                this.item4.setVisibility(View.VISIBLE);
                this.item5.setText("E- "+Html.fromHtml(itParsed[4])+"\n");
                this.item5.setVisibility(View.VISIBLE);
                break;
            default:
                Log.i("tagAffic", "Problème avec le nombre d'items");
        }
    }

    public void valid_Question(View v)
    {
        if(this.item1.isChecked() || this.item2.isChecked() || this.item3.isChecked() || this.item4.isChecked() ||this.item5.isChecked())
        {
            launchQuestions();
        }

    }

    public void launchQuestions()
    {
        Log.i("tagIndexSize",this.index.length()+"");
        int nbItems = 0;

        if(this.index.length() > 0)
        {
            Log.i("tagIndexString",this.index);
        }

        parseMat();

        this.enParsed = new String[(this.matParsed.length)/2];

        Random r = new Random();
        int i1 = r.nextInt(this.matParsed.length-1);
        if(i1 < 0)
            i1 = 1;
        else if(i1 >= this.matParsed.length)
            i1 -= 2;
        else {}
        parseEnnonce();

        int reste = i1 % 2;
        if(reste == 0)
        {
            nbItems = parseItems(i1+1);
            afficQCM(nbItems, i1/2);
        }
        else
        {
            nbItems = parseItems(i1+2);
            afficQCM(nbItems, (i1/2)+1);
        }
    }
}