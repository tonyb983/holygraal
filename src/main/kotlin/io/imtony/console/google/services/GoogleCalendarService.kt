package io.imtony.console.google.services

import com.google.api.services.calendar.Calendar

interface GoogleCalendarService : GoogleService<Calendar> {

}

class GoogleCalendarInjectedService(serviceCreator: ServiceInitializer) : GoogleCalendarService, GenericInjectedService<Calendar>(
    lazy { serviceCreator.createCalendar() }
) {
}
