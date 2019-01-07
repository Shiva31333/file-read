package com.upload.utils

import org.slf4j.{ Logger, LoggerFactory }

class ResponseException(
  message: String = "Unable to process Request: ",
  cause: Throwable = None.orNull
)
    extends Exception(message, cause)
