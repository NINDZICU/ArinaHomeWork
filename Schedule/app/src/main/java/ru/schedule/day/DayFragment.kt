package ru.schedule.day

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.add_plan_dialog.view.*
import kotlinx.android.synthetic.main.add_plan_dialog.view.time_begin
import kotlinx.android.synthetic.main.fragment_day.*
import ru.schedule.R
import ru.schedule.util.SharedPreferencesUtil
import ru.schedule.util.toHourMinutes
import java.util.*

class DayFragment : Fragment() {

    private var numberOfDay = 0
    private lateinit var plansAdapter: PlansAdapter

    companion object {
        const val DAY_NUMBER_KEY = "DAY_NUMBER_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        numberOfDay = arguments?.getInt(DAY_NUMBER_KEY) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = SharedPreferencesUtil(requireContext())
        plansAdapter = PlansAdapter(object : PlansAdapter.MenuListener {
            override fun onDelete(model: PlanModel) {
                sharedPref.savePlans(
                    plansAdapter.data.toMutableList().apply {
                        remove(model)
                    }, numberOfDay.toString()
                )
                plansAdapter.data = sharedPref.getPlans(numberOfDay.toString())
            }

        })
        plans.layoutManager = LinearLayoutManager(context)
        plans.adapter = plansAdapter

        plansAdapter.data =
            sharedPref.getPlans(numberOfDay.toString()).sortedBy { it.startDate.toHourMinutes() }

        add_plan.setOnClickListener {
            createDialog()
        }
    }

    private fun createDialog() {
        val dialog = AlertDialog.Builder(context).create()
        val dialogView = LayoutInflater.from(context).inflate(R.layout.add_plan_dialog, null)

        var startDate = Date()
        var endDate = Date()

        dialogView.time_begin.text = startDate.toHourMinutes()
        dialogView.time_end.text = endDate.toHourMinutes()
        dialogView.time_begin.setOnClickListener {
            TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    run {
                        startDate = Date(2020, 6, 1, hour, minute)
                        dialogView.time_begin.text = startDate.toHourMinutes()
                    }
                }, startDate.hours, startDate.minutes, true
            ).show()
        }

        dialogView.time_end.setOnClickListener {
            TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    run {
                        endDate = Date(2020, 6, 1, hour, minute)
                        dialogView.time_end.text = endDate.toHourMinutes()
                    }
                }, endDate.hours, endDate.minutes, true
            ).show()
        }

        dialogView.cancel_btn.setOnClickListener {
            dialog.dismiss()
        }

        dialogView.add_btn.setOnClickListener {
            if (dialogView.et_enter_name.text.isBlank()) {
                Toast.makeText(context, R.string.enter_all_fields, Toast.LENGTH_LONG).show()
            } else {
                val sharedPref = SharedPreferencesUtil(requireContext())
                sharedPref.addPlan(
                    numberOfDay.toString(), PlanModel(
                        dialogView.et_enter_name.text.toString(),
                        startDate, endDate
                    )
                )
                plansAdapter.data = sharedPref.getPlans(numberOfDay.toString())
                    .sortedBy { it.startDate.toHourMinutes() }
                dialog.dismiss()
            }
        }
        dialog.setView(dialogView)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}
