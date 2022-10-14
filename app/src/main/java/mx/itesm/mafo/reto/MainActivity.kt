package mx.itesm.mafo.reto

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import mx.itesm.mafo.reto.databinding.ActivityMainBinding
import mx.itesm.mafo.reto.view.ContactosEmergencia

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        revisarPermisos()
        registrarEventos()



    }

    private fun registrarEventos() {
        binding.crdNotif.setOnClickListener {
            val intent = Intent(this, registro_notificaciones::class.java)
            startActivity(intent)
        }
        binding.crdMapa.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        binding.crdClima.setOnClickListener {
            val intent = Intent(this, clima::class.java)
            startActivity(intent)
        }
        binding.crdEmergencia.setOnClickListener {
            val intent = Intent(this, ContactosEmergencia::class.java)
            startActivity(intent)
        }
        binding.crdRedes.setOnClickListener {
            val intent = Intent(this, TwitterFacebook::class.java)
            startActivity(intent)
        }
        binding.crdCreditos.setOnClickListener {
            val intent = Intent(this, creditos::class.java)
            startActivity(intent)
        }


    }

    private fun revisarPermisos() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 101)
        }
    }


}