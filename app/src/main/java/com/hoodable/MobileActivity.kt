package com.hoodable

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MobileActivity : AppCompatActivity(), View.OnClickListener {
    internal var name: String = ""
    internal var fname: String = ""
    internal var email: String = ""
    private var edt_mobile: EditText? = null
    private var but_continue: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile)
        name = intent.getStringExtra("name")
        fname = intent.getStringExtra("fname")
        email = intent.getStringExtra("email")

        idInilize()
        but_continue!!.setOnClickListener(this)
         supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Register")
    }

    private fun idInilize() {
        but_continue = findViewById(R.id.but_continue)
        edt_mobile = findViewById(R.id.edt_mobile)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.but_continue -> {
                val mobile = edt_mobile!!.text.toString()
                if (mobile.isEmpty()) {
                    makeToast("Please enter phone number")
                } else {
                    val intent = Intent(this@MobileActivity, BirthActivity::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("fname", fname)
                    intent.putExtra("email", email)
                    intent.putExtra("mobile", mobile)
                    startActivity(intent)
                }
            }
        }
    }

    private fun makeToast(s: String) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
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
