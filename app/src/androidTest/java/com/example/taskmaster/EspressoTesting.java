package com.example.taskmaster;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.espresso.contrib.RecyclerViewActions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class EspressoTesting {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testAddTask(){
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView2)).check(matches(withText("Add Task")));
    }

    @Test
    public void assertTextChanged() {

        onView(withId(R.id.button7)).perform(click());
        onView(withId(R.id.editTextTextPersonName3)).perform(typeText("Azzam"), closeSoftKeyboard());
        onView(withId(R.id.button8)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("Azzam's Task")));
    }

    @Test
    public void testOpenTaskDetail(){

        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.textView7)).check(matches(withText("Germany")));
        onView(withId(R.id.textView8)).check(matches(withText("Review")));
        onView(withId(R.id.textView10)).check(matches(withText("Do")));
    }

    @Test
    public void addTaskAndCheckItInTheList(){

        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.editTextTextPersonName2)).perform(typeText("ReactJS"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPersonName3)).perform(typeText("Build a new project"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPersonName)).perform(typeText("New"), closeSoftKeyboard());
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(withId(R.id.textView7)).check(matches(withText("ReactJS")));
        onView(withId(R.id.textView8)).check(matches(withText("Build a new project")));
        onView(withId(R.id.textView10)).check(matches(withText("New")));
    }
}
