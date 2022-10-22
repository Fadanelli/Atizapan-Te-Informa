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
    // Obtiene clima actual y futuro ademas de otros datos de la API
    fun varUpForecast(morning: TextView, afternoon: TextView, night: TextView, queue: RequestQueue){
        // Crea stack de la llamada
        val Vqueue = queue
        // Link del endpoint
        val url = "https://api.openweathermap.org/data/2.5/forecast?lat=19.55487630507079&lon=-99.24788474160383&appid=d07df6814ab5248acd51864441e81122&units=metric&cnt=5"
        // Request en formato JSON
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
                // Asignamos valor y le asignamos el tipo de dato correcto
                morning.text = arr[0].toDouble().roundToInt().toString() + "°C"
                afternoon.text = arr[2].toDouble().roundToInt().toString() + "°C"
                night.text = arr[4].toDouble().roundToInt().toString() + "°C"
            },
            { morning.text = "That didn't work!" })

        // Agregamos al stack de requests
        Vqueue.add(stringRequest)

    }

    // Obtenemos datos como el viento, humeda y presion de otro endpoint
    fun vartemp(queue: RequestQueue,
                temp: TextView,
                tempmax: TextView,
                tempmin: TextView,
                hume: TextView,
                presion: TextView,
                viento: TextView,
                fecha: TextView,
                icono: ImageView ){
        // Link del endpoint
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=19.55487630507079&lon=-99.24788474160383&appid=d07df6814ab5248acd51864441e81122&units=metric"
        // Formato para la fecha
        val df = SimpleDateFormat("dd/M/yyy")
        // Creamos stack de requests
        val Vqueue = queue

        // Llamada a la API en formato JSON
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


                // Establecemos el icono a desplegar dependiendo del tipo de clima pronosticado
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
                // Damos formato a los datos obtenidos
                temp.text = tempe.toDouble().roundToInt().toString() + "°C"
                tempmin.text = "Temp min " + tempemin.toDouble().roundToInt().toString() + "°C"
                tempmax.text = "Temp max " + tempemax.toDouble().roundToInt().toString() + "°C"
                hume.text = humed
                presion.text = press
                viento.text = vie + " km/h"
                fecha.text = "Fecha: " + currentDate
            },
            // En caso de fallar la llamada
            { temp.text = "That didn't work!" })

        // Agregamos al stack de requests
        Vqueue.add(weater_Request)

    }


}