package mx.itesm.mafo.reto.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.os.PersistableBundle
import android.provider.Settings
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewbinding.BuildConfig
import com.google.android.gms.location.*
import mx.itesm.mafo.reto.BuildConfig.APPLICATION_ID
import mx.itesm.mafo.reto.MainActivity
import mx.itesm.mafo.reto.R
import mx.itesm.mafo.reto.databinding.ContactosemergenciaBinding


class ContactosEmergencia : AppCompatActivity()
{
    private val CODIGO_PERMISO_GPS = 200

    // Cliente proveedor de ubicación
    private lateinit var clienteLocalizacion: FusedLocationProviderClient

    // Callback para manejar las actualizaciones de ubicación
    private lateinit var locationCallback: LocationCallback

    // Para saber si las actualizaciones están activas entre corridas de la app
    private var actualizandoPosicion: Boolean = false

    // viewbinding
    private lateinit var binding: ContactosemergenciaBinding
    // Referencia al view model
    //private val llamarVM: LlamarVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContactosemergenciaBinding.inflate(layoutInflater)

        setContentView(binding.root)

        recuperarActualizandoPosicion(savedInstanceState)



        // Configuraciones
        revisarPermisos()
        registrarEventos()
        configurarObservadores()


        // Handler de actualizaciones. La función se ejecuta cada vez que hay una nueva posición
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                val posicion = locationResult.locations.last()
                println("Nueva ubicación: $posicion")
                if (posicion != null) {
                    detenerActualizacionesPosicion()
                }
            }
        }
        val volver  = findViewById(R.id.volver) as ImageButton
        volver.setOnClickListener{
            val inter2 = Intent(this, MainActivity::class.java)
            startActivity(intent)
            startActivity(inter2)
        }

    }

    private fun mostrarMapa(posicion: Location) {
        val uri = Uri.parse("geo:19.5961052,-99.2271223?q=bomberos")
        val intMapa = Intent(Intent.ACTION_VIEW, uri)
        intMapa.setPackage("com.google.android.apps.maps")
        startActivity(intMapa)
    }

    private fun recuperarActualizandoPosicion(savedInstanceState: Bundle?) {
        savedInstanceState ?: return
        if (savedInstanceState.containsKey("ActualizandoPosicion")) {
            actualizandoPosicion = savedInstanceState.getBoolean("ActualizandoPosicion")
        }
    }

    private fun configurarGPS() {
        if (tienePermiso()) {
            iniciarActualizacionesPosicion()
        } else {
            solicitarPermisos()
        }
        leerUltimaUbicacion()
    }

    private fun leerUltimaUbicacion() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        clienteLocalizacion.lastLocation
            .addOnSuccessListener { location: Location? ->
                println("Ultima ubicación: $location")
            }
    }

    private fun solicitarPermisos() {
        val requiereJustificacion = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (requiereJustificacion) {
            mostrarDialogo()
        } else { // Pedir el permiso directo
            pedirPermisoUbicacion()
        }
    }

    // Resultado del permiso
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CODIGO_PERMISO_GPS) {
            if (grantResults.isEmpty()) {
            } else if (grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                iniciarActualizacionesPosicion()
            } else {
                // Permiso negado
                val dialogo = AlertDialog.Builder(this)
                dialogo.setMessage("Esta app requiere GPS, ¿Quieres habilitarlo manualmente?")
                    .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts(
                            "package",
                            mx.itesm.mafo.reto.BuildConfig.APPLICATION_ID, null
                        )
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    })
                    .setNeutralButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                        println("No hay forma de usar gps, cerrar la actividad")
                        //Deshabilitar funcionalidad
                    })
                    .setCancelable(false)
                dialogo.show()
            }
        }
    }
    private fun mostrarDialogo() {
        val dialogo = AlertDialog.Builder(this)
            .setMessage("Necesitamos saber tu posición para generar alertas")
            .setPositiveButton("Aceptar") { dialog, which ->
                pedirPermisoUbicacion()
            }
            .setNeutralButton("Cancelar", null)
        dialogo.show()
    }

    private fun pedirPermisoUbicacion() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), CODIGO_PERMISO_GPS
        )
    }

    @SuppressLint("MissingPermission")
    private fun iniciarActualizacionesPosicion() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 20000
            //fastestInterval = 10000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        if (locationRequest != null) {
            clienteLocalizacion.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
        actualizandoPosicion = true
    }

    private fun tienePermiso(): Boolean {
        val estado = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return estado == PackageManager.PERMISSION_GRANTED
    }

    override fun onResume() {
        super.onResume()
        if (actualizandoPosicion) {
            iniciarActualizacionesPosicion()
        }
    }


    override fun onPause() {
        super.onPause()
        detenerActualizacionesPosicion()
    }

    override fun onStop() {
        super.onStop()
        println("DETENIENDO")
    }

    private fun detenerActualizacionesPosicion() {
        println("Detiene actualizaciones")
        clienteLocalizacion.removeLocationUpdates(locationCallback)
        actualizandoPosicion = false
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState?.putBoolean("ActualizandoPosicion", actualizandoPosicion)
        super.onSaveInstanceState(outState, outPersistentState)
    }



    private fun configurarObservadores() {

    }


    private fun registrarEventos() {
        //Funciones de llamada
        binding.crdBomberos.setOnClickListener {
            val telefono = "55-3622-1004"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$telefono")
            startActivity(intent)
        }

        binding.crdAmbulancia.setOnClickListener {
            val telefono = "55-5822-2188"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$telefono")
            startActivity(intent)
        }

        binding.crdEmergencia.setOnClickListener {
            val telefono = "911"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$telefono")
            startActivity(intent)
        }

        binding.crdPolicia.setOnClickListener {
            val telefono = "55-5822-3070"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$telefono")
            startActivity(intent)
        }

        //Funciones de copiado
        binding.crdCopyBomberos.setOnClickListener() {
            val clipboard : ClipboardManager = getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
            val clip : ClipData = ClipData.newPlainText("EditText", "55-3622-1004")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Número copiado", Toast.LENGTH_SHORT).show()
        }

        binding.crdCopyAmbulancia.setOnClickListener() {
            val clipboard : ClipboardManager = getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
            val clip : ClipData = ClipData.newPlainText("EditText", "55-5822-2188")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Número copiado", Toast.LENGTH_SHORT).show()
        }

        binding.crdCopyEmergencia.setOnClickListener() {
            val clipboard : ClipboardManager = getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
            val clip : ClipData = ClipData.newPlainText("EditText", "911")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Número copiado", Toast.LENGTH_SHORT).show()
        }

        binding.crdCopyPolicia.setOnClickListener() {
            val clipboard : ClipboardManager = getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
            val clip : ClipData = ClipData.newPlainText("EditText", "55-5822-3070")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Número copiado", Toast.LENGTH_SHORT).show()
        }

        binding.crdRoute1.setOnClickListener(){
            val uri = Uri.parse("google.navigation:q=modulo+policia")
            val intMapa = Intent(Intent.ACTION_VIEW, uri)
            intMapa.setPackage("com.google.android.apps.maps")
            startActivity(intMapa)
        }

        binding.crdRoute2.setOnClickListener(){
            val uri = Uri.parse("google.navigation:q=hospital")
            val intMapa = Intent(Intent.ACTION_VIEW, uri)
            intMapa.setPackage("com.google.android.apps.maps")
            startActivity(intMapa)
        }
    }


    override fun onStart() {
        super.onStart()
        if (! this::clienteLocalizacion.isInitialized) {
            clienteLocalizacion = LocationServices.getFusedLocationProviderClient(this)
            configurarGPS()
        }
        val builder = AlertDialog.Builder(this)
            .setTitle("Aviso")
            .setMessage("Hacer llamadas falsas de emergencia es considerado delito.")
            .setCancelable(false) //No se cierra con click afuera
            .setPositiveButton("Aceptar") { _, _ ->
            }
        builder.show()
    }

    private fun revisarPermisos() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 101)
        }
    }
}