package tech.orionn.prototipo.helpers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InternalFirebase {

    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference databaseReference;

    public static FirebaseDatabase getFirebaseDatabaseReference() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
        }
        return firebaseDatabase;
    }

    public static DatabaseReference getDatabaseReferenceToUser() {
        firebaseDatabase = getFirebaseDatabaseReference();
        databaseReference = firebaseDatabase.getReference();
        return databaseReference.child("user");
    }

    public static DatabaseReference getDatabaseReferenceToProject() {
        firebaseDatabase = getFirebaseDatabaseReference();
        databaseReference = firebaseDatabase.getReference();
        return databaseReference.child("projects");
    }

    public static DatabaseReference getDatabaseReferenceToProjectWithUser() {
        firebaseDatabase = getFirebaseDatabaseReference();
        databaseReference = firebaseDatabase.getReference();
        return databaseReference.child("projects");
    }
}
