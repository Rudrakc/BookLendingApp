package com.example.finalbookapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.finalbookapp.databinding.FragmentBookInfoBinding
import androidx.appcompat.app.AlertDialog
import android.widget.NumberPicker
import androidx.navigation.fragment.findNavController

class BookInfoFragment : Fragment() {

    private var _binding: FragmentBookInfoBinding? = null
    private var updateBook: ((Book) -> Unit)? = null
    private val binding get() = _binding!!
    var maxDays = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve selected book from arguments
        val selectedBook = arguments?.getParcelable<Book>("selectedBook") as? Book

        // Retrieve the serializable wrapper from arguments
        val updateBookWrapper = arguments?.getSerializable("updateBook") as? UpdateBookFunction

        // Extract the function from the wrapper
        updateBook = updateBookWrapper?.function

        // Update UI with book details
        selectedBook?.let {
            binding.imageView.setImageResource(it.imageResource)
            binding.imageBook.setImageResource(it.imageResource)
            binding.textBookName.text = it.name
            binding.textAuthor.text = it.author
            binding.textView.text = it.summary
            binding.bookowner.text = it.owner
            binding.bookgenre.text = it.genre
            maxDays = it.maxDay
            // Set other details accordingly

            if (it.numberSelectedFlag) {
                binding.buttonSelectNumber.visibility = View.GONE
            } else {
                binding.buttonSelectNumber.visibility = View.VISIBLE
            }
        }
        // Set up button click listener
        binding.buttonSelectNumber.setOnClickListener {
            openNumberPickerPopup(selectedBook!!)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openNumberPickerPopup(selectedBook: Book) {
        val numberPicker = NumberPicker(requireContext())
        numberPicker.minValue = 1
        numberPicker.maxValue = maxDays

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select the number of days")
        builder.setView(numberPicker)
        builder.setPositiveButton("OK") { dialog, which ->
            val selectedNumber = numberPicker.value
            val modifiedBook = selectedBook.copy(
                numberSelected = selectedNumber,
                numberSelectedFlag = true
            )
            updateBook?.invoke(modifiedBook) // Pass the modified book back to HomeFragment
            binding.buttonSelectNumber.visibility = View.GONE
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

}
