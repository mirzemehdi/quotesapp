package com.mmk.core.util

import platform.Foundation.NSUUID

actual fun randomUUIDString():String = NSUUID.UUID().UUIDString