package com.hoodable

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

class SingUp : AppCompatActivity(), View.OnClickListener {
    private var edt_name: EditText? = null
    private var edt_family: EditText? = null

    internal lateinit var but_continue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        idInilize()
        but_continue.setOnClickListener(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Register")

    }

    private fun idInilize() {

        but_continue = findViewById(R.id.but_continue)
        edt_name = findViewById(R.id.edt_name)
        edt_family = findViewById(R.id.edt_family)

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.but_continue -> {
                val name = edt_name!!.text.toString()
                if (name.isEmpty()) {
                    makeToast("Please Enter name")
                } else {
                    val intent = Intent(this@SingUp, EmailActiivity::class.java)
                    intent.putExtra("name", edt_name!!.text.toString())
                    intent.putExtra("fname", edt_family!!.text.toString())
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
