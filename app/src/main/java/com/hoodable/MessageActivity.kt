package com.hoodable

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

class MessageActivity : AppCompatActivity(), View.OnClickListener {
    private var but_continue: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        idInilize()
        but_continue!!.setOnClickListener(this)
         supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("First Step")

    }

    private fun idInilize() {
        but_continue = findViewById(R.id.but_continue)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.but_continue -> {
                val intent = Intent(this@MessageActivity, SingUp::class.java)
                startActivity(intent)
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
