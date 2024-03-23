package organizer.controller.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import organizer.entity.Employee;
import organizer.entity.Shift;
import organizer.entity.Store;

@Data
@NoArgsConstructor
public class EmployeeData {
	private Long employeeId;
	private String firstName;
	private String lastName;
	private String phone;
	private BigDecimal pay;
	private EmployeeStore store;
	private Set<ShiftData> shifts = new HashSet<>();

	public EmployeeData(Employee employee) {
		this.employeeId = employee.getEmployeeId();
		this.firstName = employee.getFirstName();
		this.lastName = employee.getLastName();
		this.phone = employee.getPhone();
		this.pay = employee.getPay();
		this.store = new EmployeeStore(employee.getStore());

		for (Shift shift : employee.getShifts()) {
			this.shifts.add(new ShiftData(shift));
		}
	}

	public EmployeeData(Long employeeId, String firstName, String lastName, String phone, BigDecimal pay, Store store) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.pay = pay;
		this.store = new EmployeeStore(store);
	}

	public Employee toEmployee() {
		Employee employee = new Employee();

		employee.setEmployeeId(employeeId);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setPhone(phone);
		employee.setPay(pay);

		for (ShiftData shiftData : shifts) {
			employee.getShifts().add(shiftData.toShift());
		}

		return employee;
	}

	@Data
	public static class EmployeeStore {
		private Long storeId;
		private String storeName;
		private String storeAddress;
		private String storeCity;
		private String storeState;
		private String storeZip;
		private String storePhone;

		EmployeeStore(Store store) {
			this.storeId = store.getStoreId();
			this.storeName = store.getStoreName();
			this.storeAddress = store.getStoreAddress();
			this.storeCity = store.getStoreCity();
			this.storeState = store.getStoreState();
			this.storeZip = store.getStoreZip();
			this.storePhone = store.getStorePhone();
		}
	}

}
