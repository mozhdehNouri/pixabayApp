package com.example.navigation_lint

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

class CorrectNavigateRoute : Detector(), Detector.UastScanner {

    override fun getApplicableMethodNames(): List<String> = listOf("navigate", "composable")


    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {

        if (context.evaluator.isMemberInClass(method, "androidx.navigation.compose.composable") ||
            context.evaluator.isMemberInClass(method, "androidx. navigation. NavController")
        ) {


        }


    }


    companion object {
        /** Issue describing the problem and pointing to the detector implementation. */
        @JvmField
        val ISSUE: Issue =
            Issue.create(
                // ID: used in @SuppressLint warnings etc
                id = "NavigationRoute",
                // Title -- shown in the IDE's preference dialog, as category headers in the
                // Analysis results window, etc
                briefDescription = "Navigation Route",
                // Full explanation of the issue; you can use some markdown markup such as
                // `monospace`, *italic*, and **bold**.
                explanation =
                """
            You must attach your route to a composable function
          """, // no need to .trimIndent(), lint does that automatically
                category = Category.CUSTOM_LINT_CHECKS,
                priority = 6,
                severity = Severity.ERROR,
                implementation = Implementation(
                    CorrectNavigateRoute::class.java,
                    Scope.JAVA_FILE_SCOPE
                ),
            )
    }
}