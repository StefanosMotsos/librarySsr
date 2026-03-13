package cf.library.libraryapp.repository;

import cf.library.libraryapp.model.Capability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapabilityRepository extends JpaRepository<Capability, Long> {
}
