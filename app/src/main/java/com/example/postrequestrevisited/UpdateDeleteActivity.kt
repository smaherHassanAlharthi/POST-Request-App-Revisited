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

class UpdateDeleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)
        val id=findViewById<EditText>(R.id.etPk)
        val name=findViewById<EditText>(R.id.etNameUD)
        val location=findViewById<EditText>(R.id.etLocationUD)
        val update=findViewById<Button>(R.id.btUpdate)
        val delete=findViewById<Button>(R.id.btDelete)

        update.setOnClickListener{
            //get user name and location from edit text
            updateUser(name.text.toString(),location.text.toString(),id.text.toString())
            name.text.clear()
            location.text.clear()
            id.text.clear()
        }

        delete.setOnClickListener{

           deleteUser(id.text.toString())
            name.text.clear()
            location.text.clear()
            id.text.clear()

        }
    }


    private fun updateUser(name: String, location: String,id: String) {
        //show progress Dialog
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        //check if user inputs are not empty
        if (name.isNotEmpty() && location.isNotEmpty()&& id.isNotEmpty()) {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            apiInterface!!.updateUsersInfo(id.toInt(),UserItem(location, name)).enqueue(object : Callback<UserItem?> {
                override fun onResponse(
                    call: Call<UserItem?>?,
                    response: Response<UserItem?>
                ) {
                    progressDialog.dismiss()
                    Toast.makeText(this@UpdateDeleteActivity, "Update Success!", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<UserItem?>, t: Throwable) {
                    Toast.makeText(this@UpdateDeleteActivity, "Unable to update user", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                    call.cancel()
                }
            })
        } else {
            Toast.makeText(this@UpdateDeleteActivity, "Enter name , location and ID", Toast.LENGTH_SHORT)
                .show()
            progressDialog.dismiss()
        }
    }

    private fun deleteUser(id: String) {
        //show progress Dialog
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        //check if user inputs are not empty
        if (id.isNotEmpty()) {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            apiInterface!!.deleteUsersInfo(id.toInt()).enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>?,
                    response: Response<Void>
                ) {
                    progressDialog.dismiss()
                    Toast.makeText(this@UpdateDeleteActivity, "Delete Success!", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(
                        this@UpdateDeleteActivity,
                        "Unable to delete user",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    progressDialog.dismiss()
                    call.cancel()
                }
            })
        } else
            Toast.makeText(
                this@UpdateDeleteActivity,
                "Enter an ID to be deleted!",
                Toast.LENGTH_SHORT
            ).show()
        progressDialog.dismiss()
    }

}