package com.example.finalbookapp

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.example.finalbookapp.R
import com.google.android.material.chip.Chip

class BookAdapter(var books: List<Book>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(book: Book)
        fun setupWithNavController(navController: NavController)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val currentBook = books[position]

        holder.textBookName.text = currentBook.name
        holder.imageBook.setImageResource(currentBook.imageResource)
        holder.textAuthor.text = currentBook.author
        holder.textOwner.text = currentBook.owner
        holder.chipAvailability.text = currentBook.availability

        // Check the value of numberSelectedFlag and update visibility accordingly
        if (currentBook.numberSelectedFlag) {
            holder.textAvailability.visibility = View.VISIBLE
            holder.NotifyMe.visibility = View.VISIBLE
            holder.chipAvailability.visibility = View.GONE
        } else {
            holder.textAvailability.visibility = View.GONE
            holder.NotifyMe.visibility = View.GONE
            holder.chipAvailability.visibility = View.VISIBLE
        }

        val availabilityText = holder.itemView.context.getString(R.string.availability_text, currentBook.numberSelected)
        holder.textAvailability.text = availabilityText

        holder.cardView.setOnClickListener {
            listener.onItemClick(currentBook)
        }

    }

    fun updateBooks(updatedBooks: List<Book>) {
        books = updatedBooks
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return books.size
    }

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: MaterialCardView = itemView.findViewById(R.id.cardView)
        val imageBook: ImageView = itemView.findViewById(R.id.imageBook)
        val textBookName: TextView = itemView.findViewById(R.id.textBookName)
        val textAuthor: TextView = itemView.findViewById(R.id.textAuthor)
        val textOwner: TextView = itemView.findViewById(R.id.textOwner)
        val chipAvailability: Chip = itemView.findViewById(R.id.chipAvailable)
        val textAvailability: TextView = itemView.findViewById(R.id.textAvailability)
        val NotifyMe: TextView = itemView.findViewById(R.id.NotifyMe)

    }


}

