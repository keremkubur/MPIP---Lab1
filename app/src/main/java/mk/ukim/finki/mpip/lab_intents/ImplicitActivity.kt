package mk.ukim.finki.mpip.lab_intents

import ViewAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class ImplicitActivity : AppCompatActivity() {
    private lateinit var listView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_implicit)

            listView = findViewById(R.id.listView)

            listView.adapter = ViewAdapter(loadData())
        }
    private fun loadData():MutableList<String> {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pkgAppsList = packageManager.queryIntentActivities(mainIntent, 0)
        val myMutable = mutableListOf("MainActivity")
        for (i in pkgAppsList.indices) {
            myMutable.add(pkgAppsList[i].activityInfo.name)
        }
        return myMutable
    }
}