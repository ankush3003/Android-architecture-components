package weather.tutorial.com.weather.problems.activitylifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_a.*
import weather.tutorial.com.weather.R

// Problem: IF we start Activity A...and then Activity B - what lifecycle methods are invoked
// and in what order
class ActivityA : AppCompatActivity() {
    private val TAG = "ActivityA"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        Log.d(TAG, "--> Activity A - onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "--> Activity A - onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "--> Activity A - onResume()")

        navigateNextBtn.setOnClickListener {
            startActivity(Intent(this, ActivityB::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "--> Activity A - onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "--> Activity A - onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "--> Activity A - onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "--> Activity A - onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "--> Activity A - onSaveInstanceState()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "--> Activity A - onRestoreInstanceState()")
    }
}