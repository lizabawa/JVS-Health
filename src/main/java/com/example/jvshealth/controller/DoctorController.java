package com.example.jvshealth.controller;

import com.example.jvshealth.models.Patient;
import com.example.jvshealth.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/doctors/")
public class DoctorController {

    private DoctorService doctorService;

//    private PatientService patientService;

//    private PrescriptionService prescriptionService;

    @Autowired
    public void setDoctorService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

//    @Autowired
//    public void setPatientService(PatientService patientService) {
//        this.patientService = patientService;
//    }
//
//    @Autowired
//    public void setPrescriptionService(PrescriptionService prescriptionService) {
//        this.prescriptionService = prescriptionService;
//    }

    static HashMap<String, Object> result = new HashMap<>();
    static HashMap<String, Object> message = new HashMap<>();

    @PostMapping(path = "/patients/")
    public ResponseEntity<?> createPatientDoctor(@RequestBody Patient patientObject) {
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
        Optional<Patient> patientOptional = doctorService.createPatientDoctor(doctorId, patientObject);
        if (patientOptional.isPresent()) {
            message.put("message", "success");
            message.put("data", patientOptional.get());
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "Patient already exists");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(path = "/patients/")
    public ResponseEntity<?> getAllPatients() {
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
        List<Patient> patientList = doctorService.getAllPatients(doctorId);
        if (patientList.isEmpty()) {
            message.put("message", "No patients found for doctor with id " + doctorId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", patientList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
    @GetMapping(path = "/patients/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable(value = "patientId") Long patientId) {
        Long doctorId = DoctorService.getCurrentLoggedInDoctor().getId();
       Optional<Patient> patientOptional = doctorService.getPatientById(doctorId, patientId);
        if (patientOptional.isEmpty()) {
            message.put("message", "No patient with patient id " + patientId + " not found" );
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", patientOptional);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
