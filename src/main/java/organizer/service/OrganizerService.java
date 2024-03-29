package organizer.service;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import organizer.controller.model.EmployeeData;
import organizer.controller.model.ShiftData;
import organizer.controller.model.StoreData;
import organizer.dao.EmployeeDao;
import organizer.dao.ShiftDao;
import organizer.dao.StoreDao;
import organizer.entity.Employee;
import organizer.entity.Shift;
import organizer.entity.Store;

@Service
public class OrganizerService {
	@Autowired
	private StoreDao storeDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private ShiftDao shiftDao;

	@Transactional(readOnly = false)
	public StoreData saveStore(StoreData storeData) {
		Long storeId = storeData.getStoreId();
		Store store = findOrCreateStore(storeId, storeData);

		setFieldsInStore(store, storeData);

		return new StoreData(storeDao.save(store));
	}

	@Transactional(readOnly = false)
	public StoreData saveShiftToStore(StoreData storeData, Long shiftId) {
		Long storeId = storeData.getStoreId();
		Store store = findStoreById(storeId);
		Shift shift = findShiftById(shiftId);

		store.getShifts().add(shift);

		return new StoreData(storeDao.save(store));
	}

//	TODO
	@Transactional(readOnly = false)
	public StoreData saveEmployeeToShiftForStore(StoreData storeData, Long shiftId, Long employeeId) {
		Long storeId = storeData.getStoreId();
		Store store = findStoreById(storeId);
		Shift shift = findShiftById(shiftId);
		Employee employee = findEmployeeById(storeId, employeeId);

		shift.getEmployees().add(employee);
		store.getShifts().add(shift);
		store.getEmployees().add(employee);
		
		return new StoreData(storeDao.save(store));
	}

	private void setFieldsInStore(Store store, StoreData storeData) {
		store.setStoreName(storeData.getStoreName());
		store.setStoreAddress(storeData.getStoreAddress());
		store.setStoreCity(storeData.getStoreCity());
		store.setStoreState(storeData.getStoreState());
		store.setStoreZip(storeData.getStoreZip());
		store.setStorePhone(storeData.getStorePhone());
	}

	private Store findOrCreateStore(Long storeId, StoreData storeData) {
		Store store;

		if (Objects.isNull(storeId)) {
			store = storeData.toStore();
		} else {
			store = findStoreById(storeId);
		}

		return store;
	}

	private Store findStoreById(Long storeId) {
		return storeDao.findById(storeId)
				.orElseThrow(() -> new NoSuchElementException("Store with Id: '" + storeId + "' does not exist."));
	}

	@Transactional(readOnly = false)
	public EmployeeData saveEmployee(Long storeId, EmployeeData employeeData) {
		Store store = findStoreById(storeId);
		Long employeeId = employeeData.getEmployeeId();
		Employee employee = findOrCreateEmployee(storeId, employeeId, employeeData);

		setFieldsInEmployee(employee, employeeData);

		employee.setStore(store);
		store.getEmployees().add(employee);

		return new EmployeeData(employeeDao.save(employee));
	}

	@Transactional(readOnly = false)
	public EmployeeData saveShiftToEmployee(EmployeeData employeeData, Long shiftId) {
		Long employeeId = employeeData.getEmployeeId();
		Employee employee = findEmployeeById(employeeId);
		Shift shift = findShiftById(shiftId);

		employee.getShifts().add(shift);
		

		return new EmployeeData(employeeDao.save(employee));
	}

	private void setFieldsInEmployee(Employee employee, EmployeeData employeeData) {
		employee.setFirstName(employeeData.getFirstName());
		employee.setLastName(employeeData.getLastName());
		employee.setPhone(employeeData.getPhone());
		employee.setPay(employeeData.getPay());
	}

	private Employee findOrCreateEmployee(Long storeId, Long employeeId, EmployeeData employeeData) {
		Employee employee;

		if (Objects.isNull(employeeId)) {
			employee = employeeData.toEmployee();
		} else {
			employee = findEmployeeById(storeId, employeeId);
		}

		return employee;
	}

	private Employee findEmployeeById(Long storeId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId).orElseThrow(
				() -> new NoSuchElementException("Employee with Id: '" + employeeId + "' does not exist."));

		if (employee.getStore().getStoreId() == storeId) {
			return employee;
		} else {
			throw new IllegalArgumentException(
					"Employee with Id: '" + employeeId + "' already exists at store with Id: '" + storeId + "'.");
		}
	}

	private Employee findEmployeeById(Long employeeId) {
		return employeeDao.findById(employeeId).orElseThrow(
				() -> new NoSuchElementException("Employee with Id: '" + employeeId + "' does not exist."));
	}

	@Transactional(readOnly = false)
	public ShiftData saveShift(ShiftData shiftData) {
		Long shiftId = shiftData.getShiftId();
		Shift shift = findOrCreateShift(shiftId, shiftData);

		setFieldsInShift(shift, shiftData);

		return new ShiftData(shiftDao.save(shift));
	}

	private void setFieldsInShift(Shift shift, ShiftData shiftData) {
		shift.setDayOfWeek(shiftData.getDayOfWeek());
		shift.setStartTime(shiftData.getStartTime());
		shift.setEndTime(shiftData.getEndTime());
	}

	private Shift findOrCreateShift(Long shiftId, ShiftData shiftData) {
		Shift shift;

		if (Objects.isNull(shiftId)) {
			shift = shiftData.toShift();
		} else {
			shift = findShiftById(shiftId);
		}

		return shift;
	}

	private Shift findShiftById(Long shiftId) {
		return shiftDao.findById(shiftId)
				.orElseThrow(() -> new NoSuchElementException("Shift with Id: '" + shiftId + "' does not exist."));
	}

	public List<StoreData> retrieveAllStores() {
		return storeDao.findAll().stream().map(StoreData::new).toList();
	}

	public StoreData retrieveStoreById(Long storeId) {
		Store store = findStoreById(storeId);
		return new StoreData(store);
	}

	public List<ShiftData> retrieveStoreShifts(Long storeId) {
		Store store = findStoreById(storeId);
		List<Shift> shifts = store.getShifts();

		return shifts.stream().map(ShiftData::new).toList();
	}

	public List<EmployeeData> retrieveAllEmployees() {
		return employeeDao.findAll().stream().map(EmployeeData::new).toList();
	}

	public EmployeeData retrieveEmployeeById(Long employeeId) {
		Employee employee = findEmployeeById(employeeId);
		return new EmployeeData(employee);
	}

	public EmployeeData retrieveEmployeeById(Long storeId, Long employeeId) {
		Employee employee = findEmployeeById(storeId, employeeId);
		return new EmployeeData(employee);
	}

	@Transactional(readOnly = false)
	public void deleteEmployeeById(Long storeId, Long employeeId) {
		Employee employee = findEmployeeById(storeId, employeeId);
		employeeDao.delete(employee);
	}

	@Transactional(readOnly = false)
	public void deleteShiftById(Long shiftId) {
		Shift shift = findShiftById(shiftId);
		shiftDao.delete(shift);
	}

	@Transactional(readOnly = false)
	public void deleteShiftFromEmployee(Long shiftId, Long employeeId) {
		Shift shift = findShiftById(shiftId);
		Employee employee = findEmployeeById(employeeId);

		for (Iterator<Shift> s = employee.getShifts().iterator(); s.hasNext();) {
			Shift val = s.next();
			if (val.equals(shift)) {
				s.remove();
			}
		}

		employeeDao.save(employee);
	}

	@Transactional(readOnly = false)
	public void deleteShiftFromStore(Long shiftId, Long storeId) {
		Shift shift = findShiftById(shiftId);
		Store store = findStoreById(storeId);

		for (Iterator<Shift> s = store.getShifts().iterator(); s.hasNext();) {
			Shift val = s.next();
			if (val.equals(shift)) {
				s.remove();
			}
		}

		storeDao.save(store);
	}

//	TODO
	public void deleteEmployeeFromShiftForStore(StoreData storeData, Long shiftId, Long employeeId) {
		// TODO Auto-generated method stub
		
	}

}
