package com.example.demo.services_Impl;

import com.example.demo.dtos.OwnerDTO;
import com.example.demo.entities.OwnerEntity;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.OwnersRepository;
import com.example.demo.services.OwnersService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnersServiceImpl implements OwnersService {

    private final ModelMapper modelMapper;
    private final OwnersRepository ownersRepository;

    @Override
    public OwnerDTO createOwner(OwnerDTO ownerDTO) {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setName(ownerDTO.getName());
        ownersRepository.save(ownerEntity);
        return ownerDTO;
    }

    @Override
    public void deleteOwnerById(Integer id) {
        ownersRepository.deleteById(id);
    }

    @Override
    public OwnerDTO updateOwnerById(OwnerDTO ownerDTO, Integer id) {
        OwnerEntity owner = ownersRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Not found owner with id" + id));
        owner.setName(ownerDTO.getName());
        ownersRepository.save(owner);
        return ownerDTO;
    }

    @Override
    public OwnerDTO getOwnerById(Integer id) {
        OwnerEntity owner = ownersRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Not found owner with id" + id));
        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setName((owner.getName()));
        return ownerDTO;
    }

    @Override
    public String getLongestNameOfOwner(List<OwnerDTO> owners) {
        owners = ownersRepository.findAll().stream().map(entity -> modelMapper.map(entity, OwnerDTO.class))
                .collect(Collectors.toList());
        OwnerDTO max = Collections.max(owners, Comparator.comparing(ownerEntity -> ownerEntity.getName().length()));
        return max.getName();
    }
}