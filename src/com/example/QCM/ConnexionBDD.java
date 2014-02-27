package com.example.QCM;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Sidd8 on 30/01/14.
 */
public class ConnexionBDD
{
    private ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    private String login;
    private String password;
    public String ennonce[] = new String[500];
    public String items[] = new String[500];
    public String returnString;

    public ConnexionBDD(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public ConnexionBDD()
    {

    }

    public String[] getEnnonce()
    {
        return this.ennonce;
    }

    public String[] getItems()
    {
        return this.items;
    }

    //useless pour le moment
    public boolean setConnection(String url)
    {
        InputStream is = null;
        this.nameValuePairs.add(new BasicNameValuePair("login", this.login));
        this.nameValuePairs.add(new BasicNameValuePair("pass", this.password));
        String result = null;

        Log.i("tagNV",this.login + " " + this.password);
        Log.i("tagURL",url);
        // Envoi de la commande http
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            is = entity.getContent();

            //conversion de la réponse en chaine de caractère
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));

                StringBuilder sb  = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }

                is.close();

                result = sb.toString();
                if(result.length() > 0)
                {
                    return false;
                }
            }
            catch(Exception e)
            {
                Log.i("tagconvertstr", "" + e.toString());
                return false;
            }

            Log.i("set_connection", "ok !");
            return true;
        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error in http connection " + e.toString());
            return false;
        }
    }

    protected String getData(String ue, String nom)
    {
        Log.i("taggetData","In Get DATA");
        String url = "http://testapp.assocoma.fr/QCMgetData.php";
        String result = "";
        InputStream is = null;

        this.nameValuePairs.add(new BasicNameValuePair("ue", ue));
        this.nameValuePairs.add(new BasicNameValuePair("nom", nom));
        // Envoi de la commande http
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.i("set_connection", "ok !");
        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        // Convertion de la requête en string
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")),8);//iso-8859-1
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        // Parse les données JSON
        returnString = "";
        try
        {
            JSONArray jArray = new JSONArray(result);
            Log.i("res_taillearray"," : "+jArray.length());
            for(int i=0;i<jArray.length();i++)
            {
                JSONObject json_data = jArray.getJSONObject(i);
                this.ennonce[i] =  json_data.getString("ennonce"/* + "#,#"*/);
                this.items[i] = json_data.getString("items")/* + "#,#"*/;
                returnString += this.ennonce[i] + "#%_%#" + this.items[i] + "#%_%#";
            }
            Log.i("tagReturnString",returnString);
            return returnString;
        }
        catch(JSONException e)
        {
            Log.e("log_tag", "Error parsing data getData" + e.toString());
        }
        return returnString;
    }
}
