package mx.itesm.mafo.reto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageButton
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
import mx.itesm.mafo.reto.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val volver  = findViewById(R.id.volver) as ImageButton
        volver.setOnClickListener{
            val inter2 = Intent(this, MainActivity::class.java)
            startActivity(intent)
            startActivity(inter2)
        }
        refresh()
    }

    private fun refresh() {
        binding.button.setOnClickListener {
            finish()
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://snowker.xyz/coords"

        // Request a string response from the provided URL.
        val coordRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val obj = response.getJSONObject(0)
                val coordArr = obj.getString("Coords")
                val incidentArr = obj.getJSONArray("Incidents")
                println(incidentArr)
                if (incidentArr.length() == 0) {
                    val t = findViewById<TextView>(R.id.tvCoordenadas)
                    t.text = "No hay incidentes"
                } else {
                    val layout = findViewById<LinearLayout>(R.id.mylayout)
                    val params: ViewGroup.LayoutParams = layout.layoutParams
                    params.width = 0
                    params.height = 0
                    layout.layoutParams = params
                    var coordStr = coordArr.toString()
                    coordStr = coordStr.replace("[", "")
                    coordStr = coordStr.replace("]", "")
                    coordStr = coordStr.replace("\"", "");
                    val coords = coordStr.split(",").toTypedArray()
                    mMap = googleMap
                    for (c in 0..coords.size step 2) {
                        if ((c + 1) > coords.size) {
                            break
                        }
                        val mapPin = LatLng(coords[c].toDouble(), coords[c + 1].toDouble())
                        mMap.addMarker(MarkerOptions().position(mapPin).title(incidentArr[c].toString()))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapPin, 12f))
                    }
                }
            },
            { println("That didn't work!")})

        // Add the request to the RequestQueue.
        queue.add(coordRequest)
    }
}