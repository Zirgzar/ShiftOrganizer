package organizer.controller.model;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import organizer.entity.Employee;
import organizer.entity.Shift;
import organizer.entity.Store;

@Data
@NoArgsConstructor
public class StoreData {
	private Long storeId;
	private String storeName;
	private String storeAddress;
	private String storeCity;
	private String storeState;
	private String storeZip;
	private String storePhone;
	private Set<StoreEmployee> employees = new HashSet<>();
	private Set<StoreShift> shifts = new HashSet<>();

	public StoreData(Store store) {
		this.storeId = store.getStoreId();
		this.storeName = store.getStoreName();
		this.storeAddress = store.getStoreAddress();
		this.storeCity = store.getStoreCity();
		this.storeState = store.getStoreState();
		this.storeZip = store.getStoreZip();
		this.storePhone = store.getStorePhone();

		for (Employee employee : store.getEmployees()) {
			this.employees.add(new StoreEmployee(employee));
		}

		for (Shift shift : store.getShifts()) {
			this.shifts.add(new StoreShift(shift));
		}
	}

	public StoreData(Long storeId, String storeName, String storeAddress, String storeCity, String storeState,
			String storeZip, String storePhone) {
		this.storeId = storeId;
		this.storeName = storeName;
		this.storeAddress = storeAddress;
		this.storeCity = storeCity;
		this.storeState = storeState;
		this.storeZip = storeZip;
		this.storePhone = storePhone;
	}

	public Store toStore() {
		Store store = new Store();

		store.setStoreId(storeId);
		store.setStoreName(storeName);
		store.setStoreAddress(storeAddress);
		store.setStoreCity(storeCity);
		store.setStoreState(storeState);
		store.setStoreZip(storeZip);
		store.setStorePhone(storePhone);

		for (StoreEmployee employeeData : employees) {
			store.getEmployees().add(employeeData.toEmployee());
		}

		for (StoreShift shiftData : shifts) {
			store.getShifts().add(shiftData.toShift());
		}

		return store;
	}

	@Data
	static class StoreEmployee {
		private Long employeeId;
		private String firstName;
		private String lastName;
		private String phone;
		private BigDecimal pay;
		private Set<StoreShift> shifts = new HashSet<>();

		StoreEmployee(Employee employee) {
			this.employeeId = employee.getEmployeeId();
			this.firstName = employee.getFirstName();
			this.lastName = employee.getLastName();
			this.phone = employee.getPhone();
			this.pay = employee.getPay();
		}

		Employee toEmployee() {
			Employee employee = new Employee();

			employee.setEmployeeId(employeeId);
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employee.setPhone(phone);
			employee.setPay(pay);

			for (StoreShift storeShift : shifts) {
				employee.getShifts().add(storeShift.toShift());
			}

			return employee;
		}
	}

	@Data
	static class StoreShift {
		private Long shiftId;
		private String dayOfWeek;
		private LocalTime startTime;
		private LocalTime endTime;
		private Set<StoreEmployee> employees = new HashSet<>();

		StoreShift(Shift shift) {
			this.shiftId = shift.getShiftId();
			this.dayOfWeek = shift.getDayOfWeek();
			this.startTime = shift.getStartTime();
			this.endTime = shift.getEndTime();

			for (Employee employee : shift.getEmployees()) {
				this.employees.add(new StoreEmployee(employee));
			}
		}

		Shift toShift() {
			Shift shift = new Shift();

			shift.setShiftId(shiftId);
			shift.setDayOfWeek(dayOfWeek);
			shift.setStartTime(startTime);
			shift.setEndTime(endTime);

			return shift;
		}
	}
}
