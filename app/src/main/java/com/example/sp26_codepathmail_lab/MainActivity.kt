package com.example.sp26_codepathmail_lab

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var emails: MutableList<Email>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailsRv = findViewById<RecyclerView>(R.id.emailsRv)
        emails = EmailFetcher.getEmails()
        val emailAdapter = EmailAdapter(emails)
        emailsRv.adapter = emailAdapter
        emailsRv.layoutManager = LinearLayoutManager(this)

        // Fetch next 5 emails and display in RecyclerView
        findViewById<Button>(R.id.loadMoreBtn).setOnClickListener {
            // get new data
            val next5Emails = EmailFetcher.getNext5Emails()

            // update list entries
            val oldSize = emailAdapter.itemCount
            val addCount = next5Emails.size
            emails.addAll(next5Emails)

            // notify adapter of data change
            emailAdapter.notifyItemRangeInserted(oldSize, addCount)
        }
    }
}