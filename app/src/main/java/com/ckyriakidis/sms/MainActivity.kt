package com.ckyriakidis.sms

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val posotitaTextbox = findViewById<EditText>(R.id.PosotitaText)
        val eidosLista = findViewById<Button>(R.id.EidosButton)
        val eidosAdd = findViewById<Button>(R.id.EidosButtonAdd)
        val eidosRemove = findViewById<Button>(R.id.EidosButtonRemove)
        val eidosTextbox = findViewById<EditText>(R.id.EidosText)
        val apostoleasLista = findViewById<Button>(R.id.ApostoleasButton)
        val apostoleasAdd = findViewById<Button>(R.id.ApostoleasButtonAdd)
        val apostoleasRemove = findViewById<Button>(R.id.ApostoleasButtonRemove)
        val apostoleasTextbox = findViewById<EditText>(R.id.ApostoleasText)
        val komistraTextbox = findViewById<EditText>(R.id.KomistraText)
        val antikatavoliTextbox = findViewById<EditText>(R.id.AntikatavoliText)
        val katathesiSwitch = findViewById<SwitchMaterial>(R.id.KatathesiSwitch)
        val katathesiSpinner = findViewById<Spinner>(R.id.KatathesiSpinner)
        val paralaviSwitch = findViewById<SwitchMaterial>(R.id.ParalaviSwitch)
        val rantevouSwitch = findViewById<SwitchMaterial>(R.id.RantevouSwitch)
        val rantevouSpinner = findViewById<Spinner>(R.id.RantevouSpinner)
        val minimaButton = findViewById<Button>(R.id.ShareButton)

        katathesiSpinner.isEnabled = false
        katathesiSpinner.visibility  = View.GONE
        rantevouSpinner.isEnabled = false
        rantevouSpinner.visibility  = View.GONE

        katathesiSwitch.setOnCheckedChangeListener { _, isChecked ->
            katathesiSpinner.isEnabled = isChecked
            katathesiSpinner.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.LogariasmoiArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            katathesiSpinner.adapter = adapter
        }

        rantevouSwitch.setOnCheckedChangeListener { _, isChecked ->
            rantevouSpinner.isEnabled = isChecked
            rantevouSpinner.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.MetaforeisArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            rantevouSpinner.adapter = adapter
        }

        minimaButton.setOnClickListener {
            val message = "Μετ/κη Κυριακίδης" +
                        "\nΈχει έρθει " + posotitaTextbox.text.toString() + " " + eidosTextbox.text.toString() + " για εσάς" +
                        "\nΑπό " + apostoleasTextbox.text.toString() +
                        if (TextUtils.isEmpty(komistraTextbox.text.toString())) "" else ("\nΜεταφορικά " + komistraTextbox.text.toString() + " €" + if (katathesiSwitch.isChecked) ((when (katathesiSpinner.selectedItem.toString()) { "062" -> getString(R.string.Logariasmos062) "385" -> getString(R.string.Logariasmos385) "608" -> getString(R.string.Logariasmos608) else -> "" }) + "\nΣε περίπτωση κατάθεσης από άλλη τράπεζα με επιβάρυνση δική σας της χρέωσης του εμβάσματος και των δύο τραπεζών") else "") +
                        if (TextUtils.isEmpty(antikatavoliTextbox.text.toString())) "" else ("\nΑντικαταβολή " + antikatavoliTextbox.text.toString() + " €") +
                        if (paralaviSwitch.isChecked) "\nΠαραλαβή από γραφείο" else "" +
                        if (rantevouSwitch.isChecked) ("\nΓια ραντεβού παράδοσης παρακαλώ επικοινωνήστε 10:30-15:00 με το τηλέφωνο " +
                        when (rantevouSpinner.selectedItem.toString()) { "Γιώργος" -> "6981154724 κος Γιώργος" "Ίβο" -> "6993344810 κος Ίβο" "Νίκος" -> "6944531060 κος Νίκος" else -> "" } + "\nΟι παραδόσεις γίνονται μέρα παρά μέρα στην περιοχή σας, από τις 9:30-14:00, επί του πεζοδρομίου (εκτός άλλης ιδιωτικής συμφωνίας με τον οδηγό)") else "" +
                        "\nΠλούτωνος 63 Αιγάλεω\nΤηλ 2103459310\nhttps://goo.gl/maps/XVQzBjociLiKztUg7"

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}