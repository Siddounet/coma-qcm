package com.tutorat.assocoma.QCM;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.*;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * Created by Sidd8 on 30/01/14.
 */
public class ChoixMat extends FragmentActivity
{
    private String loginU = "";
    private String passU = "";

    private AsyncDatas datas = null;

    private List<String> nomMatiere;
    private ArrayList<String> numeroUe = new ArrayList<String>();
    private ArrayList<String> idMatiere = new ArrayList<String>();
    private List<String> nomSubMatiere;
    private ArrayList<String> idSubMatiere = new ArrayList<String>();

    private Map<String, List<String>> laptopCollection;
    private Map<String, List<String>> idListSousMatieres;

    private ProgressDialog dialog;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choix_matiere);

        this.datas = new AsyncDatas(ChoixMat.this);
        this.dialog = ProgressDialog.show(this, "Chargement", "Veuillez patienter", true);
        this.datas.execute();

        ActionBar actionBar = getActionBar();
    }

    public void remplirListeMatieres()
    {
        ExpandableListView listeMatieres = (ExpandableListView) findViewById(R.id.liste_matieres);

        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, this.nomMatiere, this.laptopCollection, this.idListSousMatieres);
        listeMatieres.setAdapter(expListAdapter);
        expListAdapter.isClicked = false;

        listeMatieres.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                expListAdapter.isClicked = true;
                String selected = (String) expListAdapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent(ChoixMat.this, Questions.class);
                intent.putExtra("loginU", loginU);
                intent.putExtra("passU", passU);
                intent.putExtra("idMatiere", selected);
                startActivity(intent);
                return true;
            }
        });
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.retour_matieres:
                Intent intent = new Intent(ChoixMat.this, ChoixMat.class);
                intent.putExtra("loginU", this.loginU);
                intent.putExtra("passU", this.passU);
                startActivity(intent);
                return true;
            case R.id.score_evolution:
                intent = new Intent(ChoixMat.this, EvolutionScore.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class AsyncDatas extends AsyncTask<Void, Integer, Boolean>
    {
        private JSONArray matieres;

        private WeakReference<ChoixMat> mActivity = null;

        public AsyncDatas (ChoixMat pActivity)
        {
            link(pActivity);
        }

        public void link (ChoixMat pActivity)
        {
            this.mActivity = new WeakReference<ChoixMat>(pActivity);
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            ConnexionBDD recupMatieres = new ConnexionBDD(loginU, passU);
            this.matieres =  recupMatieres.getData("http://testapp.assocoma.fr/getMatieres.php");

            if(this.matieres.length() > 0)
                return true;
            else
                return false;
        }

        protected void onPostExecute(Boolean result)
        {
            if (mActivity.get() != null)
            {
                this.mActivity.get().dialog.dismiss();

                nomMatiere = new ArrayList<String>();
                laptopCollection = new LinkedHashMap<String, List<String>>();
                idListSousMatieres = new LinkedHashMap<String, List<String>>();
                for (int i = 0; i < this.matieres.length(); i++)
                {
                    idSubMatiere = new ArrayList<String>();
                    nomSubMatiere = new ArrayList<String>();
                    JSONObject matiereUe = null;
                    try
                    {
                        matiereUe = this.matieres.getJSONObject(i);
                        nomMatiere.add("UE " + matiereUe.getString("numUe") + " - " + Html.fromHtml(matiereUe.getString("nomMatiere")).toString());
                        numeroUe.add(i, matiereUe.getString("numUe"));
                        idMatiere.add(i, matiereUe.getString("idMatiere"));
                        for(int j = 0; j < matiereUe.getJSONArray("sousMat").length(); j++)
                        {
                            idSubMatiere.add(matiereUe.getJSONArray("sousMat").getJSONObject(j).getString("id"));
                            nomSubMatiere.add(Html.fromHtml(matiereUe.getJSONArray("sousMat").getJSONObject(j).getString("nom")).toString());
                        }
                        laptopCollection.put(nomMatiere.get(i), nomSubMatiere);
                        idListSousMatieres.put(nomMatiere.get(i), idSubMatiere);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                remplirListeMatieres();
            }
            else
            {
                this.mActivity.get().dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        }
    }
}