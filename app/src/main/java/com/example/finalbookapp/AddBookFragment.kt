package com.example.finalbookapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalbookapp.Book
import com.example.finalbookapp.R

class AddBookFragment : Fragment() {

    private lateinit var editTextBookName: EditText
    private lateinit var editTextAuthor: EditText
    private lateinit var editTextNumber: EditText
    private lateinit var editTextGenre: EditText
    private lateinit var editTextOwner: EditText
    private lateinit var editTextSummary: EditText
    private lateinit var addButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_book, container, false)

        editTextBookName = view.findViewById(R.id.editTextBookName)
        editTextAuthor = view.findViewById(R.id.editTextAuthor)
        editTextNumber = view.findViewById(R.id.editTextNumber)
        editTextGenre = view.findViewById(R.id.editTextGenre) // Added
        editTextOwner = view.findViewById(R.id.editTextOwner) // Added
        editTextSummary = view.findViewById(R.id.editTextSummary) // Added
        addButton = view.findViewById(R.id.buttonAddBook)

        addButton.setOnClickListener {
            addBook()
        }

        return view
    }

    private fun addBook() {
        val bookName = editTextBookName.text.toString()
        val author = editTextAuthor.text.toString()
        val number = editTextNumber.text.toString()
        val days = number.toIntOrNull() ?: 1 // Default to 1 if conversion fails
        val genre = editTextGenre.text.toString() // Added
        val owner = editTextOwner.text.toString() // Added
        val summary = editTextSummary.text.toString()

        // Similarly, retrieve other fields like genre and date

        // Create a new Book object
        val newBook = Book(bookName, R.drawable.newbook,days, author, genre, owner, "Available", summary)

        // Pass the newBook object to HomeFragment and add it to the list
        val bundle = Bundle().apply {
            putParcelable("newBook", newBook)
        }
        findNavController().navigate(R.id.action_addBookFragment_to_homeFragment, bundle)
    }

}
