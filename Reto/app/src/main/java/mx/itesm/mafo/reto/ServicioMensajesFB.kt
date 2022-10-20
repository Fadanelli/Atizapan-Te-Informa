package mx.itesm.mafo.reto

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import mx.itesm.mafo.reto.view.MainActivity

class ServicioMensajesFB : FirebaseMessagingService()
    {
        private val chanelId = "mx.itesm.mafo.reto"
        private val channelName = "alertasPC"


        override fun onMessageReceived(mensaje: RemoteMessage) {
            println("Llega NOTIFICIACIÓN REMOTA!!!")
            if (mensaje.notification != null) {
                generarNotificacion(mensaje)
            }
            if (!mensaje.data.isEmpty()){
                println("Data: ${mensaje.data}")
            }

            mensaje.notification?.let {
                println("Body: ${it.body}")
            }
        }

        private fun generarNotificacion(mensaje: RemoteMessage)
        {

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

            var builder = NotificationCompat.Builder(this, chanelId)
                .setSmallIcon(R.drawable.icon)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

            builder = builder.setContent(crearVistaRemota(mensaje))

            val admNotificaciones = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(chanelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                admNotificaciones.createNotificationChannel(notificationChannel)
            }
            admNotificaciones.notify(0, builder.build())

        }

        @SuppressLint("RemoteViewLayout")
        private fun crearVistaRemota(message: RemoteMessage): RemoteViews
        {
            val titulo = message.notification?.title!!
            val mensaje = message.notification?.body!!
            val vistaRemota = RemoteViews("mx.itesm.mafo.reto", R.layout.notificacion)
            vistaRemota.setTextViewText(R.id.tvTitulo, titulo)
            vistaRemota.setTextViewText(R.id.tvMensaje, mensaje)
            vistaRemota.setImageViewResource(R.id.imgIcono, R.drawable.icon)
            return vistaRemota
        }


    }