package com.tutorat.assocoma.QCM;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
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
        setContentView(R.layout.connexion);

        this.name = (EditText)findViewById(R.id.et_Name);
        this.pass = (EditText)findViewById(R.id.et_Pass);
        this.err = (TextView)findViewById(R.id.tv_Err);

        //Récupération des credentials si existant, sinon on demande la connexion
        String loggedInUserName = PrefUtils.getFromPrefs(Connexion.this, "PREFS_LOGIN_USERNAME_KEY","");
        String loggedInUserPassword = PrefUtils.getFromPrefs(Connexion.this, "PREFS_LOGIN_PASSWORD_KEY","");

        if(!loggedInUserName.equals("") && !loggedInUserPassword.equals(""))
        {
            this.name.setText(loggedInUserName);
            this.pass.setText(loggedInUserPassword);
            valider_Co(new View(this));
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
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
                Log.i("connexion_result", result+"");
                if(result)
                {
                    PrefUtils.saveToPrefs(Connexion.this, "PREFS_LOGIN_USERNAME_KEY", this.mActivity.get().getName().getText().toString());
                    PrefUtils.saveToPrefs(Connexion.this, "PREFS_LOGIN_PASSWORD_KEY", this.mActivity.get().getPass().getText().toString());

                    this.mActivity.get().dialog.dismiss();
                    this.mActivity.get().err.setText("");
                    //Appel vue suivante
                    Intent intent = new Intent(Connexion.this, ChoixMat.class);
                    startActivity(intent);
                }
                else
                {
                    this.mActivity.get().dialog.dismiss();
                    this.mActivity.get().err.setText("Problème lors de la connection - Veuillez réessayer !");
                }
            }
        }
    }
}
