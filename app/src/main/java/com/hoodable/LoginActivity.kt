package com.hoodable

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.hoodable.MainActivity.MULTIPLE_PERMISSIONS
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var loading: ProgressDialog? = null
    private var edt_phone: EditText? = null
    private var edt_pass: EditText? = null
    private var but_login: Button? = null
    private var but_signup: Button? = null
    internal lateinit var session: Session

    internal var permissions = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.WAKE_LOCK,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.ACCESS_NOTIFICATION_POLICY,
        Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_SMS
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        idInilize()

        session = Session(this)
        if (session.loggedin()) {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        }

        but_login!!.setOnClickListener(this)
        but_signup!!.setOnClickListener(this)
        if (checkPermissions()) {
            //  permissions  granted.
        }
      ///  supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Login")
        val browser = findViewById(R.id.webview) as WebView
        browser.loadUrl("http://www.tutorialspoint.com")
        browser.webViewClient = MyBrowser()


    }

    private fun idInilize() {
        edt_pass = findViewById(R.id.edt_pass)
        edt_phone = findViewById(R.id.edt_phone)
        but_login = findViewById(R.id.but_login)
        but_signup = findViewById(R.id.but_signup)
    }


    private fun openProfile(jsonObject: JSONObject) {

        val message = jsonObject.optString("message")
        val success=jsonObject.getBoolean("success");
        if(success)
        {
            val jsonObject2=jsonObject.getJSONObject("user")
            val name=jsonObject2.getString("name")
            val fname=jsonObject2.getString("family_name")

            SharedPrefManager.getInstance(applicationContext).saveDeviceToken(name + " " + fname);
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)


            session.setLoggedin(true)
        }
        Toast.makeText(this,message,Toast.LENGTH_SHORT);
/*
        {
            "success": true,
            "message": "Logged in successfully",
            "user": {
            "id": 3,
            "name": "gautam",
            "family_name": "lunar",
            "date_of_birth": "17/12/2005",
            "postal_code": "110098",
            "email": null,
            "email_verified_at": null,
            "phone": "7518025172",
            "created_at": "2019-12-17 15:11:56",
            "updated_at": "2019-12-17 15:11:56"
        }
        }*/



    }

    @Throws(JSONException::class)
    private fun login() {
        val phone = edt_phone!!.text.toString().trim { it <= ' ' }
        val password = edt_pass!!.text.toString().trim { it <= ' ' }
        val postparams = JSONObject()
        postparams.put("email", phone)
        postparams.put("password", password)
        loading =
            ProgressDialog.show(this@LoginActivity, "Please wait...", "Sending...", false, false)
     /*   val stringRequest =
            object : JsonObjectRequest(
                Request.Method.POST, Config.LOGIN_URL, postparams,
                Response.Listener { response ->
                    loading!!.dismiss()
                    Log.d("sve_order", response.toString())
                    openProfile(response)
                },
                Response.ErrorListener { error ->
                    loading!!.dismiss()
                    var message: String? = null
                    if (error is NetworkError || error is AuthFailureError || error is NoConnectionError || error is TimeoutError) {
                        message = "Cannot connect to Internet...Please check your connection!"
                    } else if (error is ParseError) {
                        message = "Parsing error! Please try again after some time!!"
                    } else {
                        message = "Try Again"
                    }
                   // startActivity(intent)


                  *//*  val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.putExtra("name","");
                    startActivity(intent)
*//*
                    // openProfile()

                     Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
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
        requestQueue.add(stringRequest)*/



        val queue = Volley.newRequestQueue(this)
        val url =Config.LOGIN_URL
        // Request a string response from the provided URL.
        val jsonObjectRequest = object: JsonObjectRequest(Request.Method.POST, url, postparams,
            Response.Listener { response ->

                Log.d("sve_order", response.toString())
                openProfile(response)
                session.setLoggedin(true)
                loading!!.dismiss()

            },
            Response.ErrorListener {
                    error ->
                loading!!.dismiss()
                var message: String? = null
                if (error is NetworkError || error is AuthFailureError || error is NoConnectionError || error is TimeoutError) {
                    message = "Login Failed. Please try again.!"
                } else if (error is ParseError) {
                    message = "Parsing error! Please try again after some time!!"
                } else {
                    message = "Login Failed. Please try again. "
                }

                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
             }
        )
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                headers.put("Accept", "application/json")

                return headers
            }
        }
        queue.add(jsonObjectRequest)
        //// MySingleton.getInstance(context).addToRequestQueue(request);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.but_login -> try {
                val phone = edt_phone!!.text.toString().trim { it <= ' ' }
                val password = edt_pass!!.text.toString().trim { it <= ' ' }
                if (phone.isNullOrEmpty()) {
                    makeToast("Please fill email address");
                } else if (password.isNullOrEmpty()) {
                    makeToast("Please fill password.");
                } else {
                    val conMgr =
                        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val netInfo = conMgr.activeNetworkInfo
                    if (netInfo == null) {
                        Toast.makeText(applicationContext, "Network Error", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        login()

                    }


                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            R.id.but_signup -> {
                val intent = Intent(this@LoginActivity, MessageActivity::class.java)
                startActivity(intent)
            }

        }

    }

    private fun makeToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded = ArrayList<String>()
        for (p in permissions) {
            //            result = ContextCompat.checkSelfPermission(getActivity(),p);
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MULTIPLE_PERMISSIONS -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissions granted.
                } else {
                    // no permissions granted.
                }
                return
            }
        }
    }

    companion object {

        val MULTIPLE_PERMISSIONS = 10 // code you want.
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

    private inner class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

    }


}
