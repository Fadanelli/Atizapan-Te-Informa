package mx.itesm.mafo.reto.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.android.volley.toolbox.Volley
import mx.itesm.mafo.reto.R
import mx.itesm.mafo.reto.databinding.ClimaBinding
import mx.itesm.mafo.reto.viewmodel.ClimaVM
import java.util.*

class Clima : AppCompatActivity() {

    private lateinit var binding: ClimaBinding
    private val convertidorVM: ClimaVM by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ClimaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        UpdateTemp()
        UpdateForecast()
        val btnweb = findViewById(R.id.btnweb) as ImageButton
        btnweb.setOnClickListener {
            val inter = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.meteored.mx/clima_Atizapan+De+Zaragoza-America+Norte-Mexico-Mexico-MMJC-1-22286.html")
            )
            startActivity(inter)
        }

    }



    private fun UpdateForecast() {
        val morning = findViewById<TextView>(R.id.dMañana)
        val afternoon = findViewById<TextView>(R.id.dTarde)
        val night = findViewById<TextView>(R.id.dNoche)
        //temp.text= viewModel.descargarTemperaturas().toString()
        val queue = Volley.newRequestQueue(this)
        convertidorVM.obtenervarupforecast(morning,afternoon,night,queue)


    }





    private fun UpdateTemp() {
        val temp = findViewById<TextView>(R.id.temperatura)
        val tempmin = findViewById<TextView>(R.id.TempMin)
        val tempmax = findViewById<TextView>(R.id.TempMax)
        val hume = findViewById<TextView>(R.id.dhumedad)
        val presion = findViewById<TextView>(R.id.dpresi)
        val viento = findViewById<TextView>(R.id.dviento)
        val fecha = findViewById<TextView>(R.id.fecha)
        val icono = findViewById<ImageView>(R.id.imagencl)
        val queue = Volley.newRequestQueue(this)
        convertidorVM.obtenervartemp(queue,temp,tempmax,tempmin,hume,presion,viento,fecha,icono)
    }


}