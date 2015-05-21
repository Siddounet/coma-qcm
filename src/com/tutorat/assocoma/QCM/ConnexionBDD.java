package com.tutorat.assocoma.QCM;

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
    private String idMatiere = "";

    public ConnexionBDD(String login, String password)
    {
        this.login = login;
        this.password = password;

        this.nameValuePairs.add(new BasicNameValuePair("login", this.login));
        this.nameValuePairs.add(new BasicNameValuePair("pass", this.password));
    }

    public ConnexionBDD(String login, String password, String idMatiere)
    {
        this.login = login;
        this.password = password;
        this.idMatiere = idMatiere;

        this.nameValuePairs.add(new BasicNameValuePair("login", this.login));
        this.nameValuePairs.add(new BasicNameValuePair("pass", this.password));
        this.nameValuePairs.add(new BasicNameValuePair("idMatiere", this.idMatiere));
    }

    public ConnexionBDD()
    {

    }

    //useless pour le moment
    public boolean setConnection(String url)
    {
        InputStream is = null;
        String result = null;
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
                if(result.contains("null"))
                {
                    return false;
                }
                else
                    return true;
            }
            catch(Exception e)
            {
                return false;
            }
        }
        catch(Exception e)
        {
            return false;
        }
    }

    protected JSONArray getData(String url)
    {
        String result = "";
        InputStream is = null;

        // Envoi de la commande http
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }
        catch(Exception e)
        {
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
        }

        JSONArray jArray = null;
        try
        {
            jArray = new JSONArray(result);
        }
        catch(JSONException e)
        {
        }
        return jArray;
    }
}
