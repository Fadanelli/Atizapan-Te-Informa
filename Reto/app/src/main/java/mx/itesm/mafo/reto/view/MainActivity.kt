package mx.itesm.mafo.reto.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import mx.itesm.mafo.reto.databinding.ActivityMainBinding
import mx.itesm.mafo.reto.viewmodel.registro_notificaciones

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Cargamos elementos y nos suscribimos a firebase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Firebase.messaging.subscribeToTopic("alertasAtizapan")
            .addOnCompleteListener{task ->
                var msg = "Suscribed"
                if (!task.isSuccessful){
                    msg ="Subscribed failed"
                }
                println("***********************************************************$msg")
            }

        revisarPermisos()
        registrarEventos()


    }

    // Asignamos accion a cada boton en la pantalla
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
            val intent = Intent(this, Clima::class.java)
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

        binding.crdRecomendaciones.setOnClickListener {
            val intent = Intent(this, PaginaSugerencias::class.java)
            startActivity(intent)
        }

        binding.crdCreditos.setOnClickListener {
            val intent = Intent(this, creditos::class.java)
            startActivity(intent)
        }

    }

    // Comprobamos permisos para correcta funcionalidad de la aplicacion
    private fun revisarPermisos() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 101)
        }
    }


}