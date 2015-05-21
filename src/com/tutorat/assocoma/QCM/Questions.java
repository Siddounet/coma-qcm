package com.tutorat.assocoma.QCM;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created with IntelliJ IDEA.
 * User: Bastien Coutable
 * Date: 06/02/14
 * Time: 09:47
 */
public class Questions extends FragmentActivity
{
    private String loginU;
    private String passU;
    private String idMatiere;

    private ArrayList<String> ennoncesQuestions = new ArrayList<String>();
    private ArrayList<String> itemsQuestions = new ArrayList<String>();
    private ArrayList<String> reponsesQuestions = new ArrayList<String>();
    private ArrayList<String> urlSchema = new ArrayList<String>();
    private ArrayList<String> reponsesAttenduesAff = new ArrayList<String>();
    private ArrayList<String> reponsesDonneesAff = new ArrayList<String>();

    private int currentQuestion = 0;
    private ArrayList<String> reponsesDonnees = new ArrayList<String>();

    private ProgressDialog dialog;
    private AsyncDatas datas = null;

    private Button valider;
    private Button sauterQuestion;
    private TextView ennonce;
    private TextView numQuestion;
    private CheckBox item1;
    private CheckBox item2;
    private CheckBox item3;
    private CheckBox item4;
    private CheckBox item5;
    private ImageView schema;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.presqcm);

        this.loginU = getIntent().getStringExtra("loginU");
        this.passU = getIntent().getStringExtra("passU");
        this.idMatiere = getIntent().getStringExtra("idMatiere");

        this.valider = (Button)findViewById(R.id.b_Valider);
        this.sauterQuestion = (Button)findViewById(R.id.b_SauterQuestion);
        this.ennonce = (TextView)findViewById(R.id.tv_Ennonce);
        this.numQuestion = (TextView)findViewById(R.id.tv_Question);
        this.item1 = (CheckBox)findViewById(R.id.cb_Item1);
        this.item2 = (CheckBox)findViewById(R.id.cb_Item2);
        this.item3 = (CheckBox)findViewById(R.id.cb_Item3);
        this.item4 = (CheckBox)findViewById(R.id.cb_Item4);
        this.item5 = (CheckBox)findViewById(R.id.cb_Item5);
        this.schema = (ImageView)findViewById(R.id.iv_schema);

        this.datas = new AsyncDatas(Questions.this);
        this.dialog = ProgressDialog.show(this, "Chargement", "Veuillez patienter", true);
        this.datas.execute();
    }

    public boolean afficQCM()
    {
        this.schema.setVisibility(View.GONE);
        if(currentQuestion > ennoncesQuestions.size()-1)
        {
            finQCM();
            return true;
        }

        this.ennonce.setText(Html.fromHtml(this.ennoncesQuestions.get(currentQuestion)));
        this.numQuestion.setText("Question n°"+Html.fromHtml((currentQuestion+1) + "/" + ennoncesQuestions.size()));

        if(urlSchema.get(currentQuestion).length() > 0 && urlSchema.get(currentQuestion) != "null")
        {
            this.schema.setVisibility(View.VISIBLE);
            AsyncAvatar scheme = new AsyncAvatar();
            scheme.execute("http://tutorat.assocoma.fr/qcm/data/"+urlSchema.get(currentQuestion));
        }

        this.item1.setChecked(false);
        this.item2.setChecked(false);
        this.item3.setChecked(false);
        this.item4.setChecked(false);
        this.item5.setChecked(false);

        String[] splitItems = this.itemsQuestions.get(currentQuestion).split("\\|-\\|");
        int nbitem = splitItems.length;

        switch(nbitem)
        {
            case 1:
                this.item1.setText("A- "+Html.fromHtml(splitItems[0]).toString()+"\n");
                this.item1.setVisibility(View.VISIBLE);
                break;
            case 2:
                this.item1.setText("A- "+Html.fromHtml(splitItems[0]).toString()+"\n");
                this.item1.setVisibility(View.VISIBLE);
                this.item2.setText("B- "+Html.fromHtml(splitItems[1]).toString()+"\n");
                this.item2.setVisibility(View.VISIBLE);
                break;
            case 3:
                this.item1.setText("A- "+Html.fromHtml(splitItems[0]).toString()+"\n");
                this.item1.setVisibility(View.VISIBLE);
                this.item2.setText("B- "+Html.fromHtml(splitItems[1]).toString()+"\n");
                this.item2.setVisibility(View.VISIBLE);
                this.item3.setText("C- "+Html.fromHtml(splitItems[2]).toString()+"\n");
                this.item3.setVisibility(View.VISIBLE);
                break;
            case 4:
                this.item1.setText("A- "+ Html.fromHtml(splitItems[0]).toString()+"\n");
                this.item1.setVisibility(View.VISIBLE);
                this.item2.setText("B- "+Html.fromHtml(splitItems[1]).toString()+"\n");
                this.item2.setVisibility(View.VISIBLE);
                this.item3.setText("C- "+Html.fromHtml(splitItems[2]).toString()+"\n");
                this.item3.setVisibility(View.VISIBLE);
                this.item4.setText("D- "+Html.fromHtml(splitItems[3]).toString()+"\n");
                this.item4.setVisibility(View.VISIBLE);
                break;
            case 5:
                this.item1.setText("A- "+Html.fromHtml(splitItems[0]).toString()+"\n");
                this.item1.setVisibility(View.VISIBLE);
                this.item2.setText("B- "+Html.fromHtml(splitItems[1]).toString()+"\n");
                this.item2.setVisibility(View.VISIBLE);
                this.item3.setText("C- "+Html.fromHtml(splitItems[2]).toString()+"\n");
                this.item3.setVisibility(View.VISIBLE);
                this.item4.setText("D- "+Html.fromHtml(splitItems[3]).toString()+"\n");
                this.item4.setVisibility(View.VISIBLE);
                this.item5.setText("E- "+Html.fromHtml(splitItems[4]).toString()+"\n");
                this.item5.setVisibility(View.VISIBLE);
                break;
            default:
        }
        return false;
    }

    public void setScheme(Bitmap createsScheme)
    {
        schema.setImageBitmap(createsScheme);
    }

    public void validQuestion(View v)
    {
        String[] splitItems = this.itemsQuestions.get(currentQuestion).split("\\|-\\|");
        String questionsCochees = "";
        String reponseDonne = "";
        if(this.item1.isChecked() || this.item2.isChecked() || this.item3.isChecked() || this.item4.isChecked() ||this.item5.isChecked())
        {
            if(this.item1.isChecked())
            {
                questionsCochees += "0|";
                reponseDonne += Html.fromHtml(splitItems[0]).toString()+"\n";
            }
            if(this.item2.isChecked())
            {
                questionsCochees += "1|";
                reponseDonne += Html.fromHtml(splitItems[1]).toString()+"\n";
            }
            if(this.item3.isChecked())
            {
                questionsCochees += "2|";
                reponseDonne += Html.fromHtml(splitItems[2]).toString()+"\n";
            }
            if(this.item4.isChecked())
            {
                questionsCochees += "3|";
                reponseDonne += Html.fromHtml(splitItems[3]).toString()+"\n";
            }
            if(this.item5.isChecked())
            {
                questionsCochees += "4|";
                reponseDonne += Html.fromHtml(splitItems[4]).toString()+"\n";
            }

            this.reponsesDonneesAff.add(reponseDonne);
            String reponseAttendue = genererReponseAttendue();
            this.reponsesAttenduesAff.add(reponseAttendue);
            this.reponsesDonnees.add(currentQuestion, questionsCochees);
            currentQuestion++;
            afficQCM();
        }
    }

    public void sauterQuestion(View v)
    {
        this.reponsesDonnees.add(currentQuestion, "");
        this.reponsesDonneesAff.add(currentQuestion, "Vous avez passé la question\n");
        String reponseAttendue = genererReponseAttendue();
        this.reponsesAttenduesAff.add(currentQuestion, reponseAttendue);
        currentQuestion++;
        afficQCM();
    }

    public String genererReponseAttendue()
    {
        String reponseAttendue = "";
        String[] splitItems = this.itemsQuestions.get(currentQuestion).split("\\|-\\|");

        if(reponsesQuestions.get(currentQuestion).contains("0"))
        {
            reponseAttendue += Html.fromHtml(splitItems[0]).toString()+"\n";
        }
        if(reponsesQuestions.get(currentQuestion).contains("1"))
        {
            reponseAttendue += Html.fromHtml(splitItems[1]).toString()+"\n";
        }
        if(reponsesQuestions.get(currentQuestion).contains("2"))
        {
            reponseAttendue += Html.fromHtml(splitItems[2]).toString()+"\n";
        }
        if(reponsesQuestions.get(currentQuestion).contains("3"))
        {
            reponseAttendue += Html.fromHtml(splitItems[3]).toString()+"\n";
        }
        if(reponsesQuestions.get(currentQuestion).contains("4"))
        {
            reponseAttendue += Html.fromHtml(splitItems[4]).toString()+"\n";
        }

        return reponseAttendue;
    }

    public void finQCM()
    {
        Intent intent = new Intent(Questions.this, Reponses.class);
        intent.putStringArrayListExtra("reponsesDonnees", this.reponsesDonnees);
        intent.putStringArrayListExtra("reponsesQuestions", this.reponsesQuestions);
        intent.putStringArrayListExtra("ennonces", this.ennoncesQuestions);
        intent.putStringArrayListExtra("items", this.itemsQuestions);
        intent.putStringArrayListExtra("reponsesDonneesAff", this.reponsesDonneesAff);
        intent.putStringArrayListExtra("reponsesAttenduesAff", this.reponsesAttenduesAff);
        intent.putExtra("loginU", this.loginU);
        intent.putExtra("passU", this.passU);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.score_evolution);
        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.retour_matieres:
                Intent intent = new Intent(Questions.this, ChoixMat.class);
                intent.putExtra("loginU", this.loginU);
                intent.putExtra("passU", this.passU);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
    }

    public class AsyncDatas extends AsyncTask<Void, Integer, Boolean>
    {
        private JSONArray questions;

        private WeakReference<Questions> mActivity = null;

        public AsyncDatas (Questions pActivity)
        {
            link(pActivity);
        }

        public void link (Questions pActivity)
        {
            this.mActivity = new WeakReference<Questions>(pActivity);
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            ConnexionBDD recupQuestions = new ConnexionBDD(loginU, passU, idMatiere);
            this.questions =  recupQuestions.getData("http://testapp.assocoma.fr/getQCM.php");

            if(this.questions.length() > 0)
                return true;
            else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            if (mActivity.get() != null)
            {
                this.mActivity.get().dialog.dismiss();

                for (int i = 0; i < this.questions.length(); i++)
                {
                    JSONObject questions = null;
                    try
                    {
                        questions = this.questions.getJSONObject(i);
                        ennoncesQuestions.add(i,questions.getString("ennonce").replace("\\'", "'"));
                        itemsQuestions.add(i, questions.getString("items").replace("\\'", "'"));
                        reponsesQuestions.add(i,  questions.getString("reps"));
                        urlSchema.add(i, questions.getString("img"));
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                afficQCM();
            }
            else
            {
                this.mActivity.get().dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class AsyncAvatar extends AsyncTask<String, Integer, Boolean>
    {
        private Bitmap scheme;

        @Override
        protected Boolean doInBackground(String... params)
        {
            try
            {
                if(params[0].length() > 0)
                {
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    scheme = BitmapFactory.decodeStream(input);
                }
                else
                {
                    scheme = null;
                }
                return true;
            }
            catch (IOException e)
            {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            setScheme(scheme);
        }
    }
}