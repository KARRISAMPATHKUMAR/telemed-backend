package com.telemedicine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.telemedicine.model.Appointment;
import com.telemedicine.repository.AppointmentRepository;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin("*")
public class AppointmentController {

    private final AppointmentRepository repository;

    public AppointmentController(
            AppointmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Appointment> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Appointment create(
            @RequestBody Appointment appointment) {
        return repository.save(appointment);
    }
    @PutMapping("/{id}")
    public Appointment update(@PathVariable Long id,
                              @RequestBody Appointment updatedAppointment) {

        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setPatientName(updatedAppointment.getPatientName());
        appointment.setDoctorName(updatedAppointment.getDoctorName());
        appointment.setAppointmentDate(updatedAppointment.getAppointmentDate());

        return repository.save(appointment);
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        repository.deleteById(id);

        return "Appointment deleted successfully";
    }
}