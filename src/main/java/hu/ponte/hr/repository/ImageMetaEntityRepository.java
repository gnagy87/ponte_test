package hu.ponte.hr.repository;

import hu.ponte.hr.entity.ImageMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMetaEntityRepository extends JpaRepository<ImageMetaEntity, Long> {

}
