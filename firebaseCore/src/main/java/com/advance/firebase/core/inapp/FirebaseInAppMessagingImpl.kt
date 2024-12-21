package com.advance.firebase.core.inapp

import android.content.Context
import com.advance.domain.firebase.prefs.Prefs
import com.advance.domain.model.firebase.AdvanceSegment
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplay
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks
import com.google.firebase.inappmessaging.model.InAppMessage
import com.google.firebase.inappmessaging.model.MessageType

class FirebaseInAppMessagingImpl(
  val prefs: Prefs,
  val context: Context,
  val list: List<AdvanceSegment>,
  val openInApp: (
    String,
    String,
    String,
    Boolean,
    InAppMessage,
    MessageType,
    FirebaseInAppMessagingDisplayCallbacks
) -> Unit
) :
  FirebaseInAppMessagingDisplay {

    /*
    * GZ: For surveys, there are two delivery mechanisms :
    * 1) Receive campaign_id key ==> Get the survey data {survey_id,survey_version}
    * from CAMPAIGN MANAGER then call Sybile service.  (will contain intro or outro screens )
    * 2) Receive survey_id,survey_version keys ==> call Sybile service direct to start the survey.
    * */

  override fun displayMessage(
    inAppMessage: InAppMessage,
    firebaseCallback: FirebaseInAppMessagingDisplayCallbacks
  ) {

    inAppMessage.data?.let { data ->

      if (data.containsKey(CAMPAIGN_ID)) {
        data[CAMPAIGN_ID]?.let { campaignId ->
          openInApp(
            campaignId, "", "", true,
            inAppMessage,
            MessageType.UNSUPPORTED,
            firebaseCallback
          )
        }
      } else if (data.containsKey(SURVEY_ID) && data.containsKey(SURVEY_VERSION)) {
        val surveyId = data[SURVEY_ID].toString()
        val surveyVersion = data[SURVEY_VERSION].toString()
        var campaignName = ""
        inAppMessage.campaignMetadata?.campaignName?.let {
          campaignName = it
        }
        val hasSegment: Boolean = if (data.containsKey(SEGMENT)) {
          val segmentName = data[SEGMENT]
          list.find { it.name == segmentName } != null
        } else {
          true
        }

        val hasMinArticleView: Boolean = if (data.containsKey(MIN_ARTICLE_VIEWS)) {
          val minArticleView = data[MIN_ARTICLE_VIEWS]?.toInt() ?: 0
          prefs.minArticleViews >= minArticleView
        } else {
          true
        }

        val installDateMatch: Boolean = if (data.containsKey(INSTALL_DATE)) {
          // YYYY-MM-DD
          val installDate = data[INSTALL_DATE]
          prefs.dateOfFirstInstall == installDate
        } else {
          true
        }

        if (installDateMatch && hasSegment && hasMinArticleView) {
          openInApp(
            surveyId,
            surveyVersion,
            campaignName,
            false,
            inAppMessage,
            MessageType.UNSUPPORTED,
            firebaseCallback
          )
        } else {
        }
      } else { // Display the campaign by Message layout

        inAppMessage.messageType.let {

          openInApp(
            "", "", "", false,
            inAppMessage,
            it!!,
            firebaseCallback
          )
        }
      }
    }
  }

  private companion object {
    private const val CAMPAIGN_ID = "campaign_id"
    private const val SURVEY_ID = "survey_id"
    private const val SURVEY_VERSION = "survey_version"
    private const val SEGMENT = "segment"
    private const val MIN_ARTICLE_VIEWS = "min_article_views"
    private const val INSTALL_DATE = "install_date"
  }
}
