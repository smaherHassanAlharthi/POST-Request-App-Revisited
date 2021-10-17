package com.example.postrequestrevisited

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var myRv: RecyclerView
    private lateinit var rvAdapter: RVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //declare UI elements in main activity
        val addButton = findViewById<Button>(R.id.btAdd)
        val deleteButton = findViewById<Button>(R.id.btUD)
        val fpRefresh= findViewById<FloatingActionButton>(R.id.fbRefresh)
        myRv = findViewById(R.id.rvUsers)

        createApiInterface()

        addButton.setOnClickListener{
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }
        deleteButton.setOnClickListener{
            val intent = Intent(this, UpdateDeleteActivity::class.java)
            startActivity(intent)
        }
        fpRefresh.setOnClickListener {
            createApiInterface()
        }



    }

    fun createApiInterface()
    {
        //show progress Dialog
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        val call: Call<Users?>? = apiInterface!!.getUsersInfo()

        call?.enqueue(object : Callback<Users?> {
            override fun onResponse(
                call: Call<Users?>?,
                response: Response<Users?>
            ) {
                progressDialog.dismiss()

                rvAdapter=RVAdapter(response.body()!!,this@MainActivity)
                myRv.adapter = rvAdapter
                myRv.layoutManager = LinearLayoutManager(applicationContext)
                myRv.scrollToPosition( rvAdapter.getItemCount() - 1)

            }

            override fun onFailure(call: Call<Users?>, t: Throwable?) {
                Toast.makeText(applicationContext,"Unable to load data!", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
                call.cancel()
            }
        })
    }
}