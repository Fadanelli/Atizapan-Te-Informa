package mx.itesm.mafo.reto

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.itesm.mafo.reto.databinding.ActivityRegistroNotificacionesBinding

class registro_notificaciones : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroNotificacionesBinding

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroNotificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchNotis()
        refresh()
        createNotificationChannel()


        val volver  = findViewById(R.id.volver) as ImageButton
        volver.setOnClickListener{
            val inter2 = Intent(this, MainActivity::class.java)
            startActivity(intent)
            startActivity(inter2)
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notificaction Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description= descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun sendNotification(){
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.i)
            .setContentTitle("Example Title")
            .setContentText("Example Text")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)){
            notify(notificationId,builder.build())
        }
    }
    private fun refresh() {
        binding.btnRefrescar.setOnClickListener {
            fetchNotis()
        }
    }
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
        val arr = arrayOf("", "", "", "", "", "", "", "", "", "")
        var count = arr[0]

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://snowker.xyz/coords"

        // Request a string response from the provided URL.
        val notiRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val obj = response.getJSONObject(0)
                val notis = obj.getJSONArray("Notifications")
                val list: ArrayList<String> = ArrayList()
                for (i in 0 until notis.length()) {
                    list.add(notis[i].toString())
                }
                list.reverse()
                for (c in 0..9) {
                    if (c >= list.size) {
                        arr[c] = ""
                    } else {
                        arr[c] = list.elementAt(c)
                    }
                }
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
            { nt1.text = "That didn't work!" })

        if (arr[0] != count){
            sendNotification()
            count = arr[0]
        }

        // Add the request to the RequestQueue.
        queue.add(notiRequest)
    }

}