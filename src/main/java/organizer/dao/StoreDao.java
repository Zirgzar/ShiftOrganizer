package organizer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import organizer.entity.Store;

public interface StoreDao extends JpaRepository<Store, Long> {

}
