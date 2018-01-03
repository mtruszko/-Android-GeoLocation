package com.example.maro.prj4and.Place

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by maro on 04.01.2018.
 */

object FirebaseDB {

    var db = FirebaseFirestore.getInstance()

    fun saveToFirebase(product: Place) {
        db.collection("places")
                .document(product.name)
                .set(product)
    }


    fun readListOfProducts(completion: (List<Place>) -> Unit) {

        db.collection("places")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val arrayOfProducts: MutableList<Place> = mutableListOf()
                        for (document in task.result) {
                            Log.d(TAG, document.id + " => " + document.data)
                            val item = document.toObject(Place::class.java)
                            arrayOfProducts.add(item)
                        }
                        completion(arrayOfProducts)

                    } else {
                        Log.d(TAG, "get failed with ", task.exception)
                    }
                }
    }

    fun deleteProduct(product: Place) {
        db.collection("places")
                .document(product.name)
                .delete()
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

    }
}