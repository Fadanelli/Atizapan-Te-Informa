package mx.itesm.mafo.reto

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import mx.itesm.mafo.reto.databinding.ActivityMainBinding
import mx.itesm.mafo.reto.databinding.ClimaBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class clima : AppCompatActivity(){

    private lateinit var binding: ClimaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ClimaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        UpdateTemp()
        UpdateForecast()
        val btnweb = findViewById(R.id.btnweb) as ImageButton
        btnweb.setOnClickListener{
            val inter = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.meteored.mx/clima_Atizapan+De+Zaragoza-America+Norte-Mexico-Mexico-MMJC-1-22286.html")
            )
            startActivity(inter)
        }
        val volver  = findViewById(R.id.volver) as ImageButton
        volver.setOnClickListener{
            val inter2 = Intent(this, MainActivity::class.java)
            startActivity(intent)
            startActivity(inter2)
        }
        }


    @SuppressLint("SetTextI18n")
    private fun UpdateForecast() {
        val morning = findViewById<TextView>(R.id.dMañana)
        val afternoon = findViewById<TextView>(R.id.dTarde)
        val night = findViewById<TextView>(R.id.dNoche)
        //temp.text= viewModel.descargarTemperaturas().toString()
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/forecast?lat=19.55487630507079&lon=-99.24788474160383&appid=d07df6814ab5248acd51864441e81122&units=metric&cnt=5"

        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Display the first 500 characters of the response string.
                val arr = ArrayList<String>()
                val list = response.getJSONArray("list")
                for (i in 0 until list.length()) {
                    val item = list.getJSONObject(i)
                    val main = item.getJSONObject("main")
                    val tempe = main.getString("temp")
                    arr.add(tempe)
                }
                morning.text = arr[0].toDouble().roundToInt().toString() + "°C"
                afternoon.text = arr[2].toDouble().roundToInt().toString() + "°C"
                night.text = arr[4].toDouble().roundToInt().toString() + "°C"
            },
            { morning.text = "That didn't work!" })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }




    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun UpdateTemp() {
        val temp = findViewById<TextView>(R.id.temperatura)
        val tempmin = findViewById<TextView>(R.id.TempMin)
        val tempmax = findViewById<TextView>(R.id.TempMax)
        val hume = findViewById<TextView>(R.id.dhumedad)
        val presion = findViewById<TextView>(R.id.dpresi)
        val viento = findViewById<TextView>(R.id.dviento)
        val df = SimpleDateFormat("dd/M/yyy")
        val fecha = findViewById<TextView>(R.id.fecha)
        val icono = findViewById<ImageView>(R.id.imagencl)


        //temp.text= viewModel.descargarTemperaturas().toString()
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=19.55487630507079&lon=-99.24788474160383&appid=d07df6814ab5248acd51864441e81122&units=metric"

        val weater_Request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Display the first 500 characters of the response string.
                val main = response.getJSONObject("main")
                val tempe = main.getString("temp")
                val tempemin = main.getString("temp_min")
                val tempemax = main.getString("temp_max")
                val humed = main.getString("humidity")
                val press = main.getString("pressure")
                val wind = response.getJSONObject("wind")
                val vie = wind.getString("speed")
                val currentDate = df.format(Date())
                val stado = response.getJSONArray("weather")
                val cero = stado.getJSONObject(0)
                val maindes = cero.getString("main")


                if (maindes == "Clouds" || maindes == "Atmosphere") {

                    icono.setImageResource(R.drawable.cloudy)

                }
                if (maindes == "Clear") {
                    icono.setImageResource(R.drawable.sun)

                }
                if (maindes == "Rain" || maindes == "Drizzle") {
                    icono.setImageResource(R.drawable.rain)
                }
                if (maindes == "thunderstorm") {
                    icono.setImageResource(R.drawable.storm)
                }

                temp.text = tempe.toDouble().roundToInt().toString() + "°C"
                tempmin.text = "Temp min " + tempemin.toDouble().roundToInt().toString() + "°C"
                tempmax.text = "Temp max " + tempemax.toDouble().roundToInt().toString() + "°C"
                hume.text = humed
                presion.text = press
                viento.text = "$vie km/h"
                fecha.text = "Fetch: $currentDate"
            },
            { temp.text = "That didn't work!" })

// Add the request to the RequestQueue.
        queue.add(weater_Request)
    }

    }
