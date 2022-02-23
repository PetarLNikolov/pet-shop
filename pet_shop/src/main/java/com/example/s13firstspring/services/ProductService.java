package com.example.s13firstspring.services;


import com.example.s13firstspring.controllers.ImageController;
import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.entities.*;
import com.example.s13firstspring.models.repositories.*;
import com.example.s13firstspring.models.dtos.ProductAddDTO;
import com.example.s13firstspring.models.dtos.ProductResponseDTO;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ImageController imageController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private DiscountRepository discountRepository;

    public ProductResponseDTO add(ProductAddDTO product) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if (repository.findByName(product.getName()) != null) {
            throw new BadRequestException("Product name is taken");
        }
        if (product.getPrice() < 0) {
            throw new BadRequestException("Product price is lower than 0");
        }
        if (product.getUnitsInStock() < 0) {
            throw new BadRequestException("Product entity count lower than 0");
        }
        Product p = mapper.map(product, Product.class);
        Animal a = animalRepository.findById(product.getAnimalId()).orElseThrow(() -> new NotFoundException("Animal not found"));
        Subcategory s = subcategoryRepository.findById(product.getSubcategoryId()).orElseThrow(() -> new NotFoundException("Subcategory not found"));
        Brand b = brandRepository.findById(product.getBrandId()).orElseThrow(() -> new NotFoundException("Brand not found"));
        Discount d = discountRepository.findById(product.getDiscountId()).orElseThrow(() -> new NotFoundException("Discount not found"));
        p.setAnimal(a);
        p.setSubcategory(s);
        p.setBrand(b);
        p.setDiscount(d);
        if (p.getDiscount() != null) {
            p.setDiscountPrice(p.getPrice() * (100 - p.getDiscount().getPercentDiscount()) / 100);
        }
        repository.save(p);
        return mapper.map(p, ProductResponseDTO.class);
    }

    public ProductResponseDTO getByName(String name) {
        if (name == null || name.length() > 1000) {
            throw new BadRequestException("Name is mandatory and has less than 1000 characters");
        }

        Product product = repository.findByNameContaining(name.replaceAll("\\s+", ""));
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        return mapper.map(product, ProductResponseDTO.class);
    }

    public String delete(int id) {
        Product p = getById(id);
        String name = p.getName();
        repository.delete(p);
        return name;
    }

    public Integer addToStock(int id, int units) {
        Product p = repository.getById(id);
        int updatedUnits = p.getUnitsInStock() + units;
        if (updatedUnits > 1000) {
            throw new BadRequestException("Exceeded stock space for product: " + p.getName() + " (over 1000 units)");
        }
        p.setUnitsInStock(updatedUnits);
        repository.save(p);
        return p.getUnitsInStock();
    }

    public Integer removeFromStock(int id, int units) {
        Product p = repository.getById(id);
        int updatedUnits = p.getUnitsInStock() - units;
        if (updatedUnits < 0) {
            throw new BadRequestException("Not enough units of product: " + p.getName() + "" +
                    "Units in stock: " + p.getUnitsInStock());
        }
        p.setUnitsInStock(updatedUnits);
        repository.save(p);
        return p.getUnitsInStock();
    }


    public ProductResponseDTO edit(Product p) {
        Product p1 = repository.getById(p.getId());
        p1.setDiscountPrice(p.getPrice() * (100 - p.getDiscount().getPercentDiscount()) / 100);
        repository.save(p1);
        return mapper.map(p1, ProductResponseDTO.class);
    }

    @SneakyThrows
    public String uploadImage(MultipartFile file, HttpServletRequest request, int productID) {
        //By default, Java supports only these five formats for images: JPEG, PNG, BMP, WEBMP, GIF and exception handler
        // gets the msg from the null pointer exception if the file is not supported by java

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = System.nanoTime() + "." + extension;
        Files.copy(file.getInputStream(), new File("images" + File.separator + name).toPath());
        Product p = getById(productID);
        Image i = imageController.add(name, p);
        p.getImages().add(i);
        //repository.save(p);
        return i.getImage_URL();
    }

    public List<ProductResponseDTO> getAllByPrice() {
        List<Product> productsOriginal = repository.findAllByOrderByPriceDesc();
        List<ProductResponseDTO> productsNew = new ArrayList<>();
        for (Product product : productsOriginal) {
            productsNew.add(mapper.map(product, ProductResponseDTO.class));
        }
        return productsNew;
    }

    public int addToFavourites(int productId, int userId) {
        Product p = getById(productId);
        User u = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        if (u.getFavouriteProducts().contains(p)) {
            throw new BadRequestException("Product is already in favourites");
        }
        p.getFans().add(u);
        repository.save(p);
        return p.getFans().size();
    }

    public ProductResponseDTO findProductById(int id) {
        System.out.println("id is" + id);
        return mapper.map(getById(id), ProductResponseDTO.class);
    }

    //UTILITY
    private Product getById(int id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Product not found"));
    }

    public ProductResponseDTO setDiscount(int productId, int discountId) {
        Product p = repository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        p.setDiscount(discountRepository.findById(discountId).orElseThrow(() -> new NotFoundException("Discount not found")));
        return mapper.map(repository.save(p),ProductResponseDTO.class);
    }

}
