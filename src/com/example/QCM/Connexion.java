package com.example.QCM;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class Connexion extends Activity
{
    /**
     * Called when the activity is first created.
     */

    public EditText name;
    public EditText pass;
    private TextView err;
    private ProgressDialog dialog;
    private AsyncCo newCo = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
    }

    public EditText getName()
    {
        return this.name;
    }

    public EditText getPass()
    {
        return this.pass;
    }

    public void valider_Co(View v)
    {
        this.name = (EditText)findViewById(R.id.et_Name);
        this.pass = (EditText)findViewById(R.id.et_Pass);
        this.err = (TextView)findViewById(R.id.tv_Err);

        if(this.name.length() > 0)
        {
            if(this.pass.length() > 0)
            {
                this.err.setText("");

                this.newCo = new AsyncCo(Connexion.this);

                this.dialog = ProgressDialog.show(this, "Loading", "Please wait...", true);

                this.newCo.execute();
            }
            else
            {
                this.err.setText("Mot de passe vide !");
            }
        }
        else
        {
            this.err.setText("Mot de passe ou Identifiant vide ! ");
        }
    }

    public class AsyncCo extends AsyncTask<Void, Integer, Boolean>
    {
        private WeakReference<Connexion> mActivity = null;

        public AsyncCo (Connexion pActivity)
        {
            link(pActivity);
        }

        public void link (Connexion pActivity)
        {
            this.mActivity = new WeakReference<Connexion>(pActivity);
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            ConnexionBDD connection;
            connection = new ConnexionBDD(this.mActivity.get().getName().getText().toString(), this.mActivity.get().getPass().getText().toString());
            Boolean etatCo = connection.setConnection("http://testapp.assocoma.fr/connexion.php");
            return etatCo;
        }

        @Override
        protected void onPostExecute (Boolean result)
        {
            if (mActivity.get() != null)
            {
                if(result)
                {
                    this.mActivity.get().dialog.dismiss();
                    Log.i("tagEtatCo","Oui");
                    this.mActivity.get().err.setText("");
                    //Appel vue suivante
                    Intent intent = new Intent(Connexion.this, ChoixMat.class);
                    startActivity(intent);
                }
                else
                {
                    this.mActivity.get().dialog.dismiss();
                    Log.i("ErrorCo", "Problème lors de la connection");
                    this.mActivity.get().err.setText("Problème lors de la connection - Veuillez réessayer !");
                }
            }
        }
    }
}
