package com.example.navigation_lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.example.navigation_lint.CorrectNavigateRoute.Companion.ISSUE
import kotlin.reflect.KClass

class SampleIssueRegistry : IssueRegistry() {

  override val issues: List<Issue>
    get() = listOf(CorrectNavigateRoute.ISSUE)


  override val api: Int
    get() = CURRENT_API

}
