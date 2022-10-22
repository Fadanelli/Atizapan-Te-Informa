package mx.itesm.mafo.reto.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.itesm.mafo.reto.R
import mx.itesm.mafo.reto.databinding.ActivityRegistroNotificacionesBinding

class registro_notificaciones : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroNotificacionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroNotificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchNotis()
        refresh()

    }
    // Refrescar las notificaciones con un boton
    private fun refresh() {
        binding.btnRefrescar.setOnClickListener {
            fetchNotis()
        }
    }

    // Llama a la API para obtener las ultimas 10 notificaciones
    private fun fetchNotis() {
        val nt1 = findViewById<TextView>(R.id.txtNotif1)
        val nt2 = findViewById<TextView>(R.id.txtNotif2)
        val nt3 = findViewById<TextView>(R.id.txtNotif3)
        val nt4 = findViewById<TextView>(R.id.txtNotif4)
        val nt5 = findViewById<TextView>(R.id.txtNotif5)
        val nt6 = findViewById<TextView>(R.id.txtNotif6)
        val nt7 = findViewById<TextView>(R.id.txtNotif7)
        val nt8 = findViewById<TextView>(R.id.txtNotif8)
        val nt9 = findViewById<TextView>(R.id.txtNotif9)
        val nt10 = findViewById<TextView>(R.id.txtNotif10)

        // Llamada a la API
        val queue = Volley.newRequestQueue(this)
        val url = "https://snowker.xyz/coords"

        // Llamada en formato JSON
        val notiRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val obj = response.getJSONObject(0)
                val notis = obj.getJSONArray("Notifications")
                // Creamos array y recorremos la respuesta para asignarle las notificaciones
                val list: ArrayList<String> = ArrayList()
                for (i in 0 until notis.length()) {
                    list.add(notis[i].toString())
                }
                // Damos formato para asignarlas
                list.reverse()
                val arr = arrayOf("", "", "", "", "", "", "", "", "", "")
                for (c in 0..9) {
                    if (c >= list.size) {
                        arr[c] = ""
                    } else {
                        arr[c] = list.elementAt(c)
                    }
                }
                // Establecemos el orden de visualizacion
                nt1.text = arr[0]
                nt2.text = arr[1]
                nt3.text = arr[2]
                nt4.text = arr[3]
                nt5.text = arr[4]
                nt6.text = arr[5]
                nt7.text = arr[6]
                nt8.text = arr[7]
                nt9.text = arr[8]
                nt10.text = arr[9]
            },
            // En caso de fallar la llamada
            { nt1.text = "That didn't work!" })

        // Agregamos al stack de requests
        queue.add(notiRequest)
    }

}