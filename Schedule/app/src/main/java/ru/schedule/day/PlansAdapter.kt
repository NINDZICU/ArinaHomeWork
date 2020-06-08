package ru.schedule.day

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.plan_item.view.*
import ru.schedule.R
import ru.schedule.util.toHourMinutes

class PlansAdapter(val menuListener: MenuListener) :
    RecyclerView.Adapter<PlansAdapter.PlansViewHolder>() {

    var data: List<PlanModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansViewHolder =
        PlansViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.plan_item, parent, false)
        )

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PlansViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class PlansViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: PlanModel) = itemView.apply {
            description.text = model.description
            time.text = "${model.startDate.toHourMinutes()}-${model.endDate.toHourMinutes()}"

            val popupMenu = PopupMenu(context, menu_btn)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> {
                        menuListener.onDelete(model)
                        true
                    }
                    else -> false
                }
            }
            menu_btn.setOnClickListener { popupMenu.show() }
        }
    }

    interface MenuListener {
        fun onDelete(model: PlanModel)
    }
}