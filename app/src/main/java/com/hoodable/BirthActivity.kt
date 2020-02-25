package com.hoodable

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.date_of_birth.*
import java.util.*

class BirthActivity : AppCompatActivity(), View.OnClickListener {
    internal var name: String = ""
    internal var fname: String = ""
    internal var mobile: String = ""
    internal var email: String = ""
    private var etDob: TextView? = null
    private var but_continue: Button? = null
    internal lateinit var c: Calendar
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    internal val DATE_PICKER_ID = 1111
    internal lateinit var datestr: StringBuilder
    internal var timestr = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birth)
        name = intent.getStringExtra("name")
        fname = intent.getStringExtra("fname")
        mobile = intent.getStringExtra("mobile")
        email = intent.getStringExtra("email")

        idInilize()
        but_continue!!.setOnClickListener(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Register")

        c = Calendar.getInstance()
        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH)
        day = c.get(Calendar.DAY_OF_MONTH)
        var a = ""
        var b = ""
        if (day < 10) {
            a = "0"
        }
        if (month + 1 < 10) {
            b = "0"
        }
        datestr = StringBuilder().append(a).append(day).append("-").append(b).append(month + 1)
            .append("-").append(year).append("")


        etDob!!.setOnClickListener(this)


    }

    private fun idInilize() {
        etDob = findViewById(R.id.edt_dob)
        but_continue = findViewById(R.id.but_continue)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.but_continue -> {
                val dob = etDob!!.text.toString()
                if (dob.isEmpty()) {
                    makeToast("Please Enter Date of birth")
                } else {
                    val intent = Intent(this@BirthActivity, PostalCodeActivity::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("fname", fname)
                    intent.putExtra("mobile", mobile)
                    intent.putExtra("email", email)
                    intent.putExtra("dob", dob)
                    startActivity(intent)
                }

                }
            R.id.edt_dob->{
                showDialog(DATE_PICKER_ID)

            }

        }
    }

    private fun makeToast(s: String) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            DATE_PICKER_ID -> {
                val datePickerDialog = DatePickerDialog(this, from_dateListener, year, month, day)
               /// datePickerDialog.datePicker.minDate =     c.timeInMillis

                 val cal = GregorianCalendar.getInstance()
                 cal.time = Date()
                 cal.add(Calendar.YEAR, -50)
                 val daysBeforeDate = cal.time
                datePickerDialog.datePicker.minDate =     cal.timeInMillis

                datePickerDialog.show()
            }
        }
        return null
    }

    private val from_dateListener =
        DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            year = selectedYear
            month = selectedMonth
            day = selectedDay

            c = Calendar.getInstance()
            var curYear:Int = c.get(Calendar.YEAR)
            if(curYear - year < 13)
            {
                Toast.makeText(this, "You should be at least 12 years old or above'", Toast.LENGTH_SHORT).show()
                return@OnDateSetListener
            }

            datestr =
                StringBuilder().append(day).append("-").append(month + 1).append("-").append(year)
                    .append("")
            edt_dob.setText(datestr)
        }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        super.onOptionsItemSelected(item)
        when (item.itemId) {

            android.R.id.home -> super.onBackPressed()
        }//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //                startActivityForResult(intent, 0);
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //        startActivityForResult(intent, 0);
    }
}
