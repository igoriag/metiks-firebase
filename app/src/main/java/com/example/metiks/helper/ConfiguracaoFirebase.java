package com.example.metiks.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static DatabaseReference referenceFirebase;
    private static FirebaseAuth referenceAuthFirebase;

    //retorna a referencia do Database
    public static DatabaseReference getFirebase(){
        if (referenceFirebase == null){
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenceFirebase;
    }

    //retorna a instancia do Fire Base auth
    public static FirebaseAuth getFirebaseAutenticacao(){
        if (referenceAuthFirebase == null){
            referenceAuthFirebase = FirebaseAuth.getInstance();
        }
        return referenceAuthFirebase;
    }
}
