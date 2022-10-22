package mx.itesm.mafo.reto.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mx.itesm.mafo.reto.R
import mx.itesm.mafo.reto.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    // Cargamos elementos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        refresh()
    }

    // Volvemos a iniciar la actividad actual y volvemos a llamar a la API
    private fun refresh() {
        binding.button.setOnClickListener {
            finish()
            startActivity(intent)
        }
    }

    // Creamos el mapa
    override fun onMapReady(googleMap: GoogleMap) {

        // Asignamos link al endpoint y creamos stack de requests
        val queue = Volley.newRequestQueue(this)
        val url = "https://snowker.xyz/coords"

        // Llamada a la API en formato JSON
        val coordRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val obj = response.getJSONObject(0)
                val coordArr = obj.getString("Coords")
                val incidentArr = obj.getJSONArray("Incidents")
                println(incidentArr)
                // Comprobamos si no hay incidentes y reasignamos valor del textview
                if (incidentArr.length() == 0) {
                    val t = findViewById<TextView>(R.id.tvCoordenadas)
                    t.text = "No hay incidentes"
                } else {
                    // Damos formato a los datos obtenidos y los asignamos
                    val layout = findViewById<LinearLayout>(R.id.mylayout)
                    val params: ViewGroup.LayoutParams = layout.layoutParams
                    params.width = 1
                    params.height = 1
                    layout.layoutParams = params
                    var coordStr = coordArr.toString()
                    // Separamos las coordenadas individualmente
                    coordStr = coordStr.replace("[", "")
                    coordStr = coordStr.replace("]", "")
                    coordStr = coordStr.replace("\"", "");
                    val coords = coordStr.split(",").toTypedArray()
                    mMap = googleMap
                    for (c in 0..coords.size step 2) {
                        if ((c + 1) > coords.size) {
                            break
                        }
                        // Creamos un pin por cada coordenada
                        val mapPin = LatLng(coords[c].toDouble(), coords[c + 1].toDouble())
                        mMap.addMarker(MarkerOptions().position(mapPin).title(incidentArr[c].toString()))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapPin, 12f))
                    }
                }
            },
            // En caso de fallar llamada
            { println("That didn't work!")})

        // Agregamos al stack de requests
        queue.add(coordRequest)
    }
}