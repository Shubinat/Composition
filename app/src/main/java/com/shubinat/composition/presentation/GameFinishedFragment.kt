package com.shubinat.composition.presentation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.shubinat.composition.R
import com.shubinat.composition.databinding.FragmentGameFinishedBinding
import com.shubinat.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showGameResult()
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }
            })
    }

    private fun parseArguments() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    private fun showGameResult() {
        with(binding) {
            emojiResult.setImageResource(getSmileResId())

            tvRequiredAnswers.text = String.format(
                requireContext().resources.getString(R.string.required_score),
                gameResult.gameSettings.minCountOfRightAnswers
            )

            tvScoreAnswers.text = String.format(
                requireContext().resources.getString(R.string.score_answers),
                gameResult.countOfRightAnswers
            )

            tvRequiredPercentage.text = String.format(
                requireContext().resources.getString(R.string.required_percentage),
                gameResult.gameSettings.minPercentOfRightAnswers
            )

            tvScorePercentage.text = String.format(
                requireContext().resources.getString(R.string.score_percentage),
                getPercentOfRightAnswers()
            )
        }
    }

    private fun getPercentOfRightAnswers(): Int = with(gameResult) {
        return if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    private fun getSmileResId(): Int {
        return if (gameResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}