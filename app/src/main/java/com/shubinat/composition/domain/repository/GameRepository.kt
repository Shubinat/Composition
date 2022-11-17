package com.shubinat.composition.domain.repository

import com.shubinat.composition.domain.entity.GameSettings
import com.shubinat.composition.domain.entity.Level
import com.shubinat.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue : Int, countOfOptions : Int) : Question

    fun getGameSettings(level: Level) : GameSettings
}