package weather.tutorial.com.weather.interview.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import weather.tutorial.com.weather.R
import weather.tutorial.com.weather.interview.data.PrepData
import weather.tutorial.com.weather.interview.utils.PrepListAdapter
import weather.tutorial.com.weather.problems.activitylifecycle.ActivityA
import weather.tutorial.com.weather.ui.main.MainActivity

class PreparationActivity : AppCompatActivity() {
    enum class PROBLEMS(val value: String) {
        WEATHER_APP("Sunshine Weather app"),
        ACTIVITY_LIFECYCLE("Activity Lifecycle - A->B->A")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preparation)


        val prepList = findViewById<View>(R.id.problemsRecycler) as RecyclerView
        val adapter = PrepListAdapter(getProblemsList(),
            PrepListAdapter.RecyclerItemOnClickListener { recyclerClickListener(it) }
        )
        prepList.adapter = adapter
        prepList.layoutManager = LinearLayoutManager(this)
    }

    private fun getProblemsList() : List<PrepData> {
        return arrayListOf(
            PrepData(problemName = PROBLEMS.WEATHER_APP.value),
            PrepData(problemName = PROBLEMS.ACTIVITY_LIFECYCLE.value)
        )
    }

    private fun recyclerClickListener(prepData: PrepData) {
        when (prepData.problemName) {
            PROBLEMS.WEATHER_APP.value -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            PROBLEMS.ACTIVITY_LIFECYCLE.value -> {
                startActivity(Intent(this, ActivityA::class.java))
            }
        }
    }
}