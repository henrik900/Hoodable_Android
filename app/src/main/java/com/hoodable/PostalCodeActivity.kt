package com.hoodable

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PostalCodeActivity : AppCompatActivity(), View.OnClickListener {
    internal lateinit var edt_post: EditText
    internal var name: String = ""
    internal var fname: String = ""
    internal var mobile: String = ""
    internal var email: String = ""
    internal var dob: String = ""

    internal var password: String? = null
    private var but_continue: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postal_code)
        name = intent.getStringExtra("name")
        fname = intent.getStringExtra("fname")
        mobile = intent.getStringExtra("mobile")
        dob = intent.getStringExtra("dob")
        email = intent.getStringExtra("email")

        idInilize()
        but_continue!!.setOnClickListener(this)
         supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Register")

    }

    private fun idInilize() {
        edt_post = findViewById(R.id.edt_post)
        but_continue = findViewById(R.id.but_continue)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.but_continue -> {
                val post = edt_post.text.toString()
                if (post.isEmpty()) {
                    makeToast("Please input the post code")
                } else {
                    val intent = Intent(this@PostalCodeActivity, PasswordActivity::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("fname", fname)
                    intent.putExtra("mobile", mobile)
                    intent.putExtra("email", email)
                    intent.putExtra("dob", dob)
                    intent.putExtra("post", post)
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