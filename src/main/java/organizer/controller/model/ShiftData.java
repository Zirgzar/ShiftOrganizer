package organizer.controller.model;

import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import organizer.entity.Shift;

@Data
@NoArgsConstructor
public class ShiftData {
	private Long shiftId;
	private String dayOfWeek;
	private LocalTime startTime;
	private LocalTime endTime;

	public ShiftData(Shift shift) {
		this.shiftId = shift.getShiftId();
		this.dayOfWeek = shift.getDayOfWeek();
		this.startTime = shift.getStartTime();
		this.endTime = shift.getEndTime();
	}

	public ShiftData(Long shiftId, String dayOfWeek, LocalTime startTime, LocalTime endTime) {
		this.shiftId = shiftId;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Shift toShift() {
		Shift shift = new Shift();

		shift.setShiftId(shiftId);
		shift.setDayOfWeek(dayOfWeek);
		shift.setStartTime(startTime);
		shift.setEndTime(endTime);

		return shift;
	}
}
