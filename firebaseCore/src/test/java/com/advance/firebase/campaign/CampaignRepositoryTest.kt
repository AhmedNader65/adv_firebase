package com.advance.firebase.campaign
import com.advance.firebase.campaign.model.campaigns.CampaignIdAdapter
import org.junit.Assert.*
import org.junit.Test
import com.advance.domain.model.firebase.Campaign
class CampaignRepositoryTest {

    @Test
    fun testCampaignSerialization() {
        // Arrange
        val mockResponseBody = """
            {
    "campaigns": {
        "078aa8a3-8aff-4b92-b2ac-6ca6a7f436c4": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:53",
                    "username": "THEHULK"
                },
                "id": "078aa8a3-8aff-4b92-b2ac-6ca6a7f436c4",
                "impressions": null,
                "name": "SI News Free Trial Offer",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "subscription-v2",
                "schema_version": 1,
                "type": "subscription",
                "version": "1"
            },
            "primary_button": {
                "title": "Subscribe now",
                "url": null
            },
            "primary_image_url": "si_logo",
            "primary_text": "Incredible Offer\r\nToday Only",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_text": "We hope you\u2019ve enjoyed our gift of free access to subscriber exclusive content and would like to extend you this one-day-only offer to become a subscriber at our lowest price ever.",
            "type": "subscription"
        },
        "0e9bcddc-e513-41eb-9948-6b296c96a435": {
            "bullets": [
                {
                    "icon": "tick",
                    "text": "Breaking news"
                },
                {
                    "icon": "tick",
                    "text": "Weather"
                },
                {
                    "icon": "tick",
                    "text": "Traffic"
                },
                {
                    "icon": "tick",
                    "text": "And more"
                }
            ],
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:55",
                    "username": "William Goodman"
                },
                "id": "0e9bcddc-e513-41eb-9948-6b296c96a435",
                "impressions": null,
                "name": "AL News Notifications",
                "percentage": null,
                "prerequisites": [
                    "notifications_not_enabled"
                ],
                "reporting_id": "notifications-v1",
                "schema_version": 1,
                "type": "dialog4",
                "version": "1"
            },
            "primary_button": {
                "title": "Turn on notifications",
                "url": "permissions://notifications"
            },
            "primary_image_url": "al_logo",
            "primary_text": "Turn on notifications from AL.com",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "al_bell",
            "secondary_text": null,
            "type": "dialog4"
        },
        "125deb32-ae7d-48cf-8d39-ff0e8898daa8": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:48",
                    "username": "THEHULK"
                },
                "id": "125deb32-ae7d-48cf-8d39-ff0e8898daa8",
                "impressions": null,
                "name": "MA News Free Trial Offer",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "subscription-v2",
                "schema_version": 1,
                "type": "subscription",
                "version": "1"
            },
            "primary_button": {
                "title": "Subscribe now",
                "url": null
            },
            "primary_image_url": "ma_logo",
            "primary_text": "Incredible Offer\r\nToday Only",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_text": "We hope you\u2019ve enjoyed our gift of free access to subscriber exclusive content and would like to extend you this one-day-only offer to become a subscriber at our lowest price ever.",
            "type": "subscription"
        },
        "20133df8-0de9-477e-adb1-e99fffde5be0": {
            "bullets": [
                {
                    "icon": "tick",
                    "text": "Breaking news"
                },
                {
                    "icon": "tick",
                    "text": "Weather"
                },
                {
                    "icon": "tick",
                    "text": "Traffic"
                },
                {
                    "icon": "tick",
                    "text": "And more"
                }
            ],
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:56",
                    "username": "William Goodman"
                },
                "id": "20133df8-0de9-477e-adb1-e99fffde5be0",
                "impressions": null,
                "name": "SY News Notifications",
                "percentage": null,
                "prerequisites": [
                    "notifications_not_enabled"
                ],
                "reporting_id": "notifications-v1",
                "schema_version": 1,
                "type": "dialog4",
                "version": "1"
            },
            "primary_button": {
                "title": "Turn on notifications",
                "url": "permissions://notifications"
            },
            "primary_image_url": "sy_logo",
            "primary_text": "Turn on notifications from syracuse.com",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "sy_bell",
            "secondary_text": null,
            "type": "dialog4"
        },
        "23343215-7376-4857-b195-bd6a4921cc58": {
            "campaign": {
                "audit": {
                    "updated": "2022-10-04.16:16:54",
                    "username": "Amy Zaiss"
                },
                "id": "23343215-7376-4857-b195-bd6a4921cc58",
                "impressions": null,
                "name": "Select All Single Question Comment Test Android",
                "percentage": null,
                "prerequisites": [],
                "reporting_id": "ANDROID-TEST",
                "schema_version": 1,
                "type": "survey",
                "version": "5"
            },
            "intro_screen": {
                "primary_button": {
                    "title": "Sure, let's go",
                    "url": null
                },
                "primary_image_url": "nj_logo",
                "primary_text": "Have time for a few questions?",
                "secondary_image_url": null,
                "secondary_text": "Your feedback is valuable to us!"
            },
            "outro_screen": {
                "primary_button": {
                    "title": "Resume reading",
                    "url": null
                },
                "primary_image_url": "nj_logo",
                "primary_text": "Thank you very much!",
                "secondary_image_url": "icon_happy_NJ",
                "secondary_text": "You're great"
            },
            "survey_id": "sEPWhsjocrHGauwmN",
            "survey_version": "ZqY4fz2Y7rfupw6QZ",
            "type": "survey"
        },
        "2cea94a4-8333-450b-8ed4-4fe9490922b1": {
            "campaign": {
                "audit": {
                    "updated": "2022-01-11.20:31:05",
                    "username": "Greg Price"
                },
                "id": "2cea94a4-8333-450b-8ed4-4fe9490922b1",
                "impressions": 3,
                "name": "NJ Subscription",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "SUBSCRIPTION-V1",
                "schema_version": 1,
                "type": "subscription",
                "version": "1"
            },
            "primary_button": {
                "title": "Subscribe now",
                "url": null
            },
            "primary_image_url": "nj_logo",
            "primary_text": "Stay on top of the news that directly impacts you.",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "illu-subs-NJ",
            "secondary_text": "Subscribe to local news you care about from a source you trust. Reliable, relevant and right now.",
            "type": "subscription"
        },
        "38b4c324-0b90-4095-9da6-001ea3f59fb4": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:49",
                    "username": "THEHULK"
                },
                "id": "38b4c324-0b90-4095-9da6-001ea3f59fb4",
                "impressions": null,
                "name": "CL News Free Trial Offer",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "subscription-v2",
                "schema_version": 1,
                "type": "subscription",
                "version": "1"
            },
            "primary_button": {
                "title": "Subscribe now",
                "url": null
            },
            "primary_image_url": "cl_logo",
            "primary_text": "Incredible Offer\r\nToday Only",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_text": "We hope you\u2019ve enjoyed our gift of free access to subscriber exclusive content and would like to extend you this one-day-only offer to become a subscriber at our lowest price ever.",
            "type": "subscription"
        },
        "431509cd-a6bb-4464-9a19-01f7396e4865": {
            "bullets": [
                {
                    "icon": "tick",
                    "text": "Breaking news"
                },
                {
                    "icon": "tick",
                    "text": "Weather"
                },
                {
                    "icon": "tick",
                    "text": "Traffic"
                },
                {
                    "icon": "tick",
                    "text": "And more"
                }
            ],
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:57",
                    "username": "William Goodman"
                },
                "id": "431509cd-a6bb-4464-9a19-01f7396e4865",
                "impressions": null,
                "name": "NYUP Notifications",
                "percentage": null,
                "prerequisites": [
                    "notifications_not_enabled"
                ],
                "reporting_id": "notifications-v1",
                "schema_version": 1,
                "type": "dialog4",
                "version": "1"
            },
            "primary_button": {
                "title": "Turn on notifications",
                "url": "permissions://notifications"
            },
            "primary_image_url": "ny_logo",
            "primary_text": "Turn on notifications from NYup.com",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "ny_bell",
            "secondary_text": null,
            "type": "dialog4"
        },
        "464a029b-4307-4364-9294-6e6a3c404049": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.18:52:45",
                    "username": "Dan Christ"
                },
                "id": "464a029b-4307-4364-9294-6e6a3c404049",
                "impressions": 1,
                "name": "SY - Survey 3",
                "percentage": 10,
                "prerequisites": [],
                "reporting_id": "SURVEY3",
                "schema_version": 1,
                "type": "survey",
                "version": "1"
            },
            "intro_screen": {
                "primary_button": {
                    "title": "Start survey",
                    "url": null
                },
                "primary_image_url": "sy_logo",
                "primary_text": "Please take this survey.",
                "secondary_image_url": null,
                "secondary_text": "We are working hard on your app experience. Your feedback is important."
            },
            "outro_screen": {
                "primary_button": {
                    "title": "Back to app",
                    "url": null
                },
                "primary_image_url": "sy_logo",
                "primary_text": "Thank you for your response.",
                "secondary_image_url": "icon_happy_SYR",
                "secondary_text": "You are amazing and we appreciate you."
            },
            "survey_id": "rXcQ2cJAzEaMKQjHR",
            "survey_version": "NhGfaCCjR2ceDGc9m",
            "type": "survey"
        },
        "4a42ba9d-b7a3-48c4-b171-815252ac2f81": {
            "campaign": {
                "audit": {
                    "updated": "2022-01-11.20:31:06",
                    "username": "Greg Price"
                },
                "id": "4a42ba9d-b7a3-48c4-b171-815252ac2f81",
                "impressions": 3,
                "name": "SY Subscription",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "SUBSCRIPTION-V1",
                "schema_version": 1,
                "type": "subscription",
                "version": "1"
            },
            "primary_button": {
                "title": "Subscribe now",
                "url": null
            },
            "primary_image_url": "sy_logo",
            "primary_text": "Stay on top of the news that directly impacts you.",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "illu-subs-SYR",
            "secondary_text": "Subscribe to local news you care about from a source you trust. Reliable, relevant and right now.",
            "type": "subscription"
        },
        "516faf07-48b0-4152-8bb5-0c32da0b26b6": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:45",
                    "username": "Craig Thomas"
                },
                "id": "516faf07-48b0-4152-8bb5-0c32da0b26b6",
                "impressions": null,
                "name": "NJ - Free Trial Offer",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "subscription-v2",
                "schema_version": 1,
                "type": "subscription",
                "version": "1"
            },
            "primary_button": {
                "title": "Subscribe now",
                "url": null
            },
            "primary_image_url": "nj_logo",
            "primary_text": "Incredible Offer\r\nToday Only",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_text": "We hope you\u2019ve enjoyed our gift of free access to subscriber exclusive content and would like to extend you this one-day-only offer to become a subscriber at our lowest price ever.",
            "type": "subscription"
        },
        "53b28ecc-2401-4df4-98cf-58c0bd617ae3": {
            "bullets": [
                {
                    "icon": "tick",
                    "text": "Breaking news"
                },
                {
                    "icon": "tick",
                    "text": "Weather"
                },
                {
                    "icon": "tick",
                    "text": "Traffic"
                },
                {
                    "icon": "tick",
                    "text": "And more"
                }
            ],
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:58",
                    "username": "William Goodman"
                },
                "id": "53b28ecc-2401-4df4-98cf-58c0bd617ae3",
                "impressions": null,
                "name": "MI News Notifications",
                "percentage": null,
                "prerequisites": [
                    "notifications_not_enabled"
                ],
                "reporting_id": "notifications-v1",
                "schema_version": 1,
                "type": "dialog4",
                "version": "1"
            },
            "primary_button": {
                "title": "Turn on notifications",
                "url": "permissions://notifications"
            },
            "primary_image_url": "mi_logo",
            "primary_text": "Turn on notifications from MLive.com",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "mi_bell",
            "secondary_text": null,
            "type": "dialog4"
        },
        "57f3e2ff-1e7d-447b-a2c5-844d43cb7e8a": {
            "campaign": {
                "audit": {
                    "updated": "2022-10-04.16:06:26",
                    "username": "Amy Zaiss"
                },
                "id": "57f3e2ff-1e7d-447b-a2c5-844d43cb7e8a",
                "impressions": null,
                "name": "Android single question",
                "percentage": null,
                "prerequisites": [],
                "reporting_id": "ANDROID-TEST-2",
                "schema_version": 1,
                "type": "survey",
                "version": "1"
            },
            "intro_screen": {
                "primary_button": {
                    "title": "Enter survey",
                    "url": null
                },
                "primary_image_url": "nj_logo",
                "primary_text": "Please answer one question",
                "secondary_image_url": null,
                "secondary_text": "Your feedback is valuable to us!"
            },
            "outro_screen": {
                "primary_button": {
                    "title": "back to home",
                    "url": null
                },
                "primary_image_url": "nj_logo",
                "primary_text": "Thank you very much!",
                "secondary_image_url": "icon_happy_NJ",
                "secondary_text": "That wasn't so hard was it?"
            },
            "survey_id": "gjSNHcQKduSDG7ixe",
            "survey_version": "cWgM2FqbFMfjr5pNi",
            "type": "survey"
        },
        "65a0a8af-97a5-48dd-8bbe-4269f8ca0ef5": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:47",
                    "username": "Craig Thomas"
                },
                "id": "65a0a8af-97a5-48dd-8bbe-4269f8ca0ef5",
                "impressions": null,
                "name": "SY - Free Trial Offer",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "subscription-v2",
                "schema_version": 1,
                "type": "subscription",
                "version": "1"
            },
            "primary_button": {
                "title": "Subscribe now",
                "url": null
            },
            "primary_image_url": "sy_logo",
            "primary_text": "Incredible Offer\r\nToday Only",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_text": "We hope you\u2019ve enjoyed our gift of free access to subscriber exclusive content and would like to extend you this one-day-only offer to become a subscriber at our lowest price ever.",
            "type": "subscription"
        },
        "94b4fdf5-cced-495d-9bc0-f530d5fef302": {
            "bullets": [
                {
                    "icon": "tick",
                    "text": "Breaking news"
                },
                {
                    "icon": "tick",
                    "text": "Weather"
                },
                {
                    "icon": "tick",
                    "text": "Traffic"
                },
                {
                    "icon": "tick",
                    "text": "And more"
                }
            ],
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:59",
                    "username": "William Goodman"
                },
                "id": "94b4fdf5-cced-495d-9bc0-f530d5fef302",
                "impressions": null,
                "name": "MA News Notifications",
                "percentage": null,
                "prerequisites": [
                    "notifications_not_enabled"
                ],
                "reporting_id": "notifications-v1",
                "schema_version": 1,
                "type": "dialog4",
                "version": "1"
            },
            "primary_button": {
                "title": "Turn on notifications",
                "url": "permissions://notifications"
            },
            "primary_image_url": "ma_logo",
            "primary_text": "Turn on notifications from MassLive.com",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "ma_bell",
            "secondary_text": null,
            "type": "dialog4"
        },
        "97a587e8-e47c-4384-926a-1c73ee6121b8": {
            "bullets": [
                {
                    "icon": "tick",
                    "text": "Breaking news"
                },
                {
                    "icon": "tick",
                    "text": "Weather"
                },
                {
                    "icon": "tick",
                    "text": "Traffic"
                },
                {
                    "icon": "tick",
                    "text": "And more"
                }
            ],
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:54:00",
                    "username": "William Goodman"
                },
                "id": "97a587e8-e47c-4384-926a-1c73ee6121b8",
                "impressions": null,
                "name": "AD Lehigh Notifications",
                "percentage": null,
                "prerequisites": [
                    "notifications_not_enabled"
                ],
                "reporting_id": "notifications-v1",
                "schema_version": 1,
                "type": "dialog4",
                "version": "1"
            },
            "primary_button": {
                "title": "Turn on notifications",
                "url": "permissions://notifications"
            },
            "primary_image_url": "ad_logo",
            "primary_text": "Turn on notifications from lehighvalleylive.com",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "ad_bell",
            "secondary_text": null,
            "type": "dialog4"
        },
        "a4477b5f-bc90-45dd-92c8-ec44aaf43127": {
            "bullets": [
                {
                    "icon": "tick",
                    "text": "Breaking news"
                },
                {
                    "icon": "tick",
                    "text": "Weather"
                },
                {
                    "icon": "tick",
                    "text": "Traffic"
                },
                {
                    "icon": "tick",
                    "text": "And more"
                }
            ],
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:54:01",
                    "username": "William Goodman"
                },
                "id": "a4477b5f-bc90-45dd-92c8-ec44aaf43127",
                "impressions": null,
                "name": "PE News Notifications",
                "percentage": null,
                "prerequisites": [
                    "notifications_not_enabled"
                ],
                "reporting_id": "notifications-v1",
                "schema_version": 1,
                "type": "dialog4",
                "version": "1"
            },
            "primary_button": {
                "title": "Turn on notifications",
                "url": "permissions://notifications"
            },
            "primary_image_url": "pe_logo",
            "primary_text": "Turn on notifications from PennLive.com",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "pe_bell",
            "secondary_text": null,
            "type": "dialog4"
        },
        "ae3f41cb-f487-4d7c-9ec2-3bf17b2b8e7d": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:52",
                    "username": "THEHULK"
                },
                "id": "ae3f41cb-f487-4d7c-9ec2-3bf17b2b8e7d",
                "impressions": null,
                "name": "PE News Free Trial Offer",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "subscription-v2",
                "schema_version": 1,
                "type": "subscription",
                "version": "1"
            },
            "primary_button": {
                "title": "Subscribe now",
                "url": null
            },
            "primary_image_url": "pe_logo",
            "primary_text": "Incredible Offer\r\nToday Only",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_text": "We hope you\u2019ve enjoyed our gift of free access to subscriber exclusive content and would like to extend you this one-day-only offer to become a subscriber at our lowest price ever.",
            "type": "subscription"
        },
        "b1b6e195-a4d4-49bb-9e18-c2df61986075": {
            "campaign": {
                "audit": {
                    "updated": "2022-01-11.20:31:07",
                    "username": "Greg Price"
                },
                "id": "b1b6e195-a4d4-49bb-9e18-c2df61986075",
                "impressions": 3,
                "name": "OR Subscription",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "SUBSCRIPTION-V1",
                "schema_version": 1,
                "type": "subscription",
                "version": "1"
            },
            "primary_button": {
                "title": "Subscribe now",
                "url": null
            },
            "primary_image_url": "or_logo",
            "primary_text": "Stay on top of the news that directly impacts you.",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "illu-subs-OR",
            "secondary_text": "Subscribe to local news you care about from a source you trust. Reliable, relevant and right now.",
            "type": "subscription"
        },
        "bad4b6fd-7239-4564-b2d3-9f1422784d19": {
            "campaign": {
                "audit": {
                    "updated": "2022-09-01.18:05:56",
                    "username": "Amy Zaiss"
                },
                "id": "bad4b6fd-7239-4564-b2d3-9f1422784d19",
                "impressions": null,
                "name": "Notif Study 2 Test -enabled",
                "percentage": null,
                "prerequisites": [
                    "notifications_enabled"
                ],
                "reporting_id": "NOTIF-2-TEST",
                "schema_version": 1,
                "type": "survey",
                "version": "2"
            },
            "intro_screen": {
                "primary_button": {
                    "title": "Start survey",
                    "url": null
                },
                "primary_image_url": "nj_logo",
                "primary_text": "Please take this survey.",
                "secondary_image_url": null,
                "secondary_text": "We are working hard on your app experience. Your feedback is important."
            },
            "outro_screen": {
                "primary_button": {
                    "title": "Continue reading",
                    "url": null
                },
                "primary_image_url": "nj_logo",
                "primary_text": "Thank you for your response.",
                "secondary_image_url": "icon_happy_NJ",
                "secondary_text": "You are amazing and we appreciate you."
            },
            "survey_id": "f2wvbfSG5JDXCJtqN",
            "survey_version": "JDMY6DcReGLW9Me83",
            "type": "survey"
        },
        "bf555f2f-a41e-4d42-af7a-f5144ba823e5": {
            "campaign": {
                "audit": {
                    "updated": "2022-10-04.17:54:48",
                    "username": "Amy Zaiss"
                },
                "id": "bf555f2f-a41e-4d42-af7a-f5144ba823e5",
                "impressions": null,
                "name": "Multi Question Test Android",
                "percentage": null,
                "prerequisites": [],
                "reporting_id": "ANDROID-TEST-2",
                "schema_version": 1,
                "type": "survey",
                "version": "1"
            },
            "intro_screen": {
                "primary_button": {
                    "title": "clickity click",
                    "url": null
                },
                "primary_image_url": "nj_logo",
                "primary_text": "This is a test survey",
                "secondary_image_url": null,
                "secondary_text": "It is multi-question"
            },
            "outro_screen": {
                "primary_button": {
                    "title": "take me back",
                    "url": null
                },
                "primary_image_url": "nj_logo",
                "primary_text": "You did great!",
                "secondary_image_url": "icon_happy_NJ",
                "secondary_text": "Job well done"
            },
            "survey_id": "sEPWhsjocrHGauwmN",
            "survey_version": "ZqY4fz2Y7rfupw6QZ",
            "type": "survey"
        },
        "c20a1eb8-9040-46c0-928f-08abe0a0e7a9": {
            "bullets": [
                {
                    "icon": "tick",
                    "text": "Breaking news"
                },
                {
                    "icon": "tick",
                    "text": "Weather"
                },
                {
                    "icon": "tick",
                    "text": "Traffic"
                },
                {
                    "icon": "tick",
                    "text": "And more"
                }
            ],
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:54:02",
                    "username": "William Goodman"
                },
                "id": "c20a1eb8-9040-46c0-928f-08abe0a0e7a9",
                "impressions": null,
                "name": "NJ News Notifications",
                "percentage": null,
                "prerequisites": [
                    "notifications_not_enabled"
                ],
                "reporting_id": "notifications-v1",
                "schema_version": 1,
                "type": "dialog4",
                "version": "1"
            },
            "primary_button": {
                "title": "Turn on notifications",
                "url": "permissions://notifications"
            },
            "primary_image_url": "nj_logo",
            "primary_text": "Turn on notifications from NJ.com",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "nj_bell",
            "secondary_text": null,
            "type": "dialog4"
        },
        "cc85bea8-0980-4429-9b86-671da7db1d5c": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.18:52:43",
                    "username": "Dan Christ"
                },
                "id": "cc85bea8-0980-4429-9b86-671da7db1d5c",
                "impressions": 1,
                "name": "NJ - Survey 3",
                "percentage": 10,
                "prerequisites": [],
                "reporting_id": "SURVEY3",
                "schema_version": 1,
                "type": "survey",
                "version": "1"
            },
            "intro_screen": {
                "primary_button": {
                    "title": "Start survey",
                    "url": null
                },
                "primary_image_url": "nj_logo",
                "primary_text": "Please take this survey.",
                "secondary_image_url": null,
                "secondary_text": "We are working hard on your app experience. Your feedback is important."
            },
            "outro_screen": {
                "primary_button": {
                    "title": "Back to app",
                    "url": null
                },
                "primary_image_url": "nj_logo",
                "primary_text": "Thank you for your response.",
                "secondary_image_url": "icon_happy_NJ",
                "secondary_text": "You are amazing and we appreciate you."
            },
            "survey_id": "rXcQ2cJAzEaMKQjHR",
            "survey_version": "NhGfaCCjR2ceDGc9m",
            "type": "survey"
        },
        "d58a031c-ee08-4383-aadb-761b7247d8a6": {
            "bullets": [
                {
                    "icon": "tick",
                    "text": "Breaking news"
                },
                {
                    "icon": "tick",
                    "text": "Weather"
                },
                {
                    "icon": "tick",
                    "text": "Traffic"
                },
                {
                    "icon": "tick",
                    "text": "And more"
                }
            ],
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:54:03",
                    "username": "William Goodman"
                },
                "id": "d58a031c-ee08-4383-aadb-761b7247d8a6",
                "impressions": null,
                "name": "OR News Notifications",
                "percentage": null,
                "prerequisites": [
                    "notifications_not_enabled"
                ],
                "reporting_id": "notifications-v1",
                "schema_version": 1,
                "type": "dialog4",
                "version": "1"
            },
            "primary_button": {
                "title": "Turn on notifications",
                "url": "permissions://notifications"
            },
            "primary_image_url": "or_logo",
            "primary_text": "Turn on notifications from OregonLive.com",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "or_bell",
            "secondary_text": null,
            "type": "dialog4"
        },
        "d5970cd8-02ea-4dd6-a311-12deaf2668b6": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:50",
                    "username": "THEHULK"
                },
                "id": "d5970cd8-02ea-4dd6-a311-12deaf2668b6",
                "impressions": null,
                "name": "AD Lehigh Free Trial Offer",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "subscription-v2",
                "schema_version": 1,
                "type": "subscription",
                "version": "1"
            },
            "primary_button": {
                "title": "Subscribe now",
                "url": null
            },
            "primary_image_url": "ad_logo",
            "primary_text": "Incredible Offer\r\nToday Only",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_text": "We hope you\u2019ve enjoyed our gift of free access to subscriber exclusive content and would like to extend you this one-day-only offer to become a subscriber at our lowest price ever.",
            "type": "subscription"
        },
        "e5c55a5c-83c0-4e83-b84b-6be18229681c": {
            "campaign": {
                "audit": {
                    "updated": "2022-10-05.18:38:31",
                    "username": "Amy Zaiss"
                },
                "id": "e5c55a5c-83c0-4e83-b84b-6be18229681c",
                "impressions": null,
                "name": "Android test survey",
                "percentage": null,
                "prerequisites": [],
                "reporting_id": "ANDROID-TEST-2",
                "schema_version": 1,
                "type": "survey",
                "version": "1"
            },
            "intro_screen": {
                "primary_button": {
                    "title": "Start",
                    "url": null
                },
                "primary_image_url": "nj_logo",
                "primary_text": "This is a test",
                "secondary_image_url": null,
                "secondary_text": "Answer one question"
            },
            "outro_screen": {
                "primary_button": {
                    "title": "Continue reading",
                    "url": null
                },
                "primary_image_url": "nj_logo",
                "primary_text": "Test complete",
                "secondary_image_url": "icon_happy_NJ",
                "secondary_text": "thank you"
            },
            "survey_id": "Gspsuqt4Cs4bN8hbC",
            "survey_version": "3kGnLtg5YfRny7aGC",
            "type": "survey"
        },
        "eb82da40-da82-4866-a71e-01b3f6b54b49": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.18:52:44",
                    "username": "Dan Christ"
                },
                "id": "eb82da40-da82-4866-a71e-01b3f6b54b49",
                "impressions": 1,
                "name": "OR - Survey 3",
                "percentage": 10,
                "prerequisites": [],
                "reporting_id": "SURVEY3",
                "schema_version": 1,
                "type": "survey",
                "version": "1"
            },
            "intro_screen": {
                "primary_button": {
                    "title": "Start survey",
                    "url": null
                },
                "primary_image_url": "or_logo",
                "primary_text": "Please take this survey.",
                "secondary_image_url": null,
                "secondary_text": "We are working hard on your app experience. Your feedback is important."
            },
            "outro_screen": {
                "primary_button": {
                    "title": "Back to app",
                    "url": null
                },
                "primary_image_url": "or_logo",
                "primary_text": "Thank you for your response.",
                "secondary_image_url": "icon_happy_OR",
                "secondary_text": "You are amazing and we appreciate you."
            },
            "survey_id": "rXcQ2cJAzEaMKQjHR",
            "survey_version": "NhGfaCCjR2ceDGc9m",
            "type": "survey"
        },
        "ec6b2b3b-36bf-4a1c-a4da-351eedcdc8b7": {
            "bullets": [
                {
                    "icon": "tick",
                    "text": "Breaking news"
                },
                {
                    "icon": "tick",
                    "text": "Weather"
                },
                {
                    "icon": "tick",
                    "text": "Traffic"
                },
                {
                    "icon": "tick",
                    "text": "And more"
                }
            ],
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:54",
                    "username": "William Goodman"
                },
                "id": "ec6b2b3b-36bf-4a1c-a4da-351eedcdc8b7",
                "impressions": null,
                "name": "SI News Notifications",
                "percentage": null,
                "prerequisites": [
                    "notifications_not_enabled"
                ],
                "reporting_id": "notifications-v1",
                "schema_version": 1,
                "type": "dialog4",
                "version": "1"
            },
            "primary_button": {
                "title": "Turn on notifications",
                "url": "permissions://notifications"
            },
            "primary_image_url": "si_logo",
            "primary_text": "Turn on notifications from SILive.com",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "si_bell",
            "secondary_text": null,
            "type": "dialog4"
        },
        "ecd5852c-2bb6-4c6d-99c7-118520aad12a": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:51",
                    "username": "THEHULK"
                },
                "id": "ecd5852c-2bb6-4c6d-99c7-118520aad12a",
                "impressions": null,
                "name": "MI News Free Trial Offer",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "subscription-v2",
                "schema_version": 1,
                "type": "subscription",
                "version": "1"
            },
            "primary_button": {
                "title": "Subscribe now",
                "url": null
            },
            "primary_image_url": "mi_logo",
            "primary_text": "Incredible Offer\r\nToday Only",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_text": "We hope you\u2019ve enjoyed our gift of free access to subscriber exclusive content and would like to extend you this one-day-only offer to become a subscriber at our lowest price ever.",
            "type": "subscription"
        },
        "ed92b812-e4c2-4cfb-8291-6be929feb1ae": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:44",
                    "username": "Craig Thomas"
                },
                "id": "ed92b812-e4c2-4cfb-8291-6be929feb1ae",
                "impressions": null,
                "name": "Free Trial Access",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "trial-v1",
                "schema_version": 1,
                "type": "free_trial_access",
                "version": "1"
            },
            "primary_text": "Subscriber Exclusive",
            "secondary_text": "As our gift to you, we\u2019re providing limited-time access to subscriber exclusive articles like this.",
            "type": "free_trial_access"
        },
        "f7345365-2fcc-447c-95f1-6cd3c97ea8f1": {
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:53:46",
                    "username": "Craig Thomas"
                },
                "id": "f7345365-2fcc-447c-95f1-6cd3c97ea8f1",
                "impressions": null,
                "name": "OR - Free Trial Offer",
                "percentage": null,
                "prerequisites": [
                    "user_not_subscribed"
                ],
                "reporting_id": "subscription-v2",
                "schema_version": 1,
                "type": "subscription",
                "version": "1"
            },
            "primary_button": {
                "title": "Subscribe now",
                "url": null
            },
            "primary_image_url": "or_logo",
            "primary_text": "Incredible Offer\r\nToday Only",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_text": "We hope you\u2019ve enjoyed our gift of free access to subscriber exclusive content and would like to extend you this one-day-only offer to become a subscriber at our lowest price ever.",
            "type": "subscription"
        },
        "f92e8c64-f498-4f7a-b751-0a8622e1065e": {
            "bullets": [
                {
                    "icon": "tick",
                    "text": "Breaking news"
                },
                {
                    "icon": "tick",
                    "text": "Weather"
                },
                {
                    "icon": "tick",
                    "text": "Traffic"
                },
                {
                    "icon": "tick",
                    "text": "And more"
                }
            ],
            "campaign": {
                "audit": {
                    "updated": "2021-12-08.21:54:04",
                    "username": "William Goodman"
                },
                "id": "f92e8c64-f498-4f7a-b751-0a8622e1065e",
                "impressions": null,
                "name": "CL News Notifications",
                "percentage": null,
                "prerequisites": [
                    "notifications_not_enabled"
                ],
                "reporting_id": "notifications-v1",
                "schema_version": 1,
                "type": "dialog4",
                "version": "1"
            },
            "primary_button": {
                "title": "Turn on notifications",
                "url": "permissions://notifications"
            },
            "primary_image_url": "cl_logo",
            "primary_text": "Turn on notifications from cleveland.com",
            "secondary_button": {
                "title": "No thank you",
                "url": null
            },
            "secondary_image_url": "cl_bell",
            "secondary_text": null,
            "type": "dialog4"
        }
    },
    "last_updated": "2024-02-06.13:12:49"
}
        """.trimIndent()
        val serialized = CampaignIdAdapter().fromJson(mockResponseBody)
        // Act
        val data = serialized?.data?.map {
            Campaign(
                it.value.type?.name ?: "",
                it.key,
                it.value.campaign?.name!!,
                it.value.surveyId,
                it.value.surveyVersion,
                it.value.primaryImageURL,
                it.value.secondaryImageURL,
                it.value.primaryButton?.title,
                it.value.secondaryButton?.title,
                it.value.primaryText,
                it.value.secondaryText,
                it.value.bullets?.map { it.toBullet() },
                it.value.primaryButton?.url,
                it.value.secondaryButton?.url,
                it.value.campaign?.version,
                it.value.campaign?.reportingID ?: "",
                it.value.introScreenRemote?.let { it.toScreen() },
                it.value.outroScreenRemote?.let { it.toScreen() },
            )
        }

        // Assert
        assertEquals(32, data?.size)
        val campaign = data?.first()
        assertEquals("Subscription", campaign?.type)
        assertEquals("078aa8a3-8aff-4b92-b2ac-6ca6a7f436c4", campaign?.id)
        assertEquals("SI News Free Trial Offer", campaign?.name)
        // ... continue with other assertions for all fields
    }
}
