package guru.springframework.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
	
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public Set<UnitOfMeasure> listAllUoms() {
		Set<UnitOfMeasure> list = new HashSet<>();
		unitOfMeasureRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

}
