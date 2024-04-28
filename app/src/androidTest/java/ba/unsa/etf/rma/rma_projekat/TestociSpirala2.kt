package ba.unsa.etf.rma.rma_projekat
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class TestoviSpirala2 {

    @get:Rule
    var activityRule = ActivityScenarioRule(NovaBiljkaActivity::class.java)

    @Test
    fun Test1() {
        onView(withId(R.id.nazivET)).perform(scrollTo(), replaceText(""), closeSoftKeyboard())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.nazivET)).perform(scrollTo()).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }

    @Test
    fun Test2() {
        onView(withId(R.id.nazivET)).perform(replaceText("A"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.nazivET)).perform(scrollTo())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }

    @Test
    fun Test3() {
        onView(withId(R.id.nazivET)).perform(replaceText("AAAAAAAAAAAAAAAAAAAAA"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }

    @Test
    fun Test4() {
        onView(withId(R.id.nazivET)).perform(replaceText("AAA"))
        onView(withId(R.id.porodicaET)).perform(replaceText(""))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }

    @Test
    fun Test5() {
        onView(withId(R.id.nazivET)).perform(replaceText("AAA"))
        onView(withId(R.id.porodicaET)).perform(replaceText("A"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }

    @Test
    fun Test6() {
        onView(withId(R.id.nazivET)).perform(replaceText("AAA"))
        onView(withId(R.id.porodicaET)).perform(replaceText("AAAAAAAAAAAAAAAAAAAAA"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }

    @Test
    fun Test7() {
        onView(withId(R.id.nazivET)).perform(replaceText("AAA"))
        onView(withId(R.id.porodicaET)).perform(replaceText("AAA"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText(""))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }

    @Test
    fun Test8() {
        onView(withId(R.id.nazivET)).perform(replaceText("AAA"))
        onView(withId(R.id.porodicaET)).perform(replaceText("AAA"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("A"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }

    @Test
    fun Test9() {
        onView(withId(R.id.nazivET)).perform(replaceText("AAA"))
        onView(withId(R.id.porodicaET)).perform(replaceText("AAA"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("AAAAAAAAAAAAAAAAAAAAA"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo())
        onView(withId(R.id.dodajBiljkuBtn)).perform(click())
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Duzina texta mora biti izmedju 2 i 20!")))
    }

    @Test
    fun Test10() {
        onView(withId(R.id.jeloET)).perform(replaceText("Jelo"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).perform(scrollTo())
        onView(withId(R.id.jeloET)).perform(replaceText("Jelo2"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jelaLV)).perform(scrollTo())
        onView(withId(R.id.jelaLV)).check(matches(ViewMatchers.hasChildCount(2)))
    }
    @Test
    fun Test11() {
        onView(withId(R.id.jeloET)).perform(replaceText("Jelo"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jeloET)).perform(scrollTo())
        onView(withId(R.id.jeloET)).perform(replaceText("Jelo"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
        onView(withId(R.id.jelaLV)).check(matches(ViewMatchers.hasChildCount(1)))
    }

    @Test
    fun test12() {
        onView(withId(R.id.uslikajBiljkuBtn)).perform(scrollTo(),click())
        onView(withId(R.id.slikaIV)).perform(scrollTo())
        onView(withId(R.id.slikaIV)).check(matches(isDisplayed()))
    }

}