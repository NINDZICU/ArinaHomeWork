package ru.schedule.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.schedule.day.PlanModel

class SharedPreferencesUtil(private val context: Context) {

    private val gson = Gson()

    companion object {
        private const val PREFS_NAME = "SharedPreferencesFile"
        private const val IS_EULA_AGREED = "IS_EULA_AGREED"
    }

    fun savePlans(value: List<PlanModel>, key: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(key, gson.toJson(value)).apply()
    }

    fun getPlans(key: String): List<PlanModel> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return gson.fromJson(
            prefs.getString(key, ""),
            object : TypeToken<List<PlanModel>>() {}.type
        ) ?: emptyList()
    }

    fun addPlan(key: String, plan: PlanModel) {
        val newPlans = getPlans(key).toMutableList()
        newPlans.add(plan)
        savePlans(newPlans.toList(), key)
    }

    private fun setValue(value: Boolean, key: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(key, value).apply()
    }

    private fun getBooleanValue(key: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return if (prefs.contains(key)) {
            prefs.getBoolean(key, false)
        } else {
            false
        }
    }

    fun isEulaAgreed() = getBooleanValue(IS_EULA_AGREED)

    fun setIsEulaAgreed(value: Boolean) = setValue(value, IS_EULA_AGREED)
}