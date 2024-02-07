package com.example.finalbookapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalbookapp.R

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.NavController
import com.example.finalbookapp.BookAdapter

import java.io.Serializable

class UpdateBookFunction(val function: (Book) -> Unit) : Serializable


data class Book(
    val name: String,
    val imageResource: Int,
    val maxDay: Int,
    val author: String,
    val genre: String,
    val owner: String,
    val availability: String,
    val summary: String,
    var numberSelected: Int? = null, // Add this property
    var numberSelectedFlag: Boolean = false // Add this property
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(imageResource)
        parcel.writeInt(maxDay)
        parcel.writeString(author)
        parcel.writeString(genre)
        parcel.writeString(owner)
        parcel.writeString(availability)
        parcel.writeString(summary)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}

class HomeFragment : Fragment(), BookAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter

    override fun setupWithNavController(navController: NavController) {
        // Implement an empty function or add specific functionality if needed
    }
    var books = mutableListOf(
        Book("Jujutsu Kaisen", R.drawable.jujutsu,10, " Akutami Gege", "Shounen, Action, Supernatural", "Ankur", "Available", "Yuuji, a student at Sugisawa Town #3 High School happens to be ultra-talented at track and field... But  since he has no interest in running around in circles,  he's happy as a clam being a member of the Occult Research Club! Although he only joined up for kicks, things start to get serious when an actual spirit shows up at the school."),
        Book("Usogui", R.drawable.usogui,15, "Sako Tohsio", "Mystery, Suspense, Shounen, Psychological", "Rudra", "Available", "There are gamblers out there who even bet their lives as ante. But to secure the integrity of these life-threatening gambles, a violent and powerful organization by the name of Kagerou referees these games as a neutral party. Follow Baku Madarame a.k.a. Usogui (The Lie Eater) as he gambles against maniacal opponents at games - such as Escape the Abandoned Building, Old Maid, and Hangman..."),
        Book("Chainsaw Man", R.drawable.chainsaw,5, "Fujimoto Tatsuki", "Action, Drama, Comedy, Mature", "Kushagra", "Available", "Meet Denji, he's poor. Like, sell your body parts poor.But he's found a gig killing demons with his chainsaw dog. Will it be enough to pay the bills?"),
        Book("Nan Shang", R.drawable.nan_shang,7, "Sako Tohsio", "Comedy, Manhua, School Life", "Manjari", "Available", "[From : GineY] A comedy about two ordinary highschool boys"),
        Book("Under the Green Light", R.drawable.under_gd,5, "Jaxx", "Questionable, Life, Psychological, Horro", "Manjari", "Available", "Under The Green Light is a currently ongoing manhwa by Jaxx. Matthew, a sculpture student who prefers to live in isolation, is taken by Jin's captivating body at first glance. Matthew asks Jin to be his model for a sculpture as he wants to preserve this perfection.Somewhat reluctantly, Jin agrees to this request. Thus begins an unintentional, twisted relationship between the two that's sure to captivate readers. An art style that enhances the dark themes of the plot portrays the two protagonists with beautiful faces, detailed eyes, and tall builds."),
        Book("Omniscient Reader", R.drawable.omniscient,10, "Sing Shong", "Adventure, Action, Fantasy", "Rudra", "Available", "Dokja was an average office worker whose sole interest was reading his favorite web novel 'Three Ways to Survive the Apocalypse' But when the novel suddenly becomes reality, he is the only person who knows how the world will end. Armed with this realization, Dokja uses his understanding to change the course of the story, and the world, as he knows it."),
        Book("Teenage Mercenary", R.drawable.teenage,21, "Rakhyun", "Drama, Action, Romance", "Param", "Available", "Yu ljin was the sole survivor of a plane crash when he was little. After becoming a mercenary to survive for 10 years, he returns to his family in his hometown."),

        // Add more books as needed
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewBooks)
        recyclerView.layoutManager = LinearLayoutManager(context)


        // Assuming you have a list of books, replace it with your actual data

        bookAdapter = BookAdapter(books, this)
        recyclerView.adapter = bookAdapter

        view.findViewById<Button>(R.id.addBookButton).setOnClickListener {
            navigateToAddBook()
        }

        // Check if a new book has been added
        var newBook = arguments?.getParcelable<Book>("newBook")
        if (newBook != null) {
            addnewBook(newBook)
            // Set the argument to null after retrieving it
            arguments?.putParcelable("newBook", null)

        }

        return view
    }

    override fun onItemClick(book: Book) {
        val bundle = Bundle().apply {
            putParcelable("selectedBook", book)
            putSerializable("updateBook", UpdateBookFunction(::updateBook))
        }
        findNavController().navigate(R.id.action_homeFragment_to_bookInfoFragment, bundle)
    }

    private fun navigateToAddBook() {
        findNavController().navigate(R.id.action_homeFragment_to_addBookFragment)
    }

    fun addnewBook(newBook: Book) {
        books.add(newBook)
        bookAdapter.notifyDataSetChanged()
    }

    fun updateBook(modifiedBook: Book) {
        for (book in bookAdapter.books) {
            if (book.name == modifiedBook.name) {
                // Update the numberSelected and numberSelectedFlag properties
                book.numberSelected = modifiedBook.numberSelected
                book.numberSelectedFlag = modifiedBook.numberSelectedFlag
            }
        }

        Log.d("UpdateBook", "Updated Books: $modifiedBook")

        // Notify the adapter that the data has changed
        bookAdapter.notifyDataSetChanged()
    }

}
