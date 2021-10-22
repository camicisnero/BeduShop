package org.bedu.bedushop

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.*
import org.bedu.bedushop.classes.ProductR
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception

class MainApp: Application() {

    private val url = "https://fakestoreapi.com/products"
    private var jsonString: String? = null

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        getProducts()

    }

    fun getProducts() {
        val okHttpClient = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        val clientBuilder = okHttpClient.newBuilder()
        clientBuilder.build()
            .newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("API", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    try {
                        if (response.isSuccessful) {
                            jsonString = body.toString()
                            val array = if (jsonString != null) JSONArray(jsonString) else JSONArray()

                            //Log.e("array", array.toString())
                            val config = RealmConfiguration
                                .Builder()
                                .initialData { realm ->
                                    for (i in 0 until array.length()){
                                        val p = realm.createObject(ProductR::class.java, i)
                                        p.title = array.getJSONObject(i).getString("title")
                                        p.description = array.getJSONObject(i).getString("description")
                                        p.price = array.getJSONObject(i).getString("price").toFloat()
                                        p.rate = array.getJSONObject(i).getJSONObject("rating").getString("rate").toFloat()
                                        p.count = array.getJSONObject(i).getJSONObject("rating").getInt("count")
                                        p.category = array.getJSONObject(i).getString("category")
                                        p.image = array.getJSONObject(i).getString("image")
                                    }
                                }
                                .deleteRealmIfMigrationNeeded()
                                .name("realmDB.realm")
                                .build()
                            Realm.setDefaultConfiguration(config)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        getProducts()
                    }
                }
            })
    }

}