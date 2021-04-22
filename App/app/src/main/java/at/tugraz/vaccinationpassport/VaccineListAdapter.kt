package at.tugraz.vaccinationpassport


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import at.tugraz.vaccinationpassport.R.layout.vaccine_list_item
import kotlinx.android.synthetic.main.vaccine_list_item.view.*

class VaccineListAdapter(
  private val vaccines: MutableList<Vaccination>
) : RecyclerView.Adapter<VaccineListAdapter.VaccinesViewHolder>()
{
    class VaccinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccinesViewHolder {
        return VaccinesViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        vaccine_list_item,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return vaccines.size
    }

    override fun onBindViewHolder(holder: VaccinesViewHolder, position: Int) {
        val curVaccine = vaccines[position]

        holder.itemView.apply{
            tvVaccineName.text = curVaccine.name
            tvVaccineDate.text = curVaccine.date
        }
    }

    fun addVaccine(vaccine: Vaccination){
        vaccines.add(vaccine)
        notifyItemInserted(vaccines.size - 1)
    }
}