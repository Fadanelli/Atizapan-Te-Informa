package mx.itesm.mafo.reto.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_pagina_sugerencias.*
import mx.itesm.mafo.reto.R
import mx.itesm.mafo.reto.Sugerencias
import mx.itesm.mafo.reto.SugerenciasAdaptador

class PaginaSugerencias : AppCompatActivity() {

    val listaSugerencias = ArrayList<Sugerencias>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagina_sugerencias)

        initData()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val sugerenciasAdaptador = SugerenciasAdaptador(listaSugerencias)
        vistaRecycle.adapter = sugerenciasAdaptador
        vistaRecycle.setHasFixedSize(true)
    }

    private fun initData() {

        listaSugerencias.add(
            Sugerencias(
            "Que hacer en caso de sismo ",
            "                                      Antes: \n" +
                    "+ Asegurar que tu vivienda tenga las condiciones apropiadas.\n" +
                    "+ Mantener en buen estado las instalaciones de gas, agua y electricidad.\n" +
                    "+ Localiza los lugares seguros que estén retirados de ventanas u objetos que puedan caer.\n" +
                    "+ Practica simulacros.\n" +
                    "+ Aprende cómo interrumpir la energía eléctrica, gas y el agua.\n" +
                    "+ Guarda provisiones.\n" +
                    "+ Ten en mano:\n" +
                    "+ Números de emergencia\n" +
                    "+ Botiquín de primeros auxilios\n" +
                    "+ Radio portatil\n" +
                    "+ Linterna con pilas\n" +
                    "+ Guarda documentos importantes en un lugar seguro y de fácil acceso.\n "+
                    "                                      Durante:\n"+
                            "+ Mantén la calma y habla con tranquilidad.\n" +
                            "+ Aléjate de ventanas y de objetos colgantes que puedan desprenderse.\n" +
                            "+ Dirígete a lugares seguros previamente establecidos.\n" +
                            "+ NO uses elevadores.\n" +
                            "+ No te apresures en salir.\n" +
                            "+ De ser posible, cierra las llaves de agua, baja el switch principal de alimentación eléctrica y evita crear combustión.\n" +
                            "+ Si te encuentras manejando, detenga el vehículo y permanece dentro de él.\n" +
                            "+ Mantente alejado de cables y postes de energía eléctrica.\n" +
                    "                                      Después:\n" +
                            "+ Utiliza el teléfono solo para reportar emergencias.\n" +
                            "+ Evalúa los daños, de ser necesario evacuar el inmueble.\n" +
                            "+ Limpia inmediatamente derrames de sustancias tóxicas o inflamables.\n" +
                            "+ Aléjate de los edificios dañados.\n" +
                            "+ En caso de quedar atrapado, conserve la calma y trata de comunicarte con el exterior.\n"


        )
        )

        listaSugerencias.add(
            Sugerencias(
            "Como enfrentar un enfrentar un incendio ",
            "                                      Antes:\n" +
                    "+ Evita conectar muchos aparatos eléctricos en un solo contacto.\n" +
                    "+ Evita las instalaciones eléctricas provisionales, no sustituyas los fusibles.\n" +
                    "+ Nunca mojes las instalaciones eléctricas.\n" +
                    "+ No introducir objetos metálicos en los contactos eléctricos.\n" +
                    "+ Mantén fuera del alcance de los niños objetos que puedan iniciar un incendio.\n" +
                    "+ Evita la acumulación de basura\n" +
                    "+ Mantén en buen estado las instalaciones de gas.\n" +
                    "+ No tires cigarros en el piso, asegúrate de apagarlos bien antes de desecharlos.\n" +
                    "+ No dejes velas encendidas sin supervisión.\n" +
                    "+ Procura contar con extintor y colocarlo en un lugar accesible para su uso.\n" +
                    "+ Ten a la mano teléfonos de emergencia\n" +
                    "                                      Durante:\n" +
                    "+ Conserva la calma.\n" +
                    "+ Aléjate del sitio y espera la llegada del personal capacitado.\n" +
                    "+ Ayuda en la evacuación.\n" +
                    "+ Asegúrate de que nadie quede en el lugar.\n" +
                    "+ Busca el extintor más cercano y trata de combatirlo.\n" +
                    "+ Si el fuego es de origen eléctrico, no trates de combatirlo con agua.\n" +
                    "+ Si sientes asfixia, muévete a rastras y respira lo más cercano al piso.\n" +
                    "+ Humedece un trapo o pañuelo y cubre nariz y boca.\n" +
                    "+ Si se incendia tu ropa tirate al piso y rueda, de ser posible cúbrete con una manta.\n" +
                    "+ Evita usar elevadores y si deseas atravesar una puerta, asegúrate que no esté caliente.\n" +
                    "+ El pánico es tu peor enemigo.\n" +
                    "+ En caso de fuga de gas no enciendas ni apagues las luces; ventila todas las habitaciones y llama a los bomberos.\n" +
                    "                                      Después:\n" +
                    "+ Retírate del área incendiada.\n" +
                    "+ No entres a las instalaciones hasta que la autoridad lo permita.\n" +
                    "+ Obedece a las autoridades y no interfieras en sus actividades.\n"
        )
        )

        listaSugerencias.add(
            Sugerencias(
            "Cómo enfrentar una inundacion",
            "                                      Antes:\n" +
                    "+ No compres o construyas en zonas bajas, cerca de cuerpos de agua o cañadas.\n" +
                    "+ Respeta los usos de suelo y normas de construcción.\n" +
                    "+ Localiza lugares altos y rutas para llegar al refugio temporal.\n" +
                    "+ Guarda documentos importantes en bolsas de plástico y selladas.\n" +
                    "+ Almacena agua, alimentos enlatados, impermeables y botas.\n" +
                    "                                      Durante:\n" +
                    "+ Si es necesario, dirígete a un refugio temporal, lleva contigo sólo lo indispensable.\n" +
                    "+ En caminos inundados no utilices el automóvil.\n" +
                    "+ Conserva la calma, mantente informado y atiende las indicaciones de protección civil.\n" +
                    "+ No trates de caminar o nadar en caminos inundados, evita cruzar el cauce de los ríos.\n" +
                    "+ No te acercas a los postes o cables de electricidad averiados.\n" +
                    "                                      Después:\n" +
                    "+ Regresa a tu casa hasta asegurarte de que no exista riesgo de derrumbe.\n" +
                    "+ Utiliza medidas de higiene en alimentos y agua.\n" +
                    "+ Desaloja el agua estancada para evitar plagas, mosquitos, enfermedades e infecciones,\n" +
                    "+ Limpia restos de sustancias tóxicas o inflamables.\n" +
                    "+ Reporta a los heridos a las autoridades y no intentes moverlos.\n")

        )
        listaSugerencias.add(
            Sugerencias(
                "Que hace en caso de amenaza de bomba",
                "                                      Durante: \n"+
                "+ Conservar la calma.\n" +
                        "+ Seguir las órdenes del personal capacitado.\n"

            )
        )


    }
}