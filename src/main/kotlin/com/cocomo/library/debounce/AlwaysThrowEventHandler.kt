package com.cocomo.library.debounce

import org.springframework.util.ErrorHandler

class AlwaysThrowErrorHandler : ErrorHandler {
    override fun handleError(t: Throwable) = throw t
}