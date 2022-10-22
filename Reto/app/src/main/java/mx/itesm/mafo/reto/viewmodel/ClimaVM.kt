package mx.itesm.mafo.reto.viewmodel
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import mx.itesm.mafo.reto.model.Clima

class ClimaVM: ViewModel() {
    private val modelo= Clima()

    // Solicitamos los valores de las temperaturas en la manana, tarde y noche
    fun obtenervarupforecast(morning: TextView, afternoon: TextView, night: TextView, queue: RequestQueue){
        modelo.varUpForecast(morning,afternoon,night,queue)

    }

    // Solicitamos valores de la presion, humedad, viento, etc
    fun obtenervartemp(queue: RequestQueue, temp: TextView, tempmax: TextView, tempmin: TextView, hume: TextView, presion: TextView, viento: TextView, fecha: TextView,
                       icono: ImageView){
        modelo.vartemp(queue,temp,tempmax,tempmin,hume,presion,viento,fecha,icono)

    }
}
