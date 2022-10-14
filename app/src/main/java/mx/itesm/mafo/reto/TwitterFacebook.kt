package mx.itesm.mafo.reto

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageButton
import mx.itesm.mafo.reto.databinding.FragmentSocialMediaBinding

class TwitterFacebook : AppCompatActivity() {

    private lateinit var binding: FragmentSocialMediaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSocialMediaBinding.inflate(layoutInflater)

        setContentView(binding.root)

        registrarEventos()

        val volver  = findViewById(R.id.volver) as ImageButton
        volver.setOnClickListener{
            val inter2 = Intent(this, MainActivity::class.java)
            startActivity(intent)
            startActivity(inter2)
        }

    }

    private fun registrarEventos() {
        binding.facebookico.setOnClickListener{
            val inter = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.facebook.com/groups/547933222216083"))
            startActivity(inter)
        }
        binding.twitterico.setOnClickListener{
            val inter = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://twitter.com/GobAtizapan"))
            startActivity(inter)
        }
        binding.instaico.setOnClickListener{
            val inter = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.instagram.com/atizapan_de_zaragoza/"))
            startActivity(inter)
        }
        binding.telefono.setOnClickListener{
            val telefono = "072"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$telefono")
            startActivity(intent)
        }

    }
}