package be.zvz.koreanbots.exception

class NoDataReceivedException :
    NullPointerException("Request succeed, but no data received. This may be an passing API error. Please leave an issue if continues.")
