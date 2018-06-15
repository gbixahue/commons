package ru.dev.gbixahue.library.predictable.actions

import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.facebook.stetho.Stetho
import io.fabric.sdk.android.Fabric
import ru.dev.gbixahue.library.commons.BuildConfig
import ru.dev.gbixahue.library.android.BaseApplication
import ru.dev.gbixahue.library.predictable.PredictableAction
import ru.dev.gbixahue.library.android.preference.Key
import ru.dev.gbixahue.library.tracker.ATrack
import ru.dev.gbixahue.library.tracker.Configurator
import ru.dev.gbixahue.library.tracker.handlers.AppMetricaAnalysis
import ru.dev.gbixahue.library.tracker.handlers.FacebookAnalysis
import ru.dev.gbixahue.library.tracker.handlers.FirebaseAnalysis

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class LaunchAnalysisSystems(private val appContext: BaseApplication): PredictableAction {

  override fun performExpression(): Boolean = true

  override fun performAction() {
    appContext.prefRepo.save(Key.LAST_LAUNCH, System.currentTimeMillis())
    if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(appContext)
    ATrack.addAnalysisSystem(AppMetricaAnalysis.ID, Configurator.getYandexMetrica(appContext))
    ATrack.addAnalysisSystem(FacebookAnalysis.ID, Configurator.getFacebook(appContext))
    ATrack.addAnalysisSystem(FirebaseAnalysis.ID, Configurator.getFirebase(appContext))
    Fabric.with(appContext, Crashlytics(), Answers())
//    MobileAds.initialize(appContext, appContext.getString(R.string.ads_app_id))
  }
}