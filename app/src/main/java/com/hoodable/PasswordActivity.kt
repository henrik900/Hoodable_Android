package com.hoodable

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PasswordActivity : AppCompatActivity(), View.OnClickListener {
    internal lateinit var edt_pass: EditText
    internal var name: String = ""
    internal var fname: String = ""
    internal var mobile: String = ""
    internal var email: String = ""
    internal var dob: String = ""
    internal var password: String? = null
    internal var post: String = ""
    private var but_continue: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        name = intent.getStringExtra("name")
        fname = intent.getStringExtra("fname")
        mobile = intent.getStringExtra("mobile")
        email = intent.getStringExtra("email")
        dob = intent.getStringExtra("dob")
        post = intent.getStringExtra("post")
        idInilize()
        but_continue!!.setOnClickListener(this)
         supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Register")

    }

    private fun idInilize() {
        edt_pass = findViewById(R.id.edt_pass)
        but_continue = findViewById(R.id.but_continue)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.but_continue -> {
                val pass = edt_pass.text.toString()
                if (pass.isEmpty()) {
                    makeToast("Please enter the password")
                } else {
                    val intent = Intent(this@PasswordActivity, FInallizeActivity::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("fname", fname)
                    intent.putExtra("mobile", mobile)
                    intent.putExtra("dob", dob)
                    intent.putExtra("post", post)
                    intent.putExtra("pass", pass)
                    intent.putExtra("email", email)
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
