package mx.itesm.mafo.reto

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class creditos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creditos)
        val volver  = findViewById(R.id.volver) as ImageButton
        volver.setOnClickListener{
            val inter2 = Intent(this, MainActivity::class.java)
            startActivity(intent)
            startActivity(inter2)
        }
    }
}