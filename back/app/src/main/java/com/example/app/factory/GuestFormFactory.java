package com.example.app.factory;

import java.util.List;
import org.springframework.stereotype.Component;
import com.example.app.factory.FormField;
import com.example.app.model.GenericField;
import com.example.app.model.FormFactory; 
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.api.core.ApiFuture;
import com.google.firebase.cloud.FirestoreClient;
import java.util.ArrayList;

@Component
public class GuestFormFactory implements FormFactory {

    @Override
    public List<FormField> createFormFields() {
        List<FormField> formFields = new ArrayList<>();
        try{
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> future = db.collection("formFields").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments(); 
            for (QueryDocumentSnapshot doc : documents) {
                String type = doc.getString("type");
                String label = doc.getString("label");
                boolean required = doc.getBoolean("required");

                // Imprime los datos del campo de depuración
                System.out.println("----"+ type + "----" + label + "----" + required);
                // Crea un nuevo campo genérico y lo agrega a la lista
                formFields.add(new GenericField(type, label, required != null && required));
            }
        } catch (Exception e) {
            // Manejo de excepciones
            System.err.println("Error al obtener los campos del formulario: " + e.getMessage());
        }
        return formFields;
    } 
}