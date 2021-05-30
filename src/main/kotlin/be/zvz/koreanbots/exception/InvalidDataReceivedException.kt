package be.zvz.koreanbots.exception

class InvalidDataReceivedException(cause: Throwable) :
    RuntimeException("Received data is invalid. This may be an passing API error. Please leave an issue if continues.", cause)
