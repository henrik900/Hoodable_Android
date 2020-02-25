package com.hoodable

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class FInallizeActivity : AppCompatActivity(), View.OnClickListener {
    internal var name: String = ""
    internal var fname: String = ""
    internal var mobile: String = ""
    internal var email: String = ""
    internal var dob: String = ""
    internal var password: String = ""
    internal var post: String = ""
    private var but_continue: Button? = null
    internal lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finallize)
        name = intent.getStringExtra("name")
        fname = intent.getStringExtra("fname")
        mobile = intent.getStringExtra("mobile")
        email = intent.getStringExtra("email")
        dob = intent.getStringExtra("dob")
        post = intent.getStringExtra("post")
        password = intent.getStringExtra("pass")
        idInilize()
        but_continue!!.setOnClickListener(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Register")
        session = Session(this)

    }

    private fun idInilize() {
        but_continue = findViewById(R.id.but_continue)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.but_continue -> {
                login()

            }
        }
    }

    private fun makeToast(s: String) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    @Throws(JSONException::class)
    private fun login() {
        val postparams = JSONObject()
        postparams.put("phone", mobile)
        postparams.put("password", password)
        postparams.put("date_of_birth", dob)
        postparams.put("name", name)
        postparams.put("family_name", fname)
        postparams.put("email", email)
        postparams.put("password", password)
        postparams.put("postal_code", post)
        var loading =
            ProgressDialog.show(
                this@FInallizeActivity,
                "Please wait...",
                "Sending...",
                false,
                false
            )
        val stringRequest =
            object : JsonObjectRequest(
                Request.Method.POST, Config.SIGN_UP_URL, postparams,
                Response.Listener { response ->
                    loading!!.dismiss()
                    Log.d("sve_order", response.toString())
                    myresponse(response)
                },
                Response.ErrorListener { error ->
                    loading!!.dismiss()
                    var message: String? = null
                    if (error is NetworkError || error is AuthFailureError || error is NoConnectionError || error is TimeoutError) {
                        message = "Signup failed.Please try again."
                    } else if (error is ParseError) {
                        message = "Parsing error! Please try again after some time!!"
                    } else {
                        message = "Try Again"

                    }
//                    val intent = Intent(this, HomeActivity::class.java)
//                    intent.putExtra("name",name)
//                    startActivity(intent)
//                    session.setLoggedin(true)
//                    if (session.loggedin()) {
//                        startActivity(Intent(this@FInallizeActivity, HomeActivity::class.java))
//                        finish()
//                    }
                   Toast.makeText(this@FInallizeActivity, message, Toast.LENGTH_LONG).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
// params.put("customer_id", cusid);

                    return HashMap()
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val header = HashMap<String, String>()
                    header["Content-Type"] = "application/json"
                    header["Accept"] = "application/json"
                    return header
                }

            }


        stringRequest.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )


        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
        requestQueue.cancelAll(stringRequest)
        //// MySingleton.getInstance(context).addToRequestQueue(request);
    }

    private fun myresponse(jsonObject: JSONObject) {
        val message = jsonObject.optString("message")
        val success = jsonObject.getBoolean("success")
  if (success) {
            makeToast(message)
            val intent = Intent(this, HomeActivity::class.java)
            SharedPrefManager.getInstance(applicationContext).saveDeviceToken(name + " " + fname);
            startActivity(intent)

        } else {
              makeToast(message)
        }


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