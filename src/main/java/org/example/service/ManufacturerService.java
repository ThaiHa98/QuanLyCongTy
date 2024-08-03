package org.example.service;

import jakarta.transaction.Transactional;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.example.entity.Manufacturer;
import org.example.repository.ManufacturerRepository;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Optional<Manufacturer> getManufacturerById(Long id) {
        return manufacturerRepository.findById(id);
    }

    public Manufacturer addManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public Manufacturer updateManufacturer(Long id, Manufacturer manufacturerDetails) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Manufacturer not found"));

        manufacturer.setName(manufacturerDetails.getName());
        manufacturer.setOrigin(manufacturerDetails.getOrigin());
        manufacturer.setDescription(manufacturerDetails.getDescription());

        return manufacturerRepository.save(manufacturer);
    }

    @Transactional
    public void deleteManufacturer(Long id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Manufacturer not found"));

        if (!manufacturer.getProducts().isEmpty()) {
            throw new IllegalStateException("Cannot delete manufacturer with products");
        }

        manufacturerRepository.delete(manufacturer);
    }
}
