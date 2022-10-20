package mx.itesm.mafo.reto.model

import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import mx.itesm.mafo.reto.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class Clima {


    fun varUpForecast(morning: TextView, afternoon: TextView, night: TextView, queue: RequestQueue){


        val Vqueue= queue
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
        Vqueue.add(stringRequest)

    }

    fun vartemp(queue: RequestQueue,
                temp: TextView,
                tempmax: TextView,
                tempmin: TextView,
                hume: TextView,
                presion: TextView,
                viento: TextView,
                fecha: TextView,
                icono: ImageView ){
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=19.55487630507079&lon=-99.24788474160383&appid=d07df6814ab5248acd51864441e81122&units=metric"
        val df = SimpleDateFormat("dd/M/yyy")
        val Vqueue= queue

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
                viento.text = vie + " km/h"
                fecha.text = "Fecha: " + currentDate
            },
            { temp.text = "That didn't work!" })

// Add the request to the RequestQueue.
        Vqueue.add(weater_Request)

    }


}