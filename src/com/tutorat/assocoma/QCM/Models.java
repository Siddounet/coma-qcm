package com.tutorat.assocoma.QCM;

import java.util.ArrayList;

/**
 * Created by Sidd8 on 13/10/2014.
 */
public class Models
{
    private String enonce;
    private String reponseDonnee;
    private String reponseAttendue;
    private boolean etatReponse;

    public Models(String enonce, String reponseDonnee, String reponseAttendue, boolean etatReponse)
    {
        this.enonce = enonce;
        this.reponseDonnee = reponseDonnee;
        this.reponseAttendue = reponseAttendue;
        this.etatReponse = etatReponse;
    }

    public String getEnonce()
    {
        return this.enonce+"\n";
    }

    public String getReponseDonnee()
    {
        return this.reponseDonnee;
    }

    public String getReponseAttendue()
    {
        return this.reponseAttendue;
    }

    public boolean getEtatReponse()
    {
        return this.etatReponse;
    }
}
