package ru.dev.gbixahue.library.tracker

import android.app.Application
import ru.dev.gbixahue.library.commons.BuildConfig
import ru.dev.gbixahue.library.tracker.event.HightEvent
import ru.dev.gbixahue.library.tracker.handlers.*

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class Configurator {

  companion object {
    fun getYandexMetrica(application: Application): AnalysisSystem {
      val analysis = AppMetricaAnalysis(application,
          mutableMapOf(Pair(AnalysisKey.APP_METRICA_API_KEY, "PAST HERE AN YANDEX METRICA APPLICATION ID")))
      return apply(analysis)
    }

    fun getFacebook(application: Application): AnalysisSystem = apply(FacebookAnalysis(application, mutableMapOf()))

    fun getFirebase(application: Application): AnalysisSystem = apply(FirebaseAnalysis(application, mutableMapOf()))

    private fun apply(aSystem: AnalysisSystem): AnalysisSystem {
      registerHighEvents(aSystem)
      enableLogByBuild(aSystem)
      (aSystem as? BaseAnalysisSystem)?.enableLog = BuildConfig.DEBUG
      return aSystem
    }

    private fun registerHighEvents(aSystem: AnalysisSystem) {
      HightEvent.values().forEach { aSystem.registerEvent(it.name) }
    }

    private fun enableLogByBuild(aSystem: AnalysisSystem): AnalysisSystem {
//      (aSystem as? BaseAnalysisSystem)?.enableLog = BuildConfig.DEBUG
      return aSystem
    }
  }
}