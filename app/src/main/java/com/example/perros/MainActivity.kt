package com.example.perros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perros.adapter.PerrosAdapter
import com.example.perros.databinding.ActivityMainBinding
import com.example.perros.response.PerroResponse
import com.example.perros.service.PerrosAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: PerrosAdapter
    private lateinit var binding: ActivityMainBinding
    private val perrosPics = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
    }
    private fun initAdapter(){
        adapter = PerrosAdapter(perrosPics)
        binding.perros.layoutManager = LinearLayoutManager(this)
        binding.perros.adapter = adapter
        buscarPerrosPorRaza("labrador")

    }
    private fun getRetroFit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl( "https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    private fun buscarPerrosPorRaza(raza: String){
        CoroutineScope(Dispatchers.IO).launch {
            val llamado = getRetroFit().create(PerrosAPIService::class.java)
                .getPerrosPorRaza("$raza/images")
            val perrosResponse : PerroResponse? = llamado.body()

            if(llamado.isSuccessful){
                val imagenes: List<String> = perrosResponse?.imagenes?:emptyList()
                perrosPics.clear()
                perrosPics.addAll(imagenes)

            }else{

                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()

            }
        }
    }
}