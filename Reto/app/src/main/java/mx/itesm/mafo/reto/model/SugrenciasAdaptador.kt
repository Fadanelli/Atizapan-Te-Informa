package mx.itesm.mafo.reto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SugerenciasAdaptador(val listaSugerencias: List<Sugerencias>):
    RecyclerView.Adapter<SugerenciasAdaptador.SugerenciasVM>() {
    // Clase sugerencias para buscar los textview de cada elemento
    class SugerenciasVM(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tipoSiniestroTxt: TextView = itemView.findViewById(R.id.tipoSiniestro)
        var txtSugerenciasTxt: TextView = itemView.findViewById(R.id.txtSugerencias)
        var linearLayout: LinearLayout = itemView.findViewById(R.id.lLayout)
        var layoutExpandible: RelativeLayout = itemView.findViewById(R.id.layoutExpandible)
    }

    // Creamos contenedor
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SugerenciasVM {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fila, parent, false)

        return SugerenciasVM(view)
    }

    // Longitud de las sugerencias para repetir contenedor
    override fun getItemCount(): Int {
        return listaSugerencias.size
    }

    // Creamos contenedor y asignamos valores
    override fun onBindViewHolder(holder: SugerenciasVM, position: Int) {
        val sugerencias: Sugerencias = listaSugerencias[position]
        holder.tipoSiniestroTxt.text = sugerencias.tipoSiniestro
        holder.txtSugerenciasTxt.text = sugerencias.sugerencia

        // Expandemos cuando se selecciona
        val isExpandible: Boolean = listaSugerencias[position].expandible
        holder.layoutExpandible.visibility = if (isExpandible) View.VISIBLE else View.GONE

        // Creamos linear layout
        holder.linearLayout.setOnClickListener {
            val sugerencias = listaSugerencias[position]
            sugerencias.expandible = !sugerencias.expandible
            notifyItemChanged(position)
        }
    }


}