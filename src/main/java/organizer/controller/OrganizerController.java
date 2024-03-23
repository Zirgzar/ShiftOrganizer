package organizer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import organizer.controller.model.EmployeeData;
import organizer.controller.model.ShiftData;
import organizer.controller.model.StoreData;
import organizer.service.OrganizerService;

@RestController
@RequestMapping("/organizer")
@Slf4j
public class OrganizerController {
	@Autowired
	private OrganizerService organizerService;

//	CREATE

	@PostMapping("/store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public StoreData createStore(@RequestBody StoreData storeData) {
		log.info("Creating store {}", storeData);
		return organizerService.saveStore(storeData);
	}

	@PostMapping("/store/{storeId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EmployeeData createEmployee(@PathVariable Long storeId, @RequestBody EmployeeData employeeData) {
		log.info("Creating employee {} in store with Id: '{}'", employeeData, storeId);
		return organizerService.saveEmployee(storeId, employeeData);
	}

	@PostMapping("/shift")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ShiftData createShift(@RequestBody ShiftData shiftData) {
		log.info("Creating shift {}", shiftData);
		return organizerService.saveShift(shiftData);
	}

//	READ

	@GetMapping("/stores")
	public List<StoreData> retrieveAllStores() {
		log.info("Retrieving all stores");
		return organizerService.retrieveAllStores();
	}

	@GetMapping("/store/{storeId}")
	public StoreData retrieveStoreById(@PathVariable Long storeId) {
		log.info("Retrieving store with Id: '{}'", storeId);
		return organizerService.retrieveStoreById(storeId);
	}

	@GetMapping("/employees")
	public List<EmployeeData> retrieveAllEmployees() {
		log.info("Retrieving all employees");
		return organizerService.retrieveAllEmployees();
	}

	@GetMapping("/employees/{employeeId}")
	public EmployeeData retrieveEmployeeById(@PathVariable Long employeeId) {
		log.info("Retrieving employee with Id: '{}'", employeeId);
		return organizerService.retrieveEmployeeById(employeeId);
	}

	@GetMapping("/store/{storeId}/employee/{employeeId}")
	public EmployeeData retrieveStoreEmployeeById(@PathVariable Long storeId, @PathVariable Long employeeId) {
		log.info("Retrieving employee with Id: '{}' from store with Id: '{}'", employeeId, storeId);
		return organizerService.retrieveEmployeeById(storeId, employeeId);
	}

	@GetMapping("/store/{storeId}/shifts")
	public List<ShiftData> retrieveAllShiftsForStore(@PathVariable Long storeId) {
		log.info("Retrieving all shifts for store with Id: '{}'");
		return organizerService.retrieveStoreShifts(storeId);
	}

//	UPDATE
//	PUT

	@PutMapping("/store/{storeId}")
	public StoreData updateStore(@PathVariable Long storeId, @RequestBody StoreData storeData) {
		storeData.setStoreId(storeId);
		log.info("Updating store with Id: '{}'", storeId);
		return organizerService.saveStore(storeData);
	}

	@PutMapping("/store/{storeId}/employee/{employeeId}")
	public EmployeeData updateEmployee(@PathVariable Long storeId, @PathVariable Long employeeId,
			@RequestBody EmployeeData employeeData) {
		employeeData.setEmployeeId(employeeId);
		log.info("Updating employee with Id: '{}'", employeeId);
		return organizerService.saveEmployee(storeId, employeeData);
	}

	@PutMapping("/shift/{shiftId}")
	public ShiftData updateShift(@PathVariable Long shiftId, @RequestBody ShiftData shiftData) {
		shiftData.setShiftId(shiftId);
		log.info("Updating shift with Id: '{}'", shiftId);
		return organizerService.saveShift(shiftData);
	}

//	PATCH

	@PatchMapping("/store/{storeId}/shift/{shiftId}")
	public StoreData patchShiftToStore(@PathVariable Long storeId, @PathVariable Long shiftId,
			@RequestBody StoreData storeData) {
		storeData.setStoreId(storeId);
		log.info("Adding shift with Id: '{}' to store with Id: '{}'", shiftId, storeId);
		return organizerService.saveShiftToStore(storeData, shiftId);
	}

	@PatchMapping("/employees/{employeeId}/shift/{shiftId}")
	public EmployeeData patchShiftToEmployee(@PathVariable Long employeeId, @PathVariable Long shiftId,
			@RequestBody EmployeeData employeeData) {
		employeeData.setEmployeeId(employeeId);
		log.info("Adding shift availability with Id: '{}' to employee with Id: '{}'", shiftId, employeeId);
		return organizerService.saveShiftToEmployee(employeeData, shiftId);
	}

//	TODO
	@PatchMapping("/store/{storeId}/shift/{shiftId}/employee/{employeeId}")
	public StoreData patchEmployeeToShiftForStore(@PathVariable Long storeId, @PathVariable Long shiftId,
			@PathVariable Long employeeId, @RequestBody StoreData storeData) {
		storeData.setStoreId(storeId);
		log.info("Adding employee '{}' to shift '{}' for store '{}'");
		return organizerService.saveEmployeeToShiftForStore(storeData, shiftId, employeeId);
	}

}
