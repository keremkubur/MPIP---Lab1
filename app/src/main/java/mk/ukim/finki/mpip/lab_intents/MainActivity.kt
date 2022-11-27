package mk.ukim.finki.mpip.lab_intents

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var error: TextView
    private lateinit var explicitButton: Button
    private lateinit var implicitButton: Button
    private lateinit var shareButton: Button
    private lateinit var photoButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        error = findViewById(R.id.error)
        explicitButton = findViewById(R.id.startExplicitButton)
        implicitButton = findViewById(R.id.startImplicitButton)
        shareButton = findViewById(R.id.startShareButton)
        photoButton = findViewById(R.id.startSelectPhotoButton)


        var resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    textView.text = data?.getStringExtra("input")
                }
            }
        textView.setText(intent.getStringExtra("input"))

        explicitButton.setOnClickListener {
            val explicitIntent = Intent(this, ExplicitActivity::class.java)
            explicitIntent.putExtra("input", textView.text)
            resultLauncher.launch(explicitIntent)
        }
        implicitButton.setOnClickListener {
            val intent = Intent().apply {
                action = "mk.ukim.finki.mpip.IMPLICIT_ACTION"
            }
            startActivity(intent);
        }

        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).let { shareIntent ->
                shareIntent.putExtra(Intent.EXTRA_TITLE, "MPiP Send Title")
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Content send from MainActivity")
                if( shareIntent.resolveActivity(getPackageManager()) != null ){
                    startActivity(shareIntent)
                }
                else
                    error.visibility= View.VISIBLE
            }
        }

        photoButton.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivity(Intent.createChooser(intent, "Select Picture"))
        }

    }
}