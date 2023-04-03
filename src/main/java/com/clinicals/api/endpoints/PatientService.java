package com.clinicals.api.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.clinicals.api.model.ClinicalData;
import com.clinicals.api.model.Patient;
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
public class PatientService {
	
	@Autowired
	PatientRepo repo;
	
	@Path("/patients")
	@POST
	public Patient createPatient(Patient patient) {
		return repo.save(patient);
	}
	
	
	@Path("/patients/{id}")
	@GET
	public Patient getPatient(@PathParam("id") int id) {
		return repo.findById(id).get();
	}
	
	@Path("/patients")
	@GET
	public List<Patient> getPatients() {
		return repo.findAll();
	}
	
	@Path("/patients")
	@PUT
	public Response updatePatient(Patient patient) {
		Patient updatePatient = repo.save(patient);
		return Response.ok(updatePatient).build();
		
		
	}
	
	@Path("/patients/{id}")
	@DELETE
	public Response deletedPatient(@PathParam("id") int id) {
		repo.deleteById(id);
		return Response.ok(200).build();
	}
	
	@Path("/patients/analyze/{id}")
	@GET
	public Patient analyze(@PathParam("id") int id) {
		Patient patient = repo.findById(id).get();
		List<ClinicalData> clinicalData = new ArrayList<>(patient.getClinicalData());
		for(ClinicalData eachEntry : clinicalData) {
			
			if(eachEntry.getComponentName().equals("hw")) {
				String[] heigthAndWeigth = eachEntry.getComponentValue().split("/");
				String heigth = heigthAndWeigth[0];
				String weigth = heigthAndWeigth[1];
				
				float heigthInMeters = Float.parseFloat(heigth) * 0.4536F;
				Float bmi = Float.parseFloat(weigth)/(heigthInMeters * heigthInMeters);
				ClinicalData bmiData = new ClinicalData();
				bmiData.setComponentName("bmi");
				bmiData.setComponentValue(bmi.toString());
				patient.getClinicalData().add(bmiData);
			}
			
		}
		
		return patient;
	}

}
