package com.example.a21602196.tp5;



import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Annonce implements Parcelable {

    private String id;
    private String titre;
    private String description;
    private int prix;
    private String pseudo;
    private String mail;
    private String tel;
    private String ville;
    private int cp;
    private ArrayList<String> image;
    private String date;
    private boolean isFavorite ;


    protected Annonce(Parcel in) {
        id = in.readString();
        titre = in.readString();
        description = in.readString();
        prix = in.readInt();
        pseudo = in.readString();
        mail = in.readString();
        tel = in.readString();
        ville = in.readString();
        cp = in.readInt();
        image = in.createStringArrayList();
        date = in.readString();
        isFavorite = false;
    }

    public static final Creator<Annonce> CREATOR = new Creator<Annonce>() {
        @Override
        public Annonce createFromParcel(Parcel in) {
            return new Annonce(in);
        }

        @Override
        public Annonce[] newArray(int size) {
            return new Annonce[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void addImage(String image) {
        this.image.add(image);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImageSize(){
        return this.image.size();
    }


    public Annonce(JSONObject resultat) throws JSONException {
        this.id = resultat.getString("id");
        this.titre = resultat.getString("titre");
        this.description = resultat.getString("description");
        this.prix = resultat.getInt("prix");
        this.pseudo = resultat.getString("pseudo");
        this.mail = resultat.getString("emailContact");
        this.tel = resultat.getString("telContact");
        this.ville = resultat.getString("ville");
        this.cp = resultat.getInt("cp");
        this.image = new ArrayList<String>(Arrays.asList(resultat.getString("images").split(",")));
        this.date = resultat.getString("date");
        this.isFavorite = false;
    }

    public Annonce(String id, String titre, String description, int prix, String pseudo, String mail, String tel, String ville, int cp, ArrayList<String> image, String date) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.pseudo = pseudo;
        this.mail = mail;
        this.tel = tel;
        this.ville = ville;
        this.cp = cp;
        this.image = image;
        this.date = date;
    }

    @Override
    public String toString(){
        Log.i("toString",""+this.id+" "+this.titre+" "+this.description+" "+this.prix+" "+this.pseudo+" "+this.mail+" "+this.tel+" "+this.ville+" "+this.cp+" "+this.date);
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(titre);
        parcel.writeString(description);
        parcel.writeInt(prix);
        parcel.writeString(pseudo);
        parcel.writeString(mail);
        parcel.writeString(tel);
        parcel.writeString(ville);
        parcel.writeInt(cp);
        parcel.writeStringList(image);
        parcel.writeString(date);
    }

    public boolean getFavorite() {
        return this.isFavorite;
    }

    public void setFavorite(boolean bool) {
        this.isFavorite = bool;
    }
}
