package com.clinicals.api.endpoints;

import java.util.List;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.beans.factory.annotation.Autowired;

import com.clinicals.api.dto.ClinicalDataRequest;
import com.clinicals.api.model.ClinicalData;
import com.clinicals.api.model.Patient;
import com.clinicals.api.repos.ClinicalDataRepo;
import com.clinicals.api.repos.PatientRepo;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Consumes("application/json")
@Produces("application/json")
@Path("/api")
@CrossOriginResourceSharing (allowAllOrigins = true)
public class ClinicalDataService {

	@Autowired
	PatientRepo patientRepo;
	ClinicalDataRepo clinicalDataRepo;
	
	
	@Path("/clinicals")
	@POST
	public ClinicalData saveClinicalData(ClinicalDataRequest request) {
		Patient patient = patientRepo.findById(request.getPatientId()).get();
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setPatient(patient);
		clinicalData.setComponentName(request.getComponentName());
		clinicalData.setComponentValue(request.getComponentValue());
		
		return clinicalDataRepo.save(clinicalData);
	}
	
	
	@Path("/patients/{id}")
	@GET	
	public Patient getPatient(@PathParam("id") int id) {
		return patientRepo.findById(id).get();
	}
	
	@Path("/patients")
	@GET
	public List<Patient> getPatients() {
		return patientRepo.findAll();
	}
	
	@Path("/patients")
	@PUT
	public Response updatePatient(Patient patient) {
		Patient updatePatient = patientRepo.save(patient);
		return Response.ok(updatePatient).build();
		
		
	}
	
	@Path("/patients/{id}")
	@DELETE
	public Response deletedPatient(@PathParam("id") int id) {
		patientRepo.deleteById(id);
		return Response.ok(200).build();
	}
	
	
	
}
