package com.example.youpiman.moodtracker.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.youpiman.moodtracker.model.SQLiteDataBaseHelper;

/**
 * Created by Youpiman on 22/11/2017.
 */

public class DBManagement {

    private static final int NUM_COL_0 = 0;
    private static final int NUM_COL_1 = 1;
    private static final int NUM_COL_2 = 2;
    private static final int NUM_COL_3 = 3;
    private static final int NUM_COL_4 = 4;
    private static final int NUM_COL_5 = 5;
    private static final int NUM_COL_6 = 6;
    private static final int NUM_COL_7 = 7;
    private static final int NUM_COL_8 = 8;
    public static final String TABLE_NAME = "Comment_table";
    public static final String COL_0= "ID";
    public static final String COL_1 = "Il y'a une semaine";
    public static final String COL_2 = "Il y'a six jours";
    public static final String COL_3 = "Il y'a cinq jours";
    public static final String COL_4 = "Il y'a quatre jours";
    public static final String COL_5 = "Il y'a trois jours";
    public static final String COL_6 = "Avant-hier";
    public static final String COL_7 = "Hier";
    public static final String COL_8 = "Aujourd'hui";


    private SQLiteDatabase bdd;

    private SQLiteDataBaseHelper myBaseSQLite;

    public DBManagement(Context context){
        //On créer la base de données et sa table
        myBaseSQLite = new SQLiteDataBaseHelper(context);
    }

    public void open(){
        //on ouvre la base de données en écriture
        bdd = myBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la base de données
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertComment(Comment comment){
        //Création d'un ContentValues
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_8, comment.getComment());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_NAME, null, values);
    }

    public int updateComment(int id, Comment comment){
        ContentValues values = new ContentValues();
        values.put(COL_8, comment.getComment());
        return bdd.update(TABLE_NAME, values,COL_0 + " = " +id, null);
    }

    public int removeLivreWithID(int id){
        //Suppression d'un commentaire de la BDD grâce à l'ID
        return bdd.delete(TABLE_NAME, COL_0 + " = " +id, null);
    }

    public Comment getComment(){
        //Récupère dans un Cursor les valeur correspondant à un commentaire contenu dans la BDD (ici on sélectionne le commentaire d'aujourd'hui)
        Cursor c = bdd.query(TABLE_NAME, new String[] {COL_0}, COL_8, null, null, null, null);
        return cursorToComment(c);
    }

    //Cette méthode permet de convertir un cursor en un commentaire
        private Comment cursorToComment(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un commentaire
        Comment comment = new Comment();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        comment.setId(c.getInt(NUM_COL_0));
        comment.setComment(c.getString(NUM_COL_8));
        //On ferme le cursor
        c.close();
        //On retourne le commentaire
        return comment;
    }
}
