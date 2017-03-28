package henteti.feres.firebase;

import android.graphics.Region;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by feres on 3/27/2017.
 */

//la classe User doit implémenter "Serializable" pour pouvoir convertir les données
// lues de firebase directement à un objet User

public class User implements Serializable{

    private String nom;
    private String prenom;
    private String mail;
    private String motpasse;
    private String telephone;
    private String adresse;
    private String codepostal;
    private String societe;


    public User(){}

    //region getters_and_setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotpasse() {
        return motpasse;
    }

    public void setMotpasse(String motpasse) {
        this.motpasse = motpasse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telehohone) {
        this.telephone = telehohone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }
    //endregion


}
