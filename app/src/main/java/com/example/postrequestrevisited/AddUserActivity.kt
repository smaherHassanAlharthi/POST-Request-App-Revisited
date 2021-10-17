package com.example.postrequestrevisited

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adduser)

        val name=findViewById<EditText>(R.id.etName)
        val location=findViewById<EditText>(R.id.etLocation)
        val save=findViewById<Button>(R.id.btSave)


        save.setOnClickListener{
            //get user name and location from edit text
            addUser(name.text.toString(),location.text.toString())
            name.text.clear()
            location.text.clear()
        }


        val view=findViewById<Button>(R.id.btView)
        view.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addUser(name: String, location: String) {
        //show progress Dialog
        val progressDialog = ProgressDialog(this@AddUserActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        //check if user inputs are not empty
        if(name.isNotEmpty()&& location.isNotEmpty()) {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            val user = UserItem(location, name)
            val call: Call<UserItem> = apiInterface!!.addUsersInfo(user)

            call?.enqueue(object : Callback<UserItem?> {
                override fun onResponse(
                    call: Call<UserItem?>?,
                    response: Response<UserItem?>
                ) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
                }
                override fun onFailure(call: Call<UserItem?>, t: Throwable) {
                    Toast.makeText(applicationContext, "Unable to add user", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                    call.cancel()
                }
            })
        }
        else {
            Toast.makeText(applicationContext, "Enter name and location", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }
}