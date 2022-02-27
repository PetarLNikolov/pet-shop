package com.example.s13firstspring.services;


import com.example.s13firstspring.controllers.ImageController;
import com.example.s13firstspring.exceptions.BadRequestException;
import com.example.s13firstspring.exceptions.NotFoundException;
import com.example.s13firstspring.models.dtos.*;
import com.example.s13firstspring.models.dtos.products.ProductAddDTO;
import com.example.s13firstspring.models.dtos.products.ProductResponseDTO;
import com.example.s13firstspring.models.dtos.products.ProductWithIdAndName;
import com.example.s13firstspring.models.dtos.products.ProductWithUnitsAndName;
import com.example.s13firstspring.models.dtos.reviews.ReviewAddDTO;
import com.example.s13firstspring.models.dtos.reviews.ReviewResponseDTO;
import com.example.s13firstspring.models.dtos.users.UserWithNameAndIdDTO;
import com.example.s13firstspring.models.entities.*;
import com.example.s13firstspring.models.repositories.*;
import com.example.s13firstspring.services.utilities.SessionUtility;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;

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
    @Autowired
    private ReviewService reviewService;

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
        Product p = mapper.map(product, Product.class);//TODO optimize with 1 request to DB
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

    public Set<ProductResponseDTO> getByName(String name) {
        if (name == null || name.length() > 1000) {
            throw new BadRequestException("Name is mandatory and has less than 1000 characters");
        }

        Set<Product> products = repository.findAllByNameContaining(name.replaceAll("\\s+", ""));
        if (products == null) {
            throw new NotFoundException("Product not found");
        }
        Set<ProductResponseDTO> products1 = new HashSet<>();
        for (Product product : products) {
            products1.add(mapper.map(product, ProductResponseDTO.class));
        }
        return products1;
    }

    public String delete(int id) {
        Product p = getById(id);
        String name = p.getName();
        repository.delete(p);
        return name;
    }

    public ProductWithUnitsAndName addToStock(int id, int units) {
        Product p = repository.getById(id);
        int updatedUnits = p.getUnitsInStock() + units;
        if (updatedUnits > 1000) {
            throw new BadRequestException("Exceeded stock space for product: " + p.getName() + " (over 1000 units)");
        }
        p.setUnitsInStock(updatedUnits);

        return mapper.map(repository.save(p),ProductWithUnitsAndName.class);
    }

    //TODO add and remove are too similar optimize
    public ProductWithUnitsAndName removeFromStock(int id, int units) {
        Product p = repository.getById(id);
        int updatedUnits = p.getUnitsInStock() - units;
        if (updatedUnits < 0) {
            throw new BadRequestException("Not enough units of product: " + p.getName() + "" +
                    "Units in stock: " + p.getUnitsInStock());
        }
        p.setUnitsInStock(updatedUnits);
        return mapper.map(repository.save(p),ProductWithUnitsAndName.class);
    }


    public ProductResponseDTO edit(Product p) {
        Product p1 = repository.getById(p.getId());
        p1.setDiscountPrice(p.getPrice() * (100 - p.getDiscount().getPercentDiscount()) / 100);
        repository.save(p1);
        return mapper.map(p1, ProductResponseDTO.class);
    }

    @SneakyThrows
    public ImageWithoutProductDTO uploadImage(MultipartFile file, HttpServletRequest request, int productID) {
        //By default, Java supports only these five formats for images: JPEG, PNG, BMP, WEBMP, GIF and exception handler
        // gets the msg from the null pointer exception if the file is not supported by java
        //TODO MultipartResolver to filter size and type
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = System.nanoTime() + "." + extension;
        Files.copy(file.getInputStream(), new File("images" + File.separator + name).toPath());
        Product p = getById(productID);
        Image i = imageController.add(name, p);
        p.getImages().add(i);
        //repository.save(p);
        return mapper.map(i,ImageWithoutProductDTO.class);
    }

    public List<ProductResponseDTO> getAllByPrice() {
        List<Product> productsOriginal = repository.findAllByOrderByPriceDesc();
        List<ProductResponseDTO> productsNew = new ArrayList<>();
        for (Product product : productsOriginal) {
            productsNew.add(mapper.map(product, ProductResponseDTO.class));
        }
        return productsNew;
    }

    public int addToFavourites(int productId, HttpServletRequest request) {
        int userId = (int) request.getSession().getAttribute(SessionUtility.USER_ID);
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
        return mapper.map(getById(id), ProductResponseDTO.class);
    }

    //UTILITY
    private Product getById(int id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Product not found"));
    }

    public ProductResponseDTO setDiscount(int productId, int discountId) {
        Product p = repository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        Discount d = discountRepository.findById(discountId).orElseThrow(() -> new NotFoundException("Discount not found"));
        p.setDiscount(d);
        p.setDiscountPrice(p.getPrice() * (100 - d.getPercentDiscount()) / 100);
        return mapper.map(repository.save(p), ProductResponseDTO.class);
    }

    public Set<ProductResponseDTO> getTop10() {
        Set<ProductResponseDTO> products = new HashSet<>();
        repository.getTop10Products().stream().forEach((Product p) -> products.add(mapper.map(p, ProductResponseDTO.class)));
        return products;
    }

    @Transactional
    public ReviewResponseDTO addReview(int userId, int productId, int rating) {
        ReviewAddDTO review = new ReviewAddDTO();
        Product p = getById(productId);
        User u = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found")); //this probably never throws
        review.setProduct(p);
        review.setUser(u);
        review.setRating(rating);
        review.setCreatedAt(LocalDateTime.now());
        Review review1 = reviewService.addReview(review);
        p.getReviews().add(review1);

        ReviewResponseDTO r = new ReviewResponseDTO();
        r.setProduct(mapper.map(p, ProductWithIdAndName.class));
        UserWithNameAndIdDTO user = new UserWithNameAndIdDTO();
        user.setId(u.getId());
        user.setFullName(u.getFirstName() + u.getLastName());
        r.setId(review1.getId());
        r.setRating(review1.getRating());



        Integer i = p.getReviews().stream().map(Review::getRating).reduce(0, Integer::sum);
        int size = p.getReviews().size();
        p.setRating((double) ((i + r.getRating()) / size));
        repository.save(p);
        return r;
    }
}
