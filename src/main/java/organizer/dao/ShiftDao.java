package organizer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import organizer.entity.Shift;

public interface ShiftDao extends JpaRepository<Shift, Long> {

}
