package com.shubinat.composition.domain.usecases

import com.shubinat.composition.domain.entity.GameSettings
import com.shubinat.composition.domain.entity.Level
import com.shubinat.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level) : GameSettings {
        return repository.getGameSettings(level)
    }
}