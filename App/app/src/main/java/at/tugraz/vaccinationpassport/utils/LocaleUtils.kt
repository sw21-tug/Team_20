package at.tugraz.vaccinationpassport.utils

import android.content.res.Configuration
import android.content.res.Resources
import java.util.*

fun setLocale(languageCode: String?, resources: Resources) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val resources: Resources = resources
    val config: Configuration = resources.getConfiguration()
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.getDisplayMetrics())
}

fun changeLocale(language: String?, default: String, resources: Resources)
{
    if (language != null) {
        setLocale(language as String?, resources)
    }
    else {
        setLocale(default, resources)
    }
}