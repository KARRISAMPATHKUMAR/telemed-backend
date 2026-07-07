package com.telemedicine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.telemedicine.model.Appointment;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {
}